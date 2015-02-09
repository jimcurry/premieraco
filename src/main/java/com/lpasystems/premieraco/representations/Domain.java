package com.lpasystems.premieraco.representations;

import java.util.List;

/**
 * Used to hold a set of information about a Domain
 * 
 * @author Jim
 * 
 */
public class Domain {
	private String id;

	private String	name;

	private List<Measure> measures;

	/**
	 * Creates an instance of the class
	 */
	public Domain(String id, String name, List<Measure> measures) {

		this.id = id;
		this.name = name;
		this.measures = measures;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the measures
	 */
	public List<Measure> getMeasures() {
		return measures;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param measures the measures to set
	 */
	public void setMeasures(List<Measure> measures) {
		this.measures = measures;
	}

}
