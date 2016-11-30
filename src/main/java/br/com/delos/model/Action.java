package br.com.delos.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_action")
public class Action {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
    private Long id;
	
	@Column(name="procced", nullable=false)
	private Boolean procced;
	
	@Column(name="description", nullable=false)
    private String description;
	
	@Column(name="dt_action")
    private Date dateAction;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getProcced() {
		return procced;
	}

	public void setProcced(Boolean procced) {
		this.procced = procced;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateAction() {
		return dateAction;
	}

	public void setDateAction(Date dateAction) {
		this.dateAction = dateAction;
	}

}
