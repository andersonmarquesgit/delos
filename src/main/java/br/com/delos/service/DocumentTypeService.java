package br.com.delos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.delos.model.DocumentType;
import br.com.delos.repository.DocumentTypeRepository;

@Service
public class DocumentTypeService {

	@Autowired
	private DocumentTypeRepository documentTypeRepository;
	
	public List<DocumentType> list() {
		return documentTypeRepository.findAll();
	}
	
	public List<DocumentType> findById(Long id) {
		return documentTypeRepository.findById(id);
	}

}
