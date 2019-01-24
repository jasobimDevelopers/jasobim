package com.my.spring.DAO;

import com.my.spring.model.Nature;
import com.my.spring.utils.DataWrapper;
import java.util.List;

public interface NatureDao {
	Nature getById(Long id);
	boolean deleteNature(Long id);
	boolean addNature(Nature role);
	boolean updateNature(Nature dp);
	DataWrapper<List<Nature>> getNatureListByProjectId(Integer pageIndex,Integer pageSize, Nature nature);
}
