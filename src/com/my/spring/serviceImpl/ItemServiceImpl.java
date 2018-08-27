package com.my.spring.serviceImpl;

import com.my.spring.DAO.BuildingDao;
import com.my.spring.DAO.ItemDao;
import com.my.spring.DAO.MinItemDao;
import com.my.spring.DAO.ProjectDao;
import com.my.spring.DAO.QuantityDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.CallStatusEnum;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.Building;
import com.my.spring.model.DuctPojo;
import com.my.spring.model.Item;
import com.my.spring.model.MinItem;
import com.my.spring.model.MinItemPojo;
import com.my.spring.model.Project;
import com.my.spring.model.Quantity;
import com.my.spring.model.QuantityPojo;
import com.my.spring.model.User;
import com.my.spring.service.FileService;
import com.my.spring.service.ItemService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.MD5Util;
import com.my.spring.utils.QRCodeUtil2;
import com.my.spring.utils.SessionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

@Service("ItemService")
public class ItemServiceImpl implements ItemService {
    @Autowired
    ItemDao itemDao;
    @Autowired
    MinItemDao minItemDao;
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
        	dataWrapper =itemDao.getItemList(projectId,pageSize, pageIndex,item);
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrapper;
    	
    }
    @Override
    public DataWrapper<List<MinItem>> getMinItemList(Long projectId,Integer pageIndex, Integer pageSize, MinItem item,String token) {
    	DataWrapper<List<MinItem>> dataWrapper = new DataWrapper<List<MinItem>>();
        User adminInMemory = SessionManager.getSession(token);
        if (adminInMemory != null) {
        	dataWrapper =minItemDao.getMinItemList(projectId,pageSize, pageIndex,item);
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrapper;
    	
    }

	@Override
	public boolean batchImport(String name, MultipartFile file,String token,HttpServletRequest request,Long projectId,String modelPart) {
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
		        ////////查询楼栋总数，楼层最大值，户型最大值
		        int []tempb =new int[ItemList.size()];
		        int []tempf =new int[ItemList.size()];
		        int []temph =new int[ItemList.size()];
		        if(ItemList.size()>0){
		        	 b = true;
		        	fileSerivce.uploadFile(name, file, type, request);
		        	int i=0;
		        	
			        for(Item Item:ItemList){
			        	if(Item!=null){
			        		Item.setModelFlag(modelPart);
			        		if(Item.getBuildingNum()!=null){
			        			tempb[i]=Item.getBuildingNum();
			        		}else{
			        			tempb[i]=0;
			        		}
			        		if(Item.getFloorNum()!=null){
			        			tempf[i]=Item.getFloorNum();
			        		}else{
			        			tempf[i]=0;
			        		}
				        	if(Item.getHouseholdNum()!=null){
				        		temph[i]=Item.getHouseholdNum();	
				        	}else{
				        		temph[i]=0;
				        	}
			        	}
			        	i++;
			        }
			       
			        int buildingNum=0;
			        int floorNum=0;
			        int householdNum=0;
			        for(int j=0;j<ItemList.size();j++){
			        	ItemList.get(j).setProjectId(projectId);
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
			        if(buildingDao.getBuildingByProjectId(projectId).getCallStatus()==CallStatusEnum.SUCCEED){
			        	buildingDao.updateBuilding(buildingDao.getBuildingByProjectId(projectId).getData());
			        }else{
			        	buildingDao.addBuilding(building);	
			        }
			        //////////////////
		            //迭代添加构件信息
			        itemDao.addItemList(ItemList);
			        List <Quantity> quantityList=new ArrayList<Quantity>();
			    	DataWrapper<List <QuantityPojo>> quantitypojo= itemDao.getSameItem(projectId);
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
			    			String typeName=pojo.getType_name();
			    			if(typeName==null){
			    				typeName="";
			    			}
			    			if(familyAndType==null){
			    				familyAndType="";
			    			}
			    			test.setFamilyAndType(familyAndType);
			    			String serviceType=pojo.getService_type();
			    			if(serviceType==null){
			    				serviceType="";
			    			}
			    			test.setServiceType(serviceType);
			    			String systemType=pojo.getSystem_type();
			    			if(systemType==null){
			    				systemType="";
			    			}
			    			test.setSystemType(systemType);
			    			test.setProfessionType(pojo.getProfession_type());
			    			String names=pojo.getName();
			    			if(names==null){
			    				names="";
			    			}
			    			test.setName(names);
			    			String size=pojo.getSize();
			    			if(size==null){
			    				size="";
			    			}
			    			test.setSize(size);
			    			String material=pojo.getMaterial();
			    			if(material.equals("")){
			    				material="";
			    			}
			    			test.setMaterial(material);
			    			if(pojo.getLengthnum()!=0){
			    				test.setValue((pojo.getLengthnum()/1000));
			    				test.setUnit("米（m）");
			    			}
			    			if(pojo.getAreanum()!=0){
			    				test.setValue(pojo.getAreanum());
			    				test.setUnit("平米（m2）");
			    			}
			    			if(pojo.getNum()!=0 && pojo.getAreanum()==0 && pojo.getLengthnum()==0){
			    				test.setValue(new Double(pojo.getNum()));
			    				test.setUnit("个");
			    			}
			    			test.setQuantityType(0);
			    			quantityList.add(test);
			    		}
			    		quantityDao.deleteQuantityByProjectId(projectId);
			    		quantityDao.addQuantityList(quantityList);
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
	public boolean batchImports(String name, MultipartFile file,String token,HttpServletRequest request,Long projectId) {
		if (file == null || name == null || name.equals("")) {
			return false;
		}
		int type=6;
		DataWrapper<Void> dataWrapper=new DataWrapper<Void>();
		boolean b = false;
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
		        String newFileName = MD5Util.getMD5String(file.getOriginalFilename() + new Date() + UUID.randomUUID().toString()).replace(".","")
		                    + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		        //创建处理EXCEL
		        ReadExcelOfMinItem readExcel=new ReadExcelOfMinItem();
		        
		        //解析excel，构件信息集合。
		        List<MinItem> ItemList = readExcel.getExcelInfo(newFileName ,file,projectId);
		        
		        if(ItemList.size()>0){
		        	 b = true;
		        	fileSerivce.uploadFile(name, file, type, request);
		            //迭代添加构件信息
		        	minItemDao.addMinItemList(ItemList);
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
	public DataWrapper<MinItemPojo> getItemById(Long id, String token) {
		DataWrapper<MinItemPojo> dataWrapper = new DataWrapper<MinItemPojo>();
		if(id!=null){
			Item item=new Item();
			item=itemDao.getItemById(id);
			if(item!=null){
				if(item!=null){
					MinItemPojo pojo = new MinItemPojo();
					pojo.setSelfId(item.getSelfId());
					pojo.setId(item.getId());
					pojo.setArea(item.getArea());
					pojo.setBuildingNum(item.getBuildingNum());
					pojo.setFamilyAndType(item.getFamilyAndType());
					pojo.setFloorNum(item.getFloorNum());
					pojo.setHouseholdNum(item.getHouseholdNum());
					pojo.setLength(item.getLength());
					pojo.setLevel(item.getLevel());
					pojo.setMaterial(item.getMaterial());
					pojo.setName(item.getName());
					pojo.setOffset(item.getOffset());
					pojo.setProfessionType(item.getProfessionType());
					pojo.setProjectName(projectDao.getById(item.getProjectId()).getName());
					pojo.setServiceType(item.getServiceType());
					pojo.setSize(item.getSize());
					pojo.setSystemType(item.getSystemType());
					pojo.setTypeName(item.getTypeName());
					pojo.setUnitNum(item.getUnitNum());
					pojo.setInstallDate(item.getInstallDate());
					pojo.setInstallUnit(item.getInstallUnit());
					pojo.setInstallUser(item.getInstallUser());
					pojo.setItemBrand(item.getItemBrand());
					pojo.setProjectId(item.getProjectId());
					pojo.setModelFlag(item.getModelFlag());
					dataWrapper.setData(pojo);
				}
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
			}				
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
		}
		return dataWrapper;
	}
	@Override
	public DataWrapper<Item> getItemBySelfId(Long projectId) {
		DataWrapper<Item> dataWrapper = new DataWrapper<Item>();
		Item item=new Item();
		item=itemDao.getItemBySelfId(projectId);
		dataWrapper.setData(item);
		return dataWrapper;
	}
	@Override
	public DataWrapper<MinItemPojo> getMinItemById(Long id) {
		DataWrapper<MinItemPojo> dataWrapper = new DataWrapper<MinItemPojo>();
		MinItemPojo minItemPojo=new MinItemPojo();
		if(id!=null){
			MinItem item=new MinItem();
			item=minItemDao.getMinItemById(id);
			if(item!=null){
				Project project=new Project();
				project=projectDao.getById(item.getProjectId());
				minItemPojo.setProjectName(project.getName());
				
				minItemPojo.setArea(item.getArea());
				
				if(item.getBuildingNum()!=null){
					minItemPojo.setBuildingNum(item.getBuildingNum());
				}
				if(item.getFamilyAndType()!=null){
					minItemPojo.setFamilyAndType(item.getFamilyAndType());
				}
				if(item.getFloorNum()!=null){
					minItemPojo.setFloorNum(item.getFloorNum());
				}
				if(item.getHouseholdNum()!=null){
					minItemPojo.setHouseholdNum(item.getHouseholdNum());
				}
				
				minItemPojo.setLength(item.getLength());
				
				if(item.getLevel()!=null){
					minItemPojo.setLevel(item.getLevel());
				}
				if(item.getMaterial()!=null){
					minItemPojo.setMaterial(item.getMaterial());
				}
				if(item.getTypeName()!=null){
					minItemPojo.setTypeName(item.getTypeName());
				}
				if(item.getUnitNum()!=null){
					minItemPojo.setUnitNum(item.getUnitNum());
				}
				if(item.getSystemType()!=null){
					minItemPojo.setSystemType(item.getSystemType());
				}
				if(item.getSize()!=null){
					minItemPojo.setSize(item.getSize());
				}
				if(item.getServiceType()!=null){
					minItemPojo.setServiceType(item.getServiceType());
				}
				if(item.getProfessionType()!=null){
					minItemPojo.setProfessionType(item.getProfessionType());
				}
				
				minItemPojo.setOffset(item.getOffset());
				
				if(item.getName()!=null){
					minItemPojo.setName(item.getName());
				}
				
				dataWrapper.setData(minItemPojo);
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
			}				
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
		}
		return dataWrapper;
	}


	@Override
	public Long getItemByBase(Long projectId,Long buildingId,String token) {
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory != null) {
			return itemDao.getItemByBase(projectId,buildingId);
		}
		return null;
	}
	@Override
	public Long getItemByBuidlingNum(Long projectId,Long buildingId,String token) {
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory != null ) {
			return itemDao.getItemByBuidlingNum(projectId,buildingId);
		}
		return null;
	}

	@Override
	public List<Object> getHouseHoldType(Long projectId, Long buildingId, Long floorId, String token) {
		// TODO Auto-generated method stub
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory != null) {
			return itemDao.getHouseHoldType(projectId, buildingId, floorId);
		}
		return null;
	}

	@Override
	public String getCodeImg(Item item,HttpServletRequest request) {
		MinItem test=minItemDao.getMinItemBySelfId(item.getSelfId());
		String codeInformation=null;
		if(test!=null){
			codeInformation=test.getId().toString();
		}
		codeInformation="http://jasobim.com.cn/page/itemInfo.html?id="+test.getId().toString();
		SimpleDateFormat sdf =   new SimpleDateFormat("yyyyMMddHHmmssSSS" );
	   	Date d=new Date();
	   	String str=sdf.format(d);
	   	String rootPath = request.getSession().getServletContext().getRealPath("/");
	   	String filePath="/codeFiles";
	   	String imgpath=rootPath+filePath;
	   	String realPath=rootPath+filePath+"/"+str+".png";
		try{
			File QrCodeFile= new File(imgpath+str+".png");
    		//qurl=new String("http://jasobim.com.cn/page/itemInfo.html?id="+Integer.valueOf(s));
			File logoFile = new File("C:/jasobim/tomcat80/webapps/own/jaso_logo.png");
			QRCodeUtil2.drawLogoQRCode(logoFile, QrCodeFile, codeInformation, null);
			/*MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
	        @SuppressWarnings("rawtypes")
			Map hints = new HashMap();  
	        //内容所使用编码  
	        hints.put(EncodeHintType.CHARACTER_SET, "utf8");  
	        BitMatrix bitMatrix = multiFormatWriter.encode(codeInformation,BarcodeFormat.QR_CODE, 200, 200, hints);  
	        //生成二维码  
	        File outputFile = new File(imgpath,str+".png"); 
	        
	        MatrixToImageWriter.writeToFile(bitMatrix, "png", outputFile); */ 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return realPath;
	}

	@Override
	public DataWrapper<DuctPojo> getItemBySelfId(Long id, String selfId, Long projectId) {
		DataWrapper<DuctPojo> dataWrapper = new DataWrapper<DuctPojo>();
		Item duct = new Item();
    	duct=itemDao.getItemBySelfId(selfId,id,projectId).getData();
    	if(duct!=null){
    		String projectName="";
    		if(projectDao.getById(duct.getProjectId())!=null){
    			projectName=projectDao.getById(duct.getProjectId()).getName();
    		}
    		
			DuctPojo ductPojo=new DuctPojo();
			ductPojo.setId(duct.getId().toString());
    		ductPojo.setSize(duct.getSize());
    		ductPojo.setName(duct.getName());
    		ductPojo.setFamilyAndType(duct.getFamilyAndType());
    		ductPojo.setLevel(duct.getLevel());
    		ductPojo.setBuildingNum(duct.getBuildingNum().toString());
    		ductPojo.setFloorNum(duct.getFloorNum().toString());
    		ductPojo.setUnitNum(duct.getUnitNum().toString());
    		ductPojo.setHouseholdNum(duct.getHouseholdNum().toString());
    		ductPojo.setProjectId(duct.getProjectId().toString());
    		ductPojo.setProjectName(projectName);
    		ductPojo.setArea(duct.getArea()+"");
    		ductPojo.setLength(duct.getLength()+"");
    		ductPojo.setServiceType(duct.getServiceType());
    		ductPojo.setSystemType(duct.getSystemType());
        	dataWrapper.setData(ductPojo);
    	}else{
    		dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
    	}
        return dataWrapper;
	}

}
