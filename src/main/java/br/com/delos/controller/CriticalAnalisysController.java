/**
 * 
 */
package br.com.delos.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.delos.model.Conclusion;
import br.com.delos.model.CriticalAnalisys;
import br.com.delos.model.ItemCriticalAnalisys;
import br.com.delos.model.Result;
import br.com.delos.model.SectionCriticalAnalisys;
import br.com.delos.service.ConclusionService;
import br.com.delos.service.CriticalAnalisysService;
import br.com.delos.service.ResultService;
import br.com.delos.utils.DateUtil;
import br.com.delos.utils.FacesUtil;
import br.com.delos.utils.MsgConstantes;

@Scope("view")
@Controller
public class CriticalAnalisysController {
	
	private List<CriticalAnalisys> analisysList;
	
	@Autowired
	private CriticalAnalisysService criticalAnalisysService;
	
	@Autowired
	private ResultService resultService;
	
	@Autowired
	private ConclusionService conclusionService;
	
	private CriticalAnalisys criticalAnalisys;
	
	private List<Result> resultList;
	
	private List<Conclusion> conclusionList;
	
	@PostConstruct
	public void init() {
		this.initObjects();
	}
	
	private void initObjects() {
		this.initCriticalAnalisysList();
		this.initCriticalAnalisys();
		this.initResultList();
		this.initConclusionList();
	}
	
	private void initCriticalAnalisysList() {
		analisysList = criticalAnalisysService.list();
	}
	
	private void initCriticalAnalisys() {
		criticalAnalisys = criticalAnalisysService.inicializarNovaAnaliseCritica();
	}
	
	private void initResultList() {
		resultList = resultService.list();
	}
	
	private void initConclusionList() {
		conclusionList = conclusionService.list();
	}
	
	public String redirectNewCriticalAnalisys() {
		return FacesUtil.sendRedirect("/pages/criticalanalisys/newCriticalAnalisys");
	}

	public void addCriticalAnalisys() {
		if(validateRequiredFields()) {
			FacesUtil.adicionarErro(MsgConstantes.VALIDACAO_CAMPOS_OBRIGATORIOS);
		}else{
			RequestContext.getCurrentInstance().execute("PF('confirmInclusaoAnaliseCritica').show();");
		}
	}
	
	/**
	 * @return Redirect para a página principal de Análise Crítica. 
	 */
	public String confirmSaveCriticalAnalisys() {
		RequestContext.getCurrentInstance().execute("PF('confirmInclusaoAnaliseCritica').hide();");
		this.criticalAnalisys.setDateInclusion(DateUtil.getDataAtual());
		this.criticalAnalisys.setNumber(criticalAnalisysService.constructNumberCriticalAnalisys(DateUtil.getDataAtual()));
		criticalAnalisysService.save(criticalAnalisys);
		this.initObjects();
		RequestContext.getCurrentInstance().update("formNewCriticalAnalisys");
		RequestContext.getCurrentInstance().update("formCriticalAnalisys");
		FacesUtil.obterFlashScope().setKeepMessages(true);
		FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO);
		return this.redirectAnaliseCritica();
	}
	
	public Boolean validateRequiredFields() {
		Boolean existFieldNull = false;
		for (SectionCriticalAnalisys sectionCA : criticalAnalisys.getSectionCriticalAnalisys()) {
			if(sectionCA.getConclusion() == null
					|| sectionCA.getReferences().isEmpty()
					|| sectionCA.getReferences() == null) {
				existFieldNull = true;
			}
			
			for (ItemCriticalAnalisys itemAc : sectionCA.getItemsCriticalAnalisys()) {
				if(itemAc.getResult() == null) {
					existFieldNull = true;
				}
			}
		}
		
		return existFieldNull;
	}
	
	public String redirectAnaliseCritica() {
		return FacesUtil.sendRedirect("/pages/criticalanalisys/criticalAnalisys");
	}
	
	public String cancelNewCriticalAnalisys() {
		this.initObjects();
		RequestContext.getCurrentInstance().update("formCriticalAnalisys");
		return this.redirectAnaliseCritica();
	}

	public List<CriticalAnalisys> getAnalisysList() {
		return analisysList;
	}

	public void setAnalisysList(List<CriticalAnalisys> analisysList) {
		this.analisysList = analisysList;
	}

	public CriticalAnalisysService getCriticalAnalisysService() {
		return criticalAnalisysService;
	}

	public void setCriticalAnalisysService(CriticalAnalisysService criticalAnalisysService) {
		this.criticalAnalisysService = criticalAnalisysService;
	}

	public ResultService getResultService() {
		return resultService;
	}

	public void setResultService(ResultService resultService) {
		this.resultService = resultService;
	}

	public ConclusionService getConclusionService() {
		return conclusionService;
	}

	public void setConclusionService(ConclusionService conclusionService) {
		this.conclusionService = conclusionService;
	}

	public CriticalAnalisys getCriticalAnalisys() {
		return criticalAnalisys;
	}

	public void setCriticalAnalisys(CriticalAnalisys criticalAnalisys) {
		this.criticalAnalisys = criticalAnalisys;
	}

	public List<Result> getResultList() {
		return resultList;
	}

	public void setResultList(List<Result> resultList) {
		this.resultList = resultList;
	}

	public List<Conclusion> getConclusionList() {
		return conclusionList;
	}

	public void setConclusionList(List<Conclusion> conclusionList) {
		this.conclusionList = conclusionList;
	}
	
}
