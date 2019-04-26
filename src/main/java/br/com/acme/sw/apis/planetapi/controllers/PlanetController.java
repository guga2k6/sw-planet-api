package br.com.acme.sw.apis.planetapi.controllers;

import br.com.acme.sw.apis.planetapi.model.PlanetRequest;
import br.com.acme.sw.apis.planetapi.model.PlanetResponse;
import br.com.acme.sw.apis.planetapi.service.PlanetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController()
@RequestMapping("/api/planets")
public class PlanetController {


    private final PlanetService planetService;

    public PlanetController(PlanetService planetService) {
        this.planetService = planetService;
    }

    @PostMapping
    public ResponseEntity<PlanetResponse> create(@Valid @RequestBody PlanetRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new PlanetResponse(planetService.create(request)));
    }
}
