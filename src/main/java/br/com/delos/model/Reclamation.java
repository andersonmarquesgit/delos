package br.com.delos.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "tb_reclamation")
public class Reclamation {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
    private Long id;
	
	@Column(name="number")
    private String number;
	
	@Column(name="description")
    private String description;
	
	@Column(name="suggested_remediation")
	private String suggestedRemediation;
	
	@Column(name="dt_deadline_answer")
	private Date deadlineAnswer;
	
	@Column(name="dt_inclusion")
	private Date dateInclusion;
	
	@Column(name="dt_change")
	private Date dateChange;
	
	@ManyToOne
	@JoinColumn(name="fk_reclamation_type")
	private ReclamationType reclamationType;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="fk_customer")
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name="fk_reclamation_status")
	private ReclamationStatus reclamationStatus;
	
	@ManyToOne
	@JoinColumn(name="fk_user")
	@NotFound(action=NotFoundAction.IGNORE)
	private User user;
	
	@OneToOne(cascade = CascadeType.ALL, optional = true, orphanRemoval = true)
	@JoinColumn(name="fk_customer_acceptance")
	private CustomerAcceptance customerAcceptance;
	
	@OneToOne(cascade = CascadeType.ALL, optional = true, orphanRemoval = true)
	@JoinColumn(name="fk_action")
	private Action action;
	
	@ManyToOne
	@JoinColumn(name="fk_gravity")
	private Gravity gravity;
	
	@ManyToOne
	@JoinColumn(name="fk_complexity")
	private Gravity complexity;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSuggestedRemediation() {
		return suggestedRemediation;
	}

	public void setSuggestedRemediation(String suggestedRemediation) {
		this.suggestedRemediation = suggestedRemediation;
	}

	public Date getDeadlineAnswer() {
		return deadlineAnswer;
	}

	public void setDeadlineAnswer(Date deadlineAnswer) {
		this.deadlineAnswer = deadlineAnswer;
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

	public ReclamationType getReclamationType() {
		return reclamationType;
	}

	public void setReclamationType(ReclamationType reclamationType) {
		this.reclamationType = reclamationType;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public ReclamationStatus getReclamationStatus() {
		return reclamationStatus;
	}

	public void setReclamationStatus(ReclamationStatus reclamationStatus) {
		this.reclamationStatus = reclamationStatus;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public CustomerAcceptance getCustomerAcceptance() {
		return customerAcceptance;
	}

	public void setCustomerAcceptance(CustomerAcceptance customerAcceptance) {
		this.customerAcceptance = customerAcceptance;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public Gravity getGravity() {
		return gravity;
	}

	public void setGravity(Gravity gravity) {
		this.gravity = gravity;
	}

	public Gravity getComplexity() {
		return complexity;
	}

	public void setComplexity(Gravity complexity) {
		this.complexity = complexity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
