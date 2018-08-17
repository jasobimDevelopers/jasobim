package com.my.spring.DAO;

import java.util.List;

import com.my.spring.model.Mechanic;
import com.my.spring.model.MechanicPrice;
import com.my.spring.utils.DataWrapper;

public interface MechanicDao {
	 boolean addMechanic(Mechanic am);
     boolean deleteMechanic(Long id);
     boolean updateMechanic(Mechanic am);
	 DataWrapper<Void> deleteMechanicByProjectId(Long id);
	 DataWrapper<List<Mechanic>> getMechanicList(Integer pageIndex, Integer pageSize, Mechanic am);
	Mechanic getMechanicById(Long id);
	DataWrapper<List<Mechanic>> getMechanicListByProjectId(Integer pageIndex, Integer pageSize, Long projectId);
	List<Mechanic> getMechanicListByProjectId(Mechanic ps);
	List<Mechanic> getMechanicByIds(String teamUserIds);
	
}
