package com.airports.repositories;

import com.airports.model.Airport;

import java.util.List;

public interface AirportsRepository {

    /**
     * Add airport record to repository.
     *
     * @param airport the record.
     */
    void addAirport(Airport airport);

    /**
     * Check if repository contains records.
     *
     * @return check result.
     */
    boolean checkAirportsPopulated();

    /**
     * Find airports by country code.
     *
     * @param countryCode country code.
     * @return list of airports.
     */
    List<Airport> findAirportsLikeCountryCode(String countryCode);

    /**
     * Find airports by country name.
     *
     * @param countryName country name.
     * @return list of airports.
     */
    List<Airport> findAirportsLikeCountryName(String countryName);

    /**
     * Clean repository from the airports.
     */
    void deleteAirports();
}
