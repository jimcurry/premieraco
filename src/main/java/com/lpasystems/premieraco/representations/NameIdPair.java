package com.lpasystems.premieraco.representations;

/**
 * Used to hold a pair of values, one with and id and one with a name
 * 
 * @author Jim
 * 
 */
public class NameIdPair {
	private final String id;
	
	private final String name;
	
	/**
	 * Creates an instance of the class using the passed in useValue and
	 * displayValue
	 * 
	 * @param id
	 * @param name
	 */
	public NameIdPair(String id, String name) {
		this.id = id;
		this.name = name;
	}

	/**
	 * 
	 * @return the "Id"
	 */
	public String getId() {
		return id;
	}

	/**
	 * 
	 * @return the "Name"
	 */
	public String getName() {
		return name;
	}
}
