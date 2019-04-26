package br.com.acme.sw.apis.planetapi.controllers;

import br.com.acme.sw.apis.planetapi.client.model.SwPageDTO;
import br.com.acme.sw.apis.planetapi.client.model.SwPlanetDTO;
import br.com.acme.sw.apis.planetapi.model.ErrorResponse;
import br.com.acme.sw.apis.planetapi.model.PlanetRequest;
import br.com.acme.sw.apis.planetapi.model.PlanetResponse;
import br.com.acme.sw.apis.planetapi.model.SimplePage;
import br.com.six2six.fixturefactory.Fixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.atomic.AtomicLong;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertTrue;

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
    @DisplayName("Test Find Planet Alderaan by Name returning success")
    void testFindPlanetAlderaanByName() {
        ParameterizedTypeReference<SimplePage<PlanetResponse>> reference = new ParameterizedTypeReference<SimplePage<PlanetResponse>>() {};
        ResponseEntity<SimplePage<PlanetResponse>> response = this.restTemplate.exchange("/api/planets/search?name={name}", HttpMethod.GET, null, reference, "Alderaan");
        assertNotNull("Response must not be null", response);
        assertEquals("Status must be 200 - OK", HttpStatus.OK, response.getStatusCode());

        SimplePage<PlanetResponse> page = response.getBody();
        assertTrue("Planet id must be greater than or equal to 1", page.getTotalElements() >= 1);

        PlanetResponse planetResponse = page.getContent().get(0);
        assertEquals("Planet name must be Alderaan", "Alderaan", planetResponse.getName());
        assertEquals("Planet terrain must be 'Grasslands, Mountains'", "grasslands, mountains", planetResponse.getTerrain());
        assertEquals("Planet climate must be temperate", "temperate", planetResponse.getClimate());
        assertEquals("Planet appearances in film must be 2", Integer.valueOf(2), planetResponse.getFilmAppearances());
    }

    @Test
    @DisplayName("Test Find All Planets returning success")
    void testFindAllPlanets() {
        ParameterizedTypeReference<SimplePage<PlanetResponse>> reference = new ParameterizedTypeReference<SimplePage<PlanetResponse>>() {};
        ResponseEntity<SimplePage<PlanetResponse>> response = this.restTemplate.exchange("/api/planets", HttpMethod.GET, null, reference);
        assertNotNull("Response must not be null", response);
        assertEquals("Status must be 200 - OK", HttpStatus.OK, response.getStatusCode());

        SimplePage<PlanetResponse> page = response.getBody();
        assertTrue("Planet id must be greater than or equal to 1", page.getTotalElements() >= 1);
        assertNotNull("Content must not be null", page.getContent());
        assertTrue("Content must not be empty", !page.getContent().isEmpty());
    }

    @Test
    @DisplayName("Test Find Planet Tatooine by name using SwApi returning success")
    void testFindPlanetTatooineByNameUsingSwApi() {
        ParameterizedTypeReference<SwPageDTO<SwPlanetDTO>> reference = new ParameterizedTypeReference<SwPageDTO<SwPlanetDTO>>() {};
        ResponseEntity<SwPageDTO<SwPlanetDTO>> response = this.restTemplate.exchange("/api/planets/swapi?page={page}&name={name}", HttpMethod.GET, null, reference, 1, "Tatooine");
        assertNotNull("Response must not be null", response);
        assertEquals("Status must be 200 - OK", HttpStatus.OK, response.getStatusCode());

        SwPageDTO<SwPlanetDTO> responseBody = response.getBody();
        assertEquals("Page count must be 1", Long.valueOf(1), responseBody.getCount());

        SwPlanetDTO planet = responseBody.getResults().get(0);
        assertEquals("Planet name must be Tatooine", "Tatooine", planet.getName());
        assertEquals("Planet terrain must be 'desert'", "desert", planet.getTerrain());
        assertEquals("Planet climate must be temperate", "arid", planet.getClimate());
        assertEquals("Planet appearances in film must be 5", Integer.valueOf(5), Integer.valueOf(planet.getFilms().size()));
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

    @Test
    @DisplayName("Test Delete Planet Coruscant by Id returning success")
    void testDeletePlanetCoruscantById() {
        ResponseEntity<Void> response = this.restTemplate.exchange("/api/planets/{id}", HttpMethod.DELETE, null, Void.class, 2L);
        assertNotNull("Response must not be null", response);
        assertEquals("Status must be 204 - No Content", HttpStatus.NO_CONTENT, response.getStatusCode());
        assertTrue("ResponseBody must be empty", !response.hasBody());
    }

    @Test
    @DisplayName("Test Delete Unknown Planet by ID returning error")
    void testDeleteUnknownPlanetById() {
        ResponseEntity<ErrorResponse> response = this.restTemplate.getForEntity("/api/planets/{id}", ErrorResponse.class, 1234L);
        assertNotNull("Response must not be null", response);
        assertEquals("Status must be 404 - Bad Request", HttpStatus.NOT_FOUND, response.getStatusCode());

        ErrorResponse responseBody = response.getBody();
        assertEquals("Expecting error message", "Planet identified by id 1234 was not found", responseBody.getMessage());
    }
}
