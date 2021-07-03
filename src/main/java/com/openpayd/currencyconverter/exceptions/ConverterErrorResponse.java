package com.openpayd.currencyconverter.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConverterErrorResponse {

    private int status;
    private String message;
    private long timeStamp;

}
