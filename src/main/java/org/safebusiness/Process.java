package org.safebusiness;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class Process {

	@Id
	@GeneratedValue
	@Column(name="process_id")
	private Integer id;
	@Column
	private String processName;
	@OneToMany(mappedBy="process")
	private List<Procedure> procedures;
	
	// Hacky utilities
	@Transient
	private String stringId;
	@Transient
	private String procedureNames;
	
	// Getters and Setters
	
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public List<Procedure> getProcedures() {
		return procedures;
	}
	public void setProcedures(List<Procedure> procedures) {
		this.procedures = procedures;
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
	public String getStringId() {
		return stringId;
	}
	public void setStringId(String stringId) {
		this.stringId = stringId;
	}
	public String getProcedureNames() {
		return procedureNames;
	}
	public void setProcedureNames(String procedureNames) {
		this.procedureNames = procedureNames;
	}
	
}
