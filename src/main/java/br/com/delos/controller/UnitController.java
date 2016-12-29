package br.com.delos.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.delos.model.Unit;
import br.com.delos.service.UnitService;
import br.com.delos.utils.FacesUtil;
import br.com.delos.utils.MsgConstantes;

@Scope("view")
@Controller
public class UnitController {
	private Unit unit;

	private Unit unitSelected;
	
	private List<Unit> unitList;
	
	@Autowired
	private UnitService unidadeService;
	
	@PostConstruct
	public void init() {
		this.initObjects();
	}

	private void initObjects() {
		this.initUnit();
		this.initUnitList();
	}
	
	private void initUnit() {
		unit = new Unit();
	}
	
	public void initUnitList() {
		unitList = unidadeService.list();
	}
	
	public void addUnit() {
		if(unit.getName().isEmpty() || unit.getCompany() == null) {
			FacesUtil.adicionarErro(MsgConstantes.VALIDACAO_CAMPOS_OBRIGATORIOS);
		}else{
			RequestContext.getCurrentInstance().execute("PF('confirmInclusaoUnidade').show();");
		}
	}
	
	public void confirmAddUnit() {
		RequestContext.getCurrentInstance().execute("PF('modalUnidade').hide();");
		Boolean ehEdicao = (this.unit.getId() == null);
		unidadeService.salvar(unit);
		if(ehEdicao) {
			FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO);
		}else{
			FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO_EDICAO);
		}
		this.initObjects();
		RequestContext.getCurrentInstance().update("formUnidades");
	}
	
	public void cancelAddUnit() {
		this.initObjects();
		RequestContext.getCurrentInstance().update("formUnidades");
		RequestContext.getCurrentInstance().execute("PF('modalUnidade').hide();");
	}
	
	public void deleteUnit() {
		unidadeService.remover(this.unitSelected);
		this.initObjects();
		RequestContext.getCurrentInstance().update("formUnidades");
		FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO_EXCLUSAO);
	}
	
	public void confirmDeleteUnit(Unit unitSelected) {
		this.unitSelected = unitSelected;
		RequestContext.getCurrentInstance().execute("PF('confirmExclusaoUnidade').show();");
	}
	
	public void cancelDeleteUnit() {
		RequestContext.getCurrentInstance().execute("PF('confirmExclusaoUnidade').hide();");
	}
	
	public void editUnit(Unit unitSelected) {
		this.unit = unitSelected;
		RequestContext.getCurrentInstance().update("formUnidades");
		RequestContext.getCurrentInstance().update("selectEmpresa");
		RequestContext.getCurrentInstance().execute("PF('modalUnidade').show();");
	}

	//Gets e Sets ==============================================================================================
	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public Unit getUnitSelected() {
		return unitSelected;
	}

	public void setUnitSelected(Unit unitSelected) {
		this.unitSelected = unitSelected;
	}

	public List<Unit> getUnitList() {
		return unitList;
	}

	public void setUnitList(List<Unit> unitList) {
		this.unitList = unitList;
	}

}
