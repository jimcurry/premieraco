package com.lpasystems.premieraco.dao;

import java.util.List;

import org.skife.jdbi.v2.HashPrefixStatementRewriter;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.sqlobject.customizers.OverrideStatementRewriterWith;

import com.lpasystems.premieraco.mapper.ReportingPeriodListMapper;
import com.lpasystems.premieraco.representations.UseDisplayValuePair;

public interface ReportingPeriodDAO {

	/**
	 * Returns a list of dates starting with startMonth and going back in time for
	 * a total numberMonths
	 * 
	 * @param startMonth
	 * @param numberMonths
	 */
	@Mapper(ReportingPeriodListMapper.class)
	// @formatter:off
	@OverrideStatementRewriterWith(HashPrefixStatementRewriter.class)
	@SqlQuery(
		"SELECT DISTINCT msr_rpt_prd_dk, " +
		"        year_txt::int yr_num, " +
		"       month_nm mo_of_yr_nm " +
		  "FROM msr_rpt_prd_dim mrpd " +
		       "INNER JOIN date_dim dd " +
		          "ON ( dd.date_dk = mrpd.msr_rpt_prd_dk ) " +
		       "INNER JOIN practitioner_performance_fact ppf " +
		          "ON ( ppf.performance_reporting_month_dk = mrpd.msr_rpt_prd_dk ) " +
		 "ORDER BY msr_rpt_prd_dk DESC"
		)
 	// @formatter:on\
	List<UseDisplayValuePair> getReportingPeriodList();

}
