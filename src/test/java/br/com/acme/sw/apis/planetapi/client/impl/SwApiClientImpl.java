package br.com.acme.sw.apis.planetapi.client.impl;

import br.com.acme.sw.apis.planetapi.client.SwApiClient;
import br.com.acme.sw.apis.planetapi.client.model.SwPageDTO;
import br.com.acme.sw.apis.planetapi.client.model.SwPlanetDTO;
import br.com.six2six.fixturefactory.Fixture;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Primary
public class SwApiClientImpl implements SwApiClient {
    @Override
    public ResponseEntity<SwPageDTO<SwPlanetDTO>> findPlanets(String userAgent, Integer page, String name) {
        SwPageDTO pageDTO = Fixture.from(SwPageDTO.class).gimme("default");
        if ("Tatooine".equals(name)) {
            pageDTO = Fixture.from(SwPageDTO.class).gimme("tatooine");
        }

        return ResponseEntity.ok(pageDTO);
    }

    @Override
    public ResponseEntity<SwPlanetDTO> findPlanetById(String userAgent, Integer id) {
        SwPlanetDTO planetDTO = new SwPlanetDTO();
        if (id == 1) {
            planetDTO = Fixture.from(SwPlanetDTO.class).gimme("tattoine");
        }

        return ResponseEntity.ok(planetDTO);
    }
}
