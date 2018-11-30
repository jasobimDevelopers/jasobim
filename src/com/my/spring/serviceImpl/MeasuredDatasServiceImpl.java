package com.my.spring.serviceImpl;

import com.my.spring.DAO.MeasuredDataDao;
import com.my.spring.DAO.MeasuredDatasDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.MeasuredDatas;
import com.my.spring.model.User;
import com.my.spring.service.MeasuredDatasService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("MeasuredDatasService")
public class MeasuredDatasServiceImpl implements MeasuredDatasService {
    @Autowired
    MeasuredDataDao measuredDataDao;
    @Autowired
    MeasuredDatasDao measuredDatasDao;
   
    @Override
    public DataWrapper<Void> addMeasuredDatas(MeasuredDatas MeasuredData,String token,String sceneFlag) {
    	 DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
         User userInMemory = SessionManager.getSession(token);
         if (userInMemory != null || sceneFlag.equals("jasobim")) {
				if(MeasuredData!=null){
					if(!measuredDatasDao.addMeasuredDatas(MeasuredData)) 
			            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					else
						return dataWrapper;
			        
				}else{
					dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
				}
 			
 		} else {
 			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
 		}
         return dataWrapper;
    }

    @Override
    public DataWrapper<Void> deleteMeasuredDatas(Long id,String token) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(id!=null){
					if(!measuredDataDao.deleteMeasuredData(id)) 
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
    public DataWrapper<List<MeasuredDatas>> getMeasuredDatasList(Integer pageIndex, Integer pageSize, MeasuredDatas MeasuredData,String token) {
    	DataWrapper<List<MeasuredDatas>> dataWrapper = new DataWrapper<List<MeasuredDatas>>();
        User adminInMemory = SessionManager.getSession(token);
        if (adminInMemory != null) {
        	dataWrapper =measuredDatasDao.getMeasuredDatasList(pageSize, pageIndex,MeasuredData);
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrapper;
    	
    }
    

	/*@Override
	public boolean batchImport(String name, MultipartFile file,String token,HttpServletRequest request,Long projectId) {
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
		        List<MeasuredData> MeasuredDataList = readExcel.getExcelInfo(newFileName ,file);
		        ////////查询楼栋总数，楼层最大值，户型最大值
		        int []tempb =new int[MeasuredDataList.size()];
		        int []tempf =new int[MeasuredDataList.size()];
		        int []temph =new int[MeasuredDataList.size()];
		        if(MeasuredDataList.size()>0){
		        	 b = true;
		        	fileSerivce.uploadFile(name, file, type, request);
		        	int i=0;
		        	
			        for(MeasuredData MeasuredData:MeasuredDataList){
			        	if(MeasuredData!=null){
			        		if(MeasuredData.getBuildingNum()!=null){
			        			tempb[i]=MeasuredData.getBuildingNum();
			        		}else{
			        			tempb[i]=0;
			        		}
			        		if(MeasuredData.getFloorNum()!=null){
			        			tempf[i]=MeasuredData.getFloorNum();
			        		}else{
			        			tempf[i]=0;
			        		}
				        	if(MeasuredData.getHouseholdNum()!=null){
				        		temph[i]=MeasuredData.getHouseholdNum();	
				        	}else{
				        		temph[i]=0;
				        	}
			        	}
			        	i++;
			        }
			       
			        int buildingNum=0;
			        int floorNum=0;
			        int householdNum=0;
			        for(int j=0;j<MeasuredDataList.size();j++){
			        	MeasuredDataList.get(j).setProjectId(projectId);
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
			        MeasuredDataDao.addMeasuredDataList(MeasuredDataList);
			        List <Quantity> quantityList=new ArrayList<Quantity>();
			    	DataWrapper<List <QuantityPojo>> quantitypojo= MeasuredDataDao.getSameMeasuredData(projectId);
			    	if(quantitypojo.getData()!=null){
			    		for(QuantityPojo pojo:quantitypojo.getData()){
			    			
			    			Quantity test=new Quantity();
			    			Long st=pojo.getProject_id();
			    			test.setProjectId(st);
			    			test.setBuildingNum(pojo.getBuilding_num());
			    			test.setFloorNum(pojo.getFloor_num());
			    			test.setUnitNum(pojo.getUnit_num());
			    			test.setHouseholdNum(pojo.getHousehold_num());
//			    			test.setTypeName(pojo.getType_name());
			    			String familyAndType=pojo.getFamily_and_type();
			    			String typeName=pojo.getType_name();
			    			if(typeName==null){
			    				typeName="";
			    			}
//			    			test.setTypeName(typeName);
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
	}*/
	/*@Override
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
		        ReadExcelOfMinMeasuredData readExcel=new ReadExcelOfMinMeasuredData();
		        
		        //解析excel，构件信息集合。
		        List<MinMeasuredData> MeasuredDataList = readExcel.getExcelInfo(newFileName ,file,projectId);
		        
		        if(MeasuredDataList.size()>0){
		        	 b = true;
		        	fileSerivce.uploadFile(name, file, type, request);
		            //迭代添加构件信息
		        	minMeasuredDataDao.addMinMeasuredDataList(MeasuredDataList);
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
	public DataWrapper<Void> deleteMeasuredDataByProjectId(Long projectId, String token) {
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(projectId!=null){
					return MeasuredDataDao.deleteMeasuredDataByProjectId(projectId, token);
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
		
	}*/

	@Override
	public DataWrapper<MeasuredDatas> getMeasuredDatasById(Long id,String token) {
		DataWrapper<MeasuredDatas> dataWrapper = new DataWrapper<MeasuredDatas>();
		if(id!=null){
			MeasuredDatas measuredDatas=new MeasuredDatas();
			measuredDatas=measuredDatasDao.getMeasuredDatasById(id);
			dataWrapper.setData(measuredDatas);
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
		}
		return dataWrapper;
	}
	

}
