package com.my.spring.service;

import com.my.spring.model.QualityFine;
import com.my.spring.model.QualityFinePojo;
import com.my.spring.utils.DataWrapper;

import java.util.List;


public interface QualityFineService {
    DataWrapper<Void> addQualityFine(QualityFine building, String token);
    DataWrapper<Void> deleteQualityFine(Long id,String token);
    DataWrapper<Void> updateQualityFine(QualityFine building,String token);
	DataWrapper<QualityFine> getQualityFineByProjectId(Long projectId,String token);
	DataWrapper<List<QualityFinePojo>> getQualityFineList(Integer pageIndex, Integer pageSize, QualityFine duct,
			String token);
}
