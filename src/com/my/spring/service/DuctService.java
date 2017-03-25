package com.my.spring.service;

import com.my.spring.model.Duct;
import com.my.spring.utils.DataWrapper;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface DuctService {
    DataWrapper<Void> addDuct(Duct duct, String token);
    DataWrapper<Void> deleteDuct(Long id,String token);
    DataWrapper<Void> updateDuct(Duct duct,String token);
    DataWrapper<List<Duct>> getDuctList();
	DataWrapper<Duct> getDuctByProjectId(Long projectId,String token);
	boolean batchImport(String filePath, MultipartFile multipartFile, String token, HttpServletRequest request,
			Long projectId);
}
