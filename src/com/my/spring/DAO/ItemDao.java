package com.my.spring.DAO;

import com.my.spring.model.Item;
import com.my.spring.model.MinItem;
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
    boolean deleteItemByPorjectId(Long projectId);
    DataWrapper<List<Item>> getItemList(Long projectId, Integer pageSize, Integer pageIndex, Item item);
    DataWrapper<List<QuantityPojo>> getSameItem();
   // List<Item> getItemByLocation(String location);
	//DataWrapper<Void> deleteItemByTypeNameAndProjectId(Long projectid, String typeName, String token);
	DataWrapper<Void> deleteItemByProjectId(Long projectid, String token);
	Item getItemById(Long id);
	
	Long getItemByBase(Long projectId,Long buildingId);
	List<Object> getHouseHoldType(Long projectId,Long buildingId,Long floorId);
	Long getItemByBuidlingNum(Long projectId, Long buildingId);
	boolean addItemList(List<Item> itemList);
	
}
