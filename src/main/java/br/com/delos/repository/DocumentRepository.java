package br.com.delos.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.delos.model.Document;
import br.com.delos.model.DocumentType;


@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
	@Query("SELECT d FROM Document d where d.documentType = :documentType") 
	public List<Document> findByDocumentType(@Param("documentType") DocumentType documentType, Pageable pegeable);
	
	@Query("SELECT COUNT(d) FROM Document d where d.documentType = :documentType") 
	public Long countByDocumentType(@Param("documentType") DocumentType documentType);
	
	@Query("SELECT d FROM Document d where d.documentType = :documentType") 
	public List<Document> listByDocumentType(@Param("documentType") DocumentType documentType);
}
