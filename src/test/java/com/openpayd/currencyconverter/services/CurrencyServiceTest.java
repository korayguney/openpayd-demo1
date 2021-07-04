package com.openpayd.currencyconverter.services;

import com.openpayd.currencyconverter.exceptions.CurrencyConverterException;
import com.openpayd.currencyconverter.exceptions.ErrorMessageConstants;
import com.openpayd.currencyconverter.exceptions.TransactionDateParseException;
import com.openpayd.currencyconverter.models.ConversionCurrency;
import com.openpayd.currencyconverter.models.Currency;
import com.openpayd.currencyconverter.repositories.CurrencyRepository;
import com.openpayd.currencyconverter.utils.ClientRequestInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceTest {

    @Mock
    CurrencyRepository mockCurrencyRepository;
    @Mock
    ClientRequestInfo mockClientRequestInfo;
    @InjectMocks
    CurrencyService currencyService;

    @Test
    void should_ReturnEquals_When_allCurrenciesAreFetched() {
        // given
        List<Currency> expected = Arrays.asList(
                new Currency("TRY", 4.5),
                new Currency("EUR", 2.4),
                new Currency("USD", 3.1));
        when(mockCurrencyRepository.findAll()).thenReturn(expected);

        // when
        List<Currency> actual = this.currencyService.getAllCurrencies();

        // then
        assertAll(
                () -> assertTrue(actual.size() == 3),
                () -> assertEquals(actual.get(0).getName(), "EUR"),
                () -> assertEquals(actual.get(1).getName(), "TRY"),
                () -> assertEquals(actual.get(2).getName(), "USD")
        );
    }

    @Test
    void should_ReturnTrue_When_convertResultIsNotEmpty() {
        // given
        Optional<Currency> expected1 = Optional.of(new Currency("TRY", 4.0));
        when(mockCurrencyRepository.findByName(eq("TRY"))).thenReturn(expected1);
        Optional<Currency> expected2 = Optional.of(new Currency("EUR", 2.0));
        when(mockCurrencyRepository.findByName(eq("EUR"))).thenReturn(expected2);
        when(mockClientRequestInfo.getClientIpAdress()).thenReturn(anyString());

        ConversionCurrency conversionCurrency = new ConversionCurrency("TRY", "EUR", 100);

        // when
        Optional<Double> actual = this.currencyService.convert(conversionCurrency);

        // then
        assertAll(
                () -> assertTrue(actual.isPresent()),
                () -> assertEquals(50.0, actual.get())
        );
    }

    @Test
    void should_throwsCurrencyConverterException_When_conversionCurrenciesAreEmpty() {
        // given
        ConversionCurrency conversionCurrency = new ConversionCurrency("", "", 100);

        // when
        Executable executable = () -> this.currencyService.convert(conversionCurrency);

        // then
        CurrencyConverterException exp = assertThrows(CurrencyConverterException.class, executable);
        assertTrue(exp.getMessage().equals(ErrorMessageConstants.CURRENCY_CONVERTER_CURRENCIES_EMPTY));
    }

    @Test
    void should_throwsCurrencyConverterException_When_conversionCurrenciesAreIdentical() {
        // given
        ConversionCurrency conversionCurrency = new ConversionCurrency("EUR", "EUR", 100);

        // when
        Executable executable = () -> this.currencyService.convert(conversionCurrency);

        // then
        CurrencyConverterException exp = assertThrows(CurrencyConverterException.class, executable);
        assertTrue(exp.getMessage().equals(ErrorMessageConstants.CURRENCY_CONVERTER_CURRENCIES_EQUAL));
    }

    @ParameterizedTest
    @ValueSource(doubles = {0, -100, -0.5})
    void should_throwsCurrencyConverterException_When_conversionCurrencyValueIsZeroOrMinus(Double value) {
        // given
        ConversionCurrency conversionCurrency = new ConversionCurrency("EUR", "TRY", value);

        // when
        Executable executable = () -> this.currencyService.convert(conversionCurrency);

        // then
        CurrencyConverterException exp = assertThrows(CurrencyConverterException.class, executable);
        assertTrue(exp.getMessage().equals(ErrorMessageConstants.CURRENCY_CONVERTER_AMOUNT_IS_MINUS));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1","21.10.2022",""})
    void should_throwsTransactionDateParseException_When_transactionDateFormatIsWrong(String transaction_date) {
        // when
        Executable executable = () -> this.currencyService.getAllConversions(transaction_date, Pageable.unpaged());

        // then
        TransactionDateParseException exp = assertThrows(TransactionDateParseException.class, executable);
        assertTrue(exp.getMessage().equals(ErrorMessageConstants.TRANSACTION_DATE_IS_WRONG));
    }
}