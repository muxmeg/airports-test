package com.airports.repositories.impl;

import com.airports.model.Airport;
import com.airports.repositories.AirportsRepository;
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
public class AirportsRepositoryImpl implements AirportsRepository {

    private static final Logger log = LoggerFactory.getLogger(AirportsRepositoryImpl.class);

    private static final String INSERT_AIRPORT_SQL = "INSERT INTO airports (id, ident, type, name, latitude_deg, longitude_deg, elevation_ft," +
            "continent, iso_country, iso_region, municipality, scheduled_service, gps_code, iata_code, local_code," +
            "home_link, wikipedia_link, keywords) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String IS_EMPTY_SQL = "SELECT exists (select null from airports LIMIT 1)";
    private static final String DELETE_AIRPORTS = "DELETE FROM airports";
    private static final String FIND_AIRPORTS = "SELECT * FROM airports a INNER JOIN countries c ON" +
            " c.code = a.iso_country";
    private static final String FIND_AIRPORTS_LIKE_CODE = FIND_AIRPORTS + " WHERE LOWER(c.code) LIKE LOWER(?)";
    private static final String FIND_AIRPORTS_LIKE_NAME = FIND_AIRPORTS + " WHERE LOWER(c.name) LIKE LOWER(?)";

    private final JdbcTemplate jdbcTemplate;
    private final AirportMapper mapper = new AirportMapper();

    @Autowired
    public AirportsRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addAirport(Airport airport) {
        jdbcTemplate.update(INSERT_AIRPORT_SQL, airport.getId(), airport.getIdent(), airport.getType(), airport.getName(),
                airport.getLatitudeDeg(), airport.getLongitudeDeg(), airport.getElevationFt(), airport.getContinent(), airport.getIsoCountry(),
                airport.getIsoRegion(), airport.getMunicipality(), airport.getScheduledService(), airport.getGpsCode(),
                airport.getIataCode(), airport.getLocalCode(), airport.getHomeLink(), airport.getWikipediaLink(), airport.getKeywords());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkAirportsPopulated() {
        return jdbcTemplate.queryForObject(IS_EMPTY_SQL, Boolean.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Airport> findAirportsLikeCountryCode(String code) {
        return jdbcTemplate.query(FIND_AIRPORTS_LIKE_CODE, new Object[]{"%" + code + "%"}, mapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Airport> findAirportsLikeCountryName(String country) {
        return jdbcTemplate.query(FIND_AIRPORTS_LIKE_NAME, new Object[]{"%" + country + "%"}, mapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteAirports() {
        jdbcTemplate.update(DELETE_AIRPORTS);
    }

    private static class AirportMapper implements RowMapper<Airport> {

        @Override
        public Airport mapRow(ResultSet resultSet, int i) throws SQLException {
            return Airport.builder().id(resultSet.getLong("id")).continent(resultSet.getString("continent"))
                    .elevationFt(resultSet.getInt("elevation_ft")).keywords(resultSet.getString("keywords"))
                    .gpsCode(resultSet.getString("gps_code")).homeLink(resultSet.getString("home_link"))
                    .iataCode(resultSet.getString("iata_code")).isoCountry(resultSet.getString("iso_country"))
                    .ident(resultSet.getString("ident")).isoRegion(resultSet.getString("iso_region"))
                    .latitudeDeg(resultSet.getDouble("latitude_deg")).longitudeDeg(resultSet.getDouble("longitude_deg"))
                    .municipality(resultSet.getString("municipality")).type(resultSet.getString("type"))
                    .name(resultSet.getString("name")).scheduledService(resultSet.getString("scheduled_service"))
                    .build();
        }
    }
}
