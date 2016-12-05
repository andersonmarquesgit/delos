package br.com.delos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.delos.model.State;

@Repository
public interface StateRespository extends JpaRepository<State, Long> {

}
