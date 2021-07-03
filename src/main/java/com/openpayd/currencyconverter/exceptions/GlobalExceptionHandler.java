package com.openpayd.currencyconverter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({CurrencyConverterException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ConverterErrorResponse> handleException(CurrencyConverterException exc) {
        ConverterErrorResponse error = prepareErrorResponse(HttpStatus.BAD_REQUEST, exc.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NoCurrenciesFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ConverterErrorResponse> handleException(NoCurrenciesFoundException exc) {
        ConverterErrorResponse error = prepareErrorResponse(HttpStatus.NOT_FOUND, exc.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({TransactionDateParseException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ConverterErrorResponse> handleException(TransactionDateParseException exc) {
        ConverterErrorResponse error = prepareErrorResponse(HttpStatus.BAD_REQUEST, exc.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    private ConverterErrorResponse prepareErrorResponse(HttpStatus httpStatus, String message) {
        ConverterErrorResponse error = new ConverterErrorResponse();
        error.setStatus(httpStatus.value());
        error.setMessage(message);
        error.setTimeStamp(System.currentTimeMillis());
        return error;
    }
}
