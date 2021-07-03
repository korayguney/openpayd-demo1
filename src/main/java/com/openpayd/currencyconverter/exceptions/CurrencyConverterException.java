package com.openpayd.currencyconverter.exceptions;

public class CurrencyConverterException extends RuntimeException {

    public CurrencyConverterException(String message, Throwable cause) {
        super(message, cause);
    }

    public CurrencyConverterException(String message) {
        super(message);
    }

    public CurrencyConverterException(Throwable cause) {
        super(cause);
    }
}
