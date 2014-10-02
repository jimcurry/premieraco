package com.lpasystems.premieraco.representations;

/**
 * Used to hold a pair of values, one used for "display" and one "use"d for
 * internal processing like you might expect for a dropdown, etc.
 * 
 * @author Jim
 * 
 */
public class UseDisplayValuePair {
	private final String useValue;
	
	private final String displayValue;
	
	/**
	 * Creates an instance of the class using the passed in useValue and
	 * displayValue
	 * 
	 * @param useValue
	 * @param displayValue
	 */
	public UseDisplayValuePair(String useValue, String displayValue) {
		this.useValue = useValue;
		this.displayValue = displayValue;
	}

	/**
	 * 
	 * @return the "Use" value
	 */
	public String getUseValue() {
		return useValue;
	}

	/**
	 * 
	 * @return the "Display" value
	 */
	public String getDisplayValue() {
		return displayValue;
	}
}
