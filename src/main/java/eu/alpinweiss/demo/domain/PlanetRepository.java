package eu.alpinweiss.demo.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanetRepository extends JpaRepository<Planet, Long> {
}
