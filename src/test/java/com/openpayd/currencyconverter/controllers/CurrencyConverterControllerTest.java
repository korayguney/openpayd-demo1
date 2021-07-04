package com.openpayd.currencyconverter.controllers;

import com.openpayd.currencyconverter.models.ConversionCurrency;
import com.openpayd.currencyconverter.models.Currency;
import com.openpayd.currencyconverter.services.CurrencyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyConverterControllerTest {

    @Mock
    CurrencyService mockCurrencyService;
    @InjectMocks
    CurrencyConverterController converterController;

    @Test
    void should_ReturnEquals_When_convertedValuesEqual() {
        // given
        Optional<Double> expected = Optional.of(1500.15);
        when(mockCurrencyService.convert(any())).thenReturn(expected);

        // when
        ConversionCurrency currency = new ConversionCurrency();
        currency.setFrom("USD");
        currency.setTo("TRY");
        currency.setValue(100);
        Double actual = converterController.convertCurrencies(currency).getBody();

        // then
        assertEquals(expected.get(), actual.doubleValue());
    }

    @Test
    void should_ReturnFalse_When_convertResultIsEmpty() {
        // given
        Optional<Double> expected = Optional.empty();
        when(mockCurrencyService.convert(any())).thenReturn(expected);

        // when
        ConversionCurrency currency = new ConversionCurrency();
        currency.setFrom("USD");
        currency.setTo("TRY");
        currency.setValue(100);
        ResponseEntity<Double> actual = converterController.convertCurrencies(currency);

        // then
        assertAll(
                () -> assertFalse(expected.isPresent()),
                () -> assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode())
        );
    }

    @Test
    void should_ReturnEquals_When_allCurrenciesAreFetched() {
        // given
        List<Currency> expected = Arrays.asList(
                new Currency("TRY", 4.5),
                new Currency("EUR", 2.4),
                new Currency("USD", 3.1));
        when(mockCurrencyService.getAllCurrencies()).thenReturn(expected);

        // when
        List<Currency> actual = converterController.getAllCurrencies().getBody();

        // then
        assertAll(
                () -> assertEquals(expected.size(), actual.size()),
                () -> assertEquals(expected.get(1).getName(), actual.get(1).getName())
        );
    }
}