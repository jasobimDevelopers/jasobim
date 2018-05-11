package com.my.spring.service;
import com.my.spring.model.Material;
import com.my.spring.model.MaterialPojo;
import com.my.spring.utils.DataWrapper;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;


public interface MaterialService {
    DataWrapper<List<MaterialPojo>> getMaterialList(String token, Integer pageIndex, Integer pageSize, Material m);
	DataWrapper<Void> addMaterial(Material m, String token);
	DataWrapper<Void> deleteMaterial(Long id, String token);
	DataWrapper<Void> updateMaterial(Material m, String token);
	DataWrapper<Void> importMaterial(MultipartFile file, HttpServletRequest request, String token, Material material);
}
