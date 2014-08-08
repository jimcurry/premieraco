package com.lpasystems.premieraco.representations;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds the data in the format needed to display a tree view control on the
 * client
 * 
 * @author Jim
 * 
 */
public class TreeViewData {

	public class Data {
		private final String type;

		private final String id;

		public Data(String type, String id) {
			this.type = type;
			this.id = id;
		}

		public String getId() {
			return id;
		}

		public String getType() {
			return type;
		}
	}

	private final String label;

	private final Data data;

	private final List<TreeViewData> children = new ArrayList<TreeViewData>();

	/**
	 * Creates an instance of the class using the passed in label and data
	 * 
	 * @param label
	 * @param data
	 */
	public TreeViewData(String label, String type, String id) {
		this.label = label;
		this.data = new Data(type, id);
	}

	/**
	 * Adds a TreeViewData instance to this objects children list.
	 * 
	 * @param treeViewData
	 *          TreeViewData to add to children
	 */
	public void addChild(TreeViewData treeViewData) {
		children.add(treeViewData);
	}

	/**
	 * 
	 * @return the children
	 */
	public List<TreeViewData> getChildren() {
		return children;
	}

	public Data getData() {
		return data;
	}

	/**
	 * 
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

}
