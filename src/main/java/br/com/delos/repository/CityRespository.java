package br.com.delos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.delos.model.City;


@Repository
public interface CityRespository extends JpaRepository<City, Long> {

	@Query("SELECT c FROM City c where c.state.id = :idState")
	List<City> listCityByState(@Param("idState") Long idState);

}
