package br.com.delos.service;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.delos.model.Document;
import br.com.delos.model.DocumentType;
import br.com.delos.repository.DocumentRepository;
import br.com.delos.repository.DocumentTypeRepository;
import br.com.delos.utils.FacesUtil;

@Service
public class DocumentService {

	@Autowired
	private DocumentRepository documentRepository;
	
	@Autowired
	private DocumentTypeRepository documentTypeRepository;
	
	public Document salvar(Document document) {
		return documentRepository.saveAndFlush(document);
	}

	public Document findOn(Long id) {
		return documentRepository.findOne(id);
	}
	
	public void visualizarDocumento(Document document) {
    	try {
            byte[] file = document.getConteudo();
            HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            res.setContentType("application/pdf");
            //Código abaixo gerar o relatório e disponibiliza diretamente na página 
            res.setHeader("Content-disposition", "inline;filename="+ document.getFileName());
            //Código abaixo gerar o relatório e disponibiliza para o cliente baixar ou salvar 
            //res.setHeader("Content-disposition", "attachment;filename=reclamacao.pdf");
            res.getOutputStream().write(file);
            res.getCharacterEncoding();
            FacesContext.getCurrentInstance().responseComplete();
        } catch (Exception e) {
        	FacesUtil.adicionarErro(e.getMessage());
        }
    }
	
//	public void visualizarDocumento(Document documento) {
//		//TODO: alterar para o documento selecionado
////    	Document documentoVisualizar = this.findOn(19L);
//    	
//        FacesContext fc = FacesContext.getCurrentInstance();
//
//        // Obtem o HttpServletResponse, objeto responsável pela resposta do
//        // servidor ao browser
//        HttpServletResponse response = (HttpServletResponse) fc
//              .getExternalContext().getResponse();
//
//        // Limpa o buffer do response
//        response.reset();
//
//        // Seta o tipo de conteudo no cabecalho da resposta. No caso, indica que o
//        // conteudo sera um documento pdf.
//        response.setContentType("application/pdf");
//
//        // Seta o tamanho do conteudo no cabecalho da resposta. No caso, o tamanho
//        // em bytes do pdf
//        response.setContentLength(documento.getConteudo().length);
//
//        // Seta o nome do arquivo e a disposição: "inline" abre no próprio navegador
//        // Mude para "attachment" para indicar que deve ser feito um download
//        response.setHeader("Content-disposition",
//              "inline; filename=arquivo.pdf");
//        try {
//
//           // Envia o conteudo do arquivo PDF para o response
//           response.getOutputStream().write(documento.getConteudo());
//
//           // Descarrega o conteudo do stream, forçando a escrita de qualquer byte
//           // ainda em buffer
//           response.getOutputStream().flush();
//
//           // Fecha o stream, liberando seus recursos
//           response.getOutputStream().close();
//
//           // Sinaliza ao JSF que a resposta HTTP para este pedido já foi gerada
//           fc.responseComplete();
//        } catch (IOException e) {
//           e.printStackTrace();
//        }
//		
//	}

	public List<Document> list() {
		return documentRepository.findAll();
	}

	/**
	 * A anotação @Transasional foi necessária para evitar a exceção @see PSQLException
	 * Caused by: org.postgresql.util.PSQLException: 
	 * Objetos Grandes não podem ser usados no modo de efetivação automática (auto-commit).
	 */
	@Transactional
	public List<Document> listByTipoDocumento(Long id, Pageable pageable) {
		DocumentType documentType = documentTypeRepository.findOne(id);
		return documentRepository.findByDocumentType(documentType, pageable);
	}
	
	@Transactional
	public Long countByTipoDocumento(Long id) {
		DocumentType documentType = documentTypeRepository.findOne(id);
		return documentRepository.countByDocumentType(documentType);
	}

	@Transactional
	public List<Document> listByTipoDocumento(Long id) {
		DocumentType documentType = documentTypeRepository.findOne(id);
		return documentRepository.listByDocumentType(documentType);
	}
	
}
