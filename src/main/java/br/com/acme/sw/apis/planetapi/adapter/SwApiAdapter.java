package br.com.acme.sw.apis.planetapi.adapter;

import br.com.acme.sw.apis.planetapi.client.model.SwPageDTO;
import br.com.acme.sw.apis.planetapi.client.model.SwPlanetDTO;

import java.util.Optional;

public interface SwApiAdapter {
    Optional<SwPlanetDTO> findPlanetByName(String name);
    SwPageDTO findPlanets(Integer page, String name);
}
