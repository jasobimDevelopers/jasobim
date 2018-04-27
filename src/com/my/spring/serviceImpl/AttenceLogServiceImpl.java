package com.my.spring.serviceImpl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.spring.DAO.AttenceLogDao;
import com.my.spring.DAO.AttenceModelDao;
import com.my.spring.DAO.ProjectDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.DAO.UserLogDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.AttenceLog;
import com.my.spring.model.AttenceLogPojo;
import com.my.spring.model.AttenceLogs;
import com.my.spring.model.AttenceModel;
import com.my.spring.model.Files;
import com.my.spring.model.User;
import com.my.spring.parameters.Parameters;
import com.my.spring.service.AttenceLogService;
import com.my.spring.service.FileService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.InstanceUtil;
import com.my.spring.utils.SessionManager;

@Service("attenceLogService")
public class AttenceLogServiceImpl implements AttenceLogService{
    @Autowired
    UserDao userDao;
    @Autowired
    AttenceLogDao attenceLogDao;
    @Autowired
    AttenceModelDao attenceModelDao;
    @Autowired
    UserLogDao userLogDao;
    @Autowired
    FileService fileservice;
    @Autowired
    ProjectDao projectDao;

	@Override
	public DataWrapper<Void> addAttenceLog(AttenceLog am, String token,Double lat,Double lng) {
		// TODO Auto-generated method stub
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(am!=null){
				if(am.getProjectId()!=null){
					AttenceModel aml =  attenceModelDao.getAttenceModelByProjectId(am.getProjectId());
					AttenceLog al = new AttenceLog();
					if(aml!=null){
						Double instanc = InstanceUtil.distanceSimplifyMore(lat, lng, aml.getPlaceLat(), aml.getPlaceLng());
						if(instanc>aml.getAttenceRange()){
							result.setErrorCode(ErrorCodeEnum.Instance_Not_Fit);
						}else{
							am.setCreateDate(new Date());
							am.setClockFlag(1);
							am.setUserId(user.getId());
							Date realWorkTime=null;
							Date realEndTime=null;
							//////判断上班打卡时间是否符合规定
							if(am.getStartWorkTime()!=null){
								try {
									realWorkTime = Parameters.getSdf().parse(am.getStartWorkTime());
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								Date requiredWorkTime=null;
								try {
									requiredWorkTime = Parameters.getSdf().parse(aml.getStartTime());
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								if(requiredWorkTime.before(realWorkTime)){
									al.setLate(1);////1表示迟到
								}else{
									al.setLate(0);////0表示正常
								}
							}
							/////判断下班打卡时间是否符合规定
							if(am.getEndWorkTime()!=null){
								
								try {
									realEndTime = Parameters.getSdf().parse(am.getEndWorkTime());
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								Date requiredEndTime=null;
								try {
									requiredEndTime = Parameters.getSdf().parse(aml.getEndTime());
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								if(realEndTime.before(requiredEndTime)){
									al.setLeaveEarly(1);
								}else{
									al.setLeaveEarly(0);
								}
							}
							al.setCreateDate(new Date());
							al.setUserId(user.getId());
							al.setProjectId(am.getProjectId());
							al.setEndWorkTime(am.getEndWorkTime());
							al.setStartWorkTime(am.getStartWorkTime());
							al.setAttenceModelId(aml.getId());
							al.setLat(lat);
							al.setLng(lng);
							if(!attenceLogDao.addAttenceLog(al)){
								result.setErrorCode(ErrorCodeEnum.Error);
							}
						}
					}
				}else{
					result.setErrorCode(ErrorCodeEnum.Project_Not_Existed);
				}
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<Void> deleteAttenceLog(Long id, String token) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(!attenceLogDao.deleteAttenceLog(id)){
				result.setErrorCode(ErrorCodeEnum.Error);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<Void> updateAttenceLog(AttenceLog am, String token) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(!attenceLogDao.updateAttenceLog(am)){
				result.setErrorCode(ErrorCodeEnum.Error);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<List<AttenceLogPojo>> getAttenceLogList(String token, AttenceLog duct, Integer pageSize,
			Integer pageIndex,Integer year,Integer month) {
		DataWrapper<List<AttenceLogPojo>> result = new DataWrapper<List<AttenceLogPojo>>();
		List<AttenceLogPojo> results = new ArrayList<AttenceLogPojo>();
		DataWrapper<List<AttenceLogs>> amList = new DataWrapper<List<AttenceLogs>>();
		DataWrapper<List<AttenceLogs>> amLists = new DataWrapper<List<AttenceLogs>>();
		int days = Parameters.getDaysByYearMonth(year, month);
		User user = SessionManager.getSession(token);
		if(user!=null){
			amList = attenceLogDao.getAttenceLogsList(pageIndex, pageSize, duct,year,month);
			amLists=attenceLogDao.getAttenceLogsUserList(pageIndex, pageSize, duct,year,month);
			if(amList.getData()!=null)
			{
				if(amList.getData().size()>0){
					for(AttenceLogs am : amList.getData()){
						AttenceLogPojo attenceModelPojo = new AttenceLogPojo();
						if(am.getUserId()!=null){
							User users = userDao.getById(am.getUserId());
							if(users!=null){
								attenceModelPojo.setUserName(users.getRealName());
								if(users.getUserIcon()!=null){
									Files file = fileservice.getById(users.getUserIcon());
									if(file!=null){
										attenceModelPojo.setUserIconurl(file.getUrl());
									}
								}else if(users.getUserIconUrl()!=null){
									attenceModelPojo.setUserIconurl(user.getUserIconUrl());
								}
							}
						}
						//attenceModelPojo.setWorkDays(days-am.ge);
						results.add(attenceModelPojo);
					}
					result.setData(results);
				}
			}else{
				result.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

}
