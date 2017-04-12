package com.airports.model;

import lombok.Data;
import lombok.experimental.Builder;

@Builder
@Data
public class Runway {
    private final long id;
    private final long airportRef;
    private final String airportIdent;
    private final Integer lengthFt;
    private final Integer widthFt;
    private final String surface;
    private final Boolean lighted;
    private final Boolean closed;
    private final String leIdent;
    private final Double leLatitudeDeg;
    private final Double leLongitudeDeg;
    private final Integer leElevationFt;
    private final Double leHeadingDegT;
    private final Integer leDisplacedThresholdFt;
    private final String heIdent;
    private final Double heLatitudeDeg;
    private final Double heLongitudeDeg;
    private final Integer heElevationFt;
    private final Double heHeadingDegT;
    private final Integer heDisplacedThresholdFt;
}
