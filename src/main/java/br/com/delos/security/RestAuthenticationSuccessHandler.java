package br.com.delos.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import br.com.delos.model.Role;
import br.com.delos.model.User;
import br.com.delos.repository.UserRepository;
import br.com.delos.utils.SecurityUtil;

@Component
public class RestAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Autowired
	private UserRepository userRepository;

	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Role role) throws ServletException, IOException {
		User user = userRepository.findByLogin(role.getName());
		SecurityUtil.sendResponse(response, HttpServletResponse.SC_OK, user);
	} 

}
