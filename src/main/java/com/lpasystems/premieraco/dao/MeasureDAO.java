package com.lpasystems.premieraco.dao;

import java.util.List;

import org.skife.jdbi.v2.HashPrefixStatementRewriter;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.sqlobject.customizers.OverrideStatementRewriterWith;

import com.lpasystems.premieraco.mapper.MeasureMapper;
import com.lpasystems.premieraco.representations.Measure;

public interface MeasureDAO {

	/**
	 * Returns the Measure Information for the passed in domain dk
	 * 
	 * @param programId
	 */
	@Mapper(MeasureMapper.class)
	// @formatter:off
	@OverrideStatementRewriterWith(HashPrefixStatementRewriter.class)
	@SqlQuery("SELECT pmd.perf_msr_cd, " +
                    "pmd.msr_grp_cd, " +
                    "pmd.msr_type_cd, " +
                    "pmd.rvrs_scor_ind " +
             "FROM   perf_msr_dim pmd " +
                    "INNER JOIN dmn_msr_fct dmf " + 
                            "ON ( pmd.perf_msr_dk = dmf.perf_msr_dk ) " +
             "WHERE  dmf.dmn_dk = #domainDk " + 
             "ORDER  BY perf_msr_shrt_nm " 
		 )
	// @formatter:on
	List<Measure> getMeasureListByDomainDk(@Bind("domainDk") int domainDk);
	
}
