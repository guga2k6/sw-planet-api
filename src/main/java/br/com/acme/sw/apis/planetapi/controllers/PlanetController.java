package br.com.acme.sw.apis.planetapi.controllers;

import br.com.acme.sw.apis.planetapi.exceptions.PlanetNotFoundException;
import br.com.acme.sw.apis.planetapi.model.PlanetRequest;
import br.com.acme.sw.apis.planetapi.model.PlanetResponse;
import br.com.acme.sw.apis.planetapi.service.PlanetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<PlanetResponse> findById(@PathVariable("id") Long id) throws PlanetNotFoundException {
        return ResponseEntity.ok(new PlanetResponse(planetService.findById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) throws PlanetNotFoundException {
        planetService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
