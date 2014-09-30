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

import com.lpasystems.premieraco.dao.ReportingPeriodDAO;
import com.lpasystems.premieraco.representations.UseDisplayValuePair;

/**
 * Resource that returns reporting period information
 * 
 * @author Jim
 *
 */
@Path("/reportingPeriod")
@Produces(MediaType.APPLICATION_JSON)
public class ReportingPeriodResource {
	
	private final ReportingPeriodDAO reportingPeriodDAO;

	public ReportingPeriodResource(DBI jdbi) {
		reportingPeriodDAO = jdbi.onDemand(ReportingPeriodDAO.class);
	}
	
	/**
	 * Returns a list of "Use/Display value pairs of months starting with the 
	 * first month there is measurement data back in time to return the number of months passed 
	 * in the numberMonths parameter
	 * 
	 * @param numberMonths integer > 0
	 * @return see above
	 */
	@GET
	public Response getReportingPeriodList(@QueryParam("numberMonths") String numberMonths) {
		
		// Validate parameters
		// startMonth
		List<String> validationMessages = new ArrayList<String>();
		int numberMonthsInt = 0;

		// numberMonths
		if (numberMonths == null) {
			validationMessages.add("numberMonths parameter is missing");
		}
		else {
			try {
		        numberMonthsInt = Integer.parseInt(numberMonths);
		        
		        if (numberMonthsInt == 0 || numberMonthsInt > 50) {
		     		validationMessages.add("numberMonths parameter not between 1 and 50");
		        }
		    }
		    catch( NumberFormatException e ) {
		   	 validationMessages.add("numberMonths parameter is not numeric");
		    }
		}
		
		if (validationMessages.size() > 0) {
			return Response.status(Status.BAD_REQUEST).entity(validationMessages).build();
		}		
		else {
			List<UseDisplayValuePair> monthList = reportingPeriodDAO.getReportingPeriodList();
			
			while (monthList.size() > numberMonthsInt) {
				monthList.remove(monthList.size() - 1);
			}

			return Response.ok(monthList).build();
		}
	}
}
