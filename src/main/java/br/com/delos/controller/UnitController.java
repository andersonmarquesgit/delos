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
	private Unit unidade;

	private Unit unidadeSelecionada;
	
	private List<Unit> unidades;
	
	@Autowired
	private UnitService unidadeService;
	
	@PostConstruct
	public void init() {
		this.inicializarObjetosDaTela();
	}

	private void inicializarObjetosDaTela() {
		this.inicializarUnidade();
		this.inicializarUnidades();
	}
	
	private void inicializarUnidade() {
		unidade = new Unit();
	}
	
	public void inicializarUnidades() {
		unidades = unidadeService.listar();
	}
	
	public void adicionarUnidade() {
		if(unidade.getName().isEmpty() || unidade.getCompany() == null) {
			FacesUtil.adicionarErro(MsgConstantes.VALIDACAO_CAMPOS_OBRIGATORIOS);
		}else{
			RequestContext.getCurrentInstance().execute("PF('confirmInclusaoUnidade').show();");
		}
	}
	
	public void confirmSalvarUnidade() {
		RequestContext.getCurrentInstance().execute("PF('modalUnidade').hide();");
		Boolean ehEdicao = (this.unidade.getId() == null);
		unidadeService.salvar(unidade);
		if(ehEdicao) {
			FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO);
		}else{
			FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO_EDICAO);
		}
		this.inicializarObjetosDaTela();
		RequestContext.getCurrentInstance().update("formUnidades");
	}
	
	public void cancelarInclusao() {
		this.inicializarObjetosDaTela();
		RequestContext.getCurrentInstance().update("formUnidades");
		RequestContext.getCurrentInstance().execute("PF('modalUnidade').hide();");
	}
	
	public void excluirUnidade() {
		unidadeService.remover(this.unidadeSelecionada);
		this.inicializarObjetosDaTela();
		RequestContext.getCurrentInstance().update("formUnidades");
		FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO_EXCLUSAO);
	}
	
	public void confirmarExclusaoUnidade(Unit unidadeSelecionada) {
		this.unidadeSelecionada = unidadeSelecionada;
		RequestContext.getCurrentInstance().execute("PF('confirmExclusaoUnidade').show();");
	}
	
	public void cancelarExclusao() {
		RequestContext.getCurrentInstance().execute("PF('confirmExclusaoUnidade').hide();");
	}
	
	public void editarUnidade(Unit unidadeSelecionada) {
		this.unidade = unidadeSelecionada;
		RequestContext.getCurrentInstance().update("formUnidades");
		RequestContext.getCurrentInstance().update("selectEmpresa");
		RequestContext.getCurrentInstance().execute("PF('modalUnidade').show();");
	}

	//Gets e Sets ==============================================================================================
	public Unit getUnidade() {
		return unidade;
	}

	public void setUnidade(Unit unidade) {
		this.unidade = unidade;
	}

	public Unit getUnidadeSelecionada() {
		return unidadeSelecionada;
	}

	public void setUnidadeSelecionada(Unit unidadeSelecionada) {
		this.unidadeSelecionada = unidadeSelecionada;
	}

	public List<Unit> getUnidades() {
		return unidades;
	}

	public void setUnidades(List<Unit> unidades) {
		this.unidades = unidades;
	}

}
