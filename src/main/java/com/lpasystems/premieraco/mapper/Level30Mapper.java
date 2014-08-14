package com.lpasystems.premieraco.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.lpasystems.premieraco.representations.TreeViewData;

public class Level30Mapper implements ResultSetMapper<TreeViewData> {

	/**
	 * Creates a TreeViewData object from database fields
	 * AcoKey and AcoName
	 */
	@Override
	public TreeViewData map(int index, ResultSet rs, StatementContext statementContext)
			throws SQLException {

		return new TreeViewData(rs.getString("lvl_30_nm"), "30", Integer.toString(rs.getInt("lvl_30_id")));
	}
}
