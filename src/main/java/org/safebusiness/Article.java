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
public class Article {
	
	@Id
	@GeneratedValue
	@Column(name="article_id")
	private Integer id;
	@Column
	private String value;
	@OneToMany(mappedBy="parent")
	private List<Article> childArticles;
	@ManyToOne
    @JoinColumn(name="parent_id", nullable=true)
	private Article parent;
	// Section where this article falls
	@ManyToOne
    @JoinColumn(name="section_id", nullable=false)
	private Section section;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public List<Article> getChildArticles() {
		return childArticles;
	}
	
	public void setChildArticles(List<Article> childArticles) {
		this.childArticles = childArticles;
	}

	public Article getParent() {
		return parent;
	}

	public void setParent(Article parent) {
		this.parent = parent;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}	
	
}
