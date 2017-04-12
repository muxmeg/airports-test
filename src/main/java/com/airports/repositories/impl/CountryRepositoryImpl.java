package com.airports.repositories.impl;

import com.airports.model.Country;
import com.airports.repositories.CountryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CountryRepositoryImpl implements CountryRepository {

    private static final Logger log = LoggerFactory.getLogger(AirportsRepositoryImpl.class);

    private static final String INSERT_COUNTRY_SQL = "INSERT INTO countries (id, code, name, continent, wikipedia_link," +
            " keywords) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String DELETE_COUNTRIES = "DELETE FROM countries";
    private static final String IS_EMPTY_SQL = "SELECT exists (select null from countries LIMIT 1)";
    private static final String FIND_TOP_COUNTRIES_BY_AIRPORTS_COUNT = "SELECT c.name as name, count(*) as count FROM " +
            "countries c INNER JOIN airports a ON c.code = a.iso_country GROUP BY c.name ORDER BY count %s limit %d";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CountryRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * {@inheritDoc}
     */
    public void addCountry(Country country) {
        jdbcTemplate.update(INSERT_COUNTRY_SQL, country.getId(), country.getCode(), country.getName(),
                country.getContinent(), country.getWikipediaLink(), country.getKeywords());
    }

    /**
     * {@inheritDoc}
     */
    public boolean checkCountriesPopulated() {
        return jdbcTemplate.queryForObject(IS_EMPTY_SQL, Boolean.class);
    }

    /**
     * {@inheritDoc}
     */
    public void deleteCountries() {
        jdbcTemplate.update(DELETE_COUNTRIES);
    }

    /**
     * {@inheritDoc}
     */
    public List<String> findTopCountriesByAirportsCount(boolean ascending, int limit) {
        String query = String.format(FIND_TOP_COUNTRIES_BY_AIRPORTS_COUNT, ascending ? "ASC" : "DESC", limit);
        return jdbcTemplate.query(query, (rs, rowNum) -> rs.getString("name"));
    }

}
