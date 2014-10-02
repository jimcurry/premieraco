package com.lpasystems.premieraco.representations;

import java.util.List;

public class OrganizationLevelList {
	public static class OrganizationInfo {
		private final String organizatoinName;

		private final String organizationId;
		
		public OrganizationInfo(String organizatoinName, String organizationId) {
			this.organizatoinName = organizatoinName;
			this.organizationId = organizationId;
		}

		/**
		 * @return the organizationId
		 */
		public String getOrganizationId() {
			return organizationId;
		}

		/**
		 * @return the organizatoinName
		 */
		public String getOrganizatoinName() {
			return organizatoinName;
		}
	}
	
	private final List<OrganizationInfo> organizationList;

	private final String organizationDesc;

	/**
	 * 
	 * 
	 * @param organizationDesc
	 * @param organizationList
	 */
	public OrganizationLevelList(String organizationDesc, List<OrganizationInfo> organizationList) {
		this.organizationList = organizationList;
		this.organizationDesc = organizationDesc;
	}

	/**
	 * @return the organizationDesc
	 */
	public String getOrganizationDesc() {
		return organizationDesc;
	}
	
	/**
	 * @return the organizationList
	 */
	public List<OrganizationInfo> getOrganizationList() {
		return organizationList;
	}

}
