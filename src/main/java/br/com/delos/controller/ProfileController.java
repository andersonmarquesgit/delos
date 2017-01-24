package br.com.delos.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.delos.model.User;
import br.com.delos.service.UserService;
import br.com.delos.utils.AccessUtil;
import br.com.delos.utils.FacesUtil;
import br.com.delos.utils.MsgConstantes;

@Scope("view")
@Controller
public class ProfileController {
	
	private static final Logger logger = LogManager.getLogger(ProfileController.class);
	
	@Autowired
	private UserService usuarioService;
	
	private User usuarioLogado;
	
	@PostConstruct
	public void init(){
		this.usuarioLogado = AccessUtil.obterUsuarioLogado();
	}
	
	public void uploadFile(FileUploadEvent event) {
		InputStream inputStream;
		byte[] file = null;
		try {
			inputStream = event.getFile().getInputstream();
			file = new byte[inputStream.available()];
			inputStream.read(file);
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		RequestContext.getCurrentInstance().update("formPerfilUsuario");
	}
	
	public void salvar() {
		if (validarCamposObrigatorios()) {
			FacesUtil.adicionarErro(MsgConstantes.VALIDACAO_CAMPOS_OBRIGATORIOS);
		} else {
			RequestContext.getCurrentInstance().execute(
					"PF('confirmSalvarPerfil').show();");
		}
	}
	
	public String confirmSalvarPerfil() {
		RequestContext.getCurrentInstance().execute(
				"PF('confirmSalvarPerfil').hide();");
		usuarioService.salvar(this.usuarioLogado);
		RequestContext.getCurrentInstance().update("formPerfilUsuario");
		FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO_EDICAO);
		return redirectPerfil();
	}
	
	public String cancelarEdicaoPerfil() {
		return redirectDashboard();
	}
	
	public String redirectPerfil() {
		return FacesUtil.sendRedirect("/pages/user/profile");
	}
	
	public String redirectDashboard() {
		return FacesUtil.sendRedirect("/pages/index");
	}

	private boolean validarCamposObrigatorios() {
		if(this.usuarioLogado.getEmail() == null
				&& this.usuarioLogado.getName() == null){
			return true;
		}
		return false;
	}

	public User getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(User usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

}
