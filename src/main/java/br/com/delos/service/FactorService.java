package br.com.delos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.delos.model.Factor;
import br.com.delos.repository.FactorRepository;

@Service
public class FactorService {

	@Autowired
	private FactorRepository factorRepository;
	
	public List<Factor> list() {
		return factorRepository.findAll();
	}

}
