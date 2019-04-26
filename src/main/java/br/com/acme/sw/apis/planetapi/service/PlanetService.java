package br.com.acme.sw.apis.planetapi.service;

import br.com.acme.sw.apis.planetapi.adapter.SwApiAdapter;
import br.com.acme.sw.apis.planetapi.client.model.SwPlanetDTO;
import br.com.acme.sw.apis.planetapi.entities.Planet;
import br.com.acme.sw.apis.planetapi.model.PlanetRequest;
import br.com.acme.sw.apis.planetapi.repository.PlanetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanetService {

    private final SwApiAdapter apiAdapter;
    private final PlanetRepository planetRepository;

    public PlanetService(SwApiAdapter apiAdapter, PlanetRepository planetRepository) {
        this.apiAdapter = apiAdapter;
        this.planetRepository = planetRepository;
    }

    public Planet create(PlanetRequest request) {
        Planet newPlanet = request.asEntity();
        newPlanet.setFilmAppearances(apiAdapter.findPlanetByName(newPlanet.getName())
            .map(SwPlanetDTO::getFilms)
            .map(List::size)
            .orElse(0));

        return planetRepository.save(newPlanet);
    }
}
