package com.my.spring.service;

import com.my.spring.model.QualityFine;
import com.my.spring.model.QualityFinePojo;
import com.my.spring.utils.DataWrapper;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;


public interface QualityFineService {
    DataWrapper<Void> deleteQualityFine(Long id,String token);
    DataWrapper<Void> updateQualityFine(QualityFine building,String token);
	DataWrapper<QualityFine> getQualityFineByProjectId(Long projectId,String token);
	DataWrapper<List<QualityFinePojo>> getQualityFineList(Integer pageIndex, Integer pageSize, QualityFine duct,
			String token);
	DataWrapper<Void> addQualityFine(QualityFine fine, String token, MultipartFile[] files, HttpServletRequest request);
}
