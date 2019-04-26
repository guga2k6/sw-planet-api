package br.com.acme.sw.apis.planetapi.model;

import br.com.acme.sw.apis.planetapi.entities.Planet;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PlanetRequest {

    @NotNull(message = "Nome do planeta é obrigatório")
    private String name;

    @NotNull(message = "Clima do planeta é obrigatório")
    private String climate;

    @NotNull(message = "Terreno do planeta é obrigatório")
    private String terrain;

    public Planet asEntity() {
        return Planet.builder()
            .name(name)
            .terrain(terrain)
            .climate(climate)
            .build();
    }
}
