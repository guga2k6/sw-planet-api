package br.com.acme.sw.apis.planetapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.MessageFormat;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PlanetNotFoundException extends Exception {
    public PlanetNotFoundException(Long id) {
        super(MessageFormat.format("Planet identified by id {0,number,#} was not found",  id));
    }
}
