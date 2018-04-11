package com.my.spring.service;
import com.my.spring.model.Material;
import com.my.spring.model.MaterialPojo;
import com.my.spring.utils.DataWrapper;
import java.util.List;


public interface MaterialService {
    DataWrapper<List<MaterialPojo>> getMaterialList(String token, Integer pageIndex, Integer pageSize, Material m);
	DataWrapper<Void> addMaterial(Material m, String token);
	DataWrapper<Void> deleteMaterial(Long id, String token);
	DataWrapper<Void> updateMaterial(Material m, String token);
}
