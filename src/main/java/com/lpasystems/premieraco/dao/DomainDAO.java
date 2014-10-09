package com.lpasystems.premieraco.dao;

import java.util.List;

import org.skife.jdbi.v2.HashPrefixStatementRewriter;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.sqlobject.customizers.OverrideStatementRewriterWith;

import com.lpasystems.premieraco.mapper.DomainMapper;
import com.lpasystems.premieraco.representations.NameIdPair;

public interface DomainDAO {

	/**
	 * Returns the User Information for the passed in user name
	 * 
	 * @param userName
	 */
	@Mapper(DomainMapper.class)
	// @formatter:off
	@OverrideStatementRewriterWith(HashPrefixStatementRewriter.class)
	@SqlQuery("SELECT dmn_dk " +
		      ", dmn_nm " +
		 "FROM dmn_dim " +
		"where pce_cst_nm = #pce_cst_nm " + 
		"order by dmn_seq_num "
		 )
	// @formatter:on\
	List<NameIdPair> getDomainList(@Bind("pce_cst_nm") String pce_cst_nm);
}
