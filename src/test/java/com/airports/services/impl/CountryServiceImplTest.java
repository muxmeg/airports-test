package com.airports.services.impl;

import com.airports.repositories.CountryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class CountryServiceImplTest {

    @Mock
    private CountryRepository countryRepository;

    @InjectMocks
    private CountryServiceImpl countryService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findCountriesByAirportsCount() throws Exception {
        int testLimit = 5;
        List<String> testCountries1 = Arrays.asList("testCountry1", "testCountry2");
        List<String> testCountries2 = Arrays.asList("testCountry3", "testCountry4");

        when(countryRepository.findTopCountriesByAirportsCount(false, testLimit)).thenReturn(testCountries1);
        when(countryRepository.findTopCountriesByAirportsCount(true, testLimit)).thenReturn(testCountries2);

        List<List<String>> topBottomCountries = countryService.findCountriesByAirportsCount(testLimit);

        assertEquals(testCountries1, topBottomCountries.get(0));
        assertEquals(testCountries2, topBottomCountries.get(1));
    }
}