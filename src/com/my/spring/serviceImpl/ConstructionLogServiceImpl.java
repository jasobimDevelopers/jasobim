package com.my.spring.serviceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.my.spring.DAO.ConstructionLogDao;
import com.my.spring.DAO.ProductionRecordsDao;
import com.my.spring.DAO.ProjectDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.CallStatusEnum;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.ConstructionLog;
import com.my.spring.model.ConstructionLogPojo;
import com.my.spring.model.ProductionRecords;
import com.my.spring.model.Project;
import com.my.spring.model.User;
import com.my.spring.parameters.Parameters;
import com.my.spring.service.ConstructionLogService;
import com.my.spring.service.FileService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;
@Service("constructionLogService")
public class ConstructionLogServiceImpl implements ConstructionLogService {
	
    @Autowired
    ConstructionLogDao constructionLogDao;
    @Autowired
    ProductionRecordsDao productionRecordsDao;
    @Autowired
    UserDao userDao;
    
    @Autowired
    ProjectDao projectDao;
    
    @Autowired
    FileService fileService;
	@Override
	public DataWrapper<List<ConstructionLogPojo>> getConstructionLogList(String token, Integer pageIndex,
			Integer pageSize, ConstructionLog constructionLog,String start,String end,String userRealName) {
		// TODO Auto-generated method stub
		Date dateStarts=null;
    	Date dateFinisheds=null;
		DataWrapper<List<ConstructionLogPojo>> clp = new DataWrapper<List<ConstructionLogPojo>>();
		List<ConstructionLogPojo> clpsss = new ArrayList<ConstructionLogPojo>();
		DataWrapper<List<ConstructionLog>> clps = new DataWrapper<List<ConstructionLog>>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd");
    		if(start!=null){
    			try {
    				dateStarts=sdfs.parse(start);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    		if(end!=null){
    			try {
					dateFinisheds=sdfs.parse(end);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
			clps = constructionLogDao.getConstructionLogsList(pageIndex, pageSize, constructionLog,userRealName,dateStarts,dateFinisheds);
			if(clps.getData()!=null){
				if(clps.getData().size()>0)
				{
					for(ConstructionLog cl:clps.getData())
					{
						ConstructionLogPojo constructionLogPojo = new ConstructionLogPojo();
						constructionLogPojo.setConstructionDate(cl.getConstructionDateStr());
						constructionLogPojo.setCreateDate(Parameters.getSdf().format(cl.getCreateDate()));
						constructionLogPojo.setId(cl.getId());
						constructionLogPojo.setProjectId(cl.getProjectId());
						constructionLogPojo.setCreateUserId(cl.getCreateUserId());
						constructionLogPojo.setWeather(cl.getWeather());
						constructionLogPojo.setTechnologyDiscloseContent(cl.getTechnologyDiscloseContent());
						constructionLogPojo.setTechnologyDiscloseState(cl.getTechnologyDiscloseState());
						constructionLogPojo.setQualityDiscloseContent(cl.getQualityDiscloseContent());
						constructionLogPojo.setQualityDiscloseState(cl.getQualityDiscloseState());
						constructionLogPojo.setSafetyDiscloseContent(cl.getSafetyDiscloseContent());
						constructionLogPojo.setMaterialDiscloseContent(cl.getMaterialDiscloseContent());
						constructionLogPojo.setMaterialDiscloseState(cl.getMaterialDiscloseState());
						constructionLogPojo.setTemperature(cl.getTemperature());
						constructionLogPojo.setWindForce(cl.getWindForce());
						constructionLogPojo.setEmergencyState(cl.getEmergencyState());
					    constructionLogPojo.setCreateUserName(cl.getCreateUserName());
						List<ProductionRecords> productionRecordsList = new ArrayList<ProductionRecords>();
						productionRecordsList=productionRecordsDao.getProductionRecordsList(cl.getId());
						constructionLogPojo.setProductionRecordsList(productionRecordsList);
						clpsss.add(constructionLogPojo);
					}
					clp.setData(clpsss);
					clp.setTotalNumber(clps.getTotalNumber());
					clp.setNumberPerPage(clps.getNumberPerPage());
					clp.setTotalPage(clps.getTotalPage());
					clp.setCurrentPage(clps.getCurrentPage());
				}
			}
		}else{
			clp.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		  if(clp.getCallStatus()==CallStatusEnum.SUCCEED && clp.getData()==null){
		       	List<ConstructionLogPojo> pas= new ArrayList<ConstructionLogPojo>();
		       	clp.setData(pas);
		       }
		return clp;
	}

	@Override
	public DataWrapper<List<ConstructionLog>> getConstructionLogListByUserId(Long userId, String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataWrapper<ConstructionLog> addConstructionLog(ConstructionLog constructionLog, String token,String constructDates,String cityNode) {
		DataWrapper<ConstructionLog> result = new DataWrapper<ConstructionLog>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			constructionLog.setConstructionDateStr(constructDates);
			try {
				constructionLog.setConstructionDate(Parameters.getSdfs().parse(constructDates));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			constructionLog.setCreateDate(new Date());
			constructionLog.setCreateUserId(userInMemory.getId());
			constructionLog.setCreateUserName(userInMemory.getRealName());
			if(!constructionLogDao.addConstructionLog(constructionLog)){
				result.setErrorCode(ErrorCodeEnum.Error);
			}else{
				if(constructionLog.getProjectId()!=null){
					Project project = projectDao.getById(constructionLog.getProjectId());
					if(project!=null){
						if(!project.getCityCode().equals(cityNode)){
							project.setCityCode(cityNode);
							projectDao.updateProject(project);
						}
					}
				}
				result.setData(constructionLog);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<Void> deleteConstructionLog(Long id, String token) {
		// TODO Auto-generated method stub
		DataWrapper<Void> result = new DataWrapper<Void>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			if(id!=null){
				if(!constructionLogDao.deleteConstructionLog(id)){
					result.setErrorCode(ErrorCodeEnum.Error);
				}
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<String> exportConstructionLog(String token, Long id) {
		DataWrapper<String> result = new DataWrapper<String>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}


}
