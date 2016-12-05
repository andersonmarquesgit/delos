package br.com.delos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.delos.model.ReclamationStatus;

public interface ReclamationStatusRepository extends JpaRepository<ReclamationStatus, Long> {

}
