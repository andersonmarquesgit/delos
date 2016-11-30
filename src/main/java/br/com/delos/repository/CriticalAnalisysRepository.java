package br.com.delos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.delos.model.CriticalAnalisys;

@Repository
public interface CriticalAnalisysRepository extends JpaRepository<CriticalAnalisys, Long> {

}
