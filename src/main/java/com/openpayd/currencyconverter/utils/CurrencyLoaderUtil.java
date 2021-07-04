package com.openpayd.currencyconverter.utils;

import com.openpayd.currencyconverter.models.Currency;
import com.openpayd.currencyconverter.models.CurrencyDTO;
import com.openpayd.currencyconverter.repositories.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

/**
 *  This component fetches and loads all currencies and their exchange rates in EUR
 *  to database during initialize of the application, then updates every 1 hour.
 */
@Component
public class CurrencyLoaderUtil {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Value("${fixer.io.url}")
    private String fixerIoUrl;

    @Value("${fixer.io.apiKey}")
    private String fixerIoApiKey;

    // Runs every 1 hour
    @Scheduled(fixedRate = 1000 * 60 * 60)
    private void getRatesOnline() {
        Flux<CurrencyDTO> currencyDTOFlux = WebClient.create().get().uri(urlGenerator()).retrieve().bodyToFlux(CurrencyDTO.class);
        currencyDTOFlux.subscribe(CurrencyDTO -> CurrencyDTO.getRates().forEach((key, value) -> {
            Currency currency = new Currency(key, value);
            this.currencyRepository.save(currency);
        }));
    }

    private String urlGenerator(){
        return fixerIoUrl + "?access_key=" + fixerIoApiKey;
    }
}
