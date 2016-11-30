package br.com.delos.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_nivel_usuario")
public class UserLevel {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
    private Long id;
	
	@Column(name="name")
    private String name;

	@Column(name="doc_permission", columnDefinition = "boolean default false")
    private Boolean docPermission;
	
	@Column(name="sac_permission", columnDefinition = "boolean default false")
    private Boolean sacPermission;
	
	@Column(name="critical_analisys_permission", columnDefinition = "boolean default false")
    private Boolean criticalAnalisysPermission;
	
	@Column(name="configuration_permission", columnDefinition = "boolean default false")
    private Boolean configurarionPermission;
	
	@Column(name="dt_inclusion")
	private Date dateInclusion;
	
	@Column(name="dt_change")
	private Date dateChange;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getDocPermission() {
		return docPermission;
	}

	public void setDocPermission(Boolean docPermission) {
		this.docPermission = docPermission;
	}

	public Boolean getSacPermission() {
		return sacPermission;
	}

	public void setSacPermission(Boolean sacPermission) {
		this.sacPermission = sacPermission;
	}

	public Boolean getCriticalAnalisysPermission() {
		return criticalAnalisysPermission;
	}

	public void setCriticalAnalisysPermission(Boolean criticalAnalisysPermission) {
		this.criticalAnalisysPermission = criticalAnalisysPermission;
	}

	public Boolean getConfigurarionPermission() {
		return configurarionPermission;
	}

	public void setConfigurarionPermission(Boolean configurarionPermission) {
		this.configurarionPermission = configurarionPermission;
	}

	public Date getDateInclusion() {
		return dateInclusion;
	}

	public void setDateInclusion(Date dateInclusion) {
		this.dateInclusion = dateInclusion;
	}

	public Date getDateChange() {
		return dateChange;
	}

	public void setDateChange(Date dateChange) {
		this.dateChange = dateChange;
	}

}
