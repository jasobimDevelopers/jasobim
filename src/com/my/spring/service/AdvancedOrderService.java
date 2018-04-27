package com.my.spring.service;

import com.my.spring.model.AdvancedOrder;
import com.my.spring.model.AdvancedOrderCopy;
import com.my.spring.model.AdvancedOrderPojo;
import com.my.spring.utils.DataWrapper;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;


public interface AdvancedOrderService {
    DataWrapper<List<AdvancedOrderPojo>> getAdvancedOrderList(String token, Integer pageIndex, Integer pageSize, AdvancedOrder advancedOrder);
    DataWrapper<List<AdvancedOrder>> getAdvancedOrderListByUserId(Long userId,String token);
	DataWrapper<Void> addAdvancedOrder(AdvancedOrder advancedOrder, String token,MultipartFile[] contentFiles,HttpServletRequest request);
	DataWrapper<Void> deleteAdvancedOrder(String id, String token);
	DataWrapper<Void> updateAdvancedOrder(AdvancedOrder advancedOrder, String token,HttpServletRequest request,MultipartFile photoOfFinished);
	DataWrapper<AdvancedOrderPojo> getAdvancedOrderById(Long id, String token, String weixin);
	DataWrapper<String> exportAdvancedOrder(Long id, String token);
	DataWrapper<List<AdvancedOrderCopy>> getAdvancedOrderListOfNotRead(String token,Integer pageSize,Integer pageIndex);
}
