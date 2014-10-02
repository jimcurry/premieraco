package com.lpasystems.premieraco.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.lpasystems.premieraco.representations.UserSetting;

public class UserSettingsMapper implements ResultSetMapper<UserSetting> {
	
	/**
	 * Creates a UserSettingr object from database fields
	 * user setting name and user setting text
	 */
	@Override
	public UserSetting map(int index, ResultSet rs,
			StatementContext statementContext) throws SQLException {
		return new UserSetting(rs.getString("usr_setting_nm"), rs.getString("usr_setting_txt"));
	}
}