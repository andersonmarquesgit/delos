package br.com.delos.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.chartistjsf.model.chart.AspectRatio;
import org.chartistjsf.model.chart.Axis;
import org.chartistjsf.model.chart.AxisType;
import org.chartistjsf.model.chart.BarChartModel;
import org.chartistjsf.model.chart.BarChartSeries;
import org.chartistjsf.model.chart.LineChartModel;
import org.chartistjsf.model.chart.LineChartSeries;
import org.chartistjsf.model.chart.PieChartModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.delos.model.Reclamation;
import br.com.delos.model.ReclamationType;
import br.com.delos.service.ReclamationService;
import br.com.delos.service.ReclamationTypeService;
import br.com.delos.utils.DateUtil;
import br.com.delos.utils.enums.GravityEnum;
import br.com.delos.utils.enums.ReclamationStatusEnum;

@Scope("view")
@Controller
public class ChartBean implements Serializable{

	private static final long serialVersionUID = 600753791164951038L;

	private BarChartModel chartReclamacoes;
	private PieChartModel pieChartReclamacoes;
	private LineChartModel lineChartReclamacoes;
	
	private List<Integer> listaAnos;
	private Integer anoSelecionado;
	private Integer totalDefinir;
	private Integer totalBaixa;
	private Integer totalMedia;
	private Integer totalAlta;
	
	@Autowired
	private ReclamationService reclamacaoService;
	
	@Autowired
	private ReclamationTypeService tipoReclamacaoService;

	@PostConstruct
	public void init() {
		anoSelecionado = DateUtil.getAnoAtual();
		listaAnos = DateUtil.getListaAnos(anoSelecionado-4);
		createChartReclamations();
		createPieChartReclamatons();
		criarLineChartReclamacoes();
		loadTotalsByStatus();
	}

	public void loadTotalsByStatus() {
		List<Reclamation> reclamacoesPorMesEAno = reclamacaoService.listarPorMesEAno(DateUtil.getMesAtual(), DateUtil.getAnoAtual());
		this.totalDefinir = 0;
		this.totalBaixa = 0;
		this.totalMedia = 0;
		this.totalAlta = 0;
		
		for (Reclamation reclamacao : reclamacoesPorMesEAno) {
			if(reclamacao.getGravity().getId() == GravityEnum.BAIXA.getId()){
				totalBaixa += 1;
			}else  if(reclamacao.getGravity().getId() == GravityEnum.MEDIA.getId()){
				totalMedia += 1;
			}else if(reclamacao.getGravity().getId() == GravityEnum.ALTA.getId()){
				totalAlta += 1;
			}else{
				totalDefinir += 1;
			}
		}
	}

	public void selecionarAno() {
		createChartReclamations();
		createPieChartReclamatons();
	}
	private void criarLineChartReclamacoes() {
		lineChartReclamacoes = initLineChartReclamacoes();
		lineChartReclamacoes.setAspectRatio(AspectRatio.OCTAVE);
		lineChartReclamacoes.setShowTooltip(true);
		lineChartReclamacoes.setAnimateAdvanced(true);
	}
	
	private LineChartModel initLineChartReclamacoes() {
		LineChartModel model = new LineChartModel();
		LineChartSeries totalReclamacoes = new LineChartSeries();
		totalReclamacoes.setName("Total");
		
		for (Integer ano : listaAnos){
			model.addLabel(ano);
		}
		
		for (Integer ano : listaAnos) {
			Long valor = reclamacaoService.calculaReclamacoesPorAno(ano);
			totalReclamacoes.set(valor);
		}
		
		model.addSeries(totalReclamacoes);
		return model;
	}

	public void createChartReclamations() {
		chartReclamacoes = initChartReclamacoes();
		chartReclamacoes.setAspectRatio(AspectRatio.OCTAVE);
		chartReclamacoes.setShowTooltip(true);
		chartReclamacoes.setSeriesBarDistance(15);
		chartReclamacoes.setAnimateAdvanced(true);

		Axis xAxis = chartReclamacoes.getAxis(AxisType.X);
		xAxis.setShowGrid(false);
	}
	
	private BarChartModel initChartReclamacoes() {
		BarChartModel model = new BarChartModel();

		BarChartSeries analiseGravidade = new BarChartSeries();
		analiseGravidade.setName(ReclamationStatusEnum.ANALISE_GRAVIDADE.getValue());
		
		for (Integer mes = 1; mes <= DateUtil.MESES_DO_ANO.length; mes++){
			model.addLabel(DateUtil.MESES_DO_ANO[mes-1].substring(0, 3));
		}
			
		for (Integer mes = 1; mes <= DateUtil.MESES_DO_ANO.length; mes++) {
			Long valor = reclamacaoService.calculaReclamacoesNoPeriodo(mes, anoSelecionado,
					ReclamationStatusEnum.ANALISE_GRAVIDADE.getId());
			analiseGravidade.set(valor);
		}
		
		BarChartSeries analiseAcao = new BarChartSeries();
		analiseAcao.setName(ReclamationStatusEnum.ACAO_TOMADA.getValue());
		
		for (Integer mes = 1; mes <= DateUtil.MESES_DO_ANO.length; mes++) {
			Long valor = reclamacaoService.calculaReclamacoesNoPeriodo(mes, anoSelecionado,
					ReclamationStatusEnum.ACAO_TOMADA.getId());
			analiseAcao.set(valor);
		}
		
		BarChartSeries analiseAceiteCliente = new BarChartSeries();
		analiseAceiteCliente.setName(ReclamationStatusEnum.ACEITE_CLIENTE.getValue());
		
		for (Integer mes = 1; mes <= DateUtil.MESES_DO_ANO.length; mes++) {
			Long valor = reclamacaoService.calculaReclamacoesNoPeriodo(mes, anoSelecionado,
					ReclamationStatusEnum.ACEITE_CLIENTE.getId());
			analiseAceiteCliente.set(valor);
		}
		
		BarChartSeries concluidas = new BarChartSeries();
		concluidas.setName(ReclamationStatusEnum.CONCLUIDA.getValue());
		
		for (Integer mes = 1; mes <= DateUtil.MESES_DO_ANO.length; mes++) {
			Long valor = reclamacaoService.calculaReclamacoesNoPeriodo(mes, anoSelecionado,
					ReclamationStatusEnum.CONCLUIDA.getId());
			concluidas.set(valor);
		}
		
		model.addSeries(analiseGravidade);
		model.addSeries(analiseAcao);
		model.addSeries(analiseAceiteCliente);
		model.addSeries(concluidas);
		
		return model;
	}

	public void createPieChartReclamatons() {
		pieChartReclamacoes = new PieChartModel();
		pieChartReclamacoes.setAspectRatio(AspectRatio.MAJOR_SEVENTH);
		List<ReclamationType> listTiposReclamacao = tipoReclamacaoService.list();
		
		for(ReclamationType tipoReclamacao : listTiposReclamacao) {
			pieChartReclamacoes.addLabel(tipoReclamacao.getDescription());		
		}
		
		for(ReclamationType tipoReclamacao : listTiposReclamacao) {
			pieChartReclamacoes.set(reclamacaoService.calcularReclamacoesPorTipoNoAno(anoSelecionado, tipoReclamacao));
		}
        
		pieChartReclamacoes.setShowTooltip(true);
	}
	
	//Gets e Sets =======================================================================================================================
	public BarChartModel getChartReclamacoes() {
		return chartReclamacoes;
	}

	public void setChartReclamacoes(BarChartModel chartReclamacoes) {
		this.chartReclamacoes = chartReclamacoes;
	}

	public List<Integer> getListaAnos() {
		return listaAnos;
	}

	public void setListaAnos(List<Integer> listaAnos) {
		this.listaAnos = listaAnos;
	}

	public Integer getAnoSelecionado() {
		return anoSelecionado;
	}

	public void setAnoSelecionado(Integer anoSelecionado) {
		this.anoSelecionado = anoSelecionado;
	}

	public PieChartModel getPieChartReclamacoes() {
		return pieChartReclamacoes;
	}

	public void setPieChartReclamacoes(PieChartModel pieChartReclamacoes) {
		this.pieChartReclamacoes = pieChartReclamacoes;
	}

	public LineChartModel getLineChartReclamacoes() {
		return lineChartReclamacoes;
	}

	public void setLineChartReclamacoes(LineChartModel lineChartReclamacoes) {
		this.lineChartReclamacoes = lineChartReclamacoes;
	}

	public Integer getTotalDefinir() {
		return totalDefinir;
	}

	public void setTotalDefinir(Integer totalDefinir) {
		this.totalDefinir = totalDefinir;
	}

	public Integer getTotalBaixa() {
		return totalBaixa;
	}

	public void setTotalBaixa(Integer totalBaixa) {
		this.totalBaixa = totalBaixa;
	}

	public Integer getTotalMedia() {
		return totalMedia;
	}

	public void setTotalMedia(Integer totalMedia) {
		this.totalMedia = totalMedia;
	}

	public Integer getTotalAlta() {
		return totalAlta;
	}

	public void setTotalAlta(Integer totalAlta) {
		this.totalAlta = totalAlta;
	}

}
