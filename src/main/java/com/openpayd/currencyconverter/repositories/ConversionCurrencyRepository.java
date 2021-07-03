package com.openpayd.currencyconverter.repositories;


import com.openpayd.currencyconverter.models.ConversionCurrencyLogModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ConversionCurrencyRepository extends PagingAndSortingRepository<ConversionCurrencyLogModel, Long> {

    @Query("SELECT c FROM ConversionCurrencyLogModel c WHERE c.transaction_date= ?1")
    Page<List<ConversionCurrencyLogModel>> findAllByTransactionDate(LocalDate transaction_date, Pageable pageable);
}

