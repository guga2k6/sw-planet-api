package br.com.acme.sw.apis.planetapi.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
public class PlanetDTO {

    private String name;

    @JsonProperty("rotation_period")
    private String rotationPeriod;

    @JsonProperty("orbital_period")
    private String orbitalPeriod;

    private String diameter;

    private String climate;
    private String gravity;
    private String terrain;

    @JsonProperty("surface_water")
    private String surfaceWater;

    private String population;

    private List<String> residents;
    private List<String> films;

    private ZonedDateTime created;
    private ZonedDateTime edited;

    private String url;
}
