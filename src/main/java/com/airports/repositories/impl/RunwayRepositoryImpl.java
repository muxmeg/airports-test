package com.airports.repositories.impl;

import com.airports.model.Airport;
import com.airports.model.Runway;
import com.airports.repositories.RunwayRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RunwayRepositoryImpl implements RunwayRepository {
    private static final Logger log = LoggerFactory.getLogger(RunwayRepositoryImpl.class);

    private static final String INSERT_RUNWAY_SQL = "INSERT INTO runways (id, airport_ref, airport_ident, length_ft," +
            "width_ft, surface, lighted, closed, le_ident, le_latitude_deg, le_longitude_deg, le_elevation_ft," +
            "le_heading_degT, he_displaced_threshold_ft) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String IS_EMPTY_SQL = "SELECT exists (select null from runways LIMIT 1)";
    private static final String DELETE_RUNWAYS = "DELETE FROM runways";
    private static final String MOST_COMMON_RUNWAYS = "SELECT le_ident as ident, count(*) as count FROM runways GROUP BY" +
            " le_ident ORDER BY count(*) DESC LIMIT %d";
    private static final String RUNWAY_TYPES = "SELECT DISTINCT c.name as countryName, r.surface as runwayType " +
            "FROM countries c INNER JOIN airports a ON c.code = a.iso_country INNER JOIN runways r " +
            "ON r.airport_ref=a.id";
    private static final String FIND_BY_AIRPORT = "SELECT * FROM runways WHERE airport_ref = ?";

    private final JdbcTemplate jdbcTemplate;
    private final RunwayMapper mapper = new RunwayMapper();
    private final TypesByCountriesExtractor typesExtractor = new TypesByCountriesExtractor();

    @Autowired
    public RunwayRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * {@inheritDoc}
     */
    public void addRunway(Runway runway) {
        jdbcTemplate.update(INSERT_RUNWAY_SQL, runway.getId(), runway.getAirportRef(), runway.getAirportIdent(),
                runway.getLengthFt(), runway.getWidthFt(), runway.getSurface(), runway.getLighted(), runway.getClosed(),
                runway.getLeIdent(), runway.getLeLatitudeDeg(), runway.getLeLongitudeDeg(), runway.getLeElevationFt(),
                runway.getLeHeadingDegT(), runway.getHeDisplacedThresholdFt());
    }

    /**
     * {@inheritDoc}
     */
    public boolean checkRunwaysPopulated() {
        return jdbcTemplate.queryForObject(IS_EMPTY_SQL, Boolean.class);
    }

    /**
     * {@inheritDoc}
     */
    public List<Runway> findByAirport(long airportId) {
        return jdbcTemplate.query(FIND_BY_AIRPORT, new Object[]{airportId}, mapper);
    }

    /**
     * {@inheritDoc}
     */
    public Map<String, List<String>> findRunwayTypesByCountries() {
        return jdbcTemplate.query(RUNWAY_TYPES, typesExtractor);
    }

    /**
     * {@inheritDoc}
     */
    public List<String> findCommonRunwayIdentifications(int limit) {
        String query = String.format(MOST_COMMON_RUNWAYS, limit);
        return jdbcTemplate.query(query, (rs, rowNum) -> rs.getString("ident"));
    }

    /**
     * {@inheritDoc}
     */
    public void deleteRunways() {
        jdbcTemplate.update(DELETE_RUNWAYS);
    }

    private static class RunwayMapper implements RowMapper<Runway> {
        @Override
        public Runway mapRow(ResultSet rs, int i) throws SQLException {
            return Runway.builder().airportIdent(rs.getString("airport_ident"))
                    .closed(rs.getBoolean("closed")).airportRef(rs.getLong("airport_ref"))
                    .heDisplacedThresholdFt(rs.getInt("he_displaced_threshold_ft"))
                    .heElevationFt(rs.getInt("he_elevation_ft"))
                    .heHeadingDegT(rs.getDouble("he_heading_degT"))
                    .heIdent(rs.getString("he_ident"))
                    .id(rs.getLong("id"))
                    .lighted(rs.getBoolean("lighted"))
                    .lengthFt(rs.getInt("length_ft"))
                    .surface(rs.getString("surface"))
                    .heLatitudeDeg(rs.getDouble("he_latitude_deg"))
                    .heLongitudeDeg(rs.getDouble("he_longitude_deg"))
                    .widthFt(rs.getInt("width_ft"))
                    .build();
        }
    }

    private static class TypesByCountriesExtractor implements ResultSetExtractor<Map<String, List<String>>> {
        @Override
        public Map<String, List<String>> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<String, List<String>> result = new HashMap<>();
            while (rs.next()) {
                String country = rs.getString("countryName");
                List<String> runways = result.computeIfAbsent(country, k -> new ArrayList<>());
                runways.add(rs.getString("runwayType"));
            }
            return result;
        }
    }
}
