package com.lpasystems.premieraco.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.lpasystems.premieraco.representations.Domain;

public class DomainMapper implements ResultSetMapper<Domain> {
	
	/**
	 * Creates a Domain object from database fields
	 * dmn_nm and dmn_dk
	 */
	@Override
	public Domain map(int index, ResultSet rs,
			StatementContext statementContext) throws SQLException {
		return new Domain(Integer.toString(rs.getInt("dmn_dk")), rs.getString("dmn_nm"), null);
	}
}