package br.com.delos.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.delos.model.Address;
import br.com.delos.model.Customer;
import br.com.delos.model.Gravity;
import br.com.delos.model.Reclamation;
import br.com.delos.model.ReclamationStatus;
import br.com.delos.model.ReclamationType;
import br.com.delos.security.UserSession;
import br.com.delos.service.DocumentService;
import br.com.delos.service.DocumentTypeService;
import br.com.delos.service.FactorService;
import br.com.delos.service.ReclamationService;
import br.com.delos.service.ReclamationStatusService;
import br.com.delos.service.ReclamationTypeService;
import br.com.delos.utils.CepUtil;
import br.com.delos.utils.FacesUtil;
import br.com.delos.utils.MsgConstantes;
import br.com.delos.utils.enums.ReclamationStatusEnum;

@Scope("view")
@Controller
public class CustomerAttServController {

	private Reclamation reclamacao;
	private Customer cliente;
	private Address endereco;
	private String cep;
	private List<ReclamationType> tipoReclamacaoList;
	private List<Reclamation> reclamacoes;
	private Gravity gravidade;

	@Autowired
	private ReclamationTypeService tipoReclamacaoService;
	
	@Autowired
	private DocumentTypeService tipoDocumentoService;
	
	@Autowired
	private FactorService elementoService;

	@Autowired
	private DocumentService documentoService;
	
	@Autowired
	private ReclamationService reclamacaoService;
	
	@Autowired
	private ReclamationStatusService statusReclamacaoService;

	@Autowired
	private UserSession userSession;
	
	@PostConstruct
	public void init() {
		tipoReclamacaoList = tipoReclamacaoService.list();
		this.inicializarReclamacoes();
		this.inicializarObjetos();
	}

	public void inicializarObjetos() {
		reclamacao = new Reclamation();
		cliente = new Customer();
		endereco = new Address();
		gravidade = null;
		cep = "";
	}
	
	public void inicializarReclamacoes() {
		reclamacoes = reclamacaoService.listar();
	}
	
	public void salvarReclamacao() {
		if(validarCamposObrigatoriosDoCliente() || validarCamposObrigatoriosDaReclamacao()) {
			FacesUtil.adicionarErro(MsgConstantes.VALIDACAO_CAMPOS_OBRIGATORIOS);
		}else{
			RequestContext.getCurrentInstance().execute("PF('confirmInclusaoReclamacao').show();");
		}
	}
	
	public String confirmarInclusaoReclamacao() {
		this.primeiraEtapaDaReclamacao();
		RequestContext.getCurrentInstance().execute("PF('confirmInclusaoReclamacao').hide();");
		reclamacaoService.save(reclamacao);
		this.inicializarObjetos();
		this.inicializarReclamacoes();
		RequestContext.getCurrentInstance().update("formNovaReclamacao");
		RequestContext.getCurrentInstance().update("formReclamacoes");
		FacesUtil.obterFlashScope().setKeepMessages(true);
		FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO);
		return this.redirectSac();
	}
	
	public void cancelarInclusao() {
		this.inicializarObjetos();
		RequestContext.getCurrentInstance().update("formReclamacoes");
		RequestContext.getCurrentInstance().execute("PF('modalReclamacao').hide();");
	}
	
	public void analisarGravidade(Reclamation reclamacaoSelecionada) {
		this.reclamacao = reclamacaoSelecionada;
		RequestContext.getCurrentInstance().execute("PF('modalGravidade').show();");
	}
	
	public void salvarAnaliseDeGravidade() {
		if(gravidade == null) {
			FacesUtil.adicionarErro(MsgConstantes.VALIDACAO_CAMPOS_OBRIGATORIOS);
		}else{
			this.segundaEtapaDaReclamacao();
			RequestContext.getCurrentInstance().execute("PF('modalGravidade').hide();");
			Object[] params = new Object[1];
			params[0] = this.gravidade.getDescription();
			reclamacaoService.save(reclamacao);
			this.inicializarObjetos();
			this.inicializarReclamacoes();
			FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO_ANALISE_GRAVIDADE, params);
		}
	}
	
	public void cancelarAnaliseDeGravidade() {
		this.inicializarObjetos();
		this.inicializarReclamacoes();
		RequestContext.getCurrentInstance().update("formReclamacoes");
		RequestContext.getCurrentInstance().execute("PF('modalGravidade').hide();");
	}
	
	public void primeiraEtapaDaReclamacao() {
		this.cliente.setAddress(endereco);
		this.reclamacao.setCustomer(cliente);
		this.reclamacao.setDateInclusion(new Date());
		
		ReclamationStatus statusReclamacao = statusReclamacaoService
				.findOn(ReclamationStatusEnum.ANALISE_GRAVIDADE.getId());
		this.reclamacao.setReclamationStatus(statusReclamacao);
	}
	
	public void segundaEtapaDaReclamacao() {
		ReclamationStatus statusReclamacao = statusReclamacaoService
				.findOn(ReclamationStatusEnum.ACAO_TOMADA.getId());
		this.reclamacao.setReclamationStatus(statusReclamacao);
		this.reclamacao.setGravity(gravidade);
	}
	
	public Boolean validarCamposObrigatoriosDoCliente() {
		if(cliente.getName().isEmpty() ||
				cliente.getPhoneNumber().isEmpty() ||
				cliente.getEmail().isEmpty()) {
			return true;
		}else {
			return false;
		}
	}
	
	public Boolean validarCamposObrigatoriosDaReclamacao() {
		if(reclamacao.getDescription().isEmpty() || reclamacao.getReclamationType() == null) {
			return true;
		}else {
			return false;
		}
	}
	
	public void buscarEndereco(AjaxBehaviorEvent event) {
		endereco = CepUtil.buscarEndereco(this.cep);
	}
	
	public String redirectNovaReclamacao() {
		return FacesUtil.sendRedirect("/paginas/sac/reclamacao/novaReclamacao");
	}
	
	public String redirectSac() {
		return FacesUtil.sendRedirect("/paginas/sac/sac");
	}

	public List<ReclamationType> getTipoReclamacaoList() {
		return tipoReclamacaoList;
	}

	public void setTipoReclamacaoList(List<ReclamationType> tipoReclamacaoList) {
		this.tipoReclamacaoList = tipoReclamacaoList;
	}

	public Reclamation getReclamacao() {
		return reclamacao;
	}

	public void setReclamacao(Reclamation reclamacao) {
		this.reclamacao = reclamacao;
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

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}


	public List<Reclamation> getReclamacoes() {
		return reclamacoes;
	}

	public void setReclamacoes(List<Reclamation> reclamacoes) {
		this.reclamacoes = reclamacoes;
	}

	public Gravity getGravidade() {
		return gravidade;
	}

	public void setGravidade(Gravity gravidade) {
		this.gravidade = gravidade;
	}

}
