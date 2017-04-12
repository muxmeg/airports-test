package com.airports.services.impl;

import com.airports.model.Runway;
import com.airports.repositories.RunwayRepository;
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
import java.util.Map;

@Service
public class RunwayServiceImpl implements RunwayService {

    private static final Logger log = LoggerFactory.getLogger(AirportServiceImpl.class);

    private final RunwayRepository runwayRepository;

    @Autowired
    public RunwayServiceImpl(RunwayRepository runwayRepository) {
        this.runwayRepository = runwayRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadRunwaysIfEmpty() throws IOException {
        if (!runwayRepository.checkRunwaysPopulated()) {
            loadRunways();
        } else {
            log.info("Runways are populated, load been skipped.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadRunways() throws IOException {
        log.info("Runways load started.");
        Reader in = new FileReader(new ClassPathResource("csv/runways.csv").getFile());
        Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);

        int successfulRecords = 0;
        int failedRecords = 0;
        for (CSVRecord record : records) {
            try {
                Runway runway = Runway.builder()
                        .id(Long.parseLong(record.get("id")))
                        .airportRef(Long.parseLong(record.get("airport_ref")))
                        .airportIdent(record.get("airport_ident"))
                        .lengthFt(ParsingUtils.tryParseInteger(record.get("length_ft")))
                        .widthFt(ParsingUtils.tryParseInteger(record.get("width_ft")))
                        .surface(record.get("surface"))
                        .lighted(Boolean.parseBoolean(record.get("lighted")))
                        .closed(Boolean.parseBoolean(record.get("closed")))
                        .leIdent(record.get("le_ident"))
                        .leLatitudeDeg(ParsingUtils.tryParseDouble(record.get("le_latitude_deg")))
                        .leLongitudeDeg(ParsingUtils.tryParseDouble(record.get("le_longitude_deg")))
                        .leElevationFt(ParsingUtils.tryParseInteger(record.get("le_elevation_ft")))
                        .leHeadingDegT(ParsingUtils.tryParseDouble(record.get("le_heading_degT")))
                        .leDisplacedThresholdFt(ParsingUtils.tryParseInteger(record.get("le_displaced_threshold_ft")))
                        .heIdent(record.get("he_ident"))
                        .heLatitudeDeg(ParsingUtils.tryParseDouble(record.get("he_latitude_deg")))
                        .heLongitudeDeg(ParsingUtils.tryParseDouble(record.get("he_longitude_deg")))
                        .heElevationFt(ParsingUtils.tryParseInteger(record.get("he_elevation_ft")))
                        .heHeadingDegT(ParsingUtils.tryParseDouble(record.get("he_heading_degT")))
                        .heDisplacedThresholdFt(ParsingUtils.tryParseInteger(record.get("he_displaced_threshold_ft")))
                        .build();

                runwayRepository.addRunway(runway);
                successfulRecords++;
            } catch (Exception e) {
                failedRecords++;
                log.error("Can't process the record!", e);
            }
        }
        log.info("Inserted {} runways, {} records failed.", successfulRecords, failedRecords);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reloadRunways() throws IOException {
        log.info("Removing old runways.");
        runwayRepository.deleteRunways();

        loadRunways();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, List<String>> findRunwayTypesByCountries() {
        log.debug("Looking for runway types groped by countries.");
        return runwayRepository.findRunwayTypesByCountries();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> findCommonRunwayIdentifications(int limit) {
        log.debug("Looking for most common runway identifications.");
        return runwayRepository.findCommonRunwayIdentifications(limit);
    }
}