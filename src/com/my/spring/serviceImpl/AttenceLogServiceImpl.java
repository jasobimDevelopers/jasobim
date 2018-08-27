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
import com.my.spring.DAO.RoleDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.CallStatusEnum;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.AttenceForgetFactLogs;
import com.my.spring.model.AttenceLog;
import com.my.spring.model.AttenceLogPojo;
import com.my.spring.model.AttenceLogs;
import com.my.spring.model.AttenceModel;
import com.my.spring.model.Files;
import com.my.spring.model.Role;
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
    FileService fileservice;
    @Autowired
    ProjectDao projectDao;
    @Autowired
    RoleDao roleDao;
	@Override
	public DataWrapper<String> addAttenceLog(AttenceLog am, String token,Double lat,Double lng) {
		// TODO Auto-generated method stub
		DataWrapper<String> result = new DataWrapper<String>();
		User user = SessionManager.getSession(token);
		System.out.println();  
		if(user!=null){
			if(am!=null){
				if(am.getProjectId()!=null){
					
					am.setLat(lat);
					am.setLng(lng);
					am.setUserId(user.getId());
					Date nowDate = null;
					try {
						nowDate = Parameters.getSdfs().parse(Parameters.getSdfs().format(new Date()));
						am.setCreateDate(nowDate);
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
					//////判断当前是否已有打卡记录
					AttenceLog al=attenceLogDao.getAttenceLogByInfos(user.getId(),nowDate,am.getProjectId());
					/////当前系统时间（精确到天）已有打卡记录，则根据打卡记录来更新
					///////当打卡记录不为空时,判断是否签退，如果已签退，不允许在签退，否则签退
					if(al!=null){
						if(al.getEndWorkTime()==null){
							al.setEndWorkTime(Parameters.getSdf().format(new Date()));
							AttenceModel aml =  attenceModelDao.getAttenceModelByProjectId(al.getProjectId());
							if(aml!=null){
								al.setAttenceModelId(aml.getId());
								Double instanc = InstanceUtil.distanceSimplifyMore(lat, lng, aml.getPlaceLat(), aml.getPlaceLng());
								if(instanc>aml.getAttenceRange()){
									result.setErrorCode(ErrorCodeEnum.Instance_Not_Fit);
								}else{
									Date realWorkTime=null;
									//////判断上班打卡时间是否符合规定
									realWorkTime = nowDate;
									Date requiredWorkTime=null;
									try {
										String now=Parameters.getSdfs().format(nowDate);
										requiredWorkTime = Parameters.getSdf().parse(now+" "+aml.getEndTime()+":00");
									} catch (ParseException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									if(!requiredWorkTime.before(realWorkTime)){
										al.setLeaveEarly(1);////1表示早退
									}else{
										am.setLeaveEarly(0);////0表示正常
									}
									if(!attenceLogDao.updateAttenceLog(al)){
										result.setErrorCode(ErrorCodeEnum.Error);
									}else{
										result.setData(al.getEndWorkTime());
									}
								}
							}else{
								result.setErrorCode(ErrorCodeEnum.AttendModel_Not_Existed);
							}
						}else{
							result.setErrorCode(ErrorCodeEnum.Already_Done);
						}
					
					}else{
						am.setStartWorkTime(Parameters.getSdf().format(new Date()));
						AttenceModel aml =  attenceModelDao.getAttenceModelByProjectId(am.getProjectId());
						if(aml!=null){
							am.setAttenceModelId(aml.getId());
							Double instanc = InstanceUtil.distanceSimplifyMore(lat, lng, aml.getPlaceLat(), aml.getPlaceLng());
							if(instanc>aml.getAttenceRange()){
								result.setErrorCode(ErrorCodeEnum.Instance_Not_Fit);
							}else{
								Date realWorkTime=null;
								//////判断上班打卡时间是否符合规定
								realWorkTime = nowDate;
								Date requiredWorkTime=null;
								try {
									String now=Parameters.getSdfs().format(nowDate);
									requiredWorkTime = Parameters.getSdf().parse(now+" "+aml.getStartTime()+":00");
								} catch (ParseException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								if(!requiredWorkTime.before(realWorkTime)){
									am.setLate(1);;////1表示迟到
									am.setLeaveEarly(0);
								}else{
									am.setLate(0);////0表示正常
									am.setLeaveEarly(0);
								}
								if(!attenceLogDao.addAttenceLog(am)){
									result.setErrorCode(ErrorCodeEnum.Error);
								}else{
									result.setData(am.getStartWorkTime());
								}
							}
						}else{
							result.setErrorCode(ErrorCodeEnum.AttendModel_Not_Existed);
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
		DataWrapper<List<AttenceForgetFactLogs>> forgetFactNums = new DataWrapper<List<AttenceForgetFactLogs>>();
		if(year==null){
			 Date date = new Date();
		     year = Integer.valueOf(Parameters.getSdfs().format(date).split("-")[0]);
		}
		if(month==null){
			Date date = new Date();
		    month = Integer.valueOf(Parameters.getSdfs().format(date).split("-")[1]);
		}
		int days = Parameters.getDaysByYearMonth(year, month);
		User user = SessionManager.getSession(token);
		if(user!=null){
			
			int weekds=0;
			if(duct.getProjectId()!=null){
				AttenceModel aml = attenceModelDao.getAttenceModelByProjectId(duct.getProjectId());
				if(aml!=null){
					weekds = aml.getAttenceDay().split("、").length;
				}
			}
			amList = attenceLogDao.getAttenceLogsList(pageIndex, pageSize, duct,year,month);
			forgetFactNums =  attenceLogDao.getForgetFactNumsList(pageIndex, pageSize, duct,year,month);
			if(amList.getData()!=null)
			{
				if(!amList.getData().isEmpty()){
					for(AttenceLogs am : amList.getData()){
						AttenceLogPojo attenceModelPojo = new AttenceLogPojo();
						if(am.getUser_id()!=null){
							User users = userDao.getById(am.getUser_id());
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
								Role role = roleDao.getById(users.getRoleId());
								if(role!=null){
									attenceModelPojo.setWorkName(role.getName());
								}
							}
						}
						if(forgetFactNums.getData()!=null){
							if(!forgetFactNums.getData().isEmpty()){
								for(int i=0;i<forgetFactNums.getData().size();i++){
									if(am.getUser_id().equals(forgetFactNums.getData().get(i).getUser_id())){
										attenceModelPojo.setForgetClockNum(forgetFactNums.getData().get(i).getForget_nums());
										attenceModelPojo.setWorkDays(forgetFactNums.getData().get(i).getForget_nums()+forgetFactNums.getData().get(i).getFact_nums());
										int flag = days-(days/weekds)*(7-weekds);
										attenceModelPojo.setNotWorkNum(flag-forgetFactNums.getData().get(i).getForget_nums()-forgetFactNums.getData().get(i).getFact_nums());
									}
								}
							}
						}
						/////迟到次数
						attenceModelPojo.setLateNum(am.getLate_nums());
						///早退次数
						attenceModelPojo.setLeaveEarlyNum(am.getLeave_early_nums());
						////忘打卡次数
						
						///出勤次数
						if(attenceModelPojo.getForgetClockNum()==null){
							attenceModelPojo.setForgetClockNum(0);
						}
						if(attenceModelPojo.getWorkDays()==null){
							attenceModelPojo.setWorkDays(0);
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
	   if(result.getCallStatus()==CallStatusEnum.SUCCEED && result.getData()==null){
        	List<AttenceLogPojo> pas= new ArrayList<AttenceLogPojo>();
        	result.setData(pas);
        }
		return result;
	}

	@Override
	public DataWrapper<AttenceLog> getAttenceLogById(String token, AttenceLog ps) {
		DataWrapper<AttenceLog> alp = new DataWrapper<AttenceLog>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			AttenceLog gets = new AttenceLog();
			Date nowDate = null;
			try {
				nowDate = Parameters.getSdfs().parse(Parameters.getSdfs().format(new Date()));
				//nowDate = Parameters.getSdfs().parse("2018-05-10");
				ps.setCreateDate(nowDate);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(ps.getUserId()==null){
				ps.setUserId(user.getId());
			}
			gets = attenceLogDao.getAttenceLogListByIds(ps);
			if(gets==null){
				AttenceLog logs = new AttenceLog();
				alp.setData(logs);
			}else{
				alp.setData(gets);
			}
			
		}else{
			alp.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return alp;
	}

}
