package org.safebusiness;

public class ActionAttribute extends Attribute {
	
	private Integer id;
		
	public Action getAction() {
		return (Action) this.getOwner();
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
}
