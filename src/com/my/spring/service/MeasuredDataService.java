package com.my.spring.service;

import com.my.spring.model.MeasuredData;
import com.my.spring.model.MeasuredDataPojo;
import com.my.spring.utils.DataWrapper;

import java.util.List;



public interface MeasuredDataService {
    DataWrapper<Void> addMeasuredData(MeasuredData MeasuredData,String token,String webToken);
    DataWrapper<Void> deleteMeasuredData(Long id,String token);
    DataWrapper<List<MeasuredDataPojo>> getMeasuredDataList(Integer pageIndex, Integer pageSize, MeasuredData MeasuredData, String token);
    //public boolean batchImport(String name,MultipartFile file,String token,HttpServletRequest request, Long projectId);
 	DataWrapper<MeasuredDataPojo> getMeasuredDataById(Long id, String token);
}
