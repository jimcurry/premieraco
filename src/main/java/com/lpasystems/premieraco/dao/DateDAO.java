package com.lpasystems.premieraco.dao;

import java.util.List;

import org.skife.jdbi.v2.HashPrefixStatementRewriter;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.sqlobject.customizers.OverrideStatementRewriterWith;

import com.lpasystems.premieraco.mapper.MonthListMapper;
import com.lpasystems.premieraco.representations.UseDisplayValuePair;

public interface DateDAO {

	/**
	 * Returns a list of dates starting with startMonth and going back in time for
	 * a total numberMonths
	 * 
	 * @param startMonth
	 * @param numberMonths
	 */
	@Mapper(MonthListMapper.class)
	// @formatter:off
	@OverrideStatementRewriterWith(HashPrefixStatementRewriter.class)
	@SqlQuery("SELECT msr_rpt_prd_dk MonthKey, " +
			         "trim(to_char(to_date(to_char(msr_rpt_prd_dk, '99999999'), 'YYYYMMDD'), 'Month')) || ', ' || " + 
			         "to_char(to_date(to_char(msr_rpt_prd_dk, '99999999'), 'YYYYMMDD'), 'YYYY') MonthYear " +
			    "FROM old_msr_rpt_prd_dim " +
			   "WHERE msr_rpt_prd_dk <= cast(#startMonth as int) " +
			     "and msr_rpt_prd_dk >= cast(to_char(to_date(#startMonth,'YYYYMMDD')  - (#numberMonths || ' month')::INTERVAL, 'YYYYMMDD') as int) + 1 " +
			"order by monthkey desc ")
	// @formatter:on\
	List<UseDisplayValuePair> getMonthList(@Bind("startMonth") String startMonth,
			@Bind("numberMonths") int numberMonths);

}
