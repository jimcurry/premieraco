package com.lpasystems.premieraco.dao;

import java.util.List;

import org.skife.jdbi.v2.HashPrefixStatementRewriter;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.sqlobject.customizers.OverrideStatementRewriterWith;

import com.lpasystems.premieraco.mapper.DomainMapper;
import com.lpasystems.premieraco.representations.Domain;

public interface DomainDAO {

	/**
	 * Returns the Domain Information for the passed in programId
	 * 
	 * @param programId
	 */
	@Mapper(DomainMapper.class)
	// @formatter:off
	@OverrideStatementRewriterWith(HashPrefixStatementRewriter.class)
	@SqlQuery("SELECT dmn_dk " +
		      ", dmn_nm " +
		 "FROM dmn_dim " +
		"where prgm_id = #programId " + 
		"order by dmn_seq_num "
		 )
	// @formatter:on
	List<Domain> getDomainListByProgramId(@Bind("programId") int programid);
	
}
