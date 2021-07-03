package com.openpayd.currencyconverter.exceptions;

public class ErrorMessageConstants {
    private ErrorMessageConstants(){}
    /*
        Currency Converter API Error Messages
     */
    public static final String CURRENCY_CONVERTER_CURRENCIES_EMPTY = "None of the currencies can be empty! Select both currencies.";
    public static final String CURRENCY_CONVERTER_CURRENCIES_EQUAL = "Both currency types are identical. Change one of them!";
    public static final String CURRENCY_CONVERTER_AMOUNT_IS_MINUS = "Amount value must be greater than zero!";
    public static final String CURRENCY_CONVERTER_AMOUNT_IS_EMPTY = "Amount value cannot be empty!";
    public static final String TRANSACTION_DATE_IS_WRONG = "Transaction date format is wrong! It must be such as '05/07/2021'";

}
