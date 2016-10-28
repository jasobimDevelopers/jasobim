package com.my.spring.DAO;

import com.my.spring.model.Item;
import com.my.spring.model.Quantity;
import com.my.spring.model.QuantityPojo;
import com.my.spring.utils.DataWrapper;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface ItemDao {
    boolean addItem(Item Item);
    boolean deleteItem(Long id);
    boolean updateItem(Item Item);
    DataWrapper<List<Item>> getItemList();
    List getSameItem();
    List<Item> getItemByLocation(String location);
	DataWrapper<Void> deleteItemByTypeNameAndProjectId(Long projectid, String typeName, String token);
	DataWrapper<Void> deleteItemByProjectId(Long projectid, String token);
	Item getItemById(Long id);
	List<Item> getItemByOthers(Long projectId, Long typeName, Long buildingNum, Long floorNum,
			Long unitNum, Long householdNum);
}
