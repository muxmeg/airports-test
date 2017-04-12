package com.airports.controllers;

import com.airports.model.Airport;
import com.airports.services.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.airports.App.REST_SERVICE_PREFIX;

@RestController
@RequestMapping(value = AirportsController.REST_PATH, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
public class AirportsController {
    static final String REST_PATH = REST_SERVICE_PREFIX + "airports";

    private final AirportService airportService;

    @Autowired
    public AirportsController(AirportService airportService) {
        this.airportService = airportService;
    }

    /**
     * Find airports by chosen filter.
     *
     * @param countryName countryName filter.
     * @param countryCode countryCode filter.
     * @return list of airports.
     */
    @RequestMapping(value = "query", method = RequestMethod.GET)
    public List<Airport> findByQuery(@RequestParam(required = false) String countryName,
                                     @RequestParam(required = false) String countryCode) {
        if (countryName != null) {
            return airportService.findAirportsByCountryName(countryName);
        } else if (countryCode != null) {
            return airportService.findAirportsByCountryCode(countryCode);
        }
        return new ArrayList<>();
    }
}