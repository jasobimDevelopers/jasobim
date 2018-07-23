package com.my.spring.DAO;

import com.my.spring.model.ItemData;
import com.my.spring.utils.DataWrapper;

import java.util.List;

public interface ItemDataDao {
	boolean addItemData(ItemData ItemData);
    boolean updateItemData(ItemData ItemData);
    ItemData getById(Long id);
    DataWrapper<List<ItemData>> getItemDataList(Integer pageIndex, Integer pageSize, ItemData ItemData);
}