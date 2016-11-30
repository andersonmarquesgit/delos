package br.com.delos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.delos.model.Result;
import br.com.delos.repository.ResultRepository;

@Service
public class ResultService {
	
	@Autowired
	private ResultRepository resultRepository;
	
	public List<Result> list() {
		return resultRepository.findAll();
	}

}
