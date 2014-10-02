package com.lpasystems.premieraco.dao;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import com.lpasystems.premieraco.mapper.AcoMapper;
import com.lpasystems.premieraco.mapper.DepartmentMapper;
import com.lpasystems.premieraco.mapper.HealthSystemMapper;
import com.lpasystems.premieraco.mapper.Level20Mapper;
import com.lpasystems.premieraco.mapper.Level30Mapper;
import com.lpasystems.premieraco.mapper.LocationMapper;
import com.lpasystems.premieraco.mapper.MedicalGroupMapper;
import com.lpasystems.premieraco.mapper.PractitionerMapper;
import com.lpasystems.premieraco.mapper.SuperRegionalNetworkMapper;
import com.lpasystems.premieraco.representations.TreeViewData;

public interface ProviderDAO {

	/**
	 * 
	 * @return list of all the Super Regional Networks. The key is returned in the
	 *         "use" value and the name is returned in the "display" value.
	 */
	@Mapper(SuperRegionalNetworkMapper.class)
	// @formatter:off
	@SqlQuery("select distinct SuperRegionalNetworkKey, "
			           + "SuperRegionalNetworkName "
			      + "from ProviderDim "
		        + "order by SuperRegionalNetworkName " )
	// @formatter:on
	List<TreeViewData> getSuperRegionalNetworkList();

	/**
	 * 
	 * @param superRegionalNetworkKey Key of the Super Regional Network you want all the Acos for 
	 * @return list of all the Acos for the passed in SuperRegionalNetworkKey. The key is returned in the
	 *         "use" value and the name is returned in the "display" value.
	 */
	@Mapper(AcoMapper.class)
	// @formatter:off
//	@SqlQuery("select distinct AcoKey, "
//			           + "AcoName "
//			      + "from ProviderDim "
//			     + "where SuperRegionalNetworkKey = :superRegionalNetworkKey "
//			     + "order by AcoName " )
	@SqlQuery("select distinct lvl_10_id AcoKey, "
								 + "lvl_10_nm AcoName "
 + "from old_pract_dim "
 + "where lvl_10_id > 0 "
+ "order by lvl_10_nm " )
	// @formatter:on
//	List<TreeViewData> getAcoList(@Bind("superRegionalNetworkKey") int superRegionalNetworkKey);
	List<TreeViewData> getAcoList();

	/**
	 * 
	 * @param acoKey Key of the ACO you want all the Health Systems for 
	 * @return list of all the Health Systems for the passed in AcoKey. The key is returned in the
	 *         "use" value and the name is returned in the "display" value.
	 */
	@Mapper(HealthSystemMapper.class)
	// @formatter:off
	@SqlQuery("select distinct HealthSystemKey, "
			           + "HealthSystemName "
		        + "from ProviderDim "
		       + "where AcoKey = :acoKey "
		       + "order by HealthSystemName " )
	// @formatter:on
	List<TreeViewData> getHealthSystemList(@Bind("acoKey") int acoKey);

	/**
	 * 
	 * @param healthSystemKey Key of the Health System you want all the Medical Groups for 
	 * @return list of all the Medical Groups for the passed in Health System Key. The key is returned in the
	 *         "use" value and the name is returned in the "display" value.
	 */
	@Mapper(MedicalGroupMapper.class)
	// @formatter:off
//	@SqlQuery("select distinct MedicalGroupKey, "
//			           + "MedicalGroupName "
//		        + "from ProviderDim "
//		       + "where HealthSystemKey = :healthSystemKey "
//		       + "order by MedicalGroupName " )
	@SqlQuery("select distinct lvl_20_id MedicalGroupKey, "
                           + "lvl_20_nm MedicalGroupName "
            + "from old_pract_dim "
           + "where lvl_20_id = :acoKey "
           + "order by lvl_20_nm " )
	// @formatter:on
//	List<TreeViewData> getMedicalGroupList(@Bind("healthSystemKey") int healthSystemKey);
	List<TreeViewData> getMedicalGroupList(@Bind("acoKey") int acoKey);

	/**
	 * 
	 * @param medicalGroupKey Key of the Health System you want all the Medical Groups for 
	 * @return list of all the Medical Groups for the passed in Health System Key. The key is returned in the
	 *         "use" value and the name is returned in the "display" value.
	 */
	@Mapper(LocationMapper.class)
	// @formatter:off
	@SqlQuery("select distinct LocationKey, "
			           + "LocationName "
		        + "from ProviderDim "
		       + "where MedicalGroupKey = :medicalGroupKey "
		       + "order by LocationName " )
	// @formatter:on
	List<TreeViewData> getLocationList(@Bind("medicalGroupKey") int medicalGroupKey);


	/**
	 * 
	 * @param  locationKey Key of the Location you want all the Departments for 
	 * @return list of all the Medical Groups for the passed in Health System Key. The key is returned in the
	 *         "use" value and the name is returned in the "display" value.
	 */
	@Mapper(DepartmentMapper.class)
	// @formatter:off
//	@SqlQuery("select distinct DepartmentKey, "
//			           + "DepartmentName "
//		        + "from ProviderDim "
//		       + "where LocationKey = :locationKey "
//		       + "order by DepartmentName " )
	@SqlQuery("select distinct lvl_30_id DepartmentKey, "
                          + "lvl_30_nm DepartmentName "
            + "from old_pract_dim "
           + "where lvl_30_id = :medicalGroupKey "
           + "order by lvl_30_nm " )
	// @formatter:on
//	List<TreeViewData> getDepartmentList(@Bind("locationKey") int locationKey);
	List<TreeViewData> getDepartmentList(@Bind("medicalGroupKey") int medicalGroupKey);
	
	/**
	 * 
	 * @return list of all the level 20 groups for the passed in level 10 Key. The key is returned in the
	 *         "use" value and the name is returned in the "display" value.
	 */
	@Mapper(Level20Mapper.class)
	// @formatter:off
	@SqlQuery("select distinct lvl_20_id, "
                          + "lvl_20_nm "
            + "from old_pract_dim "
           + "where lvl_20_nm = 'Premier Health ACO' "
           + "order by lvl_20_nm " )
	// @formatter:on
	List<TreeViewData> getLevel20List();
	
	/**
	 * 
	 * @return list of all the level 30 groups for the passed in level 20 Key. The key is returned in the
	 *         "use" value and the name is returned in the "display" value.
	 */
	@Mapper(Level30Mapper.class)
	// @formatter:off
	@SqlQuery("select distinct lvl_30_id, "
                          + "lvl_30_nm "
            + "from old_pract_dim "
           + "where lvl_20_id = :lvl20id "
           + "order by lvl_30_nm " )
	// @formatter:on
	List<TreeViewData> getLevel30List(@Bind("lvl20id") int lvl20id);

	
	/**
	 * 
	 * @return list of all the Practitioners for the passed in level 30 Key. The key is returned in the
	 *         "use" value and the name is returned in the "display" value.
	 */
	@Mapper(PractitionerMapper.class)
	// @formatter:off
	@SqlQuery("select distinct pract_dk, "
                          + "full_nm "
            + "from old_pract_dim "
           + "where lvl_30_id = :lvl30id "
           + "order by full_nm " )
	// @formatter:on
	List<TreeViewData> getPractitionerList(@Bind("lvl30id") int lvl30id);
}
