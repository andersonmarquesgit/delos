package br.com.delos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.delos.model.ReclamationStatus;
import br.com.delos.repository.ReclamationStatusRepository;

@Service
public class ReclamationStatusService {
	@Autowired
	private ReclamationStatusRepository reclamationStatusRepository;
	
	public ReclamationStatus findOn(Long id) {
		return reclamationStatusRepository.findOne(id);
	}

}
