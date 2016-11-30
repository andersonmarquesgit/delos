package br.com.delos.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_customer_acceptance")
public class CustomerAcceptance {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
    private Long id;
	
	@Column(name="acceptance", nullable=false)
	private Boolean acceptance;
	
	@Column(name="description")
    private String description;
	
	@Column(name="dt_acceptance")
    private Date dateAcceptance;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getAcceptance() {
		return acceptance;
	}

	public void setAcceptance(Boolean acceptance) {
		this.acceptance = acceptance;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateAcceptance() {
		return dateAcceptance;
	}

	public void setDateAcceptance(Date dateAcceptance) {
		this.dateAcceptance = dateAcceptance;
	}

}
