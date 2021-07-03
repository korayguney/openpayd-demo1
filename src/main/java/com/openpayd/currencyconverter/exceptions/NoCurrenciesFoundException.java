package com.openpayd.currencyconverter.exceptions;

public class NoCurrenciesFoundException extends RuntimeException {

    public NoCurrenciesFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoCurrenciesFoundException(String message) {
        super(message);
    }

    public NoCurrenciesFoundException(Throwable cause) {
        super(cause);
    }
}
