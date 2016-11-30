package br.com.delos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.delos.model.Section;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {

}
