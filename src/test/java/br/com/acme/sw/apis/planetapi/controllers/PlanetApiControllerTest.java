package br.com.acme.sw.apis.planetapi.controllers;

import br.com.acme.sw.apis.planetapi.model.ErrorResponse;
import br.com.acme.sw.apis.planetapi.model.PlanetRequest;
import br.com.acme.sw.apis.planetapi.model.PlanetResponse;
import br.com.six2six.fixturefactory.Fixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.atomic.AtomicLong;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlanetApiControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private static AtomicLong currentCount = new AtomicLong(2L);

    @Test
    @DisplayName("Test Create Planet Tatooine returning success")
    void testPostPlanet() {
        Long expectedId = currentCount.incrementAndGet();
        PlanetRequest request = Fixture.from(PlanetRequest.class).gimme("valid");
        ResponseEntity<PlanetResponse> response = this.restTemplate.postForEntity("/api/planets", request, PlanetResponse.class);
        assertNotNull("Response must not be null", response);
        assertEquals("Status must be 201 - CREATED", HttpStatus.CREATED, response.getStatusCode());

        PlanetResponse responseBody = response.getBody();
        assertEquals("Planet id must be " + expectedId, expectedId, responseBody.getId());
        assertEquals("Planet name must be Tattoine", "Tatooine", responseBody.getName());
        assertEquals("Planet terrain must be Desert", "Desert", responseBody.getTerrain());
        assertEquals("Planet climate must be Arid", "Arid", responseBody.getClimate());
        assertEquals("Planet appearances in film must be 5", Integer.valueOf(5), responseBody.getFilmAppearances());
    }

    @Test
    @DisplayName("Test Create Planet Earth returning success")
    void testPostPlanetEarth() {
        Long expectedId = currentCount.incrementAndGet();
        PlanetRequest request = Fixture.from(PlanetRequest.class).gimme("valid.earth");
        ResponseEntity<PlanetResponse> response = this.restTemplate.postForEntity("/api/planets", request, PlanetResponse.class);
        assertNotNull("Response must not be null", response);
        assertEquals("Status must be 201 - CREATED", HttpStatus.CREATED, response.getStatusCode());

        PlanetResponse responseBody = response.getBody();
        assertEquals("Planet id must be " + expectedId, expectedId, responseBody.getId());
        assertEquals("Planet name must be Earth", "Earth", responseBody.getName());
        assertEquals("Planet terrain must be 'Grasslands, Mountains'", "Grasslands, Mountains", responseBody.getTerrain());
        assertEquals("Planet climate must be Temperate", "Temperate", responseBody.getClimate());
        assertEquals("Planet appearances in film must be 0", Integer.valueOf(0), responseBody.getFilmAppearances());
    }

    @Test
    @DisplayName("Test Create Planet Tatooine returning error")
    void testPostPlanetError() {
        PlanetRequest request = Fixture.from(PlanetRequest.class).gimme("invalid");
        ResponseEntity<ErrorResponse> response = this.restTemplate.postForEntity("/api/planets", request, ErrorResponse.class);
        assertNotNull("Response must not be null", response);
        assertEquals("Status must be 400 - Bad Request", HttpStatus.BAD_REQUEST, response.getStatusCode());

        ErrorResponse responseBody = response.getBody();
        assertEquals("Expecting error message", "Validation failed for object='planetRequest'. Error count: 1", responseBody.getMessage());
        assertEquals("Expecting 1 error", Integer.valueOf(1), Integer.valueOf(responseBody.getErrors().size()));
        assertEquals("Expecting 1 error", "Terreno do planeta é obrigatório", responseBody.getErrors().get(0).getDefaultMessage());
    }

    @Test
    @DisplayName("Test Find Planet Alderaan by Id returning success")
    void testFindPlanetAlderaanById() {
        ResponseEntity<PlanetResponse> response = this.restTemplate.getForEntity("/api/planets/{id}", PlanetResponse.class, 1L);
        assertNotNull("Response must not be null", response);
        assertEquals("Status must be 200 - OK", HttpStatus.OK, response.getStatusCode());

        PlanetResponse responseBody = response.getBody();
        assertEquals("Planet id must be 1", Long.valueOf(1), responseBody.getId());
        assertEquals("Planet name must be Alderaan", "Alderaan", responseBody.getName());
        assertEquals("Planet terrain must be 'Grasslands, Mountains'", "grasslands, mountains", responseBody.getTerrain());
        assertEquals("Planet climate must be temperate", "temperate", responseBody.getClimate());
        assertEquals("Planet appearances in film must be 2", Integer.valueOf(2), responseBody.getFilmAppearances());
    }

    @Test
    @DisplayName("Test Find Unknown Planet by ID returning error")
    void testFindUnknownPlanetById() {
        ResponseEntity<ErrorResponse> response = this.restTemplate.getForEntity("/api/planets/{id}", ErrorResponse.class, 1234L);
        assertNotNull("Response must not be null", response);
        assertEquals("Status must be 404 - Bad Request", HttpStatus.NOT_FOUND, response.getStatusCode());

        ErrorResponse responseBody = response.getBody();
        assertEquals("Expecting error message", "Planet identified by id 1234 was not found", responseBody.getMessage());
    }
}
