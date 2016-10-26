package com.my.spring.service;

import com.my.spring.model.Quantity;
import com.my.spring.utils.DataWrapper;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface QuantityService {
    DataWrapper<Void> addQuantity(Quantity quantity, String token);
    DataWrapper<Void> deleteQuantity(Long id,String token);
    DataWrapper<Void> updateQuantity(Quantity quantity,String token);
	DataWrapper<Quantity> getQuantityDetailsByAdmin(Long quantityId,String token);
	DataWrapper<List<Quantity>> getQuantityList(String token);
}
