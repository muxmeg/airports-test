package com.airports.services.impl;

import com.airports.model.Airport;
import com.airports.model.Runway;
import com.airports.repositories.AirportsRepository;
import com.airports.repositories.RunwayRepository;
import com.airports.services.AirportService;
import com.airports.services.RunwayService;
import com.airports.utils.ParsingUtils;
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
import java.util.List;

@Service
public class AirportServiceImpl implements AirportService {

    private static final Logger log = LoggerFactory.getLogger(AirportServiceImpl.class);

    private final AirportsRepository airportsRepository;
    private final RunwayRepository runwayRepository;
    private final RunwayService runwayService;

    @Autowired
    public AirportServiceImpl(AirportsRepository airportsRepository, RunwayRepository runwayRepository,
                              RunwayService runwayService) {
        this.airportsRepository = airportsRepository;
        this.runwayRepository = runwayRepository;
        this.runwayService = runwayService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadAirportsIfEmpty() throws IOException {
        if (!airportsRepository.checkAirportsPopulated()) {
            loadAirports();
        } else {
            log.info("Airports are populated, load been skipped.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadAirports() throws IOException {
        log.info("Airports load started.");
        Reader in = new FileReader(new ClassPathResource("csv/airports.csv").getFile());
        Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);

        int successfulRecords = 0;
        int failedRecords = 0;
        for (CSVRecord record : records) {
            try {
                Airport airport = Airport.builder()
                        .id(Long.parseLong(record.get("id")))
                        .ident(record.get("ident"))
                        .type(record.get("type"))
                        .name(record.get("name"))
                        .isoCountry(record.get("iso_country"))
                        .isoRegion(record.get("iso_region"))
                        .municipality(record.get("municipality"))
                        .scheduledService(record.get("scheduled_service"))
                        .gpsCode(record.get("gps_code"))
                        .iataCode(record.get("iata_code"))
                        .localCode(record.get("local_code"))
                        .homeLink(record.get("home_link"))
                        .wikipediaLink(record.get("wikipedia_link"))
                        .keywords(record.get("keywords"))
                        .latitudeDeg(ParsingUtils.tryParseDouble("latitude_deg"))
                        .longitudeDeg(ParsingUtils.tryParseDouble("longitude_deg"))
                        .elevationFt(ParsingUtils.tryParseInteger("elevation_ft"))
                        .build();

                airportsRepository.addAirport(airport);
                successfulRecords++;
            } catch (Exception e) {
                failedRecords++;
                log.error("Can't process the record!", e);
            }
        }
        log.info("Inserted {} airports, {} records failed.", successfulRecords, failedRecords);
    }

    private List<Airport> populateWithRunways(List<Airport> airports) {
        for (Airport airport : airports) {
            List<Runway> runways = runwayRepository.findByAirport(airport.getId());
            airport.setRunways(runways);
        }
        return airports;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Airport> findAirportsByCountryCode(String countryCode) {
        log.debug("Searching for airports by countryCode {}", countryCode);
        List<Airport> airports = airportsRepository.findAirportsLikeCountryCode(countryCode);
        return populateWithRunways(airports);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Airport> findAirportsByCountryName(String countryName) {
        log.debug("Searching for airports by countryName {}", countryName);
        List<Airport> airports = airportsRepository.findAirportsLikeCountryName(countryName);
        return populateWithRunways(airports);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reloadAirports() throws IOException {
        log.debug("Removing old airports and runways.");
        runwayRepository.deleteRunways();
        airportsRepository.deleteAirports();
        loadAirports();
        runwayService.loadRunways();
    }
}
