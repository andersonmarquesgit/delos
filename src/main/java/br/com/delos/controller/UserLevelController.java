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
	
	private List<UserLevel> userLevelList;
	private UserLevel userLevel;
	private UserLevel userLevelSelected;
	
	@Autowired
	private UserLevelService nivelUsuarioService;
	
	@PostConstruct
	public void init() {
		this.initObjects();
	}

	private void initObjects() {
		this.initUserLevel();
		userLevelList = nivelUsuarioService.list();
	}
	
	private void initUserLevel() {
		userLevel = new UserLevel();
	}

	public void addUserLevel() {
		if(userLevel.getName().isEmpty()) {
			FacesUtil.adicionarErro(MsgConstantes.VALIDACAO_CAMPOS_OBRIGATORIOS);
		}else{
			RequestContext.getCurrentInstance().execute("PF('confirmInclusaoNivelUsuario').show();");
		}
	}
	
	public void confirmAddUserLevel() {
		RequestContext.getCurrentInstance().execute("PF('modalUserLevel').hide();");
		userLevel.setDateInclusion(new Date());
		Boolean ehEdicao = (this.userLevel.getId() != null);
		nivelUsuarioService.salvar(userLevel);
		if(ehEdicao) {
			FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO_EDICAO);
		}else{
			FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO);
		}
		this.initObjects();
		RequestContext.getCurrentInstance().update("formUserLevel");
	}
	
	public void cancelAddUserLevel() {
		this.initObjects();
		RequestContext.getCurrentInstance().update("formUserLevel");
		RequestContext.getCurrentInstance().execute("PF('modalUserLevel').hide();");
	}
	
	public void deleteUserLevel() {
		nivelUsuarioService.remover(this.userLevelSelected);
		this.initObjects();
		RequestContext.getCurrentInstance().update("formUserLevel");
		FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO_EXCLUSAO);
	}
	
	public void confirmDeleteUserLevel(UserLevel userLevelSelected) {
		this.userLevelSelected = userLevelSelected;
		RequestContext.getCurrentInstance().execute("PF('confirmExclusaoNivelUsuario').show();");
	}
	
	public void cancelDeleteUserLevel() {
		RequestContext.getCurrentInstance().execute("PF('confirmExclusaoNivelUsuario').hide();");
	}
	
	public void editUserLevel(UserLevel userLevelSelected) {
		this.userLevel = userLevelSelected;
		RequestContext.getCurrentInstance().update("formUserLevel");
		RequestContext.getCurrentInstance().execute("PF('modalUserLevel').show();");
	}
	
	//Gets e Sets ==============================================================================================
	public List<UserLevel> getUserLevelList() {
		return userLevelList;
	}

	public void setUserLevelList(List<UserLevel> userLevelList) {
		this.userLevelList = userLevelList;
	}

	public UserLevel getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(UserLevel userLevel) {
		this.userLevel = userLevel;
	}

	public UserLevel getUserLevelSelected() {
		return userLevelSelected;
	}

	public void setUserLevelSelected(UserLevel userLevelSelected) {
		this.userLevelSelected = userLevelSelected;
	}
}
