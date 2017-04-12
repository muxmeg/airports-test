package com.airports.controllers;

import com.airports.model.Airport;
import com.airports.services.AirportService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AirportsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AirportService airportService;

    @InjectMocks
    private AirportsController controller;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void findByQuery() throws Exception {
        String testCountryCode = "testCountryCode";
        String testCountryName = "testCountryName";
        String testAirportsJson = "[{\"id\":1,\"ident\":\"ident\",\"type\":\"type\",\"name\":\"name\",\"latitudeDeg\":null,\"longitudeDeg\":null,\"elevationFt\":null,\"continent\":null,\"isoCountry\":null,\"isoRegion\":null,\"municipality\":null,\"scheduledService\":null,\"gpsCode\":null,\"iataCode\":null,\"localCode\":null,\"homeLink\":null,\"wikipediaLink\":null,\"keywords\":null,\"runways\":null},{\"id\":2,\"ident\":\"ident2\",\"type\":\"type2\",\"name\":\"name2\",\"latitudeDeg\":null,\"longitudeDeg\":null,\"elevationFt\":null,\"continent\":null,\"isoCountry\":null,\"isoRegion\":null,\"municipality\":null,\"scheduledService\":null,\"gpsCode\":null,\"iataCode\":null,\"localCode\":null,\"homeLink\":null,\"wikipediaLink\":null,\"keywords\":null,\"runways\":null}]";
        String testAirportsSublistJson = "[{\"id\":1,\"ident\":\"ident\",\"type\":\"type\",\"name\":\"name\",\"latitudeDeg\":null,\"longitudeDeg\":null,\"elevationFt\":null,\"continent\":null,\"isoCountry\":null,\"isoRegion\":null,\"municipality\":null,\"scheduledService\":null,\"gpsCode\":null,\"iataCode\":null,\"localCode\":null,\"homeLink\":null,\"wikipediaLink\":null,\"keywords\":null,\"runways\":null}]";
        List<Airport> testAirports = Arrays.asList(Airport.builder()
                .id(1).ident("ident").type("type").name("name").build(), Airport.builder()
                .id(2).ident("ident2").type("type2").name("name2").build());

        when(airportService.findAirportsByCountryName(testCountryName)).thenReturn(testAirports);
        when(airportService.findAirportsByCountryCode(testCountryCode)).thenReturn(testAirports.subList(0, 1));

        MvcResult result = mockMvc.perform(get(AirportsController.REST_PATH + "/query?countryName="
                + testCountryName)).andExpect(status().isOk()).andReturn();

        assertEquals(testAirportsJson, result.getResponse().getContentAsString());
        verify(airportService, times(1)).findAirportsByCountryName(testCountryName);
        verifyNoMoreInteractions(airportService);

        result = mockMvc.perform(get(AirportsController.REST_PATH + "/query?countryCode="
                + testCountryCode)).andExpect(status().isOk()).andReturn();

        assertEquals(testAirportsSublistJson, result.getResponse().getContentAsString());
        verify(airportService, times(1)).findAirportsByCountryCode(testCountryCode);
        verifyNoMoreInteractions(airportService);
    }

}