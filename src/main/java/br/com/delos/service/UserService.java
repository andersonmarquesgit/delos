package br.com.delos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.delos.model.User;
import br.com.delos.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;

	public User findOn(Long id) {
		return userRepository.findOne(id);
	}

	public User findByLogin(String login) {
		return userRepository.findByLogin(login);
	}

	public List<User> list() {
		return userRepository.findAll();
	}

	@Transactional
	public void salvar(User usuario) {
		userRepository.saveAndFlush(usuario);
	}
}
