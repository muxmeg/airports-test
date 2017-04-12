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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReportsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AirportService airportService;
    @Mock
    private RunwayService runwayService;
    @Mock
    private CountryService countryService;

    @InjectMocks
    private ReportsController controller;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getCountriesByAirports() throws Exception {
        List<List<String>> airports = Arrays.asList(Arrays.asList("testCountry1", "testCountry2"),
                Arrays.asList("testCountry3", "testCountry4"));
        String testResult = "{\"highest\":[\"testCountry1\",\"testCountry2\"],\"lowest\":[\"testCountry3\",\"testCountry4\"]}";

        when(countryService.findCountriesByAirportsCount(ReportsController.TOP_LIST_LIMIT)).thenReturn(airports);

        MvcResult mvcResult = mockMvc.perform(get(ReportsController.REST_PATH + "/topCountriesByAirports"))
                .andExpect(status().isOk()).andReturn();

        assertEquals(testResult, mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void getRunwayTypesPerCountry() throws Exception {
        Map<String, List<String>> result = new HashMap<>();
        result.put("testCountry1", Arrays.asList("type1", "type2"));
        result.put("testCountry2", Arrays.asList("type3", "type4"));
        String testResult = "{\"testCountry2\":[\"type3\",\"type4\"],\"testCountry1\":[\"type1\",\"type2\"]}";

        when(runwayService.findRunwayTypesByCountries()).thenReturn(result);

        MvcResult mvcResult = mockMvc.perform(get(ReportsController.REST_PATH + "/runwayTypesPerCountry"))
                .andExpect(status().isOk()).andReturn();

        assertEquals(testResult, mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void getCommonRunwayIdentifications() throws Exception {
        List<String> testIdents = Arrays.asList("ident1", "ident2");
        String testResult = "[\"ident1\",\"ident2\"]";

        when(runwayService.findCommonRunwayIdentifications(ReportsController.TOP_LIST_LIMIT)).thenReturn(testIdents);

        MvcResult mvcResult = mockMvc.perform(get(ReportsController.REST_PATH + "/commonLeIdent"))
                .andExpect(status().isOk()).andReturn();

        assertEquals(testResult, mvcResult.getResponse().getContentAsString());
    }

}