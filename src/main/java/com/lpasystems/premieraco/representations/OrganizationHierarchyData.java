package com.lpasystems.premieraco.representations;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds data needed to display the Organization Hierarchy
 * 
 * @author Jim
 * 
 */
public class OrganizationHierarchyData {

	public class Data {
		private final String id;

		private final String parentId;

		private final String type;

		public Data(String type, String id, String parentId) {
			this.type = type;
			this.id = id;
			this.parentId = parentId;
		}

		public String getId() {
			return id;
		}

		public String getParentId() {
			return parentId;
		}
		

		public String getType() {
			return type;
		}
	}

	private final int hierarchyId;

	private final int parentHierarchyId;

	private final int programId;

	private final String label;

	private final Data data;

	private final List<OrganizationHierarchyData> children = new ArrayList<OrganizationHierarchyData>();
	
	/**
	 * Creates an instance of the class using the passed in label and data
	 * 
	 * @param label
	 * @param data
	 */
	public OrganizationHierarchyData(String label, String type, String id, String parentId, int hierarchyId, int parentHierarchyId, int programId) {
		this.label = label;
		this.hierarchyId = hierarchyId;
		this.parentHierarchyId = parentHierarchyId;
		this.programId = programId;
		this.data = new Data(type, id, parentId);
	}

	/**
	 * Adds a TreeViewData instance to this objects children list.
	 * 
	 * @param treeViewData
	 *          TreeViewData to add to children
	 */
	public void addChild(OrganizationHierarchyData treeViewData) {
		children.add(treeViewData);
	}
	
	/**
	 * 
	 * @return the children
	 */
	public List<OrganizationHierarchyData> getChildren() {
		return children;
	}

	public Data getData() {
		return data;
	}

	/**
	 * @return the hierarchyId
	 */
	public int getHierarchyId() {
		return hierarchyId;
	}

	/**
	 * 
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @return the parentHierarchyId
	 */
	public int getParentHierarchyId() {
		return parentHierarchyId;
	}

	/**
	 * @return the programId
	 */
	public int getProgramId() {
		return programId;
	}

}
