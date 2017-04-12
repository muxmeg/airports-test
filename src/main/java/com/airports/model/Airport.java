package com.airports.model;

import lombok.Data;
import lombok.experimental.Builder;

import java.util.List;

@Builder
@Data
public class Airport {
    private final long id;
    private final String ident;
    private final String type;
    private final String name;
    private final Double latitudeDeg;
    private final Double longitudeDeg;
    private final Integer elevationFt;
    private final String continent;
    private final String isoCountry;
    private final String isoRegion;
    private final String municipality;
    private final String scheduledService;
    private final String gpsCode;
    private final String iataCode;
    private final String localCode;
    private final String homeLink;
    private final String wikipediaLink;
    private final String keywords;

    private List<Runway> runways;
}