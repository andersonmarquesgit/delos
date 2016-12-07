package br.com.delos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.delos.model.UserLevel;
import br.com.delos.repository.UserLevelRepository;
import br.com.delos.utils.SortUtil;

@Service
public class UserLevelService {

	@Autowired
	private UserLevelRepository userLevelRepository;
	
	public List<UserLevel> list() {
		List<UserLevel> levels = userLevelRepository.findAll();
		SortUtil.sortList(levels, true, "dateInclusion");
		return levels;
	}

	@Transactional
	public void salvar(UserLevel userLevel) {
		userLevelRepository.saveAndFlush(userLevel);
	}

	@Transactional
	public void remover(UserLevel selectedUserLevel) {
		userLevelRepository.delete(selectedUserLevel);
	}
}
