package com.airports.controllers;

import com.airports.services.AirportService;
import com.airports.services.CountryService;
import com.airports.services.RunwayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static com.airports.App.REST_SERVICE_PREFIX;

@RestController
@RequestMapping(value = DataController.REST_PATH, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
public class DataController {
    static final String REST_PATH = REST_SERVICE_PREFIX + "data/";

    private final CountryService countryService;
    private final AirportService airportService;
    private final RunwayService runwayService;

    @Autowired
    public DataController(CountryService countryService, AirportService airportService,
                          RunwayService runwayService) {
        this.countryService = countryService;
        this.airportService = airportService;
        this.runwayService = runwayService;
    }

    /**
     * Remove runways from repository and load them again from csv.
     *
     * @throws IOException if csv file with data is unavailable.
     */
    @RequestMapping(value = "reloadRunways", method = RequestMethod.POST)
    public void reloadRunways() throws IOException {
        runwayService.reloadRunways();
    }

    /**
     * Remove countries from repository and load them again from csv.
     *
     * @throws IOException if csv file with data is unavailable.
     */
    @RequestMapping(value = "reloadCountries", method = RequestMethod.POST)
    public void reloadCountries() throws IOException {
        countryService.reloadCountries();
    }

    /**
     * Remove airports from repository and load them again from csv.
     *
     * @throws IOException if csv file with data is unavailable.
     */
    @RequestMapping(value = "reloadAirports", method = RequestMethod.POST)
    public void reloadAirports() throws IOException {
        airportService.reloadAirports();
    }
}
