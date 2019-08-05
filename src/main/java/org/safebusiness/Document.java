package org.safebusiness;

import java.util.ArrayList;
import java.util.List;

/**
 * This a dynamically generated <code>Law</code> document whose
 * structure is determined by the associated {@link Process}
 * 
 * @author samuel
 *
 */
public class Document {

	private Integer id;
	private String name;
	private Process process;
	private List<ProcedureTemplate> templates;
	private String processName;
	
	public Document() {
		
	}
	
	public Document(Process process) {
		setupTemplates(process);
	}
	
	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

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

	public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}

	public List<ProcedureTemplate> getTemplates() {
		return templates;
	}

	public void setTemplates(List<ProcedureTemplate> templates) {
		this.templates = templates;
	}

	public void setupTemplates(Process process) {
		this.process = process;
		if (templates == null) {
			templates = new ArrayList<>();
		}
		List<Procedure> procedures = process.getProcedures();
		if (procedures == null) {
			throw new IllegalArgumentException("Can't create Document from an Empty Process."
					+ " No Procedures found!");
		}
		System.out.println("Process procedures: " + procedures);
		for (Procedure procedure : procedures) {
			ProcedureTemplate template = new ProcedureTemplate();
			List<Act> acts = procedure.getActs();
			if (acts != null) {
				template.setActs(acts);
			}
			if (procedure.getAction() == null) {
				throw new IllegalArgumentException("Can't use Procedure with undefined Action");
			}
			List<AttributeType> types = procedure.getAction().getAttributeTypes();
			if (types != null) {
				for (AttributeType type : types) {
					ActionAttribute attribute = new ActionAttribute();
					attribute.setName(type.getName());
					attribute.setDataTypeString(type.getDataTypeString());
					template.getAttributes().add(attribute);
				}
			}
			template.setInstructions(procedure.getAction().getInstructionString());
			templates.add(template);
		}

	}
	
	public class ProcedureTemplate {
		
		// Informative
		private List<Act> acts;
		private String instructions;
		
		// Requires input
		private List<ActionAttribute> attributes;

		public List<Act> getActs() {
			return acts;
		}

		public void setActs(List<Act> acts) {
			this.acts = acts;
		}

		public String getInstructions() {
			return instructions;
		}

		public void setInstructions(String instructions) {
			this.instructions = instructions;
		}

		public List<ActionAttribute> getAttributes() {
			if (attributes == null) {
				this.attributes = new ArrayList<>();
			}
			return attributes;
		}

		public void setAttributes(List<ActionAttribute> attributes) {
			this.attributes = attributes;
		}
		
	}
}
