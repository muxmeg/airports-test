package com.airports;

import com.airports.services.AirportService;
import com.airports.services.CountryService;
import com.airports.services.RunwayService;
import com.airports.services.impl.AirportServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class StartupListener {
    private static final Logger log = LoggerFactory.getLogger(AirportServiceImpl.class);

    private final AirportService airportService;
    private final CountryService countryService;
    private final RunwayService runwayService;

    @Autowired
    public StartupListener(AirportService airportService, CountryService countryService,
                           RunwayService runwayService) {
        this.airportService = airportService;
        this.countryService = countryService;
        this.runwayService = runwayService;
    }

    @PostConstruct
    public void init() {
        try {
            countryService.loadCountriesIfEmpty();
        } catch (IOException e) {
            log.error("Can't load the countries!", e);
        }
        try {
            airportService.loadAirportsIfEmpty();
        } catch (IOException e) {
            log.error("Can't load the airports!", e);
        }
        try {
            runwayService.loadRunwaysIfEmpty();
        } catch (IOException e) {
            log.error("Can't load the airports!", e);
        }
        log.info("Data initialization completed.");
    }
}