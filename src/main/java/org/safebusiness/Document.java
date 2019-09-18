package org.safebusiness;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.safebusiness.api.repo.ActionAttributeRepository;
import org.safebusiness.api.repo.ProcedureTemplateRepository;

/**
 * This a dynamically generated <code>Law</code> document whose
 * structure is determined by the associated {@link Process}
 * 
 * @author samuel
 *
 */
@Entity
public class Document {

	@Id
	@GeneratedValue
	@Column
	private Integer id;
	@Column
	private String name;
	@ManyToOne
	@JoinColumn(name="process_id")
	private Process process;
	@OneToMany(mappedBy="document", fetch=FetchType.EAGER)
	private List<ProcedureTemplate> templates;
	@Column
	private String processName;
	@Column
	private String clientFirstName;
	@Column
	private String clientLastName;
	
	public Document() {
		
	}
	
	public Document(Process process) {
		//setupTemplates(process);
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
		if(this.templates == null) {
			this.templates = new ArrayList<>();
		}
		this.templates.addAll(templates);
	}

	public String getClientFirstName() {
		return clientFirstName;
	}

	public void setClientFirstName(String clientFirstName) {
		this.clientFirstName = clientFirstName;
	}

	public String getClientLastName() {
		return clientLastName;
	}

	public void setClientLastName(String clientLastName) {
		this.clientLastName = clientLastName;
	}

	public void setupTemplates(Process process, ActionAttributeRepository attRepo, ProcedureTemplateRepository templateRepo) {
		this.process = process;
		if (templates == null) {
			templates = new ArrayList<>();
		}
		List<Procedure> procedures = process.getProcedures();
		if (procedures == null) {
			throw new IllegalArgumentException("Can't create Document from an Empty Process."
					+ " No Procedures found!");
		}
		for (Procedure procedure : procedures) {
			ProcedureTemplate template = new ProcedureTemplate();
			Action action = procedure.getAction();
			if (action == null) {
				throw new IllegalArgumentException("Procedure identified by(" + procedure.getName() + ") has no Action, "
						+ "first add one and then retry creating the Ducument");
			}
			template.setInstructions(action.getInstructionString());
			template = templateRepo.save(template);
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
					attribute.setProcedureTemplate(template);
					attribute = attRepo.save(attribute);
					template.getAttributes().add(attribute);
				}
			}
			templates.add(template);
		}

	}
	
	public void updateActRefsInDoc() {
		int index = 0;
		List<Procedure> procedures = process.getProcedures();
		// Remove duplicates if any
		templates = new ArrayList<>(new HashSet<>(templates));
		for (ProcedureTemplate template : templates) {
			Procedure procedure = procedures.get(index);
			template.setActs(procedure.getActs());
			index++;
		}
	}
	
}
