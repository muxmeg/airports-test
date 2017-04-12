CREATE TABLE IF NOT EXISTS countries (
  id BIGINT NOT NULL,
  code VARCHAR(4) NOT NULL,
  name VARCHAR(50) NOT NULL,
  continent VARCHAR(4) NOT NULL,
  wikipedia_link VARCHAR,
  keywords VARCHAR,
  PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS airports (
  id BIGINT NOT NULL,
  ident VARCHAR(50),
  type VARCHAR(50),
  name VARCHAR(100),
  latitude_deg DOUBLE,
  longitude_deg DOUBLE,
  elevation_ft INTEGER,
  continent VARCHAR(6),
  iso_country VARCHAR(4) NOT NULL,
  iso_region VARCHAR(8),
  municipality VARCHAR(100),
  scheduled_service VARCHAR(50),
  gps_code VARCHAR(6),
  iata_code VARCHAR(6),
  local_code VARCHAR(6),
  home_link VARCHAR,
  wikipedia_link VARCHAR,
  keywords VARCHAR,
  PRIMARY KEY (id),
  FOREIGN KEY (iso_country) references countries (code)
);


CREATE TABLE IF NOT EXISTS runways (
  id BIGINT NOT NULL,
  airport_ref BIGINT NOT NULL,
  airport_ident VARCHAR(50) NOT NULL,
  length_ft INTEGER,
  width_ft INTEGER,
  surface VARCHAR,
  lighted BOOLEAN,
  closed BOOLEAN,
  le_ident VARCHAR(10),
  le_latitude_deg DOUBLE,
  le_longitude_deg DOUBLE,
  le_elevation_ft INTEGER,
  le_heading_degT DOUBLE,
  le_displaced_threshold_ft INTEGER,
  he_ident VARCHAR(10),
  he_latitude_deg DOUBLE,
  he_longitude_deg DOUBLE,
  he_elevation_ft INTEGER,
  he_heading_degT DOUBLE,
  he_displaced_threshold_ft INTEGER,
  PRIMARY KEY (id),
  FOREIGN KEY (airport_ref) REFERENCES airports (id)
);