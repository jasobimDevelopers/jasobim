package com.my.spring.serviceImpl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.spring.DAO.DepartmentDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.DAO.UserIndexDao;
import com.my.spring.enums.CallStatusEnum;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Department;
import com.my.spring.model.DepartmentPojo;
import com.my.spring.model.MaxIndex;
import com.my.spring.model.User;
import com.my.spring.model.UserIndex;
import com.my.spring.model.UserIndexUserId;
import com.my.spring.model.UserIndexs;
import com.my.spring.parameters.Parameters;
import com.my.spring.service.DepartmentService;
import com.my.spring.service.UserIndexService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService  {
	@Autowired
	DepartmentDao departmentDao;
	@Autowired
	UserIndexService userIndexService;
	@Autowired
	UserIndexDao userIndexDao;
	@Autowired
	UserDao userDao;
	@Override
	public DataWrapper<Void> deleteDepartmentById(String token,Long id) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(!departmentDao.deleteDepartment(id)){
				result.setErrorCode(ErrorCodeEnum.Error);
			}else{
				userIndexService.deleteUserIndexByAboutId(0, id);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<Department> addDepartment(String token,Department role) {
		DataWrapper<Department> result = new DataWrapper<Department>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(role!=null){
				role.setCreateUser(user.getId());
				role.setCreateDate(new Date());
				if(!departmentDao.addDepartment(role)){
					result.setErrorCode(ErrorCodeEnum.Error);
				}else{
					List<UserIndex> indexList = new ArrayList<UserIndex>();
					List<UserIndexUserId> idList=userIndexDao.getUserIndexListByGroup();
					if(!idList.isEmpty()){
						for(int i=0;i<idList.size();i++){
							MaxIndex max=userIndexDao.getIndexMaxByUserId(idList.get(i).getId(),0);
							UserIndex userIndex = new UserIndex();
							userIndex.setAboutType(0);
							userIndex.setIndexs(max.getIndexs()+1);
							userIndex.setUserId(idList.get(i).getId());
							userIndex.setAboutId(role.getId());
							indexList.add(userIndex);
						}
						userIndexDao.addUserIndexList(indexList);
					}
					result.setData(role);
				}
			}
			
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<Void> deleteDepartmentByIdList(String token,String[] id) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(!departmentDao.deleteDepartmentList(id)){
				result.setErrorCode(ErrorCodeEnum.Error);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}


	@Override
	public DataWrapper<List<DepartmentPojo>> getDepartmentList(Integer pageIndex, Integer pageSize, Department department,
			String token) {
		DataWrapper<List<DepartmentPojo>> dp = new DataWrapper<List<DepartmentPojo>>();
		List<DepartmentPojo> dpp = new ArrayList<DepartmentPojo>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			UserIndex userIndex = new UserIndex();
			userIndex.setAboutType(0);
			userIndex.setUserId(user.getId());
			DataWrapper<List<Department>> dt = new DataWrapper<List<Department>>();
			List<UserIndexs> get=departmentDao.getDepartmentListByUserId(user.getId());
			if(get!=null){
				if(get.isEmpty()){
					dt = departmentDao.getDepartmentList(pageIndex,pageSize,department);
					if(dt!=null){
						if(dt.getData().size()>0){
							for(int i=0;i<dt.getData().size();i++){
								DepartmentPojo dps = new DepartmentPojo();
								dps.setName(dt.getData().get(i).getName());
								dps.setId(dt.getData().get(i).getId());
								dps.setIndexs((long)(i+1));
								dpp.add(dps);
							}
						}
					}
				}else{
					for(UserIndexs dpss:get){
						DepartmentPojo dps = new DepartmentPojo();
						dps.setName(dpss.getName());
						dps.setId(dpss.getId());
						dps.setIndexs(dpss.getIndexs());
						dpp.add(dps);
					}
				}
			}
			
			dp.setData(dpp);
		}else{
			dp.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		  if(dp.getCallStatus()==CallStatusEnum.SUCCEED && dp.getData()==null){
		       	List<DepartmentPojo> pas= new ArrayList<DepartmentPojo>();
		       	dp.setData(pas);
		       }
		return dp;
	}

	@Override
	public DataWrapper<DepartmentPojo> getDepartmentById(String token, Long id) {
		DataWrapper<DepartmentPojo> dp = new DataWrapper<DepartmentPojo>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			Department dt = departmentDao.getById(id);
			DepartmentPojo dps = new DepartmentPojo();
			if(dt!=null){
				dps.setName(dt.getName());
				dps.setRemark(dt.getRemark());
				dps.setCreateDate(Parameters.getSdf().format(dt.getCreateDate()));
				if(dt.getCreateUser()!=null){
					User users = userDao.getById(dt.getCreateUser());
					if(users!=null){
						dps.setCreateUser(users.getRealName());
					}
				}
			}
			dp.setData(dps);
		}else{
			dp.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dp;
	}

	@Override
	public DataWrapper<Void> updateDepartment(String token, Department department) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(department!=null){
				Department dp = new Department();
				dp = departmentDao.getById(department.getId());
				dp.setUpdateDate(new Date());
				if(department.getName()!=null){
					dp.setName(department.getName());
				}
				if(!departmentDao.updateDepartment(dp)){
					result.setErrorCode(ErrorCodeEnum.Error);
				}
			}
			
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	
}
