package org.safebusiness;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Act {
	
	@Id
	@GeneratedValue
	@Column(name="act_id")
	private Integer id;
	@OneToMany(mappedBy="act")
	private List<Section> sections;
	
	// Procedure owning this Act
	@ManyToOne
    @JoinColumn(name="procedure_id", nullable=false)
	private Procedure procedure;

	// Getters and Setters
	public List<Section> getSections() {
		return sections;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Procedure getProcedure() {
		return procedure;
	}

	public void setProcedure(Procedure procedure) {
		this.procedure = procedure;
	}
	
}
