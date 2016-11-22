package br.com.delos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.delos.model.User;


public interface UserRepository extends JpaRepository<User, Long>{

	public User findByLoginAndPassword(String login, String password);
	public User findByLogin(String login);

} 