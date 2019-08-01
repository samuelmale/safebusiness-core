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
    @JoinColumn(name="parent_id_fk")
	private Article parent;
	// Section where this article falls
	@ManyToOne
    @JoinColumn(name="section_id_fk")
	private Section section;
	@Column(unique=true)
	private Integer articleNumber;
	@Transient
	private String childrenCommaSeparatedList;
	@Transient
	private String stringId;
	
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

	public String getChildrenCommaSeparatedList() {
		return childrenCommaSeparatedList;
	}

	public void setChildrenCommaSeparatedList(String childrenCommaSeparatedList) {
		this.childrenCommaSeparatedList = childrenCommaSeparatedList;
	}

	public Integer getArticleNumber() {
		return articleNumber;
	}

	public void setArticleNumber(Integer articleNumber) {
		this.articleNumber = articleNumber;
	}

	public String getStringId() {
		return stringId;
	}

	public void setStringId(String stringId) {
		this.stringId = stringId;
	}	
	
}
