package com.lpasystems.premieraco.representations;

import java.util.List;

/**
 * Used to hold a set of information about a user
 * 
 * @author Jim
 * 
 */
public class Program {
	private String programName;

	private String	programId;

	private List<NameIdPair> domains;

	/**
	 * Creates an instance of the class
	 */
	public Program(String programId, String programName, List<NameIdPair> domains) {

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
	public List<NameIdPair> getDomains() {
		return domains;
	}
}
