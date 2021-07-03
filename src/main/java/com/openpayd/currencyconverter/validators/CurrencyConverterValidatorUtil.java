package com.openpayd.currencyconverter.validators;

import com.openpayd.currencyconverter.exceptions.CurrencyConverterException;
import com.openpayd.currencyconverter.exceptions.ErrorMessageConstants;
import com.openpayd.currencyconverter.exceptions.TransactionDateParseException;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CurrencyConverterValidatorUtil {

    private CurrencyConverterValidatorUtil() {
    }

    public static void validateCurrencies(String from, String to) {
        if (StringUtils.isEmpty(from) || StringUtils.isEmpty(to) || to.length() != 3 || from.length() != 3) {
            throw new CurrencyConverterException(ErrorMessageConstants.CURRENCY_CONVERTER_CURRENCIES_EMPTY);
        } else if (from.equals(to)) {
            throw new CurrencyConverterException(ErrorMessageConstants.CURRENCY_CONVERTER_CURRENCIES_EQUAL);
        }
    }

    public static void validateAmount(double value) {
        if (StringUtils.isEmpty(String.valueOf(value))) {
            throw new CurrencyConverterException(ErrorMessageConstants.CURRENCY_CONVERTER_AMOUNT_IS_EMPTY);
        } else if (value <= 0) {
            throw new CurrencyConverterException(ErrorMessageConstants.CURRENCY_CONVERTER_AMOUNT_IS_MINUS);
        }
    }

    public static void validateTransactionDate(String transactionDate, DateTimeFormatter formatter) {
        try {
            LocalDate.parse(transactionDate, formatter);
        } catch (DateTimeParseException ex){
            throw new TransactionDateParseException(ErrorMessageConstants.TRANSACTION_DATE_IS_WRONG);
        }
    }
}
