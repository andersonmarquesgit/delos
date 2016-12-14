package br.com.delos.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.delos.model.Role;
import br.com.delos.model.User;
import br.com.delos.service.UserService;
import br.com.delos.utils.FacesUtil;
import br.com.delos.utils.MsgConstantes;


@Scope("view")
@Controller
public class UserController {
	
	private List<User> usuarios;
	
	private User usuario;
	
	@Autowired
	private UserService userService;
	
	@PostConstruct
	public void init() {
		this.inicializarObjetos();
	}

	private void inicializarObjetos() {
		usuario = new User();
		usuarios = userService.list();
	}
	
	public void adicionarUsuario() {
		if(validarCampoObrigatorios()) {
			FacesUtil.adicionarErro(MsgConstantes.VALIDACAO_CAMPOS_OBRIGATORIOS);
		}else{
			RequestContext.getCurrentInstance().execute("PF('confirmInclusaoUsuario').show();");
		}
	}
	
	public void confirmSalvarUsuario() {
		RequestContext.getCurrentInstance().update("formUsuarios");
		RequestContext.getCurrentInstance().execute("PF('modalUsuario').hide();");
//		usuario.setRole(Role.ROLE_USER);
		usuario.setRoles(null);
		usuario.setLogin(usuario.getEmail());
		usuario.setCompany(usuario.getUnit().getCompany());
		userService.salvar(usuario);
		this.inicializarObjetos();
		if(this.usuario.getId() == null) {
			FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO);
		}else{
			FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO_EDICAO);
		}
	}
	
	public void cancelarInclusao() {
		this.inicializarObjetos();
		RequestContext.getCurrentInstance().update("formUsuarios");
		RequestContext.getCurrentInstance().execute("PF('modalUsuario').hide();");
	}
	
	public boolean validarCampoObrigatorios(){
		if(this.usuario.getName().isEmpty() ||
				this.usuario.getEmail().isEmpty() ||
				this.usuario.getPassword().isEmpty()) {
			return true;
		}
		return false;
	}
	
	//Gets e Sets ==============================================================================================
	public List<User> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<User> usuarios) {
		this.usuarios = usuarios;
	}

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

}
