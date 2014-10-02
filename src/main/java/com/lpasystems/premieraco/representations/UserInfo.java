package com.lpasystems.premieraco.representations;

import java.util.ArrayList;
import java.util.List;

/**
 * Used to hold a set of information about a user
 * 
 * @author Jim
 * 
 */
public class UserInfo {
	private String healthNetworkName;

	private String applicationRoleCode;

	private String practictionerFilterText;

	private String level10FilterText;

	private String level20FilterText;

	private String level30FilterText;

	private String level40FilterText;

	private String level50FilterText;

	private String level60FilterText;

	private String level70FilterText;

	private String level80FilterText;

	private String level90FilterText;

	private List<UserSetting> settings = new ArrayList<UserSetting>();

	/**
	 * Creates an instance of the class
	 */
	public UserInfo(String healthNetworkName, String applicationRoleCode,
			String practictionerFilterText, String level10FilterText,
			String level20FilterText, String level30FilterText,
			String level40FilterText, String level50FilterText,
			String level60FilterText, String level70FilterText,
			String level80FilterText, String level90FilterText) {
		super();
		this.healthNetworkName = healthNetworkName;
		this.applicationRoleCode = applicationRoleCode;
		this.practictionerFilterText = practictionerFilterText;
		this.level10FilterText = level10FilterText;
		this.level20FilterText = level20FilterText;
		this.level30FilterText = level30FilterText;
		this.level40FilterText = level40FilterText;
		this.level50FilterText = level50FilterText;
		this.level60FilterText = level60FilterText;
		this.level70FilterText = level70FilterText;
		this.level80FilterText = level80FilterText;
		this.level90FilterText = level90FilterText;
	}

	/**
	 * @return the applicationRoleCode
	 */
	public String getApplicationRoleCode() {
		return applicationRoleCode;
	}
	
	/**
	 * @return the healthNetworkName
	 */
	public String getHealthNetworkName() {
		return healthNetworkName;
	}
	
	/**
	 * @return the level10FilterText
	 */
	public String getLevel10FilterText() {
		return level10FilterText;
	}
	
	/**
	 * @return the level20FilterText
	 */
	public String getLevel20FilterText() {
		return level20FilterText;
	}
	
	/**
	 * @return the level30FilterText
	 */
	public String getLevel30FilterText() {
		return level30FilterText;
	}
	
	/**
	 * @return the level40FilterText
	 */
	public String getLevel40FilterText() {
		return level40FilterText;
	}
	
	/**
	 * @return the level50FilterText
	 */
	public String getLevel50FilterText() {
		return level50FilterText;
	}
	
	/**
	 * @return the level60FilterText
	 */
	public String getLevel60FilterText() {
		return level60FilterText;
	}
	
	/**
	 * @return the level70FilterText
	 */
	public String getLevel70FilterText() {
		return level70FilterText;
	}
	
	/**
	 * @return the level80FilterText
	 */
	public String getLevel80FilterText() {
		return level80FilterText;
	}
	
	/**
	 * @return the level90FilterText
	 */
	public String getLevel90FilterText() {
		return level90FilterText;
	}
	
	/**
	 * @return the practictionerFilterText
	 */
	public String getPractictionerFilterText() {
		return practictionerFilterText;
	}

	/**
	 * @return the settings
	 */
	public List<UserSetting> getSettings() {
		return settings;
	}
	
	
	/**
	 * @param settings the settings to set
	 */
	public void setSettings(List<UserSetting> settings) {
		this.settings = settings;
	}
}
