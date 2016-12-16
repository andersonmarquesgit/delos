package br.com.delos.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tb_critical_analisys")
public class CriticalAnalisys {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
    private Long id;
	
	@Column(name="number", nullable=false)
    private String number;
	
	@OneToMany(mappedBy = "criticalAnalisys", cascade=CascadeType.ALL)
	private List<SectionCriticalAnalisys> sectionCriticalAnalisysList;
	
	@Column(name="conclusion", nullable=false)
	private String conclusion;
	
	@Column(name="dt_inclusion", nullable=false)
	private Date dateInclusion;
	
	@Column(name="participants")
	private String participants;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public List<SectionCriticalAnalisys> getSectionCriticalAnalisys() {
		return sectionCriticalAnalisysList;
	}

	public void setSectionCriticalAnalisys(
			List<SectionCriticalAnalisys> sectionCriticalAnalisys) {
		this.sectionCriticalAnalisysList = sectionCriticalAnalisys;
	}

	public String getConclusion() {
		return conclusion;
	}

	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}

	public Date getDateInclusion() {
		return dateInclusion;
	}

	public void setDateInclusion(Date dateInclusion) {
		this.dateInclusion = dateInclusion;
	}

	public String getParticipants() {
		return participants;
	}

	public void setParticipants(String participants) {
		this.participants = participants;
	}

	public List<SectionCriticalAnalisys> getSectionCriticalAnalisysList() {
		return sectionCriticalAnalisysList;
	}

	public void setSectionCriticalAnalisysList(List<SectionCriticalAnalisys> sectionCriticalAnalisysList) {
		this.sectionCriticalAnalisysList = sectionCriticalAnalisysList;
	}

}
