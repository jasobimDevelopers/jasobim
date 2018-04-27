package com.my.spring.service;

import java.util.List;

import com.my.spring.model.AttenceModel;
import com.my.spring.model.AttenceModelPojo;
import com.my.spring.utils.DataWrapper;

public interface AttenceModelService {
	 DataWrapper<Void> addAttenceModel(AttenceModel duct, String token);
	 DataWrapper<Void> deleteAttenceModel(Long id,String token);
	 DataWrapper<Void> updateAttenceModel(AttenceModel duct,String token);
	 DataWrapper<List<AttenceModelPojo>> getAttenceModelList(String token,AttenceModel duct,Integer pagesize,Integer pageIndex);
}
