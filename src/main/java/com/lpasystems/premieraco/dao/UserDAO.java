package com.lpasystems.premieraco.dao;

import java.util.List;

import org.skife.jdbi.v2.HashPrefixStatementRewriter;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.sqlobject.customizers.OverrideStatementRewriterWith;

import com.lpasystems.premieraco.mapper.UserInfoMapper;
import com.lpasystems.premieraco.mapper.UserSettingsMapper;
import com.lpasystems.premieraco.representations.UserInfo;
import com.lpasystems.premieraco.representations.UserSetting;

public interface UserDAO {

	/**
	 * Returns the User Information for the passed in user name
	 * 
	 * @param userName
	 */
	@Mapper(UserInfoMapper.class)
	// @formatter:off
	@OverrideStatementRewriterWith(HashPrefixStatementRewriter.class)
	@SqlQuery("SELECT pce_cst_nm " +
		      ", apl_rl_cd " +
		      ", pract_fltr_txt " +
		      ", lvl_10_fltr_txt " +
		      ", lvl_20_fltr_txt " +
		      ", lvl_30_fltr_txt " +
		      ", lvl_40_fltr_txt " +
		      ", lvl_50_fltr_txt " +
		      ", lvl_60_fltr_txt " +
		      ", lvl_70_fltr_txt " +
		      ", lvl_80_fltr_txt " +
		      ", lvl_90_fltr_txt " +
		 "FROM usr_info " +
		"where upper(usr_nm) = upper(#userName) "
		 )
	// @formatter:on\
	UserInfo getUserInfo(@Bind("userName") String userName);

	/**
	 * Returns the User Information for the passed in user name
	 * 
	 * @param userName
	 */
	@Mapper(UserSettingsMapper.class)
	// @formatter:off
	@OverrideStatementRewriterWith(HashPrefixStatementRewriter.class)
	@SqlQuery("SELECT usr_setting_nm " +
		      ", usr_setting_txt " +
		 "FROM usr_settings " +
		"where upper(usr_nm) = upper(#userName) "
		 )
	// @formatter:on\
	List<UserSetting> getUserSettings(@Bind("userName") String userName);

}
