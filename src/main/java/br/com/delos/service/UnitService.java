package br.com.delos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.delos.model.Unit;
import br.com.delos.repository.UnitRepository;
import br.com.delos.utils.SortUtil;

@Service
public class UnitService {
	@Autowired
	private UnitRepository unitRepository;
	
	public List<Unit> listar() {
		List<Unit> unitList = unitRepository.findAll();
		SortUtil.sortList(unitList, true, "name");
		return unitList;
	}
	
	@Transactional
	public void salvar(Unit unit) {
		unitRepository.saveAndFlush(unit);
	}

	@Transactional
	public void remover(Unit unitSelected) {
		unitRepository.delete(unitSelected);
	}
}
