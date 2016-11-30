package br.com.delos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.delos.model.Item;
import br.com.delos.model.Section;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

	@Query("SELECT i FROM Item i where i.secao = :section ") 
	public List<Item> listBySection(@Param("section") Section section);

}
