package com.lpasystems.premieraco.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.lpasystems.premieraco.representations.TreeViewData;

public class LocationMapper implements ResultSetMapper<TreeViewData> {

	/**
	 * Creates a UseDisplayValuePair object from database fields
	 * LocationKey and LocationName
	 */
	@Override
	public TreeViewData map(int index, ResultSet rs, StatementContext statementContext)
			throws SQLException {
		
		return new TreeViewData(rs.getString("LocationName"), "Location", Integer.toString(rs
				.getInt("LocationKey")));
	}
}
