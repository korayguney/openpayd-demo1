package com.openpayd.currencyconverter.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversionCurrency {

    private String from;
    private String to;
    private double value;

}
