package com.my.spring.service;

import com.my.spring.model.ItemData;
import com.my.spring.model.ItemDataPojo;
import com.my.spring.utils.DataWrapper;

import java.util.List;



public interface ItemDataService {
    DataWrapper<List<ItemDataPojo>> getItemDataList(String token, Integer pageIndex, Integer pageSize, ItemData ItemData);
	DataWrapper<Void> addItemData(ItemData ItemData, String token);
	DataWrapper<Void> updateItemData(ItemData ItemData, String token);
}
