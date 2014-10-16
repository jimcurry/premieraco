package com.lpasystems.premieraco.dao;

import java.util.List;

import org.skife.jdbi.v2.HashPrefixStatementRewriter;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.sqlobject.customizers.OverrideStatementRewriterWith;

import com.lpasystems.premieraco.mapper.ReportInfoMapper;
import com.lpasystems.premieraco.representations.ReportInfo;

public interface ReportInfoDAO {

	/**
	 * Returns the Program for the health network name
	 * 
	 * @param userName
	 */
	@Mapper(ReportInfoMapper.class)
	// @formatter:off
	@OverrideStatementRewriterWith(HashPrefixStatementRewriter.class)
	@SqlQuery("SELECT rpt_cd " +
		      ", rpt_spfcn_id " +
		 "FROM rpt_dim " +
		"where pce_cst_nm = #pce_cst_nm " +
		"order by rpt_cd "
		 )
	// @formatter:on
	List<ReportInfo> getReportInfoList(@Bind("pce_cst_nm") String pce_cst_nm);
}
