package com.my.spring.service;

import com.my.spring.model.AdvancedOrderCollect;
import com.my.spring.model.AdvancedOrderCollectPojo;
import com.my.spring.utils.DataWrapper;

import java.util.List;


public interface AdvancedOrderCollectService {
    DataWrapper<List<AdvancedOrderCollectPojo>> getAdvancedOrderCollectList(String token, Integer pageIndex, Integer pageSize, AdvancedOrderCollect AdvancedOrderCollect);
    DataWrapper<List<AdvancedOrderCollect>> getAdvancedOrderCollectListByUserId(Long userId,String token);
	DataWrapper<Void> addAdvancedOrderCollect(AdvancedOrderCollect AdvancedOrderCollect, String token);
	DataWrapper<Void> deleteAdvancedOrderCollect(Long id, String token);
}
