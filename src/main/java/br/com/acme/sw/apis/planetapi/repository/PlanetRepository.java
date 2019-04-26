package br.com.acme.sw.apis.planetapi.repository;

import br.com.acme.sw.apis.planetapi.entities.Planet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanetRepository extends JpaRepository<Planet, Long> {
    Page<Planet> findByNameLike(String name, Pageable pageable);
}
