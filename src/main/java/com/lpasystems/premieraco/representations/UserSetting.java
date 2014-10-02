package com.lpasystems.premieraco.representations;

/**
 * Used to hold a pair of values for a user setting, one used for its name,
 * one used for its value
 * 
 * @author Jim
 * 
 */
public class UserSetting {
	private final String settingName;
	
	private final String settingText;
	
	/**
	 * Creates an instance of the class using the passed in name and
	 * text
	 * 
	 * @param settingName
	 * @param settingText
	 */
	public UserSetting(String settingName, String settingText) {
		this.settingName = settingName;
		this.settingText = settingText;
	}

	/**
	 * 
	 * @return the "Use" value
	 */
	public String getSettingName() {
		return settingName;
	}

	/**
	 * 
	 * @return the "Use" value
	 */
	public String getSettingText() {
		return settingText;
	}
}
