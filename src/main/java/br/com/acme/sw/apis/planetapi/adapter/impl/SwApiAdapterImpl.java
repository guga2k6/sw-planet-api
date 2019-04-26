package br.com.acme.sw.apis.planetapi.adapter.impl;

import br.com.acme.sw.apis.planetapi.adapter.SwApiAdapter;
import br.com.acme.sw.apis.planetapi.client.SwApiClient;
import br.com.acme.sw.apis.planetapi.client.model.SwPageDTO;
import br.com.acme.sw.apis.planetapi.client.model.SwPlanetDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SwApiAdapterImpl implements SwApiAdapter {

    private final SwApiClient swApiClient;

    public SwApiAdapterImpl(SwApiClient swApiClient) {
        this.swApiClient = swApiClient;
    }

    @Override
    public Optional<SwPlanetDTO> findPlanetByName(String name) {
        return swApiClient.findPlanets(SwApiClient.USER_AGENT, 1, name).getBody()
            .getResults()
            .stream()
            .findFirst();
    }

    @Override
    public SwPageDTO findPlanets(Integer page, String name) {
        return swApiClient.findPlanets(SwApiClient.USER_AGENT, page, name).getBody();
    }
}
