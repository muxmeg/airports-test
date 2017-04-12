package com.airports.services;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface RunwayService {

    /**
     * Load records to runways repository if it's empty.
     *
     * @throws IOException if csv file with data is unavailable.
     */
    void loadRunwaysIfEmpty() throws IOException;

    /**
     * Load records to runways repository.
     *
     * @throws IOException if csv file with data is unavailable.
     */
    void loadRunways() throws IOException;

    /**
     * Remove runways from repository and load them again from csv.
     *
     * @throws IOException if csv file with data is unavailable.
     */
    void reloadRunways() throws IOException;

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
}
