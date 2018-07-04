package com.my.spring.service;

import com.my.spring.model.Duct;
import com.my.spring.model.DuctAppPojo;
import com.my.spring.model.DuctConfig;
import com.my.spring.model.DuctPojo;
import com.my.spring.model.DuctPojos;
import com.my.spring.utils.DataWrapper;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

public interface DuctService {
    DataWrapper<Void> addDuct(Duct duct, String token);
    DataWrapper<Void> deleteDuct(Long id,String token);
    DataWrapper<Void> updateDuct(Duct duct,String token);
    DataWrapper<List<DuctPojo>> getDuctList(Integer pageIndex,Integer pageSize,Duct duct,String token,String content,String startTime,String finishTime);
	DataWrapper<List<DuctPojo>> getDuctByProjectId(Long projectId,String token, Duct duct);
	boolean batchImport(String filePath, MultipartFile multipartFile, String token, HttpServletRequest request,
			Long projectId);
	DataWrapper<String> exportDuct(Long projectId, String token, HttpServletRequest request,String dateStart,String dateFinished);
	DataWrapper<Void> updateDuct(Duct duct, String token, HttpServletRequest request);
	DataWrapper<DuctPojo> getDuctBySelfId(Long selfId,String id,Long projectId);
	DataWrapper<List<DuctPojos>> getDuctStateSum(String dateStart, String dateFinished, Duct duct, String token,
			String content);
	DataWrapper<List<DuctAppPojo>> getDuctNums(Long projectId, String token, Integer floorNum, Integer state,
			Integer professionType);
	DataWrapper<List<DuctConfig>> getConfigDatas(Long projectId, String token);
}
