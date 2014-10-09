package com.lpasystems.premieraco.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.skife.jdbi.v2.DBI;

import com.lpasystems.premieraco.dao.DomainDAO;
import com.lpasystems.premieraco.dao.UserDAO;
import com.lpasystems.premieraco.representations.NameIdPair;
import com.lpasystems.premieraco.representations.UserInfo;

/**
 * Resource that returns user information
 * 
 * @author Jim
 *
 */
@Path("/domain")
@Produces(MediaType.APPLICATION_JSON)
public class DomainResource {
	
	private final UserDAO userDAO;
	private final DomainDAO domainDAO;

	public DomainResource(DBI jdbi) {
		userDAO = jdbi.onDemand(UserDAO.class);
		domainDAO = jdbi.onDemand(DomainDAO.class);
	}
	
	/**
	 * Returns a UserInfo object for the passed in user name
	 * 
	 * @param userName
	 * @return see above
	 */
	@GET
	public Response getUserInfo(@QueryParam("userName") String userName) {
		
		// Validate parameters
		// startMonth
		List<String> validationMessages = new ArrayList<String>();

		// username
		if (userName == null || userName.length() == 0) {
			validationMessages.add("userName is missing");
		}

		if (validationMessages.size() > 0) {
			return Response.status(Status.BAD_REQUEST).entity(validationMessages).build();
		}
		else {
			UserInfo userInfo = userDAO.getUserInfo(userName);

			if (userInfo == null) {
				validationMessages.add("userName was not found");
				return Response.status(Status.BAD_REQUEST).entity(validationMessages).build();
			}
			else {
				
				List<NameIdPair> domains = domainDAO.getDomainList(userInfo.getHealthNetworkName());

				return Response.ok(domains).build();
			}
		}
	}
}
