package org.safebusiness;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class Section {
	
	@Id
	@GeneratedValue
	@Column(name="section_id")
	private Integer id; 
	// Could be the title, not sure?
	@Column
	private String name; 
	
	/**
	 * Captures the {@code content}
	 */
	@OneToMany(mappedBy="section")
	private List<Article> articles;
	@OneToMany(mappedBy="parent")
	private List<Section> subSections;
	// If this is a sub section, this is the Parent reference to the super section
	@ManyToOne
    @JoinColumn(name="parent_id", nullable=true)
	private Section parent;
	// The Act owning this section
	@ManyToOne
    @JoinColumn(name="act_id", nullable=true)
	private Act act;
	
	@Transient
	private Integer actId;
	
	@Transient
	private String childrenCommaSeparatedList;
	
	// Getters and Setters
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Article> getArticles() {
		return articles;
	}
	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}
	public List<Section> getSubSections() {
		return subSections;
	}
	public void setSubSections(List<Section> subSections) {
		this.subSections = subSections;
	}
	public Section getParent() {
		return parent;
	}
	public void setParent(Section parent) {
		this.parent = parent;
	}
	
	public Act getAct() {
		return act;
	}
	public void setAct(Act act) {
		this.act = act;
	}
	public String getChildrenCommaSeparatedList() {
		return childrenCommaSeparatedList;
	}
	public void setChildrenCommaSeparatedList(String childrenCommaSeparatedList) {
		this.childrenCommaSeparatedList = childrenCommaSeparatedList;
	}
	public Integer getActId() {
		return actId;
	}
	public void setActId(Integer actId) {
		this.actId = actId;
	}
	
}
