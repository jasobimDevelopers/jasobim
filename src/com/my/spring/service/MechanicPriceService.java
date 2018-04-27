package com.my.spring.service;

import java.util.List;

import com.my.spring.model.MechanicPrice;
import com.my.spring.model.MechanicPricePojo;
import com.my.spring.utils.DataWrapper;

public interface MechanicPriceService {
	 DataWrapper<Void> addMechanicPrice(MechanicPrice duct, String token);
	 DataWrapper<Void> deleteMechanicPrice(Long id,String token);
	 DataWrapper<Void> updateMechanicPrice(MechanicPrice duct,String token);
	DataWrapper<List<MechanicPricePojo>> getMechanicPriceList(String token, MechanicPrice ps, Integer pageSize,
			Integer pageIndex);
	DataWrapper<Void> updateMechanicPrice(String[] info, String token, String dates);
}
