package br.com.delos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.delos.model.Section;
import br.com.delos.repository.SectionRepository;

@Service
public class SectionService {

	@Autowired
	private SectionRepository sectionRepository;
	
	public List<Section> list() {
		return sectionRepository.findAll();
	}

}
