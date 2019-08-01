package org.safebusiness;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Procedure {
	
	@Id
	@GeneratedValue
	@Column(name="procedure_id")
	private Integer id;
	@OneToMany(mappedBy="procedure")
    private List<Act> acts;
	@OneToOne(mappedBy="procedure")
	private Action action;
	// Process owning this Procedure
	@ManyToOne
    @JoinColumn(name="process_id", nullable=false)
	private Process process;
	
	// Getters and Setters
	public List<Act> getActs() {
		return acts;
	}
	public void setActs(List<Act> acts) {
		this.acts = acts;
	}
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
}
