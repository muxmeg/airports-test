package com.airports.repositories;

import com.airports.model.Country;
import com.airports.repositories.impl.AirportsRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CountryRepository {

    /**
     * Add country record to repository.
     *
     * @param country record to add.
     */
    void addCountry(Country country);

    /**
     * Check if repository contains records.
     *
     * @return check result.
     */
    boolean checkCountriesPopulated();

    /**
     * Clean repository from the countries.
     */
    void deleteCountries();

    /**
     * Find countries by airport count.
     *
     * @param ascending whether countries with highest or lowest count should be taken.
     * @param limit amount of records to get.
     * @return list of country names.
     */
    List<String> findTopCountriesByAirportsCount(boolean ascending, int limit);
}
