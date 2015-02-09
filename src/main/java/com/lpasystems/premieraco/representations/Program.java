package com.lpasystems.premieraco.representations;

import java.util.List;

/**
 * Used to hold a set of information about a Program
 * 
 * @author Jim
 * 
 */
public class Program {
	private String programName;

	private String	programId;

	private List<Domain> domains;

	/**
	 * Creates an instance of the class
	 */
	public Program(String programId, String programName, List<Domain> domains) {

		this.programId = programId;
		this.programName = programName;
		this.domains = domains;
	}

	/**
	 * @return the programName
	 */
	public String getProgramName() {
		return programName;
	}

	/**
	 * @return the programId
	 */
	public String getProgramId() {
		return programId;
	}

	/**
	 * @return the domains
	 */
	public List<Domain> getDomains() {
		return domains;
	}
}
