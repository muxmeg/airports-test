package com.airports.services.impl;

import com.airports.model.Airport;
import com.airports.model.Runway;
import com.airports.repositories.AirportsRepository;
import com.airports.repositories.RunwayRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class AirportServiceImplTest {

    @Mock
    private AirportsRepository airportsRepository;
    @Mock
    private RunwayRepository runwayRepository;

    @InjectMocks
    private AirportServiceImpl airportService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findAirportsByCountryCode() throws Exception {
        String testCountryCode = "testCountryCode";
        List<Airport> testAirports = Arrays.asList(Airport.builder()
                .id(1).ident("ident").type("type").name("name").build(), Airport.builder()
                .id(2).ident("ident2").type("type2").name("name2").build());
        List<Runway> testRunways = Arrays.asList(Runway.builder().id(1)
                .airportRef(1).airportIdent("ident").heLongitudeDeg(33d).build(),
                Runway.builder().id(2).airportRef(1).airportIdent("ident").heLongitudeDeg(44d).build());

        when(airportsRepository.findAirportsLikeCountryCode(testCountryCode)).thenReturn(testAirports);
        when(runwayRepository.findByAirport(1)).thenReturn(testRunways);
        when(runwayRepository.findByAirport(2)).thenReturn(new ArrayList<>());

        List<Airport> airports = airportService.findAirportsByCountryCode(testCountryCode);

        assertEquals(2, airports.size());
        assertEquals(2, airports.get(0).getRunways().size());
        assertEquals(0, airports.get(1).getRunways().size());
    }

    @Test
    public void findAirportsByCountryName() throws Exception {
        String testCountryName = "testCountryName";
        List<Airport> testAirports = Arrays.asList(Airport.builder()
                .id(1).ident("ident").type("type").name("name").build(), Airport.builder()
                .id(2).ident("ident2").type("type2").name("name2").build());
        List<Runway> testRunways = Arrays.asList(Runway.builder().id(1)
                        .airportRef(1).airportIdent("ident").heLongitudeDeg(33d).build(),
                Runway.builder().id(2).airportRef(1).airportIdent("ident").heLongitudeDeg(44d).build());

        when(airportsRepository.findAirportsLikeCountryCode(testCountryName)).thenReturn(testAirports);
        when(runwayRepository.findByAirport(1)).thenReturn(testRunways);
        when(runwayRepository.findByAirport(2)).thenReturn(new ArrayList<>());

        List<Airport> airports = airportService.findAirportsByCountryCode(testCountryName);

        assertEquals(2, airports.size());
        assertEquals(2, airports.get(0).getRunways().size());
        assertEquals(0, airports.get(1).getRunways().size());
    }
}