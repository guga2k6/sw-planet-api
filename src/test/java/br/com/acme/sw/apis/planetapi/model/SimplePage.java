package br.com.acme.sw.apis.planetapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SimplePage<T> {
    private Integer totalElements;
    private List<T> content;
}
