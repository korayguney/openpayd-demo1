package com.openpayd.currencyconverter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CurrencyConverterWebController {

    @GetMapping("/")
    public String serveWebPage() {
        return "index";
    }
}
