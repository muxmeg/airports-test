package com.airports.services.impl;

import com.airports.repositories.RunwayRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


public class RunwayServiceImplTest {

    @Mock
    private RunwayRepository runwayRepository;

    @InjectMocks
    private RunwayServiceImpl runwayService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findRunwayTypesByCountries() throws Exception {
        Map<String, List<String>> result = new HashMap<>();
        result.put("testCountry1", Arrays.asList("type1", "type2"));
        result.put("testCountry2", Arrays.asList("type3", "type4"));

        when(runwayRepository.findRunwayTypesByCountries()).thenReturn(result);

        Map<String, List<String>> runwayTypesByCountries = runwayService.findRunwayTypesByCountries();

        assertEquals(result, runwayTypesByCountries);
    }

    @Test
    public void findCommonRunwayIdentifications() throws Exception {
        int limit = 5;
        List<String> testIdents = Arrays.asList("ident1", "ident2");
        when(runwayRepository.findCommonRunwayIdentifications(limit)).thenReturn(testIdents);

        List<String> idents = runwayService.findCommonRunwayIdentifications(5);

        assertEquals(testIdents, idents);
    }

}