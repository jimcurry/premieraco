package com.lpasystems.premieraco.resources;

import java.math.BigDecimal;
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

import com.lpasystems.premieraco.dao.UserDAO;
import com.lpasystems.premieraco.representations.Measure;
import com.lpasystems.premieraco.representations.UserInfo;

/**
 * Resource that returns organization information
 * 
 * @author Jim
 * 
 */
@Path("/measure")
@Produces(MediaType.APPLICATION_JSON)
public class MeasureResource {

	private final DBI dbi;
	private final UserDAO userDAO;
	

	public MeasureResource(DBI jdbi) {
		this.dbi = jdbi;
		
		userDAO = jdbi.onDemand(UserDAO.class);
	}

	@GET
	public Response getMeasure(@QueryParam("measureCode") String measureCode, @QueryParam("measureGroupCode") String measureGroupCode, @QueryParam("userName") String userName) {
		List<String> validationMessages = new ArrayList<String>();

		//measureCode
		if (measureCode == null) {
			validationMessages.add("measureCode parameter is missing");
		}
		
		//measureGroupCode
		if (measureGroupCode == null) {
			validationMessages.add("measureGroupCode parameter is missing");
		}
		
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
		
		Measure measure = null;
		
		if (validationMessages.size() == 0) {
			measure = getMeasureFromDb(measureCode, measureGroupCode, userInfo.getHealthNetworkName());
			
			if (measure == null) {
				validationMessages.add("measure not found");
			}
		}
	
		if (validationMessages.size() > 0) {
			return Response.status(Status.BAD_REQUEST).entity(validationMessages).build();
		}
		else {
			return Response.ok(measure).build();
		}
	}

	/*
	 * @param userInfo
	 * @param levelCode - indicating what level to retrieve
	 * @param parentLevelValue if not "*" will restrict to row returned to those
	 * whose parent has the passed in value.
	 * @return the level10 org info associated with the passed in userinfo
	 */
	private Measure getMeasureFromDb(String measureCode, String measureGroupCode, String healthNetworkName) {
		try (Handle h = dbi.open()) {
			StringBuilder queryTxt = new StringBuilder(
						"SELECT pmd.perf_msr_cd, " +
								"pmd.msr_grp_cd, " +
								"pmd.msr_type_cd, " +
								"rvrs_scor_ind, " +
								"rd.rng_strt_msr_val, " +
								"rd.rng_end_msr_val, " +
								"rd.rng_pctile_nm, " +
								"rng_pctile_cd " +
						"FROM perf_msr_dim pmd " +
							"INNER JOIN msr_rng_fct mrf " +
								"ON ( pmd.perf_msr_dk = mrf.perf_msr_dk ) " +
							"INNER JOIN rng_dim rd " +
								"ON ( mrf.rng_dk = rd.rng_dk ) " +
				"WHERE  pmd.perf_msr_cd = :measureCode " + 
					"AND pmd.msr_grp_cd = :measureGroupCode " +
					"AND pmd.pce_cst_nm = :healthNetworkName " +
					"AND rng_pctile_cd != '< 30' " +
					"AND pmd.actv_ind = 'Y' " +
				"ORDER BY rng_pctile_nm "
			);

			List<Map<String, Object>> measuresList = h.createQuery(queryTxt.toString())
					.bind("measureCode", measureCode)
					.bind("measureGroupCode", measureGroupCode)
					.bind("healthNetworkName", healthNetworkName)
					.list();
			
			if (measuresList.size() == 0) {
				return null;
			}
			else {			
				String measureTypeCode = null;
				String reverseScoreInd = null;
				List<Measure.Range> ranges = new ArrayList<Measure.Range>();
				
				ranges.add(new Measure.Range(new BigDecimal("0"), "None"));
				
				for (@SuppressWarnings("rawtypes")
				Iterator iterator = measuresList.iterator(); iterator.hasNext();) {
					@SuppressWarnings("rawtypes")
					Map measureRow = (Map) iterator.next();
					
					if (measureTypeCode == null) {
						measureTypeCode = (String) measureRow.get("msr_type_cd");
						reverseScoreInd = (String) measureRow.get("rvrs_scor_ind");
					}
					ranges.add(new Measure.Range(
							 (BigDecimal) measureRow.get("rng_strt_msr_val"),
							 (String) measureRow.get("rng_pctile_nm") 
					));
				}
				
				return new Measure(
						measureCode,
						measureGroupCode,
						measureTypeCode,
						reverseScoreInd,
						ranges);
			}
		}
	}

}
