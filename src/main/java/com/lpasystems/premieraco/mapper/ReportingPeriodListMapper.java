package com.lpasystems.premieraco.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.lpasystems.premieraco.representations.UseDisplayValuePair;

public class ReportingPeriodListMapper implements ResultSetMapper<UseDisplayValuePair> {
	
	/**
	 * Creates a UseDisplayValuePair object from database fields
	 * yr_num and mo_of_yr_nm
	 */
	@Override
	public UseDisplayValuePair map(int index, ResultSet rs,
			StatementContext statementContext) throws SQLException {

		return new UseDisplayValuePair(Integer.toString(rs.getInt("msr_rpt_prd_dk")), rs.getInt("yr_num") + " " + rs.getString("mo_of_yr_nm"));
	}
}