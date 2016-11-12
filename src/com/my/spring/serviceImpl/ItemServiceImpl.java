package com.my.spring.serviceImpl;

import com.my.spring.DAO.BuildingDao;
import com.my.spring.DAO.ItemDao;
import com.my.spring.DAO.ProjectDao;
import com.my.spring.DAO.QuantityDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.Building;
import com.my.spring.model.Item;
import com.my.spring.model.Quantity;
import com.my.spring.model.QuantityPojo;
import com.my.spring.model.User;
import com.my.spring.service.FileService;
import com.my.spring.service.ItemService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.MD5Util;
import com.my.spring.utils.SessionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016/6/22.
 */
@Service("ItemService")
public class ItemServiceImpl implements ItemService {
    @Autowired
    ItemDao itemDao;
    @Autowired
    UserDao userDao;
    @Autowired
    QuantityDao quantityDao;
    @Autowired
    ProjectDao projectDao;
    @Autowired
    BuildingDao buildingDao;
    @Autowired
    FileService fileSerivce;
    @Override
    public DataWrapper<Void> addItem(Item item,String token) {
    	 DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
         User userInMemory = SessionManager.getSession(token);
         if (userInMemory != null) {
 			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
 				if(item!=null){
 					if(!itemDao.addItem(item)) 
 			            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
 					else
 						return dataWrapper;
 			        
 				}else{
 					dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
 				}
 			}else{
 				dataWrapper.setErrorCode(ErrorCodeEnum.AUTH_Error);
 			}
 		} else {
 			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
 		}
         return dataWrapper;
    }

    @Override
    public DataWrapper<Void> deleteItem(Long id,String token) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(id!=null){
					if(!itemDao.deleteItem(id)) 
			            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					else
						return dataWrapper;
			        
				}else{
					dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
				}
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.AUTH_Error);
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrapper;
    }

    @Override
    public DataWrapper<Void> updateItem(Item Item,String token) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(Item!=null){
					if(!itemDao.updateItem(Item)) 
			            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					else
						return dataWrapper;
			        
				}else{
					dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
				}
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.AUTH_Error);
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrapper;
    }

    @Override
    public DataWrapper<List<Item>> getItemList(Long projectId,Integer pageIndex, Integer pageSize, Item item,String token) {
    	DataWrapper<List<Item>> dataWrapper = new DataWrapper<List<Item>>();
        User adminInMemory = SessionManager.getSession(token);
        if (adminInMemory != null) {
        	User adminInDB = userDao.getById(adminInMemory.getId());
        	if(adminInDB.getUserType()== UserTypeEnum.Admin.getType()){
        		dataWrapper =itemDao.getItemList(projectId,pageSize, pageIndex,item);
        	}
        	
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrapper;
    	
    }

	@Override
	public boolean batchImport(String name, MultipartFile file,String token,HttpServletRequest request) {
		if (file == null || name == null || name.equals("")) {
			return false;
		}
		int type=6;
		DataWrapper<Void> dataWrapper=new DataWrapper<Void>();
		boolean b = false;
		Building building = new Building();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
		        String newFileName = MD5Util.getMD5String(file.getOriginalFilename() + new Date() + UUID.randomUUID().toString()).replace(".","")
		                    + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		        //创建处理EXCEL
		        ReadExcel readExcel=new ReadExcel();
		        
		        //解析excel，构件信息集合。
		        List<Item> ItemList = readExcel.getExcelInfo(newFileName ,file);
		        int []tempb =new int[ItemList.size()];
		        int []tempf =new int[ItemList.size()];
		        int []temph =new int[ItemList.size()];
		        if(ItemList != null){
		        	fileSerivce.uploadFile(name, file, type, request);
		        	int i=0;
		        	long projectId=ItemList.get(0).getProjectId();
			        for(Item Item:ItemList){
			        	tempb[i]=Item.getBuildingNum();
			        	tempf[i]=Item.getFloorNum();
			        	temph[i]=Item.getHouseholdNum();
			        	i++;
			        }
			       
			        int buildingNum=0;
			        int floorNum=0;
			        int householdNum=0;
			        for(int j=0;j<ItemList.size();j++){
			        	if(buildingNum<tempb[j]){
			        		buildingNum=tempb[j];
			        	}
			        	if(floorNum<tempf[j]){
			        		floorNum=tempf[j];
			        	}
			        	if(householdNum<temph[j]){
			        		householdNum=temph[j];
			        	}
			        }
			        building.setProjectId(projectId);
			        building.setBuildingNum(buildingNum);
			        building.setFloorNum(floorNum);
			        building.setHouseholdNum(householdNum);
			        buildingDao.addBuilding(building);	        
		            b = true;
		            //迭代添加构件信息
			        for(Item Item:ItemList){
			        	itemDao.addItem(Item);
			        }
			            
			        
			    	DataWrapper<List <QuantityPojo>> quantitypojo= itemDao.getSameItem();
			    		
			    	
			    	if(quantitypojo.getData()!=null){
			    		for(QuantityPojo pojo:quantitypojo.getData()){
			    			Quantity test=new Quantity();
			    			Long st=pojo.getProject_id();
			    			test.setProjectId(st);
			    			test.setBuildingNum(pojo.getBuilding_num());
			    			test.setFloorNum(pojo.getFloor_num());
			    			test.setUnitNum(pojo.getUnit_num());
			    			test.setHouseholdNum(pojo.getHousehold_num());
			    			String familyAndType=pojo.getFamily_and_type();
			    			test.setTypeName(pojo.getType_name());
			    			if(familyAndType.equals("") || familyAndType==null){
			    				familyAndType="";
			    			}
			    			test.setFamilyAndType(familyAndType);
			    			String serviceType=pojo.getService_type();
			    			if(serviceType.equals("") || serviceType==null){
			    				serviceType="";
			    			}
			    			test.setServiceType(serviceType);
			    			String systemType=pojo.getSystem_type();
			    			if(systemType.equals("") || systemType==null){
			    				systemType="";
			    			}
			    			test.setSystemType(systemType);
			    			test.setProfessionType(pojo.getProfession_type());
			    			String names=pojo.getName();
			    			if(names.equals("") || names==null){
			    				names="";
			    			}
			    			test.setName(names);
			    			String size=pojo.getSize();
			    			if(size.equals("") || size==null){
			    				size="";
			    			}
			    			test.setSize(size);
			    			String material=pojo.getMaterial();
			    			if(material==null || material.equals("")){
			    				material="";
			    			}
			    			test.setMaterial(material);
			    			if(pojo.getLengthnum()!=0){
			    				test.setValue(pojo.getLengthnum());
			    				test.setUnit("米（m）");
			    			}
			    			if(pojo.getAreanum()!=0){
			    				test.setValue(pojo.getAreanum());
			    				test.setUnit("平米（m2）");
			    			}
			    			if(pojo.getNum()!=0 && pojo.getAreanum()==0 && pojo.getLengthnum()==0){
			    				test.setValue(pojo.getNum());
			    				test.setUnit("个");
			    			}
			    			if(quantityDao.findQuantity(test)){
			    				quantityDao.updateQuantity(test);
			    			}else{
			    				quantityDao.addQuantity(test);
			    			}
			    		}
			    		
			    	}
		        }
		      
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.AUTH_Error);
			}
			
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return b;
	}

	@Override
	public DataWrapper<Void> deleteItemByTypeNameAndProjectId(Long projectid, String typeName, String token) {
		// TODO Auto-generated method stub
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(projectid!=null && typeName!=null){
					return itemDao.deleteItemByTypeNameAndProjectId(projectid,typeName,token);
			        
				}else{
					dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
				}
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.AUTH_Error);
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dataWrapper;
		
	}

	@Override
	public DataWrapper<Void> deleteItemByProjectId(Long projectId, String token) {
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(projectId!=null){
					return itemDao.deleteItemByProjectId(projectId, token);
				}else{
					dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
				}
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.AUTH_Error);
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dataWrapper;
		
	}

	@Override
	public DataWrapper<Item> getItemById(Long id, String token) {
		DataWrapper<Item> dataWrapper = new DataWrapper<Item>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(id!=null){
					Item item=new Item();
					item=itemDao.getItemById(id);
					if(item!=null){
						dataWrapper.setData(item);
					}else{
						dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
					}				
				}else{
					dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
				}
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.AUTH_Error);
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		
		return dataWrapper;
	}

	////////工程量的类型-项目-楼号-单元号-层号-户号的提取方法
	@Override
	public DataWrapper<List<Item>> getItemByOthers(Long projectId, Long typeName, Long buildingNum, Long floorNum,
			Long unitNum, Long householdNum, String token) {
		DataWrapper<List<Item>> dataWrapper = new DataWrapper<List<Item>>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(projectId!=null){
					dataWrapper.setData(itemDao.getItemByOthers(projectId,typeName,buildingNum,floorNum,unitNum,householdNum));		
				}else{
					dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
				}
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.AUTH_Error);
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dataWrapper;
	}

	@Override
	public Long getItemByBase() {
		
		return itemDao.getItemByBase();
	}

}
