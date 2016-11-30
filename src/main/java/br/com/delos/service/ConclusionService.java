package br.com.delos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.delos.model.Conclusion;
import br.com.delos.repository.ConclusionRespository;

@Service
public class ConclusionService {
	
	@Autowired
	private ConclusionRespository conclusionRespository;
	
	public List<Conclusion> listar() {
		return conclusionRespository.findAll();
	}

}
