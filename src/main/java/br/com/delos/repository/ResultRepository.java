package br.com.delos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.delos.model.Result;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {

}
