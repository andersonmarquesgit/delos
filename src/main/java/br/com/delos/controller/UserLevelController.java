package br.com.delos.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.delos.model.UserLevel;
import br.com.delos.service.UserLevelService;
import br.com.delos.utils.FacesUtil;
import br.com.delos.utils.MsgConstantes;

@Scope("view")
@Controller
public class UserLevelController implements Serializable{
	private static final long serialVersionUID = -7580482456782445297L;
	
	private List<UserLevel> niveisDeUsuario;
	private UserLevel nivelUsuario;
	private UserLevel nivelUsuarioSelecionado;
	
	@Autowired
	private UserLevelService nivelUsuarioService;
	
	@PostConstruct
	public void init() {
		this.inicializarObjetosDaTela();
	}

	private void inicializarObjetosDaTela() {
		this.inicializarNivelUsuario();
		niveisDeUsuario = nivelUsuarioService.list();
	}
	
	private void inicializarNivelUsuario() {
		nivelUsuario = new UserLevel();
	}

	public void adicionarNivelUsuario() {
		if(nivelUsuario.getName().isEmpty()) {
			FacesUtil.adicionarErro(MsgConstantes.VALIDACAO_CAMPOS_OBRIGATORIOS);
		}else{
			RequestContext.getCurrentInstance().execute("PF('confirmInclusaoNivelUsuario').show();");
		}
	}
	
	public void confirmSalvarNivelUsuario() {
		RequestContext.getCurrentInstance().execute("PF('modalNivelUsuario').hide();");
		nivelUsuario.setDateInclusion(new Date());
		Boolean ehEdicao = (this.nivelUsuario.getId() == null);
		nivelUsuarioService.salvar(nivelUsuario);
		if(ehEdicao) {
			FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO);
		}else{
			FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO_EDICAO);
		}
		this.inicializarObjetosDaTela();
		RequestContext.getCurrentInstance().update("formNiveisUsuarios");
	}
	
	public void cancelarInclusao() {
		this.inicializarObjetosDaTela();
		RequestContext.getCurrentInstance().update("formNiveisUsuarios");
		RequestContext.getCurrentInstance().execute("PF('modalNivelUsuario').hide();");
	}
	
	public void excluirNivelUsuario() {
		nivelUsuarioService.remover(this.nivelUsuarioSelecionado);
		this.inicializarObjetosDaTela();
		RequestContext.getCurrentInstance().update("formNiveisUsuarios");
		FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO_EXCLUSAO);
	}
	
	public void confirmarExclusaoNivelUsuario(UserLevel nivelUsuarioSelecionado) {
		this.nivelUsuarioSelecionado = nivelUsuarioSelecionado;
		RequestContext.getCurrentInstance().execute("PF('confirmExclusaoNivelUsuario').show();");
	}
	
	public void cancelarExclusao() {
		RequestContext.getCurrentInstance().execute("PF('confirmExclusaoNivelUsuario').hide();");
	}
	
	public void editarNivelUsuario(UserLevel nivelUsuarioSelecionado) {
		this.nivelUsuario = nivelUsuarioSelecionado;
		RequestContext.getCurrentInstance().update("formNiveisUsuarios");
		RequestContext.getCurrentInstance().execute("PF('modalNivelUsuario').show();");
	}
	
	//Gets e Sets ==============================================================================================
	public List<UserLevel> getNiveisDeUsuario() {
		return niveisDeUsuario;
	}

	public void setNiveisDeUsuario(List<UserLevel> niveisDeUsuario) {
		this.niveisDeUsuario = niveisDeUsuario;
	}

	public UserLevel getNivelUsuario() {
		return nivelUsuario;
	}

	public void setNivelUsuario(UserLevel nivelUsuario) {
		this.nivelUsuario = nivelUsuario;
	}

	public UserLevel getNivelUsuarioSelecionado() {
		return nivelUsuarioSelecionado;
	}

	public void setNivelUsuarioSelecionado(UserLevel nivelUsuarioSelecionado) {
		this.nivelUsuarioSelecionado = nivelUsuarioSelecionado;
	}
}
