package org.safebusiness;

import java.util.ArrayList;
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
public class ProcedureTemplate {
	
	@GeneratedValue
	@Id
	@Column
	private Integer id;
	@Transient
	private List<Act> acts;
	@Column
	private String instructions;	
	@OneToMany(mappedBy="procedureTemplate", fetch=FetchType.EAGER)
	private List<ActionAttribute> attributes;
	@ManyToOne
	@JoinColumn(name="document_id", nullable=true)
	private Document document;

	public List<Act> getActs() {
		return acts;
	}

	public void setActs(List<Act> acts) {
		if(this.acts == null) {
			this.acts = new ArrayList<>();
		}
		this.acts.addAll(acts);
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public List<ActionAttribute> getAttributes() {
		if (attributes == null) {
			this.attributes = new ArrayList<>();
		}
		return attributes;
	}

	public void setAttributes(List<ActionAttribute> attributes) {
		if(this.attributes == null) {
			this.attributes = new ArrayList<>();
		}
		this.attributes.addAll(attributes);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}
	
}
