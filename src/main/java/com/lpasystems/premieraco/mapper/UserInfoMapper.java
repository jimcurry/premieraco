package com.lpasystems.premieraco.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.lpasystems.premieraco.representations.UserInfo;

public class UserInfoMapper implements ResultSetMapper<UserInfo> {
	
	/**
	 * Creates a UseDisplayValuePair object from database fields
	 * yr_num and mo_of_yr_nm
	 */
	@Override
	public UserInfo map(int index, ResultSet rs,
			StatementContext statementContext) throws SQLException {

		return new UserInfo(rs.getString("pce_cst_nm"),
				rs.getString("apl_rl_cd"),
				rs.getString("pract_fltr_txt"),
				rs.getString("lvl_10_fltr_txt"),
				rs.getString("lvl_20_fltr_txt"),
				rs.getString("lvl_30_fltr_txt"),
				rs.getString("lvl_40_fltr_txt"),
				rs.getString("lvl_50_fltr_txt"),
				rs.getString("lvl_60_fltr_txt"),
				rs.getString("lvl_70_fltr_txt"),
				rs.getString("lvl_80_fltr_txt"),
				rs.getString("lvl_90_fltr_txt")
				);
	}
}