package com.openpayd.currencyconverter.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Transaction;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ConversionCurrencyLogModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String currency_from;
    private String currency_to;
    private double currency_value;
    private double convert_result;
    private String clientIpAdress;
    private String clientUrl;
    private String clientSessionActivityId;
    private LocalDate transaction_date;
    private TransactionType transactionType;
}
