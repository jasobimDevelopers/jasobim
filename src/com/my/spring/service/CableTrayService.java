package com.my.spring.service;

import com.my.spring.model.CableTray;
import com.my.spring.utils.DataWrapper;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface CableTrayService {
    DataWrapper<Void> addCableTray(CableTray CableTray, String token);
    DataWrapper<Void> deleteCableTray(Long id,String token);
    DataWrapper<Void> updateCableTray(CableTray CableTray,String token);
    DataWrapper<List<CableTray>> getCableTrayList();
	DataWrapper<CableTray> getCableTrayByProjectId(Long projectId,String token);
	public boolean batchImports(String name,MultipartFile file,String token,HttpServletRequest request, Long projectId);
}
