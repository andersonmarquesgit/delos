package br.com.delos.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.delos.model.CriticalAnalisys;
import br.com.delos.model.Section;
import br.com.delos.model.SectionCriticalAnalisys;

@Service
public class SectionCriticalAnalisysService {

	@Autowired
	private SectionService sectionService;
	
	@Autowired
	private ItemCriticalAnalisysService itemCriticalAnalisysService;
	
	public List<SectionCriticalAnalisys> constructSectionCriticalAnalisys(CriticalAnalisys criticalAnalisys) {
		List<SectionCriticalAnalisys> sections = new ArrayList<SectionCriticalAnalisys>();
		for (Section section : sectionService.list()) {
			SectionCriticalAnalisys sectionCriticalAnalisys = new SectionCriticalAnalisys();
			sectionCriticalAnalisys.setSection(section);
			sectionCriticalAnalisys.setCriticalAnalisys(criticalAnalisys);
			sectionCriticalAnalisys.setItemsCriticalAnalisys(itemCriticalAnalisysService.constructItemsCriticalAnalisys(sectionCriticalAnalisys, section));
			sections.add(sectionCriticalAnalisys);
		}
		
		return sections;
	}

}
