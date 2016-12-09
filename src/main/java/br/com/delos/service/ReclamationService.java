package br.com.delos.service;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.delos.model.Reclamation;
import br.com.delos.model.ReclamationType;
import br.com.delos.repository.ReclamationRepository;
import br.com.delos.utils.Constantes;
import br.com.delos.utils.DateUtil;
import br.com.delos.utils.EmailUtil;
import br.com.delos.utils.FacesUtil;
import br.com.delos.utils.MsgConstantes;

@Service
public class ReclamationService {
	
//	private static final Logger LOG = Logger.getLogger(ReclamacaoService.class);
	
	@Autowired
	private ReclamationRepository reclamacaoRepository;

	@Transactional
	public Reclamation salvar(Reclamation reclamacao) {
		return reclamacaoRepository.saveAndFlush(reclamacao);
	}

	public List<Reclamation> listar() {
		return reclamacaoRepository.findAll(orderByDataInclusao());
	}
	
	private Sort orderByDataInclusao() {
	    return new Sort(Sort.Direction.DESC, "dataInclusao");
	}

	public String construirNumeroDaReclamacao(Date dataAtual){
		SimpleDateFormat yyyy = new SimpleDateFormat("yyyy");  
		SimpleDateFormat mm = new SimpleDateFormat("MM");
		SimpleDateFormat dd = new SimpleDateFormat("dd");
		String ano = yyyy.format(dataAtual);
		String mes = mm.format(dataAtual);
		String dia = dd.format(dataAtual);
		Long qtdReclamacaoNoDia = this.buscarQtdReclamacaoNoDia(dataAtual);
		String numeroReclamacao = ano + mes + dia + this.formatarQtdReclamacaoNoDia(qtdReclamacaoNoDia);
		return numeroReclamacao;
	}

	private String formatarQtdReclamacaoNoDia(Long qtdReclamacaoNoDia) {
		return StringUtils.rightPad(qtdReclamacaoNoDia.toString(), Constantes.QTD_ZEROS_NUM_RECLAMACAO, "0");
	}
	
	private Long buscarQtdReclamacaoNoDia(Date dataAtual) {
		Long qtd = 1L;
		for (Reclamation reclamation : reclamacaoRepository.findAll()) {
			if(DateUtil.getDataFormatada(reclamation.getDateInclusion(), DateUtil.DIA_MES_ANO)
					.equals(DateUtil.getDataFormatada(dataAtual, DateUtil.DIA_MES_ANO))) {
				qtd++;
			}
		}
		return qtd;
	}

	public Long calculaReclamacoesNoPeriodo(Integer mes, Integer anoSelecionado, Long statusId) {
		return reclamacaoRepository.calculaReclamacoesNoMes(mes, anoSelecionado, statusId);
	}
	
	public Long calculaReclamacoesPorAno(Integer ano) {
		return reclamacaoRepository.calculaReclamacoesPorAno(ano);
	}

	public Number calcularReclamacoesPorTipoNoAno(Integer anoSelecionado,
			ReclamationType reclamationType) {
		return reclamacaoRepository.calcularReclamacoesPorTipoNoAno(anoSelecionado, reclamationType);
	}

	public void enviarEmail(Reclamation reclamation) {
		EmailUtil email = new EmailUtil("Teste de envio da reclamação", 
				FacesUtil.obterTexto(MsgConstantes.EMAIL_EMPRESA) + " - " +
				FacesUtil.obterTexto(MsgConstantes.EMAIL_REGISTRO_DE_RECLAMACAO) + " Nº " + reclamation.getNumber(), 
				reclamation.getCustomer().getEmail(), reclamation.getCustomer().getName());
		try {
			email.enviaEmailFormatoHtml(reclamation);
		} catch (EmailException e) {
			System.out.println(e);
//			LOG.error("Erro durante o envio do email", e);
		} catch (MalformedURLException e) {
			System.out.println(e);
//			LOG.error("Erro de formatação do email", e);
		}
		
	}

	public List<Reclamation> listarPorMesEAno(int mesAtual, int anoAtual) {
		return reclamacaoRepository.listarPorMesEAno(mesAtual, anoAtual);
	}
}
