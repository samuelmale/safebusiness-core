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

import org.hibernate.annotations.Type;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Article {
	
	@Id
	@GeneratedValue
	@Column(name="article_id")
	private Integer id;
	@Column(columnDefinition="text")
	private String value;
	@Column(columnDefinition="text")
	private String basicTranslation;
	@OneToMany(mappedBy="parent", fetch=FetchType.EAGER)
	@JsonIgnore
	private List<Article> childArticles;
	@ManyToOne
    @JoinColumn(name="parent_id_fk")
	@JsonIgnore
	private Article parent;
	// Section where this article falls
	@ManyToOne
    @JoinColumn(name="section_id_pk")
	@JsonIgnore
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
	
	public String getBasicTranslation() {
		return basicTranslation;
	}

	public void setBasicTranslation(String basicTranslation) {
		this.basicTranslation = basicTranslation;
	}

	public List<Article> getChildArticles() {
		return childArticles;
	}
	
	public void setChildArticles(List<Article> childArticles) {
		if(this.childArticles == null) {
			this.childArticles = new ArrayList<>();
		}
		this.childArticles.addAll(childArticles);
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
