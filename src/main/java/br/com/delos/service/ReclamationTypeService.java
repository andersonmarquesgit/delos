package br.com.delos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.delos.model.ReclamationType;
import br.com.delos.repository.ReclamationTypeRepository;

@Service
public class ReclamationTypeService {

	@Autowired
	private ReclamationTypeRepository reclamationTypeRepository;
	
	public List<ReclamationType> list() {
		return reclamationTypeRepository.findAll();
	}

}
