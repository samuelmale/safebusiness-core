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
	@OneToMany(mappedBy="section", fetch=FetchType.EAGER)
	private List<Article> articles;
	@OneToMany(mappedBy="parent", fetch=FetchType.EAGER)
	private List<Section> subSections;
	// If this is a sub section, this is the Parent reference to the super section
	@ManyToOne
    @JoinColumn(name="parent_id_fk", nullable=true)
	private Section parent;
	// The Act owning this section
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="act_id_fk_silly", nullable=true)
	private Act act;
	
	//This a hack around having this @id in String format
	// I hate casting in thymeleafe templates
	@Column
	private String stringId;
	
	@Transient
	private Integer actId;
	
	@Transient
	private String childrenCommaSeparatedList;
	@Transient
	private String articleCommaSeparatedList;
	
	// Getters and Setters
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
		if (this.articles == null) {
			this.articles = new ArrayList<>();
		}
		this.articles.addAll(articles);
	}
	public List<Section> getSubSections() {
		
		return subSections;
	}
	public void setSubSections(List<Section> subSections) {
		if (this.subSections == null) {
			this.subSections = new ArrayList<>();
		}
		this.subSections.addAll(subSections);
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
	public String getStringId() {
		return stringId;
	}
	public void setStringId(String stringId) {
		this.stringId = stringId;
	}
	public Integer getId() {
		if(id != null) {
			this.stringId = id.toString();
		}
		return id;
	}
	
	public void setId(Integer id) {
		if (id != null) {
			setStringId(id.toString());
		}
		this.id = id;
	}
	public String getArticleCommaSeparatedList() {
		return articleCommaSeparatedList;
	}
	public void setArticleCommaSeparatedList(String articleCommaSeparatedList) {
		this.articleCommaSeparatedList = articleCommaSeparatedList;
	}
	
}
