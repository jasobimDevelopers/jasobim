package com.my.spring.DAO;

import com.my.spring.model.HouseType;

public interface HouseTypeDao {
	public HouseType getHouseTypeByProjectId(Long projectId);
	public boolean addHouseType(HouseType houseType);
	public boolean updateHouseType(HouseType houseType);
}
