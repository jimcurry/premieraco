package com.lpasystems.premieraco.resources;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.util.StringMapper;

import com.lpasystems.premieraco.dao.UserDAO;
import com.lpasystems.premieraco.representations.OrganizationHierarchyData;
import com.lpasystems.premieraco.representations.OrganizationLevelList;
import com.lpasystems.premieraco.representations.UserInfo;

/**
 * Resource that returns organization information
 * 
 * @author Jim
 * 
 */
@Path("/organization")
@Produces(MediaType.APPLICATION_JSON)
public class OrganizationResource {

	private final DBI dbi;
	private final UserDAO userDAO;

	public OrganizationResource(DBI jdbi) {
		this.dbi = jdbi;
		this.userDAO = jdbi.onDemand(UserDAO.class);
	}

	private void addChildrenToItem(OrganizationHierarchyData parent, String level, UserInfo userInfo) {
		List<Map<String, Object>> lvlRows = getLevelOrgs(userInfo, level, parent.getData().getId());

		for (@SuppressWarnings("rawtypes")
		Iterator iterator = lvlRows.iterator(); iterator.hasNext();) {
			@SuppressWarnings("rawtypes")
			Map levelRow = (Map) iterator.next();
			if (levelRow.get("lvl_" + level + "_id") != null) {
				OrganizationHierarchyData child = new OrganizationHierarchyData((String) levelRow.get("lvl_" + level + "_nm"), level,
						Integer.toString((Integer) levelRow.get("lvl_" + level + "_id")), parent.getData().getId());

				String nextLevel = String.valueOf(Integer.parseInt(level) + 10);

				addChildrenToItem(child, nextLevel, userInfo);
				parent.addChild(child);
			}
		}
	}
	
	@GET
	@Path("/hierarchy")
	public Response getOrganizationHierarchy(@QueryParam("userName") String userName) {
		List<String> validationMessages = new ArrayList<String>();

		// username
		if (userName == null || userName.length() == 0) {
			validationMessages.add("userName is missing");
		}
		
		UserInfo userInfo = null;;
		if (validationMessages.size() == 0) {
			userInfo = userDAO.getUserInfo(userName);
			
			if (userInfo == null) {
				validationMessages.add("userName not found");
			}
		}
		
		List<OrganizationHierarchyData> organizationHierarchyList = null;
		if (validationMessages.size() == 0) {
			List<Map<String, Object>> lvl10Rows = getLevelOrgs(userInfo, "10", "*");
			organizationHierarchyList = expandOrganizationHierarchy(lvl10Rows, userInfo);
		}

		if (validationMessages.size() > 0) {
			return Response.status(Status.BAD_REQUEST).entity(validationMessages).build();
		}
		else {
			return Response.ok(organizationHierarchyList).build();
		}
	}
	
	@GET
	@Path("/children")
	public Response getChildOrganization(@QueryParam("parentLevelCode") String parentLevelCode, @QueryParam("parentLevelId") String parentLevelId,
			@QueryParam("userName") String userName) {
		List<String> validationMessages = new ArrayList<String>();

		// username
		if (userName == null || userName.length() == 0) {
			validationMessages.add("userName is missing");
		}

		UserInfo userInfo = null;
		;
		if (validationMessages.size() == 0) {
			userInfo = userDAO.getUserInfo(userName);

			if (userInfo == null) {
				validationMessages.add("userName not found");
			}
		}

		// parentLevelCode
		if (parentLevelCode == null || parentLevelCode.length() == 0) {
			validationMessages.add("parentLevelCode is missing");
		}
		else if ("00.10.20.30.40.50.60.70.80".indexOf(parentLevelCode) < 0) {
			validationMessages.add("parentLevelCode is invald");
		}
		
		// parentLevelId
		if (parentLevelId == null || parentLevelId.length() == 0) {
			validationMessages.add("parentLevelId is missing");
		}

		OrganizationLevelList organizationLevelList = null;
		if (validationMessages.size() <= 0) {
			organizationLevelList = getChildOrgList(userInfo, parentLevelCode, parentLevelId);
		}

		if (validationMessages.size() > 0) {
			return Response.status(Status.BAD_REQUEST).entity(validationMessages).build();
		}
		else {
			return Response.ok(organizationLevelList).build();
		}
	}

	private List<OrganizationHierarchyData> expandOrganizationHierarchy(List<Map<String, Object>> lvl10Rows, UserInfo userinfo) {
		List<OrganizationHierarchyData> organizationHierarchyList = new ArrayList<OrganizationHierarchyData>();

		for (@SuppressWarnings("rawtypes")
		Iterator iterator = lvl10Rows.iterator(); iterator.hasNext();) {
			@SuppressWarnings("rawtypes")
			Map level10Row = (Map) iterator.next();
			OrganizationHierarchyData organizationHierarchyData = new OrganizationHierarchyData((String) level10Row.get("lvl_10_nm"), "10",
					Integer.toString((Integer) level10Row.get("lvl_10_id")),
					null);
			organizationHierarchyList.add(organizationHierarchyData);
			addChildrenToItem(organizationHierarchyData, "20", userinfo);
		}

		return organizationHierarchyList;
	}

	/*
	 * @param userInfo
	 * @param levelCode - indicating what level to retrieve
	 * @param parentLevelValue if not "*" will restrict to row returned to those
	 * whose parent has the passed in value.
	 * @return the level10 org info associated with the passed in userinfo
	 */
	private List<Map<String, Object>> getLevelOrgs(UserInfo userInfo, String levelCode, String parentLevelId) {
		try (Handle h = dbi.open()) {
			StringBuilder queryTxt = new StringBuilder(
					"SELECT DISTINCT lvl_" + levelCode + "_id, "
					+ "lvl_" + levelCode + "_nm "
				+ "FROM org_dim "
				+ "WHERE rcrd_pce_cst_src_nm = :healthNetworkName ");
			
			//Add parent filter
			if (!parentLevelId.equals("*")) {
				if (parentLevelId.equals("20")) {
					queryTxt.append("AND lvl_10_id = " + parentLevelId + " ");
				}
				
				if (parentLevelId.equals("30")) {
					queryTxt.append("AND lvl_20_id = " + parentLevelId + " ");
				}
				
				if (parentLevelId.equals("40")) {
					queryTxt.append("AND lvl_30_id = " + parentLevelId + " ");
				}
				
				if (parentLevelId.equals("50")) {
					queryTxt.append("AND lvl_40_id = " + parentLevelId + " ");
				}
				
				if (parentLevelId.equals("60")) {
					queryTxt.append("AND lvl_50_id = " + parentLevelId + " ");
				}
				
				if (parentLevelId.equals("70")) {
					queryTxt.append("AND lvl_60_id = " + parentLevelId + " ");
				}
				
				if (parentLevelId.equals("80")) {
					queryTxt.append("AND lvl_70_id = " + parentLevelId + " ");
				}
				
				if (parentLevelId.equals("90")) {
					queryTxt.append("AND lvl_80_id = " + parentLevelId + " ");
				}
			}
			
			//Add in security filter
			if (userInfo.getLevel10FilterText() != null && !userInfo.getLevel10FilterText().equals("*")) {
				queryTxt.append("AND lvl_10_id in (" + userInfo.getLevel10FilterText() + ") ");
			}
			
			if (levelCode.compareTo("20")  >= 0 && userInfo.getLevel20FilterText() != null && !userInfo.getLevel20FilterText().equals("*")) {
				queryTxt.append("AND lvl_20_id in (" + userInfo.getLevel20FilterText() + ") ");
			}
			
			if (levelCode.compareTo("30")  >= 0 && userInfo.getLevel30FilterText() != null && !userInfo.getLevel30FilterText().equals("*")) {
				queryTxt.append("AND lvl_30_id in (" + userInfo.getLevel30FilterText() + ") ");
			}
			
			if (levelCode.compareTo("40")  >= 0 && userInfo.getLevel40FilterText() != null && !userInfo.getLevel40FilterText().equals("*")) {
				queryTxt.append("AND lvl_40_id in (" + userInfo.getLevel40FilterText() + ") ");
			}
			
			if (levelCode.compareTo("50")  >= 0 && userInfo.getLevel50FilterText() != null && !userInfo.getLevel50FilterText().equals("*")) {
				queryTxt.append("AND lvl_50_id in (" + userInfo.getLevel50FilterText() + ") ");
			}
			
			if (levelCode.compareTo("60")  >= 0 && userInfo.getLevel60FilterText() != null && !userInfo.getLevel60FilterText().equals("*")) {
				queryTxt.append("AND lvl_60_id in (" + userInfo.getLevel60FilterText() + ") ");
			}
			
			if (levelCode.compareTo("70")  >= 0 && userInfo.getLevel70FilterText() != null && !userInfo.getLevel70FilterText().equals("*")) {
				queryTxt.append("AND lvl_70_id in (" + userInfo.getLevel70FilterText() + ") ");
			}
			
			if (levelCode.compareTo("80")  >= 0 && userInfo.getLevel80FilterText() != null && !userInfo.getLevel80FilterText().equals("*")) {
				queryTxt.append("AND lvl_80_id in (" + userInfo.getLevel80FilterText() + ") ");
			}
			
			if (levelCode.compareTo("90")  >= 0 && userInfo.getLevel90FilterText() != null && !userInfo.getLevel90FilterText().equals("*")) {
				queryTxt.append("AND lvl_90_id in (" + userInfo.getLevel90FilterText() + ") ");
			}

			List<Map<String, Object>> list = h.createQuery(queryTxt.toString())
					.bind("healthNetworkName", userInfo.getHealthNetworkName())
					.list();
			return list;
		}
	}


	private OrganizationLevelList getChildOrgList(UserInfo userInfo, String parentLevelCode, String parentLevelId) {
		try (Handle h = dbi.open()) {
			String childLevel = String.valueOf(Integer.parseInt(parentLevelCode) + 10);
			
			StringBuilder queryTxt = new StringBuilder(
					"SELECT DISTINCT lvl_" + childLevel + "_id org_id, "
					+ "lvl_" + childLevel + "_nm org_nm "
				+ "FROM org_dim "
				+ "WHERE rcrd_pce_cst_src_nm = :healthNetworkName ");
			
			// Add parent filter
			if (parentLevelCode.equals("10")) {
				queryTxt.append("AND lvl_10_id = " + parentLevelId + " ");
			}

			if (parentLevelCode.equals("20")) {
				queryTxt.append("AND lvl_10_id = " + parentLevelId + " ");
			}

			if (parentLevelCode.equals("30")) {
				queryTxt.append("AND lvl_20_id = " + parentLevelId + " ");
			}

			if (parentLevelCode.equals("40")) {
				queryTxt.append("AND lvl_30_id = " + parentLevelId + " ");
			}

			if (parentLevelCode.equals("50")) {
				queryTxt.append("AND lvl_40_id = " + parentLevelId + " ");
			}

			if (parentLevelCode.equals("60")) {
				queryTxt.append("AND lvl_50_id = " + parentLevelId + " ");
			}

			if (parentLevelCode.equals("70")) {
				queryTxt.append("AND lvl_60_id = " + parentLevelId + " ");
			}

			if (parentLevelCode.equals("80")) {
				queryTxt.append("AND lvl_70_id = " + parentLevelId + " ");
			}
			
			//Add in security filter
			if (userInfo.getLevel10FilterText() != null && !userInfo.getLevel10FilterText().equals("*")) {
				queryTxt.append("AND lvl_10_id in (" + userInfo.getLevel10FilterText() + ") ");
			}
			
			if (childLevel.compareTo("20")  >= 0 && userInfo.getLevel20FilterText() != null && !userInfo.getLevel20FilterText().equals("*")) {
				queryTxt.append("AND lvl_20_id in (" + userInfo.getLevel20FilterText() + ") ");
			}
			
			if (childLevel.compareTo("30")  >= 0 && userInfo.getLevel30FilterText() != null && !userInfo.getLevel30FilterText().equals("*")) {
				queryTxt.append("AND lvl_30_id in (" + userInfo.getLevel30FilterText() + ") ");
			}
			
			if (childLevel.compareTo("40")  >= 0 && userInfo.getLevel40FilterText() != null && !userInfo.getLevel40FilterText().equals("*")) {
				queryTxt.append("AND lvl_40_id in (" + userInfo.getLevel40FilterText() + ") ");
			}
			
			if (childLevel.compareTo("50")  >= 0 && userInfo.getLevel50FilterText() != null && !userInfo.getLevel50FilterText().equals("*")) {
				queryTxt.append("AND lvl_50_id in (" + userInfo.getLevel50FilterText() + ") ");
			}
			
			if (childLevel.compareTo("60")  >= 0 && userInfo.getLevel60FilterText() != null && !userInfo.getLevel60FilterText().equals("*")) {
				queryTxt.append("AND lvl_60_id in (" + userInfo.getLevel60FilterText() + ") ");
			}
			
			if (childLevel.compareTo("70")  >= 0 && userInfo.getLevel70FilterText() != null && !userInfo.getLevel70FilterText().equals("*")) {
				queryTxt.append("AND lvl_70_id in (" + userInfo.getLevel70FilterText() + ") ");
			}
			
			if (childLevel.compareTo("80")  >= 0 && userInfo.getLevel80FilterText() != null && !userInfo.getLevel80FilterText().equals("*")) {
				queryTxt.append("AND lvl_80_id in (" + userInfo.getLevel80FilterText() + ") ");
			}
			
			if (childLevel.compareTo("90")  >= 0 && userInfo.getLevel90FilterText() != null && !userInfo.getLevel90FilterText().equals("*")) {
				queryTxt.append("AND lvl_90_id in (" + userInfo.getLevel90FilterText() + ") ");
			}

			List<Map<String, Object>> list = h.createQuery(queryTxt.toString())
					.bind("healthNetworkName", userInfo.getHealthNetworkName())
					.list();
			
			List<OrganizationLevelList.OrganizationInfo> organizationLevelList = new ArrayList<OrganizationLevelList.OrganizationInfo>();
			for (@SuppressWarnings("rawtypes")
			Iterator iterator = list.iterator(); iterator.hasNext();) {
				@SuppressWarnings("rawtypes")
				Map listItem = (Map) iterator.next();
				if (listItem.get("org_id") != null) {
					OrganizationLevelList.OrganizationInfo organizationLevel = 
							new OrganizationLevelList.OrganizationInfo((String) listItem.get("org_nm"),
							Integer.toString((Integer) listItem.get("org_id")));					
					organizationLevelList.add(organizationLevel);
				}
			}
			
			String levelDesc = getLevelName(userInfo, childLevel);

			return new OrganizationLevelList(levelDesc, organizationLevelList);
		}
	}
	
	/**
	 * Returns the level name for that passed in level
	 * 
	 * @param levelCode
	 * @returnt he level name for that passed in level
	 */
	private String getLevelName(UserInfo userInfo, String levelCode) {
		try (Handle h = dbi.open()) {
			StringBuilder queryTxt = new StringBuilder(
					"SELECT lvl_" + levelCode + "_lvl_descr "
				+ "FROM org_dim "
				+ "WHERE rcrd_pce_cst_src_nm = :healthNetworkName ");
			

			String levelDesc = h.createQuery(queryTxt.toString())
					.bind("healthNetworkName", userInfo.getHealthNetworkName())
					.map(StringMapper.FIRST).first();
			return levelDesc;
		}
	}

}
