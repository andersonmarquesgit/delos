package br.com.delos.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_item_analise_critica")
public class ItemCriticalAnalisys {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
    private Long id;
	
	@ManyToOne
	@JoinColumn(name="fk_item")
	private Item item;
	
	@ManyToOne
	@JoinColumn(name="fk_resultado")
	private Result resultado;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "fk_secao_analise_critica")
	private SectionCriticalAnalisys secaoAnaliseCritica;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Result getResultado() {
		return resultado;
	}

	public void setResultado(Result resultado) {
		this.resultado = resultado;
	}

	public SectionCriticalAnalisys getSecaoAnaliseCritica() {
		return secaoAnaliseCritica;
	}

	public void setSecaoAnaliseCritica(SectionCriticalAnalisys secaoAnaliseCritica) {
		this.secaoAnaliseCritica = secaoAnaliseCritica;
	}

}
