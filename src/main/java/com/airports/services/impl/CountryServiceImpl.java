package com.airports.services.impl;

import com.airports.model.Country;
import com.airports.repositories.AirportsRepository;
import com.airports.repositories.CountryRepository;
import com.airports.repositories.RunwayRepository;
import com.airports.services.AirportService;
import com.airports.services.CountryService;
import com.airports.services.RunwayService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {
    private static final Logger log = LoggerFactory.getLogger(AirportServiceImpl.class);

    private final CountryRepository countryRepository;
    private final RunwayRepository runwayRepository;
    private final AirportsRepository airportsRepository;
    private final AirportService airportService;
    private final RunwayService runwayService;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository, RunwayRepository runwayRepository,
                              AirportsRepository airportsRepository, AirportService airportService,
                              RunwayService runwayService) {
        this.countryRepository = countryRepository;
        this.runwayRepository = runwayRepository;
        this.airportsRepository = airportsRepository;
        this.airportService = airportService;
        this.runwayService = runwayService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadCountriesIfEmpty() throws IOException {
        if (!countryRepository.checkCountriesPopulated()) {
            loadCountries();
        } else {
            log.info("Countries are populated, load been skipped.");
        }
    }

    private void loadCountries() throws IOException {
        log.info("Countries load started.");

        Reader in = new FileReader(new ClassPathResource("csv/countries.csv").getFile());
        Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);

        int successfulRecords = 0;
        int failedRecords = 0;
        for (CSVRecord record : records) {
            try {
                Country country = Country.builder()
                        .id(Long.parseLong(record.get("id")))
                        .code(record.get("code"))
                        .name(record.get("name"))
                        .continent(record.get("continent"))
                        .wikipediaLink(record.get("wikipedia_link"))
                        .keywords(record.get("keywords"))
                        .build();

                countryRepository.addCountry(country);
                successfulRecords++;
            } catch (Exception e) {
                failedRecords++;
                log.error("Can't process the record!", e);
            }
        }
        log.info("Inserted {} countries, {} records failed.", successfulRecords, failedRecords);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reloadCountries() throws IOException {
        log.info("Removing old countries, airports and runways.");
        runwayRepository.deleteRunways();
        airportsRepository.deleteAirports();
        countryRepository.deleteCountries();

        loadCountries();
        airportService.loadAirports();
        runwayService.loadRunways();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<List<String>> findCountriesByAirportsCount(int amount) {
        log.debug("Finding {} countries with lowest/highest amount of airports.");
        List<String> highest = countryRepository.findTopCountriesByAirportsCount(false, amount);
        List<String> lowest = countryRepository.findTopCountriesByAirportsCount(true, amount);
        return Arrays.asList(highest, lowest);
    }
}
