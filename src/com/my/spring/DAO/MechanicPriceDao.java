package com.my.spring.DAO;

import java.util.List;

import com.my.spring.model.MechanicPrice;
import com.my.spring.utils.DataWrapper;

public interface MechanicPriceDao {
	 boolean addMechanicPrice(MechanicPrice am);
     boolean deleteMechanicPrice(Long id);
     boolean updateMechanicPrice(MechanicPrice am);
	 DataWrapper<List<MechanicPrice>> getMechanicPriceList(Integer pageIndex, Integer pageSize, MechanicPrice am);
	 DataWrapper<Void> deleteMechanicPriceByMechanicId(Long id);
	MechanicPrice getMechanicPriceById(Long id);
}
