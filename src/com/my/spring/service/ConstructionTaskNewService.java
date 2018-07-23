package com.my.spring.service;

import com.my.spring.model.ConstructionTaskNew;
import com.my.spring.model.ConstructionTaskNewPojo;
import com.my.spring.model.ItemDataPojo;
import com.my.spring.model.ProcessLog;
import com.my.spring.model.ProcessLogPojo;
import com.my.spring.utils.DataWrapper;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;



public interface ConstructionTaskNewService {
	DataWrapper<Void> deleteConstructionTaskNew(Long id, String token);
	DataWrapper<Void> updateConstructionTaskNew(ConstructionTaskNew ConstructionTaskNew, String token);
	DataWrapper<List<ConstructionTaskNewPojo>> getConstructionTaskNewList(String token, Integer pageIndex, Integer pageSize,
			ConstructionTaskNew ConstructionTaskNew, Integer type);
	DataWrapper<Void> addConstructionTaskNew(ConstructionTaskNew ConstructionTaskNew, String token,
			MultipartFile[] imgs, HttpServletRequest request);
	DataWrapper<ProcessLogPojo> approveConstructionTaskNew(String token,Long id,String note,Integer idea,Long processDataId,Integer currentNode);
	DataWrapper<List<ConstructionTaskNewPojo>> getConstructionTaskNewDetail(String token,
			ConstructionTaskNew constructionTaskNew);
	DataWrapper<List<ProcessLogPojo>> getProcessLogByConstructionId(String token, Long id, Long processDataId);
	DataWrapper<List<ProcessLogPojo>> getAllProcessLogByConstructionId(String token, Long id, Long processDataId);
}
