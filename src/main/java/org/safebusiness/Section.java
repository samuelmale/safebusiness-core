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
public class Section {
	
	@Id
	@GeneratedValue
	@Column(name="section_id")
	private String id; 
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
    @JoinColumn(name="act_id", nullable=false)
	private Act act;

	// Getters and Setters
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
	
}
