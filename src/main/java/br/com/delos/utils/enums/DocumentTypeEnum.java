package br.com.delos.utils.enums;

public enum DocumentTypeEnum {

	PROCEDIMENTO(1L), 
	POLITICA_TRATAMENTO_RECLAMACAO(2L), 
	TREINAMENTO(3L), 
	DESIGNACAO(4L), 
	DOCUMENTOS_COMPLEMENTARES(5L);
	
	private final Long id;

	DocumentTypeEnum(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

}
