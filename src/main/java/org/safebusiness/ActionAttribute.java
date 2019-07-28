package org.safebusiness;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ActionAttribute extends Attribute {
	
	@ManyToOne
    @JoinColumn(name="action_id", nullable=false)
	private Action action;
	
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
	
}
