package com.my.spring.serviceImpl;

import java.io.FileOutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONArray;
import com.my.spring.DAO.AttenceModelDao;
import com.my.spring.DAO.MechanicDao;
import com.my.spring.DAO.MechanicPriceDao;
import com.my.spring.DAO.ProjectDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.CallStatusEnum;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.MechanicPrice;
import com.my.spring.model.MechanicPriceNum;
import com.my.spring.model.MechanicPricePojo;
import com.my.spring.model.MechanicPricePojos;
import com.my.spring.model.Project;
import com.my.spring.model.Mechanic;
import com.my.spring.model.MechanicData;
import com.my.spring.model.MechanicDataOfHour;
import com.my.spring.model.MechanicDataPeople;
import com.my.spring.model.User;
import com.my.spring.model.UserLog;
import com.my.spring.parameters.Parameters;
import com.my.spring.parameters.ProjectDatas;
import com.my.spring.service.FileService;
import com.my.spring.service.MechanicPriceService;
import com.my.spring.service.UserLogService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.DateDemo;
import com.my.spring.utils.ExportExcelOfMenchainc;
import com.my.spring.utils.SessionManager;

@Service("mechanicPriceService")
public class MechanicPriceServiceImpl implements MechanicPriceService{
    @Autowired
    UserDao userDao;
    @Autowired
    MechanicPriceDao mechanicPriceDao;
    @Autowired
    MechanicDao mechanicDao;
    @Autowired
    AttenceModelDao attenceModelDao;
    @Autowired
    UserLogService userLogService;
    @Autowired
    FileService fileservice;
    @Autowired
    ProjectDao projectDao;
   
	@Override
	public DataWrapper<Void> addMechanicPrice(MechanicPrice am, String token) {
		// TODO Auto-generated method stub
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(am.getMechanicId()!=null){
				if(user.getSystemId()!=null){
					if(am.getProjectId()!=null){
						UserLog userLog = new UserLog();
		    			userLog.setProjectPart(ProjectDatas.AttenceLog_area.getCode());
		    			userLog.setActionDate(new Date());
		    			userLog.setActionType(1);
		    			userLog.setProjectId(am.getProjectId());
		    			userLog.setUserId(user.getId());
		    			userLog.setSystemType(user.getSystemId());
		    			userLog.setVersion("3.0");
		    			userLogService.addUserLog(userLog, token);
					}
				}
				if(!mechanicPriceDao.addMechanicPrice(am)){
					result.setErrorCode(ErrorCodeEnum.Error);
				}
			}else{
				result.setErrorCode(ErrorCodeEnum.Empty_Inputs);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<Void> addMechanicPriceList(String am, String token) {
		// TODO Auto-generated method stub
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			List<MechanicPrice> list = JSONArray.parseArray(am, MechanicPrice.class);
			if(user.getSystemId()!=null){
				if(list.get(0).getProjectId()!=null){
					UserLog userLog = new UserLog();
	    			userLog.setProjectPart(ProjectDatas.AttenceLog_area.getCode());
	    			userLog.setActionDate(new Date());
	    			userLog.setActionType(1);
	    			userLog.setProjectId(list.get(0).getProjectId());
	    			userLog.setUserId(user.getId());
	    			userLog.setSystemType(user.getSystemId());
	    			userLog.setVersion("3.0");
	    			userLogService.addUserLog(userLog, token);
				}
			}
			for( MechanicPrice mp:list){
				MechanicPrice mps = mechanicPriceDao.getMechanicPriceLists(-1, 10, mp);
				if(mps!=null){
					mp.setId(mps.getId());
					mp.setEditDate(new Date());
					mechanicPriceDao.updateMechanicPrice(mp);
				}else{
					mechanicPriceDao.addMechanicPrice(mp);
				}
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}
	@Override
	public DataWrapper<Void> deleteMechanicPrice(Long id, String token) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(!mechanicPriceDao.deleteMechanicPrice(id)){
				result.setErrorCode(ErrorCodeEnum.Error);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}


	@Override
	public DataWrapper<List<MechanicPricePojo>> getMechanicPriceList(String token, MechanicPrice duct, Integer pageSize,
			Integer pageIndex,Long projectId,String date) {
		DataWrapper<List<MechanicPricePojo>> result = new DataWrapper<List<MechanicPricePojo>>();
		List<MechanicPricePojo> results = new ArrayList<MechanicPricePojo>();
		DataWrapper<List<Mechanic>> amList = new DataWrapper<List<Mechanic>>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(projectId!=null){
				Date now = new Date();
		        String startday = null;
		        String endday=null;
		        if(date!=null){
					try {
						startday = DateDemo.getMinMonthDate(date);
						endday= DateDemo.getMaxMonthDate(date);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }else{
		        	try {
						startday = DateDemo.getMinMonthDate(Parameters.getSdfs().format(now));
						endday= DateDemo.getMaxMonthDate(Parameters.getSdfs().format(now));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
				
		        
				amList=mechanicDao.getMechanicListByProjectId(pageIndex, pageSize, projectId);
				if(amList.getData()!=null){
					if(amList.getData().size()>0){
						for(Mechanic mp : amList.getData()){
							List<MechanicPrice> mps = null;
							try {
								mps = mechanicPriceDao.getMechanicPriceListByMechanicId(-1, 10, mp.getId(), Parameters.getSdfs().parse(startday),Parameters.getSdfs().parse(endday));
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if(mps!=null && mps.size()>0){
								MechanicPricePojo mpp = new MechanicPricePojo();
								mpp.setCreateDate(Parameters.getSdf().format(mp.getCreateDate()));
								mpp.setUserName(mp.getRealName());
								mpp.setWorkName(mp.getWorkName());
								mpp.setMp(mps);
								mpp.setId(mp.getId());
								results.add(mpp);
							}
						}
						result.setData(results);
					}
				}else{
					result.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
				}
			}else{
				result.setErrorCode(ErrorCodeEnum.Empty_Inputs);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
	  if(result.getCallStatus()==CallStatusEnum.SUCCEED && result.getData()==null){
	       	List<MechanicPricePojo> pas= new ArrayList<MechanicPricePojo>();
	       	result.setData(pas);
	    }
		return result;
	}

	@Override
	public DataWrapper<Void> updateMechanicPrice(MechanicPrice duct,String token,String date ) {
		DataWrapper<Void> result = new  DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			
			if(duct.getId()!=null){
				MechanicPrice mp = mechanicPriceDao.getMechanicPriceById(duct.getId());
				mp.setHour(duct.getHour());
				mp.setEditDate(new Date());
				mechanicPriceDao.updateMechanicPrice(mp);
			}else{
				MechanicPrice mps = new MechanicPrice();
				mps.setHour(duct.getHour());
				try {
					mps.setCreateDate(Parameters.getSdfs().parse(date));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mps.setMechanicId(duct.getMechanicId());
				mps.setProjectId(duct.getProjectId());
				mechanicPriceDao.addMechanicPrice(mps);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<List<MechanicPricePojos>> getMechanicPriceNum(String token, Long projectId,String date) {
		DataWrapper<List<MechanicPricePojos>> result = new DataWrapper<List<MechanicPricePojos>>();
		List<MechanicPricePojos> results = new ArrayList<MechanicPricePojos>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(projectId!=null){
				if(user.getSystemId()!=null){
						UserLog userLog = new UserLog();
		    			userLog.setProjectPart(ProjectDatas.AttenceLog_area.getCode());
		    			userLog.setActionDate(new Date());
		    			userLog.setActionType(0);
		    			userLog.setProjectId(projectId);
		    			userLog.setUserId(user.getId());
		    			userLog.setSystemType(user.getSystemId());
		    			userLog.setVersion("3.0");
		    			userLogService.addUserLog(userLog, token);
				}
				Date now = new Date();
		        String startday = null;
		        String endday=null;
		        if(date!=null){
					try {
						startday = DateDemo.getMinMonthDate(date);
						endday= DateDemo.getMaxMonthDate(date);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }else{
		        	try {
						startday = DateDemo.getMinMonthDate(Parameters.getSdfs().format(now));
						endday= DateDemo.getMaxMonthDate(Parameters.getSdfs().format(now));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
				List<MechanicPriceNum> mechanicList = mechanicPriceDao.getMechanicPriceNumByProjectId(startday,endday,projectId);
				//amList=mechanicPriceDao.getMechanicPriceListByProjectId(pageIndex, pageSize, projectId);
				if(mechanicList!=null){
					if(mechanicList.size()>0){
						Float ss=(float)projectDao.getById(projectId).getWorkHour();
						for(MechanicPriceNum mp : mechanicList){
							MechanicPricePojos mps = new MechanicPricePojos();
							mps.setUserName(mp.getReal_name());
							mps.setDaySalary(mp.getDay_salary());
							if(mp.getNum()==null){
								mp.setNum(0);
							}
							mps.setDayNum((mp.getNum()/ss));
							mps.setHourNum(mp.getNum());
							mps.setWorkName(mp.getWork_name());
							mps.setSalaryNum((mp.getNum()/ss)*mp.getDay_salary());
							results.add(mps);
						}
						result.setData(results);
					}
				}else{
					result.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
				}
			}else{
				result.setErrorCode(ErrorCodeEnum.Empty_Inputs);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<String> exportMechanicNum(String token, Long projectId, String date) {
		// TODO Auto-generated method stub
		DataWrapper<List<MechanicPricePojos>> mvs= new DataWrapper<List<MechanicPricePojos>>();
		DataWrapper<String> result = new DataWrapper<String>();
    	User userInMemory=SessionManager.getSession(token);
    	if(userInMemory!=null){
    		mvs=this.getMechanicPriceNum(token,projectId,date);
    		Project project = projectDao.getById(projectId);
    		if(mvs.getData()!=null){
    			result.setData("/mechanicFiles/mechanicPrice.xls");
    			if(mvs.getData().size()>0){
    				try  
    		        {  
    					Calendar cal = Calendar.getInstance();
    					cal.setTime(Parameters.getSdfs().parse(date));
    		            FileOutputStream fout = new FileOutputStream("D://jasobim/tomcat_8080/webapps/ROOT/mechanicFiles/mechanicPrice.xls");
    		            ExportExcelOfMenchainc aa = new ExportExcelOfMenchainc();
    		            aa.getValue(mvs.getData(), fout,project.getName(),cal.get(Calendar.MONTH) + 1);
    		            fout.close();  
    		        }  
    		        catch (Exception e)  
    		        {  
    		            e.printStackTrace();  
    		        }  
    				/*WriteMechanicDataToExcel wd = new WriteMechanicDataToExcel();
    	    		String url=wd.WriteData(mvs.getData());*/
    			}
    		}
    	}else{
    		result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
    	}
		return result;
	}

	@Override
	public DataWrapper<List<MechanicData>> getMechanicDatas(String token, Long projectId, String date) {
		// TODO Auto-generated method stub
		DataWrapper<List<MechanicData>> result = new DataWrapper<List<MechanicData>>();
		List<MechanicData> resultList = new ArrayList<MechanicData>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(user.getSystemId()!=null){
				if(projectId!=null){
					UserLog userLog = new UserLog();
	    			userLog.setProjectPart(ProjectDatas.MechanicPrice_area.getCode());
	    			userLog.setActionDate(new Date());
	    			userLog.setActionType(0);
	    			userLog.setProjectId(projectId);
	    			userLog.setUserId(user.getId());
	    			userLog.setSystemType(user.getSystemId());
	    			userLog.setVersion("3.0");
	    			userLogService.addUserLog(userLog, token);
				}
			}
			Date now = new Date();
	        String startday = null;
	        String endday=null;
	        if(date!=null){
				try {
					startday = DateDemo.getMinMonthDate(date);
					endday= DateDemo.getMaxMonthDate(date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }else{
	        	try {
					startday = DateDemo.getMinMonthDate(Parameters.getSdfs().format(now));
					endday= DateDemo.getMaxMonthDate(Parameters.getSdfs().format(now));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
			List<MechanicDataOfHour> mechanicHourList = mechanicPriceDao.getMechanicHourByProjectId(startday,endday,projectId);
			List<MechanicDataPeople> mechanicPeopleList = mechanicPriceDao.getMechanicPeopleByProjectId(startday, endday, projectId);
			if(mechanicHourList!=null){
				if(!mechanicHourList.isEmpty()){
					for(MechanicDataOfHour mdh:mechanicHourList){
						MechanicData md = new MechanicData();
						md.setDate(Parameters.getSdfs().format(mdh.getCreate_date()));
						md.setNum(mdh.getHours());
						md.setType(1);
						resultList.add(md);
					}
				}
			}
			if(mechanicPeopleList!=null){
				if(!mechanicPeopleList.isEmpty()){
					for(MechanicDataPeople mdh:mechanicPeopleList){
						MechanicData md = new MechanicData();
						md.setDate(Parameters.getSdfs().format(mdh.getCreate_date()));
						md.setNum(mdh.getPeople_num());
						md.setType(0);
						resultList.add(md);
					}
				}
			}
			result.setData(resultList);
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		
		return result;
	}

}
