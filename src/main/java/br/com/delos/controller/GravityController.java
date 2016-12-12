package br.com.delos.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.delos.model.Gravity;
import br.com.delos.service.GravityService;

@Scope("view")
@Controller
public class GravityController {

	@Autowired
	private GravityService gravidadeService;
	
	private List<Gravity> gravidades;
	
	@PostConstruct
	public void init() {
		gravidades = gravidadeService.listar();
	}

	public List<Gravity> getGravidades() {
		return gravidades;
	}

	public void setGravidades(List<Gravity> gravidades) {
		this.gravidades = gravidades;
	}
}
