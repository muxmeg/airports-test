package com.airports.model;

import lombok.Data;
import lombok.experimental.Builder;

import java.util.List;

@Builder
@Data
public class Country {
    private final long id;
    private final String code;
    private final String name;
    private final String continent;
    private final String wikipediaLink;
    private final String keywords;

    private final List<Airport> airports;
}
