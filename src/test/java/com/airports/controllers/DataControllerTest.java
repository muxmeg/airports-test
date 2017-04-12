package com.airports.controllers;

import com.airports.services.AirportService;
import com.airports.services.CountryService;
import com.airports.services.RunwayService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DataControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AirportService airportService;
    @Mock
    private RunwayService runwayService;
    @Mock
    private CountryService countryService;

    @InjectMocks
    private DataController controller;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void reloadAirports() throws Exception {
        mockMvc.perform(post(DataController.REST_PATH + "/reloadAirports"))
                .andExpect(status().isOk()).andReturn();

        verify(airportService, times(1)).reloadAirports();
    }

    @Test
    public void reloadCountries() throws Exception {
        mockMvc.perform(post(DataController.REST_PATH + "/reloadCountries"))
                .andExpect(status().isOk()).andReturn();

        verify(countryService, times(1)).reloadCountries();
    }

    @Test
    public void reloadRunways() throws Exception {
        mockMvc.perform(post(DataController.REST_PATH + "/reloadRunways"))
                .andExpect(status().isOk()).andReturn();

        verify(runwayService, times(1)).reloadRunways();
    }

}