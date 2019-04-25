package br.com.acme.sw.apis.planetapi.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Planet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 120, nullable = false)
    private String name;

    @Column(length = 40, nullable = false)
    private String climate;

    @Column(length = 200, nullable = false)
    private String terrain;

    @Column(nullable = false)
    private Integer filmAppearances;
}
