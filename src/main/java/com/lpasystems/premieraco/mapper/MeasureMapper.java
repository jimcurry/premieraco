package com.lpasystems.premieraco.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.lpasystems.premieraco.representations.Measure;

public class MeasureMapper implements ResultSetMapper<Measure> {
	
	/**
	 * Creates a Measure object from database fields
	 * perf_msr_cd, msr_grp_cd, msr_type_cd, rvrs_scor_ind
	 */
	@Override
	public Measure map(int index, ResultSet rs,
			StatementContext statementContext) throws SQLException {
		return new Measure(rs.getString("perf_msr_cd"), rs.getString("msr_grp_cd"), rs.getString("msr_type_cd"), rs.getString("rvrs_scor_ind"), null);
	}
}