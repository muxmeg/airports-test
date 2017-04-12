package com.airports.services;

import java.io.IOException;
import java.util.List;

public interface CountryService {

    /**
     * Load records to country repository if it's empty.
     *
     * @throws IOException if csv file with data is unavailable.
     */
    void loadCountriesIfEmpty() throws IOException;

    /**
     * Remove countries from repository and load them again from csv.
     *
     * @throws IOException if csv file with data is unavailable.
     */
    void reloadCountries() throws IOException;

    /**
     * Find countries by highest/lowest airports count.
     *
     * @param amount amount of records to get for each collection.
     * @return two lists of country names - highest and lowest respectively.
     */
    List<List<String>> findCountriesByAirportsCount(int amount);
}
