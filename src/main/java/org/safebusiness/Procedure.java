package org.safebusiness;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "safebusiness_procedure")
public class Procedure {
	
	@Id
	@GeneratedValue
	@Column(name="procedure_id")
	private Integer id;
	@OneToMany(mappedBy="procedure", fetch=FetchType.EAGER)
	@JsonIgnore
    private List<Act> acts;
	@OneToOne(mappedBy="procedure")
	private Action action;
	// Process owning this Procedure
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="process_id_pk", nullable=true)
	@JsonIgnore
	private Process process;
	@Column(unique=true)
	private String name;
	
	// Utility hacks
	@Transient
	private String stringId;
	@Transient
	private String actNamesString;
	@Transient
	private String actionName;
	
	// Getters and Setters
	public List<Act> getActs() {
		return acts;
	}
	public void setActs(List<Act> acts) {
		if(this.acts == null) {
			this.acts = new ArrayList<>();
		}
		this.acts.addAll(acts);
	}
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
	}
	public Integer getId() {
		if (id != null) {
			setStringId(id.toString());
		}
		return id;
	}
	public void setId(Integer id) {
		if (id != null) {
			setStringId(id.toString());
		}
		this.id = id;
	}
	public Process getProcess() {
		return process;
	}
	public void setProcess(Process process) {
		this.process = process;
	}
	public String getStringId() {
		return stringId;
	}
	public void setStringId(String stringId) {
		this.stringId = stringId;
	}
	public String getActNamesString() {
		return actNamesString;
	}
	public void setActNamesString(String actNamesString) {
		this.actNamesString = actNamesString;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
