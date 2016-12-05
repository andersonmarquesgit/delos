package br.com.delos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.delos.model.ReclamationType;

public interface ReclamationTypeRepository extends JpaRepository<ReclamationType, Long> {

}
