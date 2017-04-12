package com.airports.repositories;

import com.airports.model.Runway;

import java.util.List;
import java.util.Map;

public interface RunwayRepository {

    /**
     * Add runway record to repository.
     *
     * @param runway the record.
     */
    void addRunway(Runway runway);

    /**
     * Check if repository contains records.
     *
     * @return check result.
     */
    boolean checkRunwaysPopulated();

    /**
     * Find runways for given airport.
     *
     * @param airportId airport's id.
     * @return list of runways.
     */
    List<Runway> findByAirport(long airportId);

    /**
     * Find runway types groped by countries.
     *
     * @return runways groped as Map<CountryName, List<RunwayType>>
     */
    Map<String, List<String>> findRunwayTypesByCountries();

    /**
     * Find most used runway identifications.
     *
     * @param limit amount of records to get.
     * @return list of runway identifications.
     */
    List<String> findCommonRunwayIdentifications(int limit);

    /**
     * Clean repository from the runways.
     */
    void deleteRunways();
}
