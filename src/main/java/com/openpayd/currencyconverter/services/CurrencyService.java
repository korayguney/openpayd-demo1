package com.openpayd.currencyconverter.services;

import com.openpayd.currencyconverter.models.ConversionCurrency;
import com.openpayd.currencyconverter.models.ConversionCurrencyLogModel;
import com.openpayd.currencyconverter.models.Currency;
import com.openpayd.currencyconverter.models.TransactionType;
import com.openpayd.currencyconverter.repositories.ConversionCurrencyRepository;
import com.openpayd.currencyconverter.repositories.CurrencyRepository;
import com.openpayd.currencyconverter.utils.ClientRequestInfo;
import com.openpayd.currencyconverter.validators.CurrencyConverterValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private ConversionCurrencyRepository conversionCurrencyRepository;
    @Autowired
    private ClientRequestInfo clientRequestInfo;

    public List<Currency> getAllCurrencies() {
        List<Currency> currencyList = this.currencyRepository.findAll();
        currencyList.sort(Comparator.comparing(Currency::getName));
        return currencyList;
    }

    public Optional<Double> convert(ConversionCurrency conversionCurrency) {
        this.validateRequest(conversionCurrency);
        Optional<Currency> toOptional = this.currencyRepository.findByName(conversionCurrency.getTo().toUpperCase());
        Optional<Currency> fromOptional = this.currencyRepository.findByName(conversionCurrency.getFrom().toUpperCase());

        if (toOptional.isPresent() && fromOptional.isPresent()) {

            if (conversionCurrency.getValue() < 0) {
                return Optional.empty();
            }

            Currency to = toOptional.get();
            Currency from = fromOptional.get();
            Double toValue = to.getValueInEUR();
            Double fromValue = from.getValueInEUR();
            Double result = toValue * conversionCurrency.getValue() / fromValue;
            this.saveTransactionToDatabase(conversionCurrency, result);
            return Optional.of(result);
        }

        return Optional.empty();
    }

    private void validateRequest(ConversionCurrency conversionCurrency) {
        CurrencyConverterValidatorUtil.validateCurrencies(conversionCurrency.getFrom(), conversionCurrency.getTo());
        CurrencyConverterValidatorUtil.validateAmount(conversionCurrency.getValue());
    }

    private void saveTransactionToDatabase(ConversionCurrency conversionCurrency, Double result) {
        ConversionCurrencyLogModel model = new ConversionCurrencyLogModel();
        model.setCurrency_to(conversionCurrency.getTo());
        model.setCurrency_from(conversionCurrency.getFrom());
        model.setCurrency_value(conversionCurrency.getValue());
        model.setClientIpAdress(clientRequestInfo.getClientIpAdress());
        model.setClientUrl(clientRequestInfo.getClientIpAdress()+clientRequestInfo.getClientUrl());
        model.setClientSessionActivityId(clientRequestInfo.getSessionActivityId());
        model.setTransaction_date(LocalDate.now());
        if(conversionCurrency.getValue() == 1){
            model.setTransactionType(TransactionType.EXCHANGE_RATE);
        } else {
            model.setTransactionType(TransactionType.CONVERSION);
        }
        model.setConvert_result(result);
        this.conversionCurrencyRepository.save(model);
    }

    public Page<List<ConversionCurrencyLogModel>> getAllConversions(String transactionDate, Pageable pageable) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        CurrencyConverterValidatorUtil.validateTransactionDate(transactionDate, formatter);
        LocalDate  transaction_date = LocalDate.parse(transactionDate, formatter);
        return this.conversionCurrencyRepository.findAllByTransactionDate(transaction_date, pageable);
    }
}
