package com.my.spring.serviceImpl;

import com.my.spring.DAO.BuildingDao;
import com.my.spring.DAO.MeasuredDataDao;
import com.my.spring.DAO.MeasuredDatasDao;
import com.my.spring.DAO.ProjectDao;
import com.my.spring.DAO.QuantityDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.MeasuredData;
import com.my.spring.model.MeasuredDataPojo;
import com.my.spring.model.MeasuredDatas;
import com.my.spring.model.User;
import com.my.spring.model.UserLog;
import com.my.spring.parameters.Parameters;
import com.my.spring.service.FileService;
import com.my.spring.service.MeasuredDataService;
import com.my.spring.service.UserLogService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service("MeasuredDataService")
public class MeasuredDataServiceImpl implements MeasuredDataService {
    @Autowired
    MeasuredDataDao measuredDataDao;
    @Autowired
    MeasuredDatasDao measuredDatasDao;
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
    @Autowired
    UserLogService userLogSerivce;
    @Override
    public DataWrapper<Void> addMeasuredData(MeasuredData measuredData,String token,String webToken) {
    	 DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
         if(webToken.equals("jasobim")){
        	 if(measuredData!=null){
        		 measuredData.setCreateDate(new Date());
					if(!measuredDataDao.addMeasuredData(measuredData)) 
			            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					else{
						String measuredDatas1 = measuredData.getMeasuredData();
						String[] measuredDatas2 = measuredDatas1.split("-");
						List<MeasuredDatas> measuredDatasList = new ArrayList<MeasuredDatas>(measuredDatas2.length);
						for(int i=0;i<measuredDatas2.length;i++){
							MeasuredDatas measuredDatas = new MeasuredDatas();
							measuredDatas.setMeasuredDataId(measuredData.getId());
							String[] allData=measuredDatas2[i].split("a");
							measuredDatas.setCheckContent(Parameters.checkContent[Integer.valueOf(allData[0])-1]);
							String inputData=null;
							Integer shiceData=0;
							Integer passData=0;
							if(allData.length>1){
								String[] realDatas=allData[1].split(",");
								inputData="";
								shiceData=realDatas.length;
								passData=0;
								for(int j=0;j<realDatas.length;j++){
									Integer dt = Integer.valueOf(realDatas[j].split(":")[1]);
									if(Integer.valueOf(allData[0])==3){
										if(dt>=-1 && dt<=1){
											passData++;
										}
									}else{
										if(Integer.valueOf(allData[0])==5){
											if(dt>=145 && dt<=205){
												passData++;
											}
										}else{
											int test=Integer.valueOf(allData[0]);
											if(Parameters.designLevel[test-1].equals("")){
												if(dt>=-5 && dt<=5){
													passData++;
												}
											}else{
												int biaozhun=Integer.valueOf(Parameters.designLevel[test-1]);
												if((dt-biaozhun)>=-5 && (dt-biaozhun)<=5){
													passData++;
												}
											}
										}
									}
									if(inputData.equals("")){
										inputData=realDatas[j].split(":")[1];
									}else{
										inputData+=","+realDatas[j].split(":")[1];
									}
								}
							}
							measuredDatas.setInputData(inputData);
							measuredDatas.setShiceData(shiceData);
							measuredDatas.setPassData(passData);
							if(Integer.valueOf(allData[0])==3){
								measuredDatas.setCheckTemplete(Parameters.checkTemplate[1]);
							}else{
								measuredDatas.setCheckTemplete(Parameters.checkTemplate[0]);
							}
							measuredDatas.setLocationSize(Parameters.locationSize[Integer.valueOf(allData[0])-1]);
							measuredDatas.setDesignLevel(Parameters.designLevel[Integer.valueOf(allData[0])-1]);
							measuredDatas.setCheckContent(Parameters.checkContent[Integer.valueOf(allData[0])-1]);
							measuredDatasList.add(measuredDatas);
						}
						measuredDatasDao.addMeasuredDatasList(measuredDatasList);
						return dataWrapper;
					}
			        
				}else{
					dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
				}
         }
    	 User userInMemory = SessionManager.getSession(token);
         if (userInMemory != null) {
 			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
 				if(measuredData!=null){
 					if(!measuredDataDao.addMeasuredData(measuredData)) 
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
    public DataWrapper<Void> deleteMeasuredData(Long id,String token) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(id!=null){
				if(!measuredDataDao.deleteMeasuredData(id)) 
		            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
				else{
					measuredDatasDao.deleteMeasuredDatasByMeasuredDataId(id);
					return dataWrapper;
				}
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrapper;
    }

   

    @Override
    public DataWrapper<List<MeasuredDataPojo>> getMeasuredDataList(Integer pageIndex, Integer pageSize, MeasuredData MeasuredData,String token) {
    	DataWrapper<List<MeasuredData>> dataWrapper = new DataWrapper<List<MeasuredData>>();
    	DataWrapper<List<MeasuredDataPojo>> dataWrappers = new DataWrapper<List<MeasuredDataPojo>>();
    	List<MeasuredDataPojo> dataWrapperss = new ArrayList<MeasuredDataPojo>();
    	User adminInMemory = SessionManager.getSession(token);
        if (adminInMemory != null) {
        	dataWrapper =measuredDataDao.getMeasuredDataList(pageSize, pageIndex,MeasuredData);
        	if(dataWrapper.getData()!=null && dataWrapper.getData().size()>0){
        		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        		
        		for(MeasuredData msd : dataWrapper.getData()){
        			NumberFormat numberFormat = NumberFormat.getInstance();  
        		    // 设置精确到小数点后2位  
        		    numberFormat.setMaximumFractionDigits(2);  
        		    float fenzi=Float.valueOf(msd.getQualifiedDataSum());
        		    float fenmu=Float.valueOf(msd.getMeasuredDataSum());
        		    String result = numberFormat.format((fenzi/fenmu * 100))+"%";  
        			MeasuredDataPojo pojo = new MeasuredDataPojo();
        			pojo.setPassPercent(result);
        			pojo.setCreateDate(sdf.format(msd.getCreateDate()));
        			pojo.setCeliangUser(msd.getCeliangUser());
        			pojo.setCheckMoreUser(msd.getCheckMoreUser());
        			pojo.setCheckPart(msd.getCheckPart());
        			pojo.setCheckUser(msd.getCheckUser());
        			pojo.setConstructor(msd.getConstructor());
        			pojo.setId(msd.getId());
        			pojo.setLeaderName(msd.getLeaderName());
        			pojo.setLeaderUnit(msd.getLeaderUnit());
        			pojo.setProjectName(msd.getProjectName());
        			pojo.setQuantiter(msd.getQuantiter());
        			pojo.setMeasuredDataSum(msd.getMeasuredDataSum());
        			pojo.setQualifiedDataSum(msd.getQualifiedDataSum());
        			pojo.setSubmiteUser(msd.getSubmiteUser());
        			dataWrapperss.add(pojo);
        		}
        		dataWrappers.setData(dataWrapperss);
        	}
		} else {
			dataWrappers.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrappers;
    	
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
	public DataWrapper<MeasuredDataPojo> getMeasuredDataById(Long id,String token) {
		DataWrapper<MeasuredDataPojo> dataWrapper = new DataWrapper<MeasuredDataPojo>();
		if(id!=null){
			MeasuredDataPojo measuredDatapojo=new MeasuredDataPojo();
			MeasuredData measuredData=new MeasuredData();
			measuredData=measuredDataDao.getMeasuredDataById(id);
			dataWrapper.setData(measuredDatapojo);
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
		}
		return dataWrapper;
	}
	

}
