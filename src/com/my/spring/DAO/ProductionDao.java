package com.my.spring.DAO;

import com.my.spring.model.Production;
import com.my.spring.utils.DataWrapper;
import java.util.List;

public interface ProductionDao {
	DataWrapper<List<Production>> getProductionList(Integer pageSize, Integer pageIndex, Production Production);

	boolean addProduction(Production Production);

	boolean deleteProduction(String[] ids);

	Production getById(Long id);
}
