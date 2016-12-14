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

	private List<Reclamation> reclamacoes;
	private List<Reclamation> reclamacoesFiltradas;
	private Reclamation reclamacao;
	private Reclamation reclamacaoSelecionada;
	private Customer cliente;
	private Address endereco;
	private List<ReclamationType> tipoReclamacaoList;
	private Gravity gravidade;
	private Gravity complexidade;
	private Action acaoTomada;
	private CustomerAcceptance aceiteCliente;
	private List<State> estados;
	private List<City> cidades;
	private State estado;
	
	@Autowired
	private ReclamationTypeService tipoReclamacaoService;

	@Autowired
	private ReclamationService reclamacaoService;

	@Autowired
	private ReclamationStatusService statusReclamacaoService;

	@Autowired
	private UserSession userSession;

	@Autowired
	private ChartBean chartBean;
	
	@Autowired
	private AddressService enderecoService;
	
	@PostConstruct
	public void init() {
		this.inicializarObjetosDaTela();
	}

	private void inicializarObjetosDaTela() {
		cliente = new Customer();
		endereco = new Address();
		gravidade = null;
		complexidade = null;
		tipoReclamacaoList = tipoReclamacaoService.list();
		this.inicializarReclamacao();
		this.inicializarReclamacoes();
		this.inicializarAcaoTomada();
		this.inicializarAceiteCliente();
		this.inicializarEstados();
	}

	private void inicializarReclamacao() {
		reclamacao = new Reclamation();
	}

	private void inicializarReclamacoes() {
		reclamacoes = reclamacaoService.listar();
	}

	private void inicializarAcaoTomada() {
		acaoTomada = new Action();
		acaoTomada.setProcced(true);
	}

	private void inicializarAceiteCliente() {
		aceiteCliente = new CustomerAcceptance();
	}

	private void inicializarEstados() {
		estados = enderecoService.listarEstados();
	}
	
	public void adicionarReclamacao() {
		if (validarCamposObrigatoriosDoCliente()
				|| validarCamposObrigatoriosDaReclamacao()) {
			FacesUtil
					.adicionarErro(MsgConstantes.VALIDACAO_CAMPOS_OBRIGATORIOS);
		} else {
			RequestContext.getCurrentInstance().execute(
					"PF('confirmInclusaoReclamacao').show();");
		}
	}

	public Boolean validarCamposObrigatoriosDoCliente() {
		if (cliente.getName().isEmpty() || cliente.getPhoneNumber().isEmpty()
				|| cliente.getEmail().isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public Boolean validarCamposObrigatoriosDaReclamacao() {
		if (reclamacao.getDescription().isEmpty()
				|| reclamacao.getReclamationType() == null
				|| reclamacao.getDeadlineAnswer() == null
				|| reclamacao.getGravity() == null
				|| reclamacao.getComplexity() == null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @return Redirect para a página principal de Sac. Método responsável pela
	 *         primeira etapa da Reclamação {@link Reclamacao}, ou seja, o
	 *         cadastro inicial
	 */
	public String confirmSalvarReclamacao() {
		this.primeiraEtapaDaReclamacao();
		RequestContext.getCurrentInstance().execute(
				"PF('confirmInclusaoReclamacao').hide();");
		reclamacaoService.salvar(reclamacao);
		reclamacaoService.enviarEmail(this.reclamacao);
		this.inicializarObjetosDaTela();
		this.inicializarReclamacoes();
		RequestContext.getCurrentInstance().update("formNovaReclamacao");
		RequestContext.getCurrentInstance().update("formReclamacoes");
		FacesUtil.obterFlashScope().setKeepMessages(true);
		FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO);
		this.atualizarDadosDoDashboard();
		return this.redirectSac();
	}
	
	private void atualizarDadosDoDashboard(){
		chartBean.carregarTotaisPorStatus();
		chartBean.criarChartReclamacoes();
		chartBean.criarPieChartReclamacoes();
	}

	public String cancelarNovaReclamacao() {
		this.inicializarObjetosDaTela();
		RequestContext.getCurrentInstance().update("formNovaReclamacao");
		return this.redirectSac();
	}

	public void primeiraEtapaDaReclamacao() {
		Date dataAtual = new Date();
		this.cliente.setAddress(endereco);
		this.reclamacao.setCustomer(cliente);
		this.reclamacao.setDateInclusion(dataAtual);
		this.reclamacao.setNumber(reclamacaoService
				.construirNumeroDaReclamacao(dataAtual));
		this.reclamacao.setUser(userSession.obterUsuarioLogado());

		// ReclamationStatus statusReclamacao = statusReclamacaoService
		// .findOn(ReclamationStatusEnum.ANALISE_GRAVIDADE.getId());
		ReclamationStatus statusReclamacao = statusReclamacaoService
				.findOn(ReclamationStatusEnum.ACAO_TOMADA.getId());
		this.reclamacao.setReclamationStatus(statusReclamacao);
	}

	public void adicionarGravidade(Reclamation reclamacao) {
		this.reclamacao = reclamacao;
		RequestContext.getCurrentInstance().execute(
				"PF('modalGravidade').show();");
	}

	public void salvarAnaliseDeGravidade() {
		if (validarCamposObrigatoriosGravidade()) {
			FacesUtil
					.adicionarErro(MsgConstantes.VALIDACAO_CAMPOS_OBRIGATORIOS);
		} else {
			RequestContext.getCurrentInstance().execute(
					"PF('confirmGravidadeReclamacao').show();");
		}
	}

	/**
	 * Método responsável pela segunda etapa da Reclamação {@link Reclamacao},
	 * ou seja, a realização da análise da gravidade {@link Gravity}
	 */
	public void confirmGravidadeReclamacao() {
		RequestContext.getCurrentInstance().execute(
				"PF('modalGravidade').hide();");
		this.segundaEtapaDaReclamacao();
		Object[] params = new Object[1];
		params[0] = this.gravidade.getDescription();
		reclamacaoService.salvar(reclamacao);
		FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO_ANALISE_GRAVIDADE,
				params);
		this.inicializarObjetosDaTela();
		this.inicializarReclamacoes();
		RequestContext.getCurrentInstance().update("formReclamacoes");
		RequestContext.getCurrentInstance().update("modalGravidade");
	}

	public void cancelarAnaliseDeGravidade() {
		this.inicializarObjetosDaTela();
		this.inicializarReclamacoes();
		RequestContext.getCurrentInstance().update("modalGravidade");
		RequestContext.getCurrentInstance().execute(
				"PF('modalGravidade').hide();");

	}

	public void segundaEtapaDaReclamacao() {
		ReclamationStatus statusReclamacao = statusReclamacaoService
				.findOn(ReclamationStatusEnum.ACAO_TOMADA.getId());
		this.reclamacao.setReclamationStatus(statusReclamacao);
		this.reclamacao.setGravity(gravidade);
		this.reclamacao.setComplexity(complexidade);
	}

	public Boolean validarCamposObrigatoriosGravidade() {
		return (this.gravidade == null || this.complexidade == null);
	}

	public void analisarAcao(Reclamation reclamacao) {
		this.reclamacao = reclamacao;
		RequestContext.getCurrentInstance().execute(
				"PF('modalAcaoTomada').show();");
		RequestContext.getCurrentInstance().update("numeroReclamacao");
	}

	public void salvarAnaliseDeAcao() {
		if (validarCamposObrigatoriosAcao()) {
			FacesUtil
					.adicionarErro(MsgConstantes.VALIDACAO_CAMPOS_OBRIGATORIOS);
		} else {
			RequestContext.getCurrentInstance().execute(
					"PF('confirmAcaoReclamacao').show();");
		}

	}

	public Boolean validarCamposObrigatoriosAcao() {
		return (this.acaoTomada.getProcced() == null
				|| this.acaoTomada.getDescription() == null || this.acaoTomada
					.getDescription() == "");
	}

	/**
	 * Método responsável pela terceira etapa da Reclamação {@link Reclamation},
	 * ou seja, a realização da análise da ação a ser tomada {@link Gravity}
	 */
	public void confirmAcaoReclamacao() {
		RequestContext.getCurrentInstance().execute(
				"PF('modalAcaoTomada').hide();");
		this.terceiraEtapaDaReclamacao();
		reclamacaoService.salvar(reclamacao);
		FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO_ANALISE_ACAO);
		this.inicializarObjetosDaTela();
		this.inicializarReclamacoes();
		RequestContext.getCurrentInstance().update("formReclamacoes");
		RequestContext.getCurrentInstance().update("modalAcaoTomada");
	}

	public void cancelarAnaliseDeAcao() {
		this.inicializarObjetosDaTela();
		this.inicializarReclamacoes();
		RequestContext.getCurrentInstance().update("modalAcaoTomada");
		RequestContext.getCurrentInstance().execute(
				"PF('modalAcaoTomada').hide();");
	}

	public void terceiraEtapaDaReclamacao() {
		ReclamationStatus statusReclamacao = statusReclamacaoService
				.findOn(ReclamationStatusEnum.ACEITE_CLIENTE.getId());
		this.reclamacao.setReclamationStatus(statusReclamacao);
		this.reclamacao.setAction(acaoTomada);
	}

	public void analisarAceite(Reclamation reclamacao) {
		this.reclamacao = reclamacao;
		RequestContext.getCurrentInstance().execute(
				"PF('modalAceiteCliente').show();");
	}

	public void salvarAnaliseDeAceite() {
		if (validarCamposObrigatoriosAceite()) {
			FacesUtil
					.adicionarErro(MsgConstantes.VALIDACAO_CAMPOS_OBRIGATORIOS);
		} else {
			RequestContext.getCurrentInstance().execute(
					"PF('confirmAceiteReclamacao').show();");
		}

	}

	public Boolean validarCamposObrigatoriosAceite() {
		Boolean descricaoObrigatoria = false;
		if (this.aceiteCliente.getAcceptance() == false) {
			descricaoObrigatoria = this.aceiteCliente.getDescription() == null
					|| this.aceiteCliente.getDescription().isEmpty();
		}

		return (this.aceiteCliente.getAcceptance() == null || descricaoObrigatoria);
	}

	/**
	 * Método responsável pela quarta etapa da Reclamação {@link Reclamation}, ou
	 * seja, a realização da análise do aceite do cliente {@link CustomerAcceptance}
	 */
	public void confirmAcAcceptanceacao() {
		RequestContext.getCurrentInstance().execute("PF('modalAceiteCliente').hide();");
		this.quartaEtapaDaReclamacao();
		reclamacaoService.salvar(reclamacao);
		FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO_ANALISE_ACEITE);
		this.inicializarObjetosDaTela();
		RequestContext.getCurrentInstance().update("formReclamacoes");
		RequestContext.getCurrentInstance().update("modalAceiteCliente");
	}

	public void cancelarAnaliseDeAceite() {
		this.inicializarObjetosDaTela();
		this.inicializarReclamacoes();
		RequestContext.getCurrentInstance().update("modalAceiteCliente");
		RequestContext.getCurrentInstance().execute(
				"PF('modalAceiteCliente').hide();");
	}

	public void quartaEtapaDaReclamacao() {
		ReclamationStatus statusReclamacao = statusReclamacaoService
				.findOn(ReclamationStatusEnum.CONCLUIDA.getId());
		this.reclamacao.setReclamationStatus(statusReclamacao);
		this.reclamacao.setCustomerAcceptance(aceiteCliente);
	}

	public String redirectNovaReclamacao() {
		return FacesUtil.sendRedirect("/paginas/sac/reclamacao/novaReclamacao");
	}

	public String redirectSac() {
		return FacesUtil.sendRedirect("/paginas/sac/sac");
	}

	public void listarCidadesPorEstado() {
		cidades = enderecoService.listarCidadesPorEstado(this.estado.getId());
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
		return this.verificarTextoNA(this.reclamacao.getSuggestedRemediation());
	}
	
	public String getDescricao(){
		return this.verificarTextoNA(this.reclamacao.getDescription());
	}
	
	public String verificarTextoNA(String texto) {
		if(texto == null || texto.isEmpty()) {
			return Constantes.NA;
		}
		
		return texto;
	}
	
	// Gets e Sets
	// ==============================================================================================

	public List<Reclamation> getReclamacoes() {
		return reclamacoes;
	}

	public void setReclamacoes(List<Reclamation> reclamacoes) {
		this.reclamacoes = reclamacoes;
	}

	public List<Reclamation> getReclamacoesFiltradas() {
		return reclamacoesFiltradas;
	}

	public void setReclamacoesFiltradas(List<Reclamation> reclamacoesFiltradas) {
		this.reclamacoesFiltradas = reclamacoesFiltradas;
	}

	public Reclamation getReclamacao() {
		return reclamacao;
	}

	public void setReclamacao(Reclamation reclamacao) {
		this.reclamacao = reclamacao;
	}

	public Reclamation getReclamacaoSelecionada() {
		return reclamacaoSelecionada;
	}

	public void setReclamacaoSelecionada(Reclamation reclamacaoSelecionada) {
		this.reclamacaoSelecionada = reclamacaoSelecionada;
	}

	public Customer getCliente() {
		return cliente;
	}

	public void setCliente(Customer cliente) {
		this.cliente = cliente;
	}

	public Address getEndereco() {
		return endereco;
	}

	public void setEndereco(Address endereco) {
		this.endereco = endereco;
	}

	public List<ReclamationType> getTipoReclamacaoList() {
		return tipoReclamacaoList;
	}

	public void setTipoReclamacaoList(List<ReclamationType> tipoReclamacaoList) {
		this.tipoReclamacaoList = tipoReclamacaoList;
	}

	public Gravity getGravidade() {
		return gravidade;
	}

	public void setGravidade(Gravity gravidade) {
		this.gravidade = gravidade;
	}

	public Gravity getComplexidade() {
		return complexidade;
	}

	public void setComplexidade(Gravity complexidade) {
		this.complexidade = complexidade;
	}

	public Action getAcaoTomada() {
		return acaoTomada;
	}

	public void setAcaoTomada(Action acaoTomada) {
		this.acaoTomada = acaoTomada;
	}

	public CustomerAcceptance getAceiteCliente() {
		return aceiteCliente;
	}

	public void setAceiteCliente(CustomerAcceptance aceiteCliente) {
		this.aceiteCliente = aceiteCliente;
	}

	public List<State> getEstados() {
		return estados;
	}

	public void setEstados(List<State> estados) {
		this.estados = estados;
	}

	public List<City> getCidades() {
		return cidades;
	}

	public void setCidades(List<City> cidades) {
		this.cidades = cidades;
	}

	public State getEstado() {
		return estado;
	}

	public void setEstado(State estado) {
		this.estado = estado;
	}

	public ChartBean getChartBean() {
		return chartBean;
	}

	public void setChartBean(ChartBean chartBean) {
		this.chartBean = chartBean;
	}

}
