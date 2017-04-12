package com.airports.services;

import com.airports.model.Airport;

import java.io.IOException;
import java.util.List;

public interface AirportService {

    /**
     * Load records to airport repository if it's empty.
     *
     * @throws IOException if csv file with data is unavailable.
     */
    void loadAirportsIfEmpty() throws IOException;

    /**
     * Load records to airport repository.
     *
     * @throws IOException if csv file with data is unavailable.
     */
    void loadAirports() throws IOException;

    /**
     * Find airports by country code.
     *
     * @param countryCode given target code.
     * @return list of airports.
     */
    List<Airport> findAirportsByCountryCode(String countryCode);

    /**
     * Find airports by country name.
     *
     * @param countryName given target name.
     * @return list of airports.
     */
    List<Airport> findAirportsByCountryName(String countryName);

    /**
     * Remove airports from repository and load them again from csv.
     *
     * @throws IOException if csv file with data is unavailable.
     */
    void reloadAirports() throws IOException;
}
