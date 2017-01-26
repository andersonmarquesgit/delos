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
	
	private List<User> userList;
	
	private User user;
	
	@Autowired
	private UserService userService;
	
	@PostConstruct
	public void init() {
		this.initObjects();
	}

	private void initObjects() {
		user = new User();
		userList = userService.list();
	}
	
	public void addUser() {
		if(validateUserRequiredFields()) {
			FacesUtil.adicionarErro(MsgConstantes.VALIDACAO_CAMPOS_OBRIGATORIOS);
		}else{
			RequestContext.getCurrentInstance().execute("PF('confirmInclusaoUsuario').show();");
		}
	}
	
	public void confirmAddUser() {
		RequestContext.getCurrentInstance().update("formUser");
		RequestContext.getCurrentInstance().execute("PF('modalUser').hide();");
//		usuario.setRole(Role.ROLE_USER);
		user.setRoles(null);
		user.setLogin(user.getEmail());
		user.setCompany(user.getUnit().getCompany());
		userService.salvar(user);
		this.initObjects();
		if(this.user.getId() == null) {
			FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO);
		}else{
			FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO_EDICAO);
		}
	}
	
	public void cancelAddUser() {
		this.initObjects();
		RequestContext.getCurrentInstance().update("formUser");
		RequestContext.getCurrentInstance().execute("PF('modalUser').hide();");
	}
	
	public boolean validateUserRequiredFields(){
		if(this.user.getName().isEmpty() ||
				this.user.getEmail().isEmpty() ||
				this.user.getPassword().isEmpty()) {
			return true;
		}
		return false;
	}
	
	//Gets e Sets ==============================================================================================
	
	public List<User> getUserList() {
		return userList;
	}
	
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

}
