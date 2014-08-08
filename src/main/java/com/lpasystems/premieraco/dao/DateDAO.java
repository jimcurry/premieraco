package com.lpasystems.premieraco.dao;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

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
//	@SqlQuery("select distinct MonthKey, MonthYear " +
//		          "from DateDim " +
//		         "where MonthKey <= cast(:startMonth as int) " +
//		           "and MonthKey >= convert(char(8), dateadd(month, -:numberMonths+1, convert(datetime, :startMonth, 116)), 112) " +
//		         "order by MonthKey desc ")
	@SqlQuery("SELECT msr_rpt_prd_dk MonthKey, " +
			"DATENAME(month, CONVERT(datetime, CONVERT(varchar(8), msr_rpt_prd_dk), 112)) + ', ' + " +
			"DATENAME(yyyy, CONVERT(datetime, CONVERT(varchar(8), msr_rpt_prd_dk), 112)) MonthYear " +
      "from msr_rpt_prd_dim " +
     "where msr_rpt_prd_dk <= cast(:startMonth as int) " +
       "and msr_rpt_prd_dk >= convert(char(8), dateadd(month, -:numberMonths+1, convert(datetime, :startMonth, 116)), 112) " +
     "order by msr_rpt_prd_dk desc ")
	// @formatter:on
	List<UseDisplayValuePair> getMonthList(@Bind("startMonth") String startMonth,
			@Bind("numberMonths") int numberMonths);

}
