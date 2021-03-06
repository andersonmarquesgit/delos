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

@Scope("view")
@Controller
public class CompanyController {
	
	private static final Logger logger = LogManager.getLogger(CompanyController.class);
	
	private Company company;

	private Company companySelected;
	
	private List<Company> companyList;
	
	@Autowired
	private CompanyService companyService;
	
	@PostConstruct
	public void init() {
		this.inicializarObjetosDaTela();
	}

	private void inicializarObjetosDaTela() {
		this.initCompany();
		this.initCompanyList();
	}
	
	private void initCompany() {
		company = new Company();
	}
	
	public void initCompanyList() {
		companyList = companyService.list();
	}


	public void addCompany() {
		if(company.getName().isEmpty() || company.getCnpj().isEmpty()) {
			FacesUtil.adicionarErro(MsgConstantes.VALIDACAO_CAMPOS_OBRIGATORIOS);
		}else{
			RequestContext.getCurrentInstance().execute("PF('confirmInclusaoEmpresa').show();");
		}
	}
	
	public void confirmSaveCompany() {
		RequestContext.getCurrentInstance().execute("PF('modalCompany').hide();");
		Boolean ehEdicao = (this.company.getId() == null);
		companyService.salvar(company);
		if(ehEdicao) {
			FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO);
		}else{
			FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO_EDICAO);
		}
		this.inicializarObjetosDaTela();
		RequestContext.getCurrentInstance().update("formCompany");
	}
	
	public void cancelAddCompany() {
		this.inicializarObjetosDaTela();
		RequestContext.getCurrentInstance().update("formCompany");
		RequestContext.getCurrentInstance().execute("PF('modalCompany').hide();");
	}
	
	public void deleteCompany() {
		try {
			companyService.remover(this.companySelected);
			this.inicializarObjetosDaTela();
			RequestContext.getCurrentInstance().update("formCompany");
			FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO_EXCLUSAO);
		} catch (Exception e) {
			logger.error(FacesUtil.obterTexto(MsgConstantes.ERRO_EXCLUSAO), e);
			FacesUtil.adicionarMensagem(MsgConstantes.ERRO_EXCLUSAO);
			throw new BusinessException(FacesUtil.obterTexto(MsgConstantes.ERRO_EXCLUSAO));
		}
	}
	
	public void confirmDeleteCompany(Company companySelected) {
		this.companySelected = companySelected;
		RequestContext.getCurrentInstance().execute("PF('confirmExclusaoEmpresa').show();");
	}
	
	public void cancelDelete() {
		RequestContext.getCurrentInstance().execute("PF('confirmExclusaoEmpresa').hide();");
	}
	
	public void editCompany(Company companySelected) {
		this.company = companySelected;
		RequestContext.getCurrentInstance().update("formCompany");
		RequestContext.getCurrentInstance().execute("PF('modalCompany').show();");
	}

	//Gets e Sets ==============================================================================================
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Company getCompanySelected() {
		return companySelected;
	}

	public void setCompanySelected(Company companySelected) {
		this.companySelected = companySelected;
	}

	public List<Company> getCompanyList() {
		return companyList;
	}

	public void setCompanyList(List<Company> companyList) {
		this.companyList = companyList;
	}
	
}
