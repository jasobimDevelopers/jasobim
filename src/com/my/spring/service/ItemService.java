package com.my.spring.service;

import com.my.spring.model.Item;
import com.my.spring.model.ItemStateData;
import com.my.spring.model.ItemStatesData;
import com.my.spring.model.MinItem;
import com.my.spring.model.MinItemPojo;
import com.my.spring.utils.DataWrapper;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

public interface ItemService {
    DataWrapper<Void> addItem(Item Item,String token);
    DataWrapper<Void> deleteItem(Long id,String token);
    //DataWrapper<Void> deleteItemByTypeNameAndProjectId(Long projectid,String typeName,String token);
    DataWrapper<Void> updateItem(Item Item,String token);
    DataWrapper<List<Item>> getItemList(Long projectId,Integer pageIndex, Integer pageSize, Item item, String token);
    public boolean batchImport(String name,MultipartFile file,String token,HttpServletRequest request, Long projectId,String modelPart);
	DataWrapper<Void> deleteItemByProjectId(Long projectId, String token);
	DataWrapper<MinItemPojo> getItemById(Long id, String token);
	Long getItemByBase(Long projectId, Long buildingId,String token);
	List<Object> getHouseHoldType(Long projectId, Long buildingId, Long floorId,String token);
	Long getItemByBuidlingNum(Long projectId, Long buildingId, String token);
	public boolean batchImports(String name,MultipartFile file,String token,HttpServletRequest request, Long projectId);
	DataWrapper<List<MinItem>> getMinItemList(Long projectId, Integer pageIndex, Integer pageSize, MinItem item,
			String token);
	String getCodeImg(Item item, HttpServletRequest request);
	DataWrapper<MinItemPojo> getMinItemById(Long id);
	//public boolean batchImportss(String name,MultipartFile file,String token,HttpServletRequest request, Long projectId);
	DataWrapper<Item> getItemBySelfId(Long projectId);
	DataWrapper<List<ItemStateData>> getItemStateData(Long projectId, String token, Integer pageIndex,
			Integer pageSize,Integer professionType);
	DataWrapper<ItemStatesData> getItemStatesData(Long projectId, String token);
}
