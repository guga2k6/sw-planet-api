package br.com.acme.sw.apis.planetapi.client.model;

import lombok.Data;

import java.util.List;

@Data
public class PageDTO<T> {

    private Long count;
    private String next;
    private String previous;
    private List<T> results;
}
