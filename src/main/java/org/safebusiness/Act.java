package org.safebusiness;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class Act {
	
	@Id
	@GeneratedValue
	@Column(name="act_id")
	private Integer id;
	@OneToMany(mappedBy="act", fetch=FetchType.EAGER)
	private List<Section> sections;
	@Column
	private String name;
	// Procedure owning this Act
	@ManyToOne
    @JoinColumn(name="procedure_id_pk", nullable=true)
	private Procedure procedure;
	@Column
	private String stringId;
	@Transient
	private String sectionListString;
	
	// Getters and Setters
	public List<Section> getSections() {
		return sections;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
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

	public Procedure getProcedure() {
		return procedure;
	}

	public void setProcedure(Procedure procedure) {
		this.procedure = procedure;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStringId() {
		return stringId;
	}

	public void setStringId(String stringId) {
		this.stringId = stringId;
	}

	public String getSectionListString() {
		return sectionListString;
	}

	public void setSectionListString(String sectionListString) {
		this.sectionListString = sectionListString;
	}
	
}
