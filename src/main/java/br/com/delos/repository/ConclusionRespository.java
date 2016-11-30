package br.com.delos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.delos.model.Conclusion;

@Repository
public interface ConclusionRespository extends JpaRepository<Conclusion, Long> {

}
