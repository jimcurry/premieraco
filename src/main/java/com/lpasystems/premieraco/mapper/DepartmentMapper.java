package com.lpasystems.premieraco.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.lpasystems.premieraco.representations.TreeViewData;

public class DepartmentMapper implements ResultSetMapper<TreeViewData> {

	/**
	 * Creates a TreeViewData object from database fields DepartmentKey and
	 * DepartmentName
	 */
	@Override
	public TreeViewData map(int index, ResultSet rs, StatementContext statementContext)
			throws SQLException {

		return new TreeViewData(rs.getString("DepartmentName"), "30", Integer.toString(rs
				.getInt("DepartmentKey")));
	}
}
