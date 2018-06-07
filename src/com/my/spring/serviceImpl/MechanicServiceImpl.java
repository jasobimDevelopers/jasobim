package com.my.spring.serviceImpl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.my.spring.DAO.MechanicDao;
import com.my.spring.DAO.MechanicPriceDao;
import com.my.spring.DAO.ProjectDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.DAO.UserLogDao;
import com.my.spring.enums.CallStatusEnum;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Files;
import com.my.spring.model.MaterialLogPojo;
import com.my.spring.model.Mechanic;
import com.my.spring.model.MechanicPojo;
import com.my.spring.model.MechanicPojos;
import com.my.spring.model.MechanicPrice;
import com.my.spring.model.Project;
import com.my.spring.model.User;
import com.my.spring.parameters.Parameters;
import com.my.spring.service.FileService;
import com.my.spring.service.MechanicService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

@Service("mechanicService")
public class MechanicServiceImpl implements MechanicService{
    @Autowired
    UserDao userDao;
    @Autowired
    MechanicDao mechanicDao;
    @Autowired
    MechanicPriceDao mechanicPriceDao;
    @Autowired
    UserLogDao userLogDao;
    @Autowired
    FileService fileService;
    @Autowired
    ProjectDao projectDao;

	@Override
	public DataWrapper<Void> addMechanic(Mechanic am, String token,MultipartFile file,HttpServletRequest request) {
		// TODO Auto-generated method stub
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(am!=null){
				am.setCreateDate(new Date());
				am.setCreateUser(user.getId());
			}
			if(file!=null){
				Files files = fileService.uploadFile("/mechanic/"+am.getProjectId(),file, 5,request);
				if(files!=null){
					am.setIdCardImg(files.getId());
				}
			}
			if(!mechanicDao.addMechanic(am)){
				result.setErrorCode(ErrorCodeEnum.Error);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<Void> deleteMechanic(Long id, String token) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(!mechanicDao.deleteMechanic(id)){
				result.setErrorCode(ErrorCodeEnum.Error);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<Void> updateMechanic(Mechanic am, String token,MultipartFile file,HttpServletRequest request) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(am.getId()!=null){
				Mechanic mechanice = mechanicDao.getMechanicById(am.getId());
				if(mechanice!=null){
					mechanice.setDaySalary(am.getDaySalary());
					mechanice.setIdCard(am.getIdCard());
					mechanice.setRealName(am.getRealName());
					mechanice.setRemark(am.getRemark());
					mechanice.setTel(am.getTel());
					mechanice.setWorkName(am.getWorkName());
					mechanice.setProjectId(am.getProjectId());
					if(file!=null){
						Files files = fileService.uploadFile("/mechanic/"+am.getProjectId(), file, 5, request);
						if(files!=null){
							mechanice.setIdCardImg(files.getId());
						}
					}
					if(!mechanicDao.updateMechanic(mechanice)){
						result.setErrorCode(ErrorCodeEnum.Error);
					}
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
	public DataWrapper<List<MechanicPojo>> getMechanicList(String token, Mechanic duct, Integer pageSize,
			Integer pageIndex) throws ParseException {
		DataWrapper<List<MechanicPojo>> result = new DataWrapper<List<MechanicPojo>>();
		List<MechanicPojo> results = new ArrayList<MechanicPojo>();
		DataWrapper<List<Mechanic>> amList = new DataWrapper<List<Mechanic>>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			amList = mechanicDao.getMechanicList(pageIndex, pageSize, duct);
			if(amList.getData()!=null)
			{
				if(amList.getData().size()>0){
					for(Mechanic am : amList.getData()){
						MechanicPojo attenceModelPojo = new MechanicPojo();
						MechanicPrice mpce = new MechanicPrice();
						String dates = Parameters.getSdfs().format(new Date());
						mpce.setCreateDate(Parameters.getSdfs().parse(dates));
						mpce.setMechanicId(am.getId());
						mpce.setProjectId(am.getProjectId());
						MechanicPrice mps =mechanicPriceDao.getMechanicPriceLists(-1, 10, mpce);
						if(mps!=null){
							attenceModelPojo.setDayHours(mps.getHour());
							attenceModelPojo.setCreateDate(Parameters.getSdf().format(mps.getCreateDate()));
						}else{
							Project projects = projectDao.getById(am.getProjectId());
							if(projects!=null){
								attenceModelPojo.setDayHours(projects.getWorkHour());
							}
							attenceModelPojo.setCreateDate(Parameters.getSdf().format(am.getCreateDate()));
						}
						if(am.getIdCardImg()!=null){
							Files filess = fileService.getById(am.getIdCardImg());
							if(filess!=null){
								attenceModelPojo.setIdCardImg(filess.getUrl());
							}
						}
						attenceModelPojo.setTel(am.getTel());
						attenceModelPojo.setDaySalary(am.getDaySalary());
						attenceModelPojo.setWorkName(am.getWorkName());
						attenceModelPojo.setIdCard(am.getIdCard());
						attenceModelPojo.setRealName(am.getRealName());
						attenceModelPojo.setRemark(am.getRemark());
						attenceModelPojo.setId(am.getId());
						
						if(am.getCreateUser()!=null){
							User users = userDao.getById(am.getCreateUser());
							if(users!=null){
								attenceModelPojo.setCreateUser(users.getRealName());
							}
						}
						attenceModelPojo.setProjectId(am.getProjectId());
						results.add(attenceModelPojo);
					}
					result.setData(results);
					result.setCallStatus(amList.getCallStatus());
					result.setCurrentPage(amList.getCurrentPage());
					result.setNumberPerPage(amList.getNumberPerPage());
					result.setTotalNumber(amList.getTotalNumber());
					result.setTotalPage(amList.getTotalPage());
					result.setErrorCode(amList.getErrorCode());
				}
			}else{
				result.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
	  if(result.getCallStatus()==CallStatusEnum.SUCCEED && result.getData()==null){
	       	List<MechanicPojo> pas= new ArrayList<MechanicPojo>();
	       	result.setData(pas);
	    }
		return result;
	}

	@Override
	public DataWrapper<List<MechanicPojos>> getMechanicInfos(String token, Mechanic ps) throws ParseException {
		DataWrapper<List<MechanicPojos>> result = new DataWrapper<List<MechanicPojos>>();
		List<MechanicPojos> results = new ArrayList<MechanicPojos>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			List<Mechanic> gets = new ArrayList<Mechanic>();
			if(ps!=null){
				if(ps.getProjectId()!=null){
					Project project = projectDao.getById(ps.getProjectId());
					result.setTotalPage(project.getWorkHour());
				}
			}
			gets = mechanicDao.getMechanicListByProjectId(ps);
			Date now = new Date();
			if(!gets.isEmpty()){
				for(Mechanic item:gets){
					MechanicPojos mps = new MechanicPojos();
					mps.setId(item.getId());
					mps.setProjectId(item.getProjectId());
					mps.setRealName(item.getRealName());
					mps.setWorkName(item.getWorkName());
					if(item.getIdCardImg()!=null){
						Files file = fileService.getById(item.getIdCardImg());
						if(file!=null){
							mps.setUserIcon(file.getUrl());
						}
					}
					MechanicPrice mp = mechanicPriceDao.getMechanicPriceListByInfos(item.getId(),item.getProjectId(),Parameters.getSdfs().parse(Parameters.getSdfs().format(now)));
					if(mp!=null){
						mps.setWorkHours(mp.getHour());
					}
					results.add(mps);
				}
				result.setData(results);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

}
