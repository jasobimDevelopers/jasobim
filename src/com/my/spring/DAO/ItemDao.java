package com.my.spring.DAO;

import com.my.spring.model.Item;
import com.my.spring.model.ItemCount;
import com.my.spring.model.ItemStateLog;
import com.my.spring.model.QuantityPojo;
import com.my.spring.utils.DataWrapper;
import java.util.List;

public interface ItemDao {
    boolean addItem(Item Item);
    boolean deleteItem(Long id);
    boolean updateItem(Item Item);
    boolean deleteItemByPorjectId(Long projectId);
    DataWrapper<List<Item>> getItemList(Long projectId, Integer pageSize, Integer pageIndex, Item item);
    DataWrapper<List<QuantityPojo>> getSameItem(Long projectId);
   // List<Item> getItemByLocation(String location);
	//DataWrapper<Void> deleteItemByTypeNameAndProjectId(Long projectid, String typeName, String token);
	DataWrapper<Void> deleteItemByProjectId(Long projectid, String token);
	Item getItemById(Long id);
	
	Long getItemByBase(Long projectId,Long buildingId);
	List<Object> getHouseHoldType(Long projectId,Long buildingId,Long floorId);
	Long getItemByBuidlingNum(Long projectId, Long buildingId);
	boolean addItemList(List<Item> itemList);
	Item getItemBySelfId(Long projectId);
	List<ItemCount> getNumsGroupBy(Long projectId);
	List<ItemCount> getNumsGroupByState(Long projectId);
	List<Item> getItemBySelfId(ItemStateLog itemStateLog,String idList);
	Item getItemBySelfId(Long selfId, Long projectId);
	DataWrapper<List<Item>> getItemLists(Long projectId, Integer pageIndex, Integer pageSize, Item empty);
	List<ItemCount> getNumsGroupByProfesstionType(Long projectId);
	List<ItemCount> getNumsGroupByProfesstionTypeAndState(Long projectId);
	List<Object> getAllNumsGroupByStates(Long projectId);
	boolean updateItemList(List<Item> item);
	boolean updateItemByProjectIdAndSelfIds(Long projectId, String selfIdList);
	List<Item> getItemListsByIdList(Long projectId, String idList);
	boolean updateItemByProjectIdAndSelfIdsToZ(Long projectId, String selfIdList);
	
}
