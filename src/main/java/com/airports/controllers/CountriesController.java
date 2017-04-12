package com.airports.controllers;

import com.airports.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.airports.App.REST_SERVICE_PREFIX;

@RestController
@RequestMapping(value = CountriesController.REST_PATH, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
public class CountriesController {
    static final String REST_PATH = REST_SERVICE_PREFIX + "countries";

    private final CountryService countryService;

    @Autowired
    public CountriesController(CountryService countryService) {
        this.countryService = countryService;
    }

}