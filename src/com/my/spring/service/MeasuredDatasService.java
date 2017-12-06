package com.my.spring.service;

import com.my.spring.model.MeasuredDatas;
import com.my.spring.utils.DataWrapper;

import java.util.List;



public interface MeasuredDatasService {
    DataWrapper<Void> addMeasuredDatas(MeasuredDatas MeasuredDatas,String token,String sceneFlag);
    DataWrapper<Void> deleteMeasuredDatas(Long id,String token);
    DataWrapper<List<MeasuredDatas>> getMeasuredDatasList(Integer pageIndex, Integer pageSize, MeasuredDatas MeasuredData, String token);
    //public boolean batchImport(String name,MultipartFile file,String token,HttpServletRequest request, Long projectId);
	DataWrapper<MeasuredDatas> getMeasuredDatasById(Long id, String token);
}
