package br.com.delos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.delos.model.Company;
import br.com.delos.repository.CompanyRepository;
import br.com.delos.utils.SortUtil;

@Service
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;
	
	public List<Company> list() {
		List<Company> companys = companyRepository.findAll();
		SortUtil.sortList(companys, true, "name");
		return companys;
	}
	
	@Transactional
	public void salvar(Company company) {
		companyRepository.saveAndFlush(company);
	}

	@Transactional
	public void remover(Company selectedCompany) {
		companyRepository.delete(selectedCompany);
	}
}
