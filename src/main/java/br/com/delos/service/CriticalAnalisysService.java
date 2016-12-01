package br.com.delos.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.delos.model.CriticalAnalisys;
import br.com.delos.repository.CriticalAnalisysRepository;
import br.com.delos.utils.Constantes;
import br.com.delos.utils.DateUtil;

@Service
public class CriticalAnalisysService {

	@Autowired
	private CriticalAnalisysRepository criticalAnalisysRepository;
	
	@Autowired
	private SectionCriticalAnalisysService sectionCriticalAnalisysService;
	
	@Transactional
	public CriticalAnalisys save(CriticalAnalisys criticalAnalisys) {
		return criticalAnalisysRepository.saveAndFlush(criticalAnalisys);
	}

	public List<CriticalAnalisys> list() {
		return criticalAnalisysRepository.findAll(orderByDateInclusion());
	}
	
	private Sort orderByDateInclusion() {
	    return new Sort(Sort.Direction.DESC, "dateInclusion");
	}

	public CriticalAnalisys inicializarNovaAnaliseCritica() {
		CriticalAnalisys criticalAnalisys = new CriticalAnalisys();
		criticalAnalisys.setSectionCriticalAnalisys(sectionCriticalAnalisysService.constructSectionCriticalAnalisys(criticalAnalisys));
		return criticalAnalisys;
	}
	
	public String constructNumberCriticalAnalisys(Date currentDate){
		SimpleDateFormat yyyy = new SimpleDateFormat("yyyy");  
		SimpleDateFormat mm = new SimpleDateFormat("MM");
		SimpleDateFormat dd = new SimpleDateFormat("dd");
		String year = yyyy.format(currentDate);
		String month = mm.format(currentDate);
		String day = dd.format(currentDate);
		Long amountCriticalAnalisysInDay = this.countCriticalAnalisysInDay(currentDate);
		String numberCriticalAnalisys = year + month + day + this.formatAmountCriticalAnalisysInDay(amountCriticalAnalisysInDay);
		return numberCriticalAnalisys;
	}

	private String formatAmountCriticalAnalisysInDay(Long amountCriticalAnalisysInDay) {
		return StringUtils.rightPad(amountCriticalAnalisysInDay.toString(), Constantes.QTD_ZEROS_NUM_ANALISE_CRITICA, "0");
	}
	
	private Long countCriticalAnalisysInDay(Date dataAtual) {
		Long qtd = 1L;
		for (CriticalAnalisys criticalAnalisys : criticalAnalisysRepository.findAll()) {
			if(DateUtil.getDataFormatada(criticalAnalisys.getDateInclusion(), DateUtil.DIA_MES_ANO)
					.equals(DateUtil.getDataFormatada(dataAtual, DateUtil.DIA_MES_ANO))) {
				qtd++;
			}
		}
		return qtd;
	}
}
