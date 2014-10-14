package com.lpasystems.premieraco.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.lpasystems.premieraco.representations.NameIdPair;

public class ProgramMapper implements ResultSetMapper<NameIdPair> {
	
	/**
	 * Creates a UseDisplayValuePair object from database fields
	 * prgm_id and prgm_nm
	 */
	@Override
	public NameIdPair map(int index, ResultSet rs,
			StatementContext statementContext) throws SQLException {
		return new NameIdPair(Integer.toString(rs.getInt("prgm_id")), rs.getString("prgm_nm"));
	}
}