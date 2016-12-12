package br.com.delos.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.delos.exceptions.BusinessException;
import br.com.delos.model.Company;
import br.com.delos.service.CompanyService;
import br.com.delos.utils.FacesUtil;
import br.com.delos.utils.MsgConstantes;

//@ManagedBean
//@ViewScoped
//@Controller
@Scope("view")
@Controller
public class CompanyController {
	
	private static final Logger logger = LogManager.getLogger(CompanyController.class);
	
	private Company empresa;

	private Company empresaSelecionada;
	
	private List<Company> empresas;
	
	@Autowired
	private CompanyService empresaService;
	
	@PostConstruct
	public void init() {
		this.inicializarObjetosDaTela();
	}

	private void inicializarObjetosDaTela() {
		this.inicializarEmpresa();
		this.inicializarEmpresas();
	}
	
	private void inicializarEmpresa() {
		empresa = new Company();
	}
	
	public void inicializarEmpresas() {
		empresas = empresaService.listar();
	}


	public void adicionarEmpresa() {
		if(empresa.getName().isEmpty() || empresa.getCnpj().isEmpty()) {
			FacesUtil.adicionarErro(MsgConstantes.VALIDACAO_CAMPOS_OBRIGATORIOS);
		}else{
			RequestContext.getCurrentInstance().execute("PF('confirmInclusaoEmpresa').show();");
		}
	}
	
	public void confirmSalvarEmpresa() {
		RequestContext.getCurrentInstance().execute("PF('modalEmpresa').hide();");
		Boolean ehEdicao = (this.empresa.getId() == null);
		empresaService.salvar(empresa);
		if(ehEdicao) {
			FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO);
		}else{
			FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO_EDICAO);
		}
		this.inicializarObjetosDaTela();
		RequestContext.getCurrentInstance().update("formEmpresas");
	}
	
	public void cancelarInclusao() {
		this.inicializarObjetosDaTela();
		RequestContext.getCurrentInstance().update("formEmpresas");
		RequestContext.getCurrentInstance().execute("PF('modalEmpresa').hide();");
	}
	
	public void excluirEmpresa() {
		try {
			empresaService.remover(this.empresaSelecionada);
			this.inicializarObjetosDaTela();
			RequestContext.getCurrentInstance().update("formEmpresas");
			FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO_EXCLUSAO);
		} catch (Exception e) {
			logger.error(FacesUtil.obterTexto(MsgConstantes.ERRO_EXCLUSAO), e);
			FacesUtil.adicionarMensagem(MsgConstantes.ERRO_EXCLUSAO);
			throw new BusinessException(FacesUtil.obterTexto(MsgConstantes.ERRO_EXCLUSAO));
		}
	}
	
	public void confirmarExclusaoEmpresa(Company empresaSelecionada) {
		this.empresaSelecionada = empresaSelecionada;
		RequestContext.getCurrentInstance().execute("PF('confirmExclusaoEmpresa').show();");
	}
	
	public void cancelarExclusao() {
		RequestContext.getCurrentInstance().execute("PF('confirmExclusaoEmpresa').hide();");
	}
	
	public void editarEmpresa(Company empresaSelecionada) {
		this.empresa = empresaSelecionada;
		RequestContext.getCurrentInstance().update("formEmpresas");
		RequestContext.getCurrentInstance().execute("PF('modalEmpresa').show();");
	}

	//Gets e Sets ==============================================================================================
	public Company getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Company empresa) {
		this.empresa = empresa;
	}

	public Company getEmpresaSelecionada() {
		return empresaSelecionada;
	}

	public void setEmpresaSelecionada(Company empresaSelecionada) {
		this.empresaSelecionada = empresaSelecionada;
	}

	public List<Company> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(List<Company> empresas) {
		this.empresas = empresas;
	}
	
}
