package br.com.delos.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_analisys_complexity")
public class ComplexityAnalisys {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
    private Long id;
	
	@ManyToOne
	@JoinColumn(name="fk_complexity", nullable=false)
	private Complexity complexity;
	
	@ManyToOne
	@JoinColumn(name="fk_gravity", nullable=false)
	private Gravity gravity;
	
	@Column(name="dt_analisys", nullable=false)
	private Date dateAnalisys;

	@OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name="fk_reclamation")
	private Reclamation reclamation;
	
	public Complexity getComplexity() {
		return complexity;
	}

	public void setComplexity(Complexity complexity) {
		this.complexity = complexity;
	}

	public Gravity getGravity() {
		return gravity;
	}

	public void setGravity(Gravity gravity) {
		this.gravity = gravity;
	}

	public Date getDateAnalisys() {
		return dateAnalisys;
	}

	public void setDateAnalisys(Date dateAnalisys) {
		this.dateAnalisys = dateAnalisys;
	}

	public Reclamation getReclamation() {
		return reclamation;
	}

	public void setReclamation(Reclamation reclamation) {
		this.reclamation = reclamation;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
}
