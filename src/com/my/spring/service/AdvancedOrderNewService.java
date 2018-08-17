package com.my.spring.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.my.spring.model.AdvancedOrderNew;
import com.my.spring.model.AdvancedOrderNewPojo;
import com.my.spring.utils.DataWrapper;

public interface AdvancedOrderNewService {
	  DataWrapper<List<AdvancedOrderNewPojo>> getAdvancedOrderNewList(String token, Integer pageIndex, Integer pageSize, AdvancedOrderNew advancedOrder,String content);
	  DataWrapper<List<AdvancedOrderNew>> getAdvancedOrderNewListByUserId(Long userId,String token);
	  DataWrapper<Void> addAdvancedOrderNew(AdvancedOrderNew advancedOrder, String token,MultipartFile[] contentFiles,HttpServletRequest request);
	  DataWrapper<Void> updateAdvancedOrderNew(AdvancedOrderNew advancedOrder, String token,HttpServletRequest request,MultipartFile photoOfFinished);
      DataWrapper<AdvancedOrderNewPojo> getAdvancedOrderNewById(Long id, String token, String weixin);
	  DataWrapper<String> exportAdvancedOrderNew(Long id, String token);
	DataWrapper<Void> deleteAdvancedOrderNew(Long id, String token);
}
