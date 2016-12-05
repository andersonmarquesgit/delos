package br.com.delos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.delos.model.UserLevel;

@Repository
public interface UserLevelRepository extends JpaRepository<UserLevel, Long> {

}
