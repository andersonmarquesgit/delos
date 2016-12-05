package br.com.delos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.delos.model.Reclamation;
import br.com.delos.model.ReclamationType;

@Repository
public interface ReclamationRepository extends JpaRepository<Reclamation, Long>{

	@Query("SELECT COUNT(r) FROM Reclamation r where r.reclamationStatus.id = :reclamationStatus "
			+ "AND month(r.dateInclusion) = :month "
			+ "AND year(r.dateInclusion) = :year") 
	public Long calculaReclamacoesNoMes(@Param("month") Integer mes, @Param("year") Integer anoSelecionado, @Param("reclamationStatus") Long statusId);

	@Query("SELECT COUNT(r) FROM Reclamation r where r.reclamationType = :reclamationType "
			+ "AND year(r.dateInclusion) = :year") 
	public Number calcularReclamacoesPorTipoNoAno(@Param("year") Integer anoSelecionado, @Param("reclamationType") ReclamationType tipoReclamacao);

	@Query("SELECT COUNT(r) FROM Reclamation r where year(r.dateInclusion) = :year") 
	public Long calculaReclamacoesPorAno(@Param("year") Integer ano);

	@Query("SELECT r FROM Reclamation r where month(r.dateInclusion) = :monthCurrent "
			+ "AND year(r.dateInclusion) = :yearCurrent")
	public List<Reclamation> listarPorMesEAno(@Param("monthCurrent") int mesAtual, @Param("yearCurrent") int anoAtual);

}
