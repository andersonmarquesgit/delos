package br.com.delos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.delos.model.SectionCriticalAnalisys;

@Repository
public interface SectionCriticalAnalisysRepository extends
		JpaRepository<SectionCriticalAnalisys, Long> {

}
