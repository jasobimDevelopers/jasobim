package com.my.spring.DAO;

import com.my.spring.model.Material;
import com.my.spring.model.MaterialType;
import com.my.spring.utils.DataWrapper;

import java.util.List;


public interface MaterialDao {
    Material getById(Long id);
    DataWrapper<List<Material>> getMaterialList(Integer pageIndex, Integer pageSize, Material m);
	boolean addMaterial(Material m);
	boolean deleteMaterial(Long id);
	boolean updateMaterial(Material m);
}
