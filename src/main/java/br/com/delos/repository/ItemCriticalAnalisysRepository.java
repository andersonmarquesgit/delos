package br.com.delos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.delos.model.ItemCriticalAnalisys;

@Repository
public interface ItemCriticalAnalisysRepository extends
		JpaRepository<ItemCriticalAnalisys, Long> {

}
