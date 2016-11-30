package br.com.delos.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_section_critical_analisys")
public class SectionCriticalAnalisys {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
    private Long id;
	
	@OneToMany(mappedBy = "sectionCriticalAnalisys", cascade=CascadeType.ALL)
	private List<ItemCriticalAnalisys> itemsCriticalAnalisys;
	
	@ManyToOne
	@JoinColumn(name = "fk_critical_analisys")
	private CriticalAnalisys criticalAnalisys;
	
	@ManyToOne
	@JoinColumn(name = "fk_conclusion")
	private Conclusion conclusion;
	
	@Column(name="references")
	private String references;
	
	@Column(name="comments")
	private String comments;
	
	@OneToOne
	private Section section;

	public List<ItemCriticalAnalisys> getItemsCriticalAnalisys() {
		return itemsCriticalAnalisys;
	}

	public void setItemsCriticalAnalisys(
			List<ItemCriticalAnalisys> itemsCriticalAnalisys) {
		this.itemsCriticalAnalisys = itemsCriticalAnalisys;
	}

	public CriticalAnalisys getCriticalAnalisys() {
		return criticalAnalisys;
	}

	public void setCriticalAnalisys(CriticalAnalisys criticalAnalisys) {
		this.criticalAnalisys = criticalAnalisys;
	}

	public Conclusion getConclusion() {
		return conclusion;
	}

	public void setConclusion(Conclusion conclusion) {
		this.conclusion = conclusion;
	}

	public String getReferences() {
		return references;
	}

	public void setReferences(String references) {
		this.references = references;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
