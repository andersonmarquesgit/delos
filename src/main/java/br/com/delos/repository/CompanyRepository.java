package br.com.delos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.delos.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

}
