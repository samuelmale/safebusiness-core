package org.safebusiness;

import java.util.List;

public class Section {
	
	private String id; 
	// Could be the title, not sure?
	private String name; 
	
	/**
	 * Captures the {@code content}
	 */
	private List<Article> articles;
	private List<Section> subSections;

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
	
	
}
