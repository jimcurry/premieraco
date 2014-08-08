package com.lpasystems.premieraco.resources;

import java.util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.skife.jdbi.v2.DBI;

import com.lpasystems.premieraco.dao.ProviderDAO;
import com.lpasystems.premieraco.representations.TreeViewData;

/**
 * Resource that returns date information
 * 
 * @author Jim
 * 
 */
@Path("/provider")
@Produces(MediaType.APPLICATION_JSON)
public class ProviderResource {

	private final ProviderDAO providerDAO;

	public ProviderResource(DBI jdbi) {
		providerDAO = jdbi.onDemand(ProviderDAO.class);
	}

	@GET
	@Path("/hierarchy")
	public Response getProviderHierarchy() {
		// List<TreeViewData> superRegionalNetworkList =
		// providerDAO.getSuperRegionalNetworkList();
		//
		// expandProviderHierarchy(superRegionalNetworkList);
		List<TreeViewData> acoList = providerDAO.getAcoList();

		expandProviderHierarchy(acoList);

		return Response.ok(acoList).build();
	}

	private void expandProviderHierarchy(List<TreeViewData> treeViewList) {
		for (Iterator<TreeViewData> iterator = treeViewList.iterator(); iterator.hasNext();) {
			TreeViewData treeViewData = (TreeViewData) iterator.next();
			addChildrenToItem(treeViewData, "20");
		}
	}

	private void addChildrenToItem(TreeViewData parent, String level) {
		List<TreeViewData> childrenToAdd = new ArrayList<TreeViewData>();

		if (level.equals("20")) {
			childrenToAdd = providerDAO.getMedicalGroupList(Integer.parseInt(parent.getData().getId()));

			if (childrenToAdd.size() == 1
					&& childrenToAdd.get(0).getLabel().equals(parent.getLabel())) {
				addChildrenToItem(parent, "30");
				return;
			}
		}
		else if (level.equals("30")) {
			childrenToAdd = providerDAO.getDepartmentList(Integer.parseInt(parent.getData().getId()));

			if (childrenToAdd.size() == 1
					&& childrenToAdd.get(0).getLabel().equals(parent.getLabel())) {
				return;
			}
		}

		for (Iterator<TreeViewData> iterator = childrenToAdd.iterator(); iterator.hasNext();) {
			TreeViewData child = (TreeViewData) iterator.next();

			if (child.getData().getType().equals("20")) {
				addChildrenToItem(child, "30");
			}

			parent.addChild(child);
		}
	}
	// private void addChildrenToItem(TreeViewData parent, String level) {
	// List<TreeViewData> childrenToAdd = new ArrayList<TreeViewData>();
	//
	// if (level.equals("ACO")) {
	// childrenToAdd =
	// providerDAO.getAcoList(Integer.parseInt(parent.getData().getId()));
	//
	// if (childrenToAdd.size() == 1 &&
	// childrenToAdd.get(0).getData().getId().equals(parent.getData().getId())) {
	// addChildrenToItem(parent, "MedicalGroup");
	// return;
	// }
	// }
	// else if (level.equals("HealthSystem")) {
	// childrenToAdd =
	// providerDAO.getHealthSystemList(Integer.parseInt(parent.getData().getId()));
	// }
	// else if (level.equals("MedicalGroup")) {
	// childrenToAdd =
	// providerDAO.getMedicalGroupList(Integer.parseInt(parent.getData().getId()));
	//
	// if (childrenToAdd.size() == 1 &&
	// childrenToAdd.get(0).getData().getId().equals(parent.getData().getId())) {
	// addChildrenToItem(parent, "Location");
	// return;
	// }
	// }
	// else if (level.equals("Location")) {
	// childrenToAdd =
	// providerDAO.getLocationList(Integer.parseInt(parent.getData().getId()));
	//
	// if (childrenToAdd.size() == 1 &&
	// childrenToAdd.get(0).getData().getId().equals(parent.getData().getId())) {
	// addChildrenToItem(parent, "Department");
	// return;
	// }
	// }
	// else if (level.equals("Department")) {
	// childrenToAdd =
	// providerDAO.getDepartmentList(Integer.parseInt(parent.getData().getId()));
	//
	// if (childrenToAdd.size() == 1 &&
	// childrenToAdd.get(0).getData().getId().equals(parent.getData().getId())) {
	// return;
	// }
	// }
	//
	// for (Iterator<TreeViewData> iterator = childrenToAdd.iterator();
	// iterator.hasNext();) {
	// TreeViewData child = (TreeViewData) iterator.next();
	//
	// if (child.getData().getType().equals("ACO")) {
	// addChildrenToItem(child, "HealthSystem");
	// }
	//
	// else if (child.getData().getType().equals("HealthSystem")) {
	// addChildrenToItem(child, "MedicalGroup");
	// }
	//
	// else if (child.getData().getType().equals("MedicalGroup")) {
	// addChildrenToItem(child, "Location");
	// }
	//
	// else if (child.getData().getType().equals("Location")) {
	// addChildrenToItem(child, "Department");
	// }
	//
	// parent.addChild(child);
	// }
	// }

}
