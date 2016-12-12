package br.com.delos.utils.enums;

public enum GravityEnum {
	BAIXA(1L), 
	MEDIA(2L), 
	ALTA(3L);
	
	private final Long id;

	GravityEnum(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
}
