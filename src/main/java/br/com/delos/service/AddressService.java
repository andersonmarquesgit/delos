package br.com.delos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.delos.model.City;
import br.com.delos.model.State;
import br.com.delos.repository.CityRespository;
import br.com.delos.repository.StateRespository;

@Service
public class AddressService {
	
	@Autowired
	private StateRespository stateRespository;
	
	@Autowired
	private CityRespository cityRespository;
	
	public List<State> listarEstados() {
		return stateRespository.findAll();
	}

	public List<City> listarCidadesPorEstado(Long idState) {
		return cityRespository.listCityByState(idState);
	}
}
