package org.safebusiness;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ActionAttribute extends Attribute {
	
	@Transient
	@JsonIgnore
	private Action action;
	
	@ManyToOne
	@JoinColumn(name="procedure_template_id", nullable=true)
	@JsonIgnore
	private ProcedureTemplate procedureTemplate;
	
	public Action getAction() {
		return (Action) this.getOwner();
	}

	@Override
	public void setOwner(Customizable owner) {
		setAction((Action) owner);
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public ProcedureTemplate getProcedureTemplate() {
		return procedureTemplate;
	}

	public void setProcedureTemplate(ProcedureTemplate procedureTemplate) {
		this.procedureTemplate = procedureTemplate;
	}
	
}
