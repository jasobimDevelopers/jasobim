package com.my.spring.service;

import com.my.spring.model.Item;
import com.my.spring.utils.DataWrapper;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface ItemService {
    DataWrapper<Void> addItem(Item Item,String token);
    DataWrapper<Void> deleteItem(Long id,String token);
    DataWrapper<Void> deleteItemByTypeNameAndProjectId(Long projectid,String typeName,String token);
    DataWrapper<Void> updateItem(Item Item,String token);
    DataWrapper<List<Item>> getItemList(Long projectId,Integer pageIndex, Integer pageSize, Item item, String token);
    public boolean batchImport(String name,MultipartFile file,String token,HttpServletRequest request, Long projectId);
	DataWrapper<Void> deleteItemByProjectId(Long projectId, String token);
	DataWrapper<Item> getItemById(Long id, String token);
	DataWrapper<List<Item>> getItemByOthers(Long projectId, Long typeName, Long buildingNum, Long floorNum,
			Long unitNum, Long householdNum, String token);
	Long getItemByBase(Long projectId, Long buildingId,String token);
	List<Object> getHouseHoldType(Long projectId, Long buildingId, Long floorId,String token);
	Long getItemByBuidlingNum(Long projectId, Long buildingId, String token);
}
