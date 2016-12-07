package br.com.delos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.delos.model.Gravity;
import br.com.delos.repository.GravityRepository;
import br.com.delos.utils.SortUtil;

@Service
public class GravityService {

	@Autowired
	private GravityRepository gravityRepository;
	
	public List<Gravity> listar() {
		List<Gravity> gravidades = gravityRepository.findAll();
		SortUtil.sortList(gravidades, false, "id");
		return gravidades;
	}
}
