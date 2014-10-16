package com.lpasystems.premieraco.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.lpasystems.premieraco.representations.ReportInfo;

public class ReportInfoMapper implements ResultSetMapper<ReportInfo> {
	
	/**
	 * Creates a UseDisplayValuePair object from database fields
	 * report_specification_id and report_code
	 */
	@Override
	public ReportInfo map(int index, ResultSet rs,
			StatementContext statementContext) throws SQLException {
		return new ReportInfo(rs.getString("rpt_spfcn_id"), rs.getString("rpt_cd"));
	}
}