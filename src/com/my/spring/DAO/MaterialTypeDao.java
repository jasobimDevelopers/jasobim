package com.my.spring.DAO;

import java.util.List;
import com.my.spring.model.MaterialType;
import com.my.spring.utils.DataWrapper;

public interface MaterialTypeDao {

	boolean addMaterialType(MaterialType m);

	boolean deleteMaterialType(Long id);

	MaterialType getById(Long id);

	boolean updateMaterialType(MaterialType m);

	DataWrapper<List<MaterialType>> getMaterialTypeList(Integer pageIndex, Integer pageSize, MaterialType m);

	MaterialType getMaterialByName(String materialName);
	
}
