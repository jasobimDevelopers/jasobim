package com.my.spring.service;

import java.util.List;

import com.my.spring.model.MaterialType;
import com.my.spring.model.MaterialTypePojo;
import com.my.spring.utils.DataWrapper;

public interface MaterialTypeService {
	DataWrapper<Void> addMaterialType(MaterialType m, String token);
	DataWrapper<Void> deleteMaterialType(Long id, String token);
	DataWrapper<Void> updateMaterialType(MaterialType m, String token);
	DataWrapper<List<MaterialTypePojo>> getMaterialTypeList(String token, Integer pageIndex, Integer pageSize,
			MaterialType m);
}
