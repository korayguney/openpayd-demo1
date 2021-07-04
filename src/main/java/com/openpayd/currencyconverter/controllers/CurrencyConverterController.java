package com.openpayd.currencyconverter.controllers;

import com.openpayd.currencyconverter.models.ConversionCurrency;
import com.openpayd.currencyconverter.models.ConversionCurrencyLogModel;
import com.openpayd.currencyconverter.models.Currency;
import com.openpayd.currencyconverter.services.CurrencyService;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CurrencyConverterController {

    CurrencyService currencyService;

    public CurrencyConverterController(CurrencyService currencyService) {
       this.currencyService = currencyService;
    }

    /**
     * This method ensures result for both 'Exchange Rate API' and 'Conversion API'
     * The default calculated amount for the 'Exchange Rate API' will be 1,
     *
     * @param  conversionCurrency
     * @return calculated amount or exchange rate in ResponseEntity<Double> type
     */
    @PostMapping(value = "/convert-currencies", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Double> convertCurrencies(@RequestBody ConversionCurrency conversionCurrency) {
        Optional<Double> resultOptional = this.currencyService.convert(conversionCurrency);
        if (resultOptional.isPresent()) {
            return new ResponseEntity<>(resultOptional.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * This method fetches all currencies and their values in EUR by default
     *
     * @return currencies and exchange rate list in ResponseEntity<List<Currency>> type
     */
    @GetMapping(value = "/currencies", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Currency>> getAllCurrencies() {
        return new ResponseEntity<>(this.currencyService.getAllCurrencies(), HttpStatus.OK);
    }

    /**
     * This method fetches list of conversions filtered by the transaction date
     *
     * @param  transactionDate Transaction date in dd/MM/YYYY format
     * @param  pageable default size is 10
     * @return requested conversion lists in ResponseEntity<List<ConversionCurrencyLogModel>> type
     */
    @GetMapping(value = "/conversions", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<List<ConversionCurrencyLogModel>>> getAllConversions(
            @ApiParam(value = "transaction date for query conversions", example = "e.g. 05/07/2021", required = true)
            @RequestParam String transactionDate, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        return new ResponseEntity<>(this.currencyService.getAllConversions(transactionDate, pageable), HttpStatus.OK);
    }

}
