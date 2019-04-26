package br.com.acme.sw.apis.planetapi.controllers;

import br.com.acme.sw.apis.planetapi.adapter.SwApiAdapter;
import br.com.acme.sw.apis.planetapi.client.model.SwPageDTO;
import br.com.acme.sw.apis.planetapi.exceptions.PlanetNotFoundException;
import br.com.acme.sw.apis.planetapi.model.PlanetRequest;
import br.com.acme.sw.apis.planetapi.model.PlanetResponse;
import br.com.acme.sw.apis.planetapi.service.PlanetService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController()
@RequestMapping("/api/planets")
public class PlanetController {


    private final PlanetService planetService;
    private final SwApiAdapter swApiAdapter;

    public PlanetController(PlanetService planetService, SwApiAdapter swApiAdapter) {
        this.planetService = planetService;
        this.swApiAdapter = swApiAdapter;
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

    @GetMapping
    public ResponseEntity<Page<PlanetResponse>> findAll(Pageable pageable) {
        return ResponseEntity.ok(planetService.findAll(pageable).map(PlanetResponse::new));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<PlanetResponse>> findByName(@RequestParam("name") String name, Pageable pageable) {
        return ResponseEntity.ok(planetService.findByName(name, pageable).map(PlanetResponse::new));
    }

    @GetMapping("/swapi")
    public ResponseEntity<SwPageDTO> findFromSwApi(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "name", required = false) String name) throws PlanetNotFoundException {
        return ResponseEntity.ok(swApiAdapter.findPlanets(page, name));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) throws PlanetNotFoundException {
        planetService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
