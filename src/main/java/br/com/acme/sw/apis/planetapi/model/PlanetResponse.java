package br.com.acme.sw.apis.planetapi.model;

import br.com.acme.sw.apis.planetapi.entities.Planet;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlanetResponse {

    private Long id;
    private String name;
    private String terrain;
    private String climate;
    private Integer filmAppearances;

    public PlanetResponse(Planet planet) {
        this.id = planet.getId();
        this.name = planet.getName();
        this.climate = planet.getClimate();
        this.terrain = planet.getTerrain();
        this.filmAppearances = planet.getFilmAppearances();
    }
}
