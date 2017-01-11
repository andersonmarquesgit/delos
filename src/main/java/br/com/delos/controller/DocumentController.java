package br.com.delos.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.delos.filters.DocumentLazyList;
import br.com.delos.model.Document;
import br.com.delos.model.DocumentType;
import br.com.delos.model.Factor;
import br.com.delos.service.DocumentService;
import br.com.delos.service.DocumentTypeService;
import br.com.delos.service.FactorService;
import br.com.delos.utils.FacesUtil;
import br.com.delos.utils.MsgConstantes;
import br.com.delos.utils.enums.DocumentTypeEnum;

@Scope("session")
@Controller
public class DocumentController {

	private Document document;
	private Document documentSelected;
	private LazyDataModel<Document> documentosProcedimentos;
	private LazyDataModel<Document> documentosPoliticas;
	private LazyDataModel<Document> documentosTreinamentos;
	private LazyDataModel<Document> documentosDesignacoes;
	private LazyDataModel<Document> docExternalList;
	private List<DocumentType> tiposDeDocumentos;
	private List<DocumentType> documentoTypeExternalList;
	private List<Factor> elementList;
	private StreamedContent streamedContent;
    private InputStream stream;
    
	@Autowired
	private DocumentTypeService tipoDocumentoService;
	
	@Autowired
	private FactorService elementoService;

	@Autowired
	private DocumentService documentoService;
	
	@PostConstruct
	public void init() {
		this.inicializarObjetos();
	}

	public void inicializarObjetos() {
		inicializarDocumentosInternos();
		inicializarDocumentosExternos();
		inicializarElementosENovoDoc();
	}

	private void inicializarElementosENovoDoc() {
		elementList = elementoService.list();
		document = new Document();
	}

	private void inicializarDocumentosExternos() {
		docExternalList = new DocumentLazyList(DocumentTypeEnum.DOCUMENTOS_COMPLEMENTARES.getId(), documentoService);
		documentoTypeExternalList = tipoDocumentoService.findById(DocumentTypeEnum.DOCUMENTOS_COMPLEMENTARES.getId());
	}

	private void inicializarDocumentosInternos() {
		tiposDeDocumentos = tipoDocumentoService.list();
		documentosProcedimentos = new DocumentLazyList(DocumentTypeEnum.PROCEDIMENTO.getId(), documentoService);
		documentosPoliticas = new DocumentLazyList(DocumentTypeEnum.POLITICA_TRATAMENTO_RECLAMACAO.getId(), documentoService);
		documentosTreinamentos = new DocumentLazyList(DocumentTypeEnum.TREINAMENTO.getId(), documentoService);
		documentosDesignacoes = new DocumentLazyList(DocumentTypeEnum.DESIGNACAO.getId(), documentoService);
	}
	
	public LazyDataModel<Document> listDocumentosProcedimentos(){
		return new DocumentLazyList(DocumentTypeEnum.PROCEDIMENTO.getId(), documentoService);
	}
	
	public LazyDataModel<Document> listDocumentosPoliticas(){
		return new DocumentLazyList(DocumentTypeEnum.POLITICA_TRATAMENTO_RECLAMACAO.getId(), documentoService);
	}
	
	public LazyDataModel<Document> listDocumentosTreinamentos(){
		return new DocumentLazyList(DocumentTypeEnum.TREINAMENTO.getId(), documentoService);
	}
	
	public LazyDataModel<Document> listDocumentosDesignacoes(){
		return new DocumentLazyList(DocumentTypeEnum.DESIGNACAO.getId(), documentoService);
	}
	
	public LazyDataModel<Document> listDocumentosExternos(){
		return new DocumentLazyList(DocumentTypeEnum.DOCUMENTOS_COMPLEMENTARES.getId(), documentoService);
	}
	
	public void addDocExternal() {
		RequestContext.getCurrentInstance().execute("PF('modalAddDocumento').show();");
	}
	
	public void cancelSendDocExternal() {
		this.inicializarDocumentosExternos();
		this.inicializarElementosENovoDoc();
		RequestContext.getCurrentInstance().update("modalAddDocumento");
		RequestContext.getCurrentInstance().execute("PF('modalAddDocumento').hide();");
	}
	
	public void adicionarDocInterno() {
		RequestContext.getCurrentInstance().execute("PF('modalAddDocumentoInterno').show();");
	}
	
	public void cancelarEnvioDocInterno() {
		this.inicializarDocumentosInternos();
		this.inicializarElementosENovoDoc();
		RequestContext.getCurrentInstance().update("formAddDocumentoInterno");
		RequestContext.getCurrentInstance().execute("PF('modalAddDocumentoInterno').hide();");
	}
	
	public void visualizarDocumento(Document documentoSelecionado) {
		documentoService.visualizarDocumento(documentoSelecionado);
	}
	
	public void uploadFile(FileUploadEvent event)throws IOException{
		InputStream inputStream = event.getFile().getInputstream();
		byte[] conteudo = new byte[inputStream.available()];
		inputStream.read(conteudo);
		inputStream.close();
		document.setConteudo(conteudo);
		document.setFileName(event.getFile().getFileName());
	}
	
	public void confirmAddDocExternal(){
		if(camposObrigatoriosPreenchidos()) {
			documentoService.salvar(document);
			this.inicializarDocumentosExternos();
			this.inicializarElementosENovoDoc();
			RequestContext.getCurrentInstance().update("modalAddDocumento");
			RequestContext.getCurrentInstance().update("formDocumentosExternos");
			RequestContext.getCurrentInstance().execute("PF('modalAddDocumento').hide();");
			FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO_DOCUMENTO_EXTERNO);
		}else {
			FacesUtil.adicionarErro(MsgConstantes.VALIDACAO_CAMPOS_OBRIGATORIOS);
		}
	}
	
	public void confirmarInclusaoDocInterno(){
		if(camposObrigatoriosPreenchidos()) {
			documentoService.salvar(document);
			this.inicializarDocumentosInternos();
			this.inicializarElementosENovoDoc();
			RequestContext.getCurrentInstance().update("modalAddDocumentoInterno");
			RequestContext.getCurrentInstance().update("formDocumentosInternos");
			RequestContext.getCurrentInstance().execute("PF('modalAddDocumentoInterno').hide();");
			FacesUtil.adicionarMensagem(MsgConstantes.SUCESSO_DOCUMENTO_INTERNO);
		}else {
			FacesUtil.adicionarErro(MsgConstantes.VALIDACAO_CAMPOS_OBRIGATORIOS);
		}
	}
	
	public boolean camposObrigatoriosPreenchidos() {
		if(document.getConteudo() != null
				&& !document.getDescription().isEmpty() && document.getDescription() != null
				&& !document.getTitle().isEmpty() && document.getTitle() != null
				&& document.getElement() != null && document.getDocumentType() != null){
			return true;
		}
		
		return false;
	}
	
	public void viewDocumentPDF(Document documentSelected) {
		this.documentSelected = documentSelected;
		RequestContext.getCurrentInstance().update("modalDocumentoPDF");
        stream = new ByteArrayInputStream(documentSelected.getConteudo());
        stream.mark(0); //remember to this position!
        streamedContent = new DefaultStreamedContent(stream, "application/pdf");
        RequestContext.getCurrentInstance().execute("PF('modalDocumentoPDF').show();");
	}
	
	public void visualizarDocumentoDOC(Document documentoSelecionado) {
		this.documentSelected = documentoSelecionado;
		RequestContext.getCurrentInstance().update("modalDocumentoPDF");
        stream = new ByteArrayInputStream(documentoSelecionado.getConteudo());
        stream.mark(0); //remember to this position!
        streamedContent = new DefaultStreamedContent(stream, "application/doc");
        RequestContext.getCurrentInstance().execute("PF('modalDocumentoPDF').show();");
	}
	
	// Gets e Sets
	// ==============================================================================================
	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}


	public List<DocumentType> getTiposDeDocumentos() {
		return tiposDeDocumentos;
	}

	public void setTiposDeDocumentos(List<DocumentType> tiposDeDocumentos) {
		this.tiposDeDocumentos = tiposDeDocumentos;
	}

	public List<Factor> getElementList() {
		return elementList;
	}

	public void setElementList(List<Factor> elementList) {
		this.elementList = elementList;
	}

	public LazyDataModel<Document> getDocumentosProcedimentos() {
		return documentosProcedimentos;
	}

	public void setDocumentosProcedimentos(
			LazyDataModel<Document> documentosProcedimentos) {
		this.documentosProcedimentos = documentosProcedimentos;
	}

	public LazyDataModel<Document> getDocumentosPoliticas() {
		return documentosPoliticas;
	}

	public void setDocumentosPoliticas(LazyDataModel<Document> documentosPoliticas) {
		this.documentosPoliticas = documentosPoliticas;
	}

	public LazyDataModel<Document> getDocumentosTreinamentos() {
		return documentosTreinamentos;
	}

	public void setDocumentosTreinamentos(
			LazyDataModel<Document> documentosTreinamentos) {
		this.documentosTreinamentos = documentosTreinamentos;
	}

	public LazyDataModel<Document> getDocumentosDesignacoes() {
		return documentosDesignacoes;
	}

	public void setDocumentosDesignacoes(
			LazyDataModel<Document> documentosDesignacoes) {
		this.documentosDesignacoes = documentosDesignacoes;
	}

	public LazyDataModel<Document> getDocExternalList() {
		return docExternalList;
	}

	public void setDocExternalList(LazyDataModel<Document> docExternalList) {
		this.docExternalList = docExternalList;
	}

	public List<DocumentType> getDocumentoTypeExternalList() {
		return documentoTypeExternalList;
	}

	public void setDocumentoTypeExternalList(
			List<DocumentType> documentoTypeExternalList) {
		this.documentoTypeExternalList = documentoTypeExternalList;
	}

	public StreamedContent getStreamedContent() throws IOException {
		if (streamedContent != null)
            streamedContent.getStream().reset();
		return streamedContent;
	}

	public void setStreamedContent(StreamedContent streamedContent) {
		this.streamedContent = streamedContent;
	}

	public InputStream getStream() {
		return stream;
	}

	public void setStream(InputStream stream) {
		this.stream = stream;
	}

	public Document getDocumentSelected() {
		return documentSelected;
	}

	public void setDocumentSelected(Document documentSelected) {
		this.documentSelected = documentSelected;
	}
}
