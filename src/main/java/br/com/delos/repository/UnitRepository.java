package br.com.delos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.delos.model.Unit;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {

}
