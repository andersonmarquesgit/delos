package br.com.delos.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.time.DateUtils;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.delos.model.Action;
import br.com.delos.model.Address;
import br.com.delos.model.City;
import br.com.delos.model.Customer;
import br.com.delos.model.CustomerAcceptance;
import br.com.delos.model.Gravity;
import br.com.delos.model.Reclamation;
import br.com.delos.model.ReclamationStatus;
import br.com.delos.model.ReclamationType;
import br.com.delos.model.State;
import br.com.delos.security.UserSession;
import br.com.delos.service.AddressService;
import br.com.delos.service.ReclamationService;
import br.com.delos.service.ReclamationStatusService;
import br.com.delos.service.ReclamationTypeService;
import br.com.delos.utils.Constantes;
import br.com.delos.utils.FacesUtil;
import br.com.delos.utils.MsgConstantes;
import br.com.delos.utils.enums.ReclamationStatusEnum;

@Scope("view")
@Controller
public class ReclamationController {

	private static final long serialVersionUID = -7580482456782445297L;

	private List<Reclamation> reclamationList;
	private List<Reclamation> reclamacoesFiltradas;
	private Reclamation reclamation;
	private Reclamation reclamacaoSelecionada;
	private Customer customer;
	private Address address;
	private List<ReclamationType> reclamationTypeList;
	private Gravity gravity;
	private Gravity complexity;
	private Action action;
	private CustomerAcceptance customerAcceptance;
	private List<State> stateList;
	private List<City> cityList;
	private State state;
	
	@Autowired
	private ReclamationTypeService reclamationTypeService;

	@Autowired
	private ReclamationService reclamationService;

	@Autowired
	private ReclamationStatusService reclamationStatusService;

	@Autowired
	private UserSession userSession;

	@Autowired
	private ChartBean chartBean;
	
	@Autowired
	private AddressService addressService;
	
	@PostConstruct
	public void init() {
		this.initObjects();
	}

	private void initObjects() {
		customer = new Customer();
		address = new Address();
		gravity = null;
		complexity = null;
		reclamationTypeList = reclamationTypeService.list();
		this.initReclamation();
		this.initReclamationList();
		this.initAction();
		this.initCustomerAcceptance();
		this.initStateList();
	}

	private void initReclamation() {
		reclamation = new Reclamation();
	}

	private void initReclamationList() {
		reclamationList = reclamationService.listar();
	}

	private void initAction() {
		action = new Action();
		action.setProcced(true);
	}

	private void initCustomerAcceptance() {
		customerAcceptance = new CustomerAcceptance();
	}

	private void initStateList() {
		stateList = addressService.listarEstados();
	}
	
	public void addReclamation() {
		if (validateCustomerRequiredFields()
				|| validateReclamationRequiredFields()) {
			FacesUtil
					.adicionarErro(MsgConstantes.VALIDACAO_CAMPOS_OBRIGATORIOS);
		} else {
			RequestContext.getCurrentInstance().execute(
					"PF('confirmAddReclamation').show();");
		}
	}

	public Boolean validateCustomerRequiredFields() {
		if (customer.getName().isEmpty() || customer.getPhoneNumber().isEmpty()
				|| customer.getEmail().isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public Boolean validateReclamationRequiredFields() {
		if (reclamation.getDescription().isEmpty()
				|| reclamation.getReclamationType() == null
				|| reclamation.getDeadlineAnswer() == null
				|| reclamation.getGravity() == null
				|| reclamation.getComplexity() == null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @return Redirect para a página principal de Sac. Método responsável pela
	 *         primeira etapa da Reclamação {@link Reclamation}, ou seja, o
	 *         cadastro inicial
	 */
	public String confirmSaveReclamation() {
		this.firstStepRegisterReclamation();
		RequestContext.getCurrentInstance().execute(
				"PF('confirmAddReclamation').hide();");
		reclamationService.save(reclamation);
		reclamationService.sendEmail(this.reclamation);
		this.initObjects();
		this.initReclamationList();
		RequestContext.getCurrentInstance().update("formNewReclamation");
		RequestContext.getCurrentInstance().update("formReclamation");
		FacesUtil.obterFlashScope().setKeepMessages(true);
		FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO);
		this.refreshDashboard();
		return this.redirectSac();
	}
	
	private void refreshDashboard(){
		chartBean.loadTotalsByStatus();
		chartBean.createChartReclamations();
		chartBean.createPieChartReclamatons();
	}

	public String cancelNewReclamation() {
		this.initObjects();
		RequestContext.getCurrentInstance().update("formNewReclamation");
		return this.redirectSac();
	}

	public void firstStepRegisterReclamation() {
		Date dataAtual = new Date();
		this.customer.setAddress(address);
		this.reclamation.setCustomer(customer);
		this.reclamation.setDateInclusion(dataAtual);
		this.reclamation.setNumber(reclamationService
				.construirNumeroDaReclamacao(dataAtual));
		this.reclamation.setUser(userSession.obterUsuarioLogado());

		// ReclamationStatus statusReclamacao = statusReclamacaoService
		// .findOn(ReclamationStatusEnum.ANALISE_GRAVIDADE.getId());
		ReclamationStatus statusReclamacao = reclamationStatusService
				.findOn(ReclamationStatusEnum.ACAO_TOMADA.getId());
		this.reclamation.setReclamationStatus(statusReclamacao);
	}

	public void addGravity(Reclamation reclamacao) {
		this.reclamation = reclamacao;
		RequestContext.getCurrentInstance().execute(
				"PF('modalGravity').show();");
	}

	public void saveGravityAnalisys() {
		if (validityGravityRequiredFields()) {
			FacesUtil
					.adicionarErro(MsgConstantes.VALIDACAO_CAMPOS_OBRIGATORIOS);
		} else {
			RequestContext.getCurrentInstance().execute(
					"PF('confirmGravityReclamation').show();");
		}
	}

	/**
	 * Método responsável pela segunda etapa da Reclamação {@link Reclamation},
	 * ou seja, a realização da análise da gravidade {@link Gravity}
	 */
	public void confirmGravityReclamation() {
		RequestContext.getCurrentInstance().execute(
				"PF('modalGravity').hide();");
		this.secondStepRegisterReclamation();
		Object[] params = new Object[1];
		params[0] = this.gravity.getDescription();
		reclamationService.save(reclamation);
		FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO_ANALISE_GRAVIDADE,
				params);
		this.initObjects();
		this.initReclamationList();
		RequestContext.getCurrentInstance().update("formReclamation");
		RequestContext.getCurrentInstance().update("modalGravity");
	}

	public void cancelGravityAnalisys() {
		this.initObjects();
		this.initReclamationList();
		RequestContext.getCurrentInstance().update("modalGravity");
		RequestContext.getCurrentInstance().execute(
				"PF('modalGravity').hide();");

	}

	public void secondStepRegisterReclamation() {
		ReclamationStatus reclamationStatus = reclamationStatusService
				.findOn(ReclamationStatusEnum.ACAO_TOMADA.getId());
		this.reclamation.setReclamationStatus(reclamationStatus);
		this.reclamation.setGravity(gravity);
		this.reclamation.setComplexity(complexity);
	}

	public Boolean validityGravityRequiredFields() {
		return (this.gravity == null || this.complexity == null);
	}

	public void analyzeAction(Reclamation reclamation) {
		this.reclamation = reclamation;
		RequestContext.getCurrentInstance().execute(
				"PF('modalAction').show();");
		RequestContext.getCurrentInstance().update("numeroReclamacao");
	}

	public void saveActionAnalisys() {
		if (validarCamposObrigatoriosAcao()) {
			FacesUtil
					.adicionarErro(MsgConstantes.VALIDACAO_CAMPOS_OBRIGATORIOS);
		} else {
			RequestContext.getCurrentInstance().execute(
					"PF('confirmActionReclamation').show();");
		}

	}

	public Boolean validarCamposObrigatoriosAcao() {
		return (this.action.getProcced() == null
				|| this.action.getDescription() == null || this.action
					.getDescription() == "");
	}

	/**
	 * Método responsável pela terceira etapa da Reclamação {@link Reclamation},
	 * ou seja, a realização da análise da ação a ser tomada {@link Gravity}
	 */
	public void confirmActionReclamation() {
		RequestContext.getCurrentInstance().execute(
				"PF('modalAction').hide();");
		this.terceiraEtapaDaReclamacao();
		reclamationService.save(reclamation);
		FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO_ANALISE_ACAO);
		this.initObjects();
		this.initReclamationList();
		RequestContext.getCurrentInstance().update("formReclamation");
		RequestContext.getCurrentInstance().update("modalAction");
	}

	public void cancelarAnaliseDeAcao() {
		this.initObjects();
		this.initReclamationList();
		RequestContext.getCurrentInstance().update("modalAction");
		RequestContext.getCurrentInstance().execute(
				"PF('modalAction').hide();");
	}

	public void terceiraEtapaDaReclamacao() {
		ReclamationStatus statusReclamacao = reclamationStatusService
				.findOn(ReclamationStatusEnum.ACEITE_CLIENTE.getId());
		this.reclamation.setReclamationStatus(statusReclamacao);
		this.reclamation.setAction(action);
	}

	public void analisarAceite(Reclamation reclamacao) {
		this.reclamation = reclamacao;
		RequestContext.getCurrentInstance().execute(
				"PF('modalCustomerAcceptance').show();");
	}

	public void salvarAnaliseDeAceite() {
		if (validarCamposObrigatoriosAceite()) {
			FacesUtil
					.adicionarErro(MsgConstantes.VALIDACAO_CAMPOS_OBRIGATORIOS);
		} else {
			RequestContext.getCurrentInstance().execute(
					"PF('confirmAcceptanceReclamation').show();");
		}

	}

	public Boolean validarCamposObrigatoriosAceite() {
		Boolean descricaoObrigatoria = false;
		if (this.customerAcceptance.getAcceptance() == false) {
			descricaoObrigatoria = this.customerAcceptance.getDescription() == null
					|| this.customerAcceptance.getDescription().isEmpty();
		}

		return (this.customerAcceptance.getAcceptance() == null || descricaoObrigatoria);
	}

	/**
	 * Método responsável pela quarta etapa da Reclamação {@link Reclamation}, ou
	 * seja, a realização da análise do aceite do cliente {@link CustomerAcceptance}
	 */
	public void confirmAcceptanceReclamation() {
		RequestContext.getCurrentInstance().execute("PF('modalCustomerAcceptance').hide();");
		this.quartaEtapaDaReclamacao();
		reclamationService.save(reclamation);
		FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO_ANALISE_ACEITE);
		this.initObjects();
		RequestContext.getCurrentInstance().update("formReclamation");
		RequestContext.getCurrentInstance().update("modalCustomerAcceptance");
	}

	public void cancelarAnaliseDeAceite() {
		this.initObjects();
		this.initReclamationList();
		RequestContext.getCurrentInstance().update("modalCustomerAcceptance");
		RequestContext.getCurrentInstance().execute(
				"PF('modalCustomerAcceptance').hide();");
	}

	public void quartaEtapaDaReclamacao() {
		ReclamationStatus statusReclamacao = reclamationStatusService
				.findOn(ReclamationStatusEnum.CONCLUIDA.getId());
		this.reclamation.setReclamationStatus(statusReclamacao);
		this.reclamation.setCustomerAcceptance(customerAcceptance);
	}

	public String redirectNovaReclamacao() {
		return FacesUtil.sendRedirect("/pages/sac/reclamation/newReclamation");
	}

	public String redirectSac() {
		return FacesUtil.sendRedirect("/pages/sac/sac");
	}

	public void listarCidadesPorEstado() {
		cityList = addressService.listarCidadesPorEstado(this.state.getId());
	}
	
	public boolean filterByData(Object value, Object filter, Locale locale) {
		if (filter == null) {
			return true;
		}

		if (value == null) {
			return false;
		}

		return DateUtils.truncatedEquals((Date) filter, (Date) value,
				Calendar.DATE);
	}
	
	public String getRemediacaoSugerida(){
		return this.verificarTextoNA(this.reclamation.getSuggestedRemediation());
	}
	
	public String getDescricao(){
		return this.verificarTextoNA(this.reclamation.getDescription());
	}
	
	public String verificarTextoNA(String texto) {
		if(texto == null || texto.isEmpty()) {
			return Constantes.NA;
		}
		
		return texto;
	}
	
	// Gets e Sets
	// ==============================================================================================

	public List<Reclamation> getReclamationList() {
		return reclamationList;
	}

	public void setReclamationList(List<Reclamation> reclamationList) {
		this.reclamationList = reclamationList;
	}

	public List<Reclamation> getReclamacoesFiltradas() {
		return reclamacoesFiltradas;
	}

	public void setReclamacoesFiltradas(List<Reclamation> reclamacoesFiltradas) {
		this.reclamacoesFiltradas = reclamacoesFiltradas;
	}

	public Reclamation getReclamation() {
		return reclamation;
	}

	public void setReclamation(Reclamation reclamation) {
		this.reclamation = reclamation;
	}

	public Reclamation getReclamacaoSelecionada() {
		return reclamacaoSelecionada;
	}

	public void setReclamacaoSelecionada(Reclamation reclamacaoSelecionada) {
		this.reclamacaoSelecionada = reclamacaoSelecionada;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<ReclamationType> getReclamationTypeList() {
		return reclamationTypeList;
	}

	public void setReclamationTypeList(List<ReclamationType> reclamationTypeList) {
		this.reclamationTypeList = reclamationTypeList;
	}

	public Gravity getGravity() {
		return gravity;
	}

	public void setGravity(Gravity gravity) {
		this.gravity = gravity;
	}

	public Gravity getComplexity() {
		return complexity;
	}

	public void setComplexity(Gravity complexity) {
		this.complexity = complexity;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public CustomerAcceptance getCustomerAcceptance() {
		return customerAcceptance;
	}

	public void setCustomerAcceptance(CustomerAcceptance aceiteCliente) {
		this.customerAcceptance = aceiteCliente;
	}

	public List<State> getStateList() {
		return stateList;
	}

	public void setStateList(List<State> stateList) {
		this.stateList = stateList;
	}

	public List<City> getCityList() {
		return cityList;
	}

	public void setCityList(List<City> cityList) {
		this.cityList = cityList;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public ChartBean getChartBean() {
		return chartBean;
	}

	public void setChartBean(ChartBean chartBean) {
		this.chartBean = chartBean;
	}

}
