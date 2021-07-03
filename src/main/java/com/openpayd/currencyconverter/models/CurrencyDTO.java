package com.openpayd.currencyconverter.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyDTO {

    private boolean success;
    private long timestamp;
    private String base;
    private String date;
    private Map<String, Double> rates;

}
