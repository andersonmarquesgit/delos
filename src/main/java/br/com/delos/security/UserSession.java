package br.com.delos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import br.com.delos.model.User;
import br.com.delos.service.UserService;
import br.com.delos.utils.Constantes;
import br.com.delos.utils.FacesUtil;

@Scope("session")
@Controller
public class UserSession {
	
	@Autowired
	private UserService userService;
	
	public User obterUsuarioLogado() {
		SecurityContext context = SecurityContextHolder.getContext();
		User usuarioLogado = null;
		
		if(context instanceof SecurityContext) {
			Authentication authentication = context.getAuthentication();
			if(authentication instanceof Authentication) {
				usuarioLogado = userService.findByLogin(((org.springframework.security.core.userdetails.User)authentication.getPrincipal()).getUsername());
				FacesUtil.setSessionAttribute(Constantes.PROPRIEDADE_USUARIO_LOGADO, usuarioLogado);
			}
		}
		return usuarioLogado;
	}

}
