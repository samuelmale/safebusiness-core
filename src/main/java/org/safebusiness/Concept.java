package org.safebusiness;

import java.util.ArrayList;
import java.util.List;

/**
 * A Concept is anything in safebusiness.
 * This could be a question or an answer to a question.
 * @author samuel
 *
 */
public class Concept {
	
	private Integer id;
	private String valueText;
	private Integer valueInt;
	private String name;
	private String description;
	// These are the expected possible answers if this Concept is coded
	private List<Concept> answers;
	private ConceptType type;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getValueText() {
		return valueText;
	}

	public void setValueText(String valueText) {
		this.valueText = valueText;
	}

	public Integer getValueInt() {
		return valueInt;
	}

	public void setValueInt(Integer valueInt) {
		this.valueInt = valueInt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Concept> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Concept> answers) {
		if(this.answers == null) {
			this.answers = new ArrayList<>();
		}
		this.answers.addAll(answers);
	}

	public ConceptType getType() {
		return type;
	}

	public void setType(ConceptType type) {
		this.type = type;
	}

	public enum ConceptType {
		/**
		 * This is a {@link Concept} that has other concepts as possible answer
		 */
		CODED, 
		/**
		 * This is a {@code Concept} that is just like an answer to a coded one.
		 */
		ANSWER
	}
}
