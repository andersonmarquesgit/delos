package br.com.delos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_contato")
public class Contact {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
    private Long id;
	
	@Column(name="nome", nullable=false)
	private String nome;
	
	@Column(name="detalhes_conversa", nullable=false)
	private String detalhesConversa;
	
	@ManyToOne
	@JoinColumn(name="fk_tipo_contato", nullable=false)
	private ContactType tipoContato;
}
