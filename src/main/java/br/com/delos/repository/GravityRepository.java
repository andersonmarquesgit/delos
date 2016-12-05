package br.com.delos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.delos.model.Gravity;

@Repository
public interface GravityRepository extends JpaRepository<Gravity, Long> {

}
