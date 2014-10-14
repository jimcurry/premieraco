package com.lpasystems.premieraco.dao;

import java.util.List;

import org.skife.jdbi.v2.HashPrefixStatementRewriter;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.sqlobject.customizers.OverrideStatementRewriterWith;

import com.lpasystems.premieraco.mapper.ProgramMapper;
import com.lpasystems.premieraco.representations.NameIdPair;

public interface ProgramDAO {

	/**
	 * Returns the Program for the health network name
	 * 
	 * @param userName
	 */
	@Mapper(ProgramMapper.class)
	// @formatter:off
	@OverrideStatementRewriterWith(HashPrefixStatementRewriter.class)
	@SqlQuery("SELECT DISTINCT prgm_id " +
		      ", prgm_nm " +
		 "FROM prgm_org " +
		"where pce_cst_nm = #pce_cst_nm " + 
		"order by prgm_nm "
		 )
	// @formatter:on
	List<NameIdPair> getProgramList(@Bind("pce_cst_nm") String pce_cst_nm);
}
