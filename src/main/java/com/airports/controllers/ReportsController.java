package com.airports.controllers;

import com.airports.services.CountryService;
import com.airports.services.RunwayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.airports.App.REST_SERVICE_PREFIX;

@RestController
@RequestMapping(value = ReportsController.REST_PATH, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
public class ReportsController {
    static final String REST_PATH = REST_SERVICE_PREFIX + "reports";
    static final int TOP_LIST_LIMIT = 10; //can get it from request and keep 10 as default value

    private final CountryService countryService;
    private final RunwayService runwayService;

    @Autowired
    public ReportsController(CountryService countryService, RunwayService runwayService) {
        this.countryService = countryService;
        this.runwayService = runwayService;
    }

    /**
     * Get report of airports with lowest and highest countries count.
     *
     * @return json object with two collections of country names.
     */
    @RequestMapping(value = "/topCountriesByAirports", method = RequestMethod.GET)
    public Map<String, List<String>> getCountriesByAirports() {
        List<List<String>> countries = countryService.findCountriesByAirportsCount(TOP_LIST_LIMIT);
        Map<String, List<String>> result = new HashMap<>();
        result.put("highest", countries.get(0));
        result.put("lowest", countries.get(1));
        return result;
    }

    /**
     * Find report of runway types groped by countries.
     *
     * @return runways groped as Map<CountryName, List<RunwayType>>
     */
    @RequestMapping(value = "/runwayTypesPerCountry", method = RequestMethod.GET)
    public Map<String, List<String>> getRunwayTypesPerCountry() {
        return runwayService.findRunwayTypesByCountries();
    }

    /**
     * Find report of most used runway identifications.
     *
     * @return list of runway identifications.
     */
    @RequestMapping(value = "/commonLeIdent", method = RequestMethod.GET)
    public List<String> getCommonRunwayIdentifications() {
        return runwayService.findCommonRunwayIdentifications(TOP_LIST_LIMIT);
    }
}