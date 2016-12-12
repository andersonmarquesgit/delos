package br.com.delos.utils;

import br.com.delos.model.User;

public abstract class AccessUtil {

	/**
	 * Método estático para obter o usuário logado no sistema.
	 *  
	 * @return retorna um objeto do tipo Usuario.
	 */
	public static User obterUsuarioLogado(){
		return (User) FacesUtil.getSessionAttribute(Constantes.PROPRIEDADE_USUARIO_LOGADO);		
	}
	
}
