package com.lpasystems.premieraco.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.lpasystems.premieraco.representations.UseDisplayValuePair;

public class MonthListMapper implements ResultSetMapper<UseDisplayValuePair> {
	
	/**
	 * Creates a UseDisplayValuePair object from database fields
	 * MonthKey and MonthYear
	 */
	@Override
	public UseDisplayValuePair map(int index, ResultSet rs,
			StatementContext statementContext) throws SQLException {
		return new UseDisplayValuePair(Integer.toString(rs.getInt("MonthKey")), rs.getString("MonthYear"));
	}
}