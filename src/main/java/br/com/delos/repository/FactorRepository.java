package br.com.delos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.delos.model.Factor;

@Repository
public interface FactorRepository extends JpaRepository<Factor, Long> {

}
