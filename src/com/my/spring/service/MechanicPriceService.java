package com.my.spring.service;

import java.util.List;

import com.my.spring.model.MechanicData;
import com.my.spring.model.MechanicPrice;
import com.my.spring.model.MechanicPricePojo;
import com.my.spring.model.MechanicPricePojos;
import com.my.spring.utils.DataWrapper;

public interface MechanicPriceService {
	 DataWrapper<Void> deleteMechanicPrice(Long id,String token);
	 DataWrapper<Void> updateMechanicPrice(MechanicPrice duct,String token,String date);
	DataWrapper<List<MechanicPricePojo>> getMechanicPriceList(String token, MechanicPrice ps, Integer pageSize,
			Integer pageIndex,Long projectId,String date);
	DataWrapper<Void> addMechanicPrice(MechanicPrice am, String token);
	DataWrapper<Void> addMechanicPriceList(String am, String token);
	DataWrapper<List<MechanicPricePojos>> getMechanicPriceNum(String token, Long projectId,String date);
	DataWrapper<String> exportMechanicNum(String token, Long projectId, String date);
	DataWrapper<List<MechanicData>> getMechanicDatas(String token, Long projectId, String date);
}
