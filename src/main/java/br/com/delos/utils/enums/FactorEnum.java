package br.com.delos.utils.enums;

public enum FactorEnum {

	SAC(1L), 
	ANALISE_CRITICA(2L), 
	AUDITORIA_INTERNA(3L), 
	NAO_CONFORMIDADE(4L), 
	PLANO_DE_MELHORIA(5L),
	PRODUTOS(6L), 
	CALIBRACAO(7L), 
	RECURSOS_HUMANOS(8L), ;
	
	private final Long id;

	FactorEnum(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

}
