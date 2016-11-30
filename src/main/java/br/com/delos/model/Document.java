package br.com.delos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_documento")
public class Document {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
    private Long id;
	
	@Column(name="title")
	private String title;
	
	@Column(name="description")
	private String description;
	
	@Column(name="file_name")
	private String fileName;
	
	@ManyToOne
	@JoinColumn(name="fk_document_type")
	private DocumentType documentType;
	
	@ManyToOne
	@JoinColumn(name="fk_element")
	private Factor element;
	
	@Column(name="revision")
	private Integer revision;
	
	@ManyToOne
	@JoinColumn(name="fk_status_documento")
	private DocumentStatus statusDocumento;
	
	//Este atributo corresponde ao campo BLOB/CLOB que armazena 
	// o conteudo do documento PDF (Note a anotação @Lob)
	@Lob
	@Column
	private byte[] conteudo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public DocumentType getDocumentType() {
		return documentType;
	}

	public void setDocumentType(DocumentType documentType) {
		this.documentType = documentType;
	}

	public Factor getElement() {
		return element;
	}

	public void setElement(Factor element) {
		this.element = element;
	}

	public Integer getRevision() {
		return revision;
	}

	public void setRevision(Integer revision) {
		this.revision = revision;
	}

	public DocumentStatus getStatusDocumento() {
		return statusDocumento;
	}

	public void setStatusDocumento(DocumentStatus statusDocumento) {
		this.statusDocumento = statusDocumento;
	}

	public byte[] getConteudo() {
		return conteudo;
	}

	public void setConteudo(byte[] conteudo) {
		this.conteudo = conteudo;
	}

}
