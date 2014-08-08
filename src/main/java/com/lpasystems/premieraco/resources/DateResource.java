package com.lpasystems.premieraco.resources;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.skife.jdbi.v2.DBI;

import com.lpasystems.premieraco.dao.DateDAO;
import com.lpasystems.premieraco.representations.UseDisplayValuePair;

/**
 * Resource that returns date information
 * 
 * @author Jim
 *
 */
@Path("/date")
@Produces(MediaType.APPLICATION_JSON)
public class DateResource {
	
	private final DateDAO dateDAO;

	public DateResource(DBI jdbi) {
		dateDAO = jdbi.onDemand(DateDAO.class);
	}
	
	/**
	 * Returns a list of "Use/Display value pairs of months starting with the 
	 * passed in month and going back in time to return the number of months passed 
	 * in the numberMonths parameter
	 * 
	 * @param startMonth in YYYYMM format
	 * @param numberMonths integer > 0
	 * @return see above
	 */
	@GET
	@Path("/monthRange")
	public Response getDateList(@QueryParam("startMonth") String startMonth, @QueryParam("numberMonths") String numberMonths) {
		
		// Validate parameters
		// startMonth
		List<String> validationMessages = new ArrayList<String>();
		int numberMonthsInt = 0;

		if (startMonth == null) {
			validationMessages.add("startMonth parameter is missing");
		}
		else {
			if (startMonth.length() != 6) {
				validationMessages.add("startMonth parameter not in YYYYMM format (length wrong)");
			}
			else {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				sdf.setLenient(false);
				try {
					@SuppressWarnings("unused")
					Date test = sdf.parse(startMonth + "01");
				}
				catch (ParseException pe) {
					validationMessages.add("startMonth parameter not in YYYYMM format");
				}
			}
		}
		
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
			List<UseDisplayValuePair> monthList = dateDAO.getMonthList(startMonth + "01", numberMonthsInt);

			return Response.ok(monthList).build();
		}
	}
}
