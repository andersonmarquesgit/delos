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
	
	public String redirectNovaAnaliseCritica() {
		return FacesUtil.sendRedirect("/paginas/analisecritica/novaAnaliseCritica");
	}

	public void adicionarAnaliseCritica() {
		if(validarCamposObrigatorios()) {
			FacesUtil.adicionarErro(MsgConstantes.VALIDACAO_CAMPOS_OBRIGATORIOS);
		}else{
			RequestContext.getCurrentInstance().execute("PF('confirmInclusaoAnaliseCritica').show();");
		}
	}
	
	/**
	 * @return Redirect para a página principal de Análise Crítica. 
	 */
	public String confirmSalvarAnaliseCritica() {
		RequestContext.getCurrentInstance().execute("PF('confirmInclusaoAnaliseCritica').hide();");
		this.criticalAnalisys.setDateInclusion(DateUtil.getDataAtual());
		this.criticalAnalisys.setNumber(criticalAnalisysService.constructNumberCriticalAnalisys(DateUtil.getDataAtual()));
		criticalAnalisysService.save(criticalAnalisys);
		this.initObjects();
		RequestContext.getCurrentInstance().update("formNovaReclamacao");
		RequestContext.getCurrentInstance().update("formAnaliseCritica");
		FacesUtil.obterFlashScope().setKeepMessages(true);
		FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO);
		return this.redirectAnaliseCritica();
	}
	
	public Boolean validarCamposObrigatorios() {
		Boolean existeCompoNulo = false;
		for (SectionCriticalAnalisys secaoAc : criticalAnalisys.getSectionCriticalAnalisys()) {
			if(secaoAc.getConclusion() == null
					|| secaoAc.getReferences().isEmpty()
					|| secaoAc.getReferences() == null) {
				existeCompoNulo = true;
			}
			
			for (ItemCriticalAnalisys itemAc : secaoAc.getItemsCriticalAnalisys()) {
				if(itemAc.getResult() == null) {
					existeCompoNulo = true;
				}
			}
		}
		
		return existeCompoNulo;
	}
	
	public String redirectAnaliseCritica() {
		return FacesUtil.sendRedirect("/paginas/analisecritica/analiseCritica");
	}
	
	public String cancelarNovaAnaliseCritica() {
		this.initObjects();
		RequestContext.getCurrentInstance().update("formAnaliseCritica");
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
