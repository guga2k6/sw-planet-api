package br.com.acme.sw.apis.planetapi.client;

import br.com.acme.sw.apis.planetapi.client.model.SwPageDTO;
import br.com.acme.sw.apis.planetapi.client.model.SwPlanetDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "swapi.io", url = "${swapi.url}")
public interface SwApiClient {

    String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.105 Safari/537.36;";

    @GetMapping(value = "/api/planets/", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<SwPageDTO<SwPlanetDTO>> findPlanets(@RequestHeader(value = "User-Agent", defaultValue = USER_AGENT) String userAgent, @RequestParam(required = false) Integer page, @RequestParam("search") String name);

    @GetMapping(value = "/api/planets/{id}/", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<SwPlanetDTO> findPlanetById(@RequestHeader(value = "User-Agent", defaultValue = USER_AGENT) String userAgent, @PathVariable("id") Integer id);
}
