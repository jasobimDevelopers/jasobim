package com.my.spring.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.my.spring.DAO.DepartmentDao;
import com.my.spring.DAO.DepartmentUserDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.DAO.WorkTypeDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Department;
import com.my.spring.model.DepartmentUser;
import com.my.spring.model.DepartmentUserPojo;
import com.my.spring.model.Files;
import com.my.spring.model.User;
import com.my.spring.model.WorkType;
import com.my.spring.parameters.Parameters;
import com.my.spring.service.DepartmentUserService;
import com.my.spring.service.FileService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;
@Service("departmentUserService")
public class DepartmentUserServiceImpl implements DepartmentUserService {
	@Autowired
	DepartmentUserDao departmentUserDao;
	@Autowired
	DepartmentDao departmentDao;
	@Autowired
	WorkTypeDao workTypeDao;
	@Autowired
	UserDao userDao;
	@Autowired
	FileService fileService;
	private final String filePath="files/department/idCardImg";
	private final String[] sexList={"男","女"};
	@Override
	public DataWrapper<Void> deleteDepartmentUserById(String token, Long id) {
		// TODO Auto-generated method stub
		DataWrapper<Void> result = new DataWrapper<Void>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			if(id!=null){
				if(!departmentUserDao.deleteDepartmentUser(id)){
					result.setErrorCode(ErrorCodeEnum.Error);
				}
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<Void> addDepartmentUser(String token, DepartmentUser dt,MultipartFile idCardImgZ,MultipartFile idCardImgF,HttpServletRequest request) {
		// TODO Auto-generated method stub
		DataWrapper<Void> result = new DataWrapper<Void>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			if(dt!=null){
				if(idCardImgZ!=null){
					Files file1 = fileService.uploadFile(filePath, idCardImgZ, 5, request);
					if(file1!=null){
						dt.setIdCardImgZ(file1.getId());
					}
				}
				if(idCardImgF!=null){
					Files file2 = fileService.uploadFile(filePath, idCardImgF, 5, request);
					if(file2!=null){
						dt.setIdCardImgZ(file2.getId());
					}
				}
				dt.setCreateDate(new Date());
				dt.setCreateUser(userInMemory.getId());
				if(!departmentUserDao.addDepartmentUser(dt)){
					result.setErrorCode(ErrorCodeEnum.Error);
				}
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<Void> deleteDepartmentByIdList(String token, String[] id) {
		// TODO Auto-generated method stub
		DataWrapper<Void> result = new DataWrapper<Void>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			if(id!=null){
				if(!departmentUserDao.deleteDepartmentUserList(id)){
					result.setErrorCode(ErrorCodeEnum.Error);
				}
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<List<DepartmentUserPojo>> getDepartmentUserList(Integer pageIndex, Integer pageSize,
			DepartmentUser departmentUser, String token) {
		// TODO Auto-generated method stub
		DataWrapper<List<DepartmentUserPojo>> result = new DataWrapper<List<DepartmentUserPojo>>();
		List<DepartmentUserPojo> outputs = new ArrayList<DepartmentUserPojo>();
		List<DepartmentUser> gets = new ArrayList<DepartmentUser>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			gets=departmentUserDao.getDepartmentUserList(pageSize, pageIndex, departmentUser);
			if(!gets.isEmpty()){
				for(DepartmentUser du : gets){
					DepartmentUserPojo dup = new DepartmentUserPojo();
					dup.setName(du.getName());
					dup.setId(du.getId());
					Department de=departmentDao.getById(du.getDepartmentId());
					if(de!=null){
						dup.setDepartment(de.getName());
					}
					WorkType wt = workTypeDao.getById(du.getWorkTypeId());
					if(wt!=null){
						dup.setWorkTypeName(wt.getName());
					}
					if(du.getIdCardImgZ()!=null){
						Files idCardZ = fileService.getById(du.getIdCardImgZ());
						if(idCardZ!=null){
							dup.setIdCardImgZ(idCardZ.getUrl());
						}
					}
					if(du.getIdCardImgF()!=null){
						Files idCardF = fileService.getById(du.getIdCardImgF());
						if(idCardF!=null){
							dup.setIdCardImgF(idCardF.getUrl());
						}
					}
					User createUser = userDao.getById(du.getCreateUser());
					if(createUser!=null){
						dup.setCreateUser(createUser.getRealName());
					}
					if(du.getUpdateDate()!=null){
						dup.setUpdateDate(Parameters.getSdf().format(du.getUpdateDate()));
					}
					if(du.getUpdateUser()!=null){
						User updateUser = userDao.getById(du.getUpdateUser());
						if(updateUser!=null){
							dup.setUpdateUser(updateUser.getRealName());
						}
					}
					dup.setCreateDate(Parameters.getSdf().format(du.getCreateDate()));
					dup.setIdCard(du.getIdCard());
					dup.setTel(du.getTel());
					dup.setSalary(du.getSalary());
					dup.setSex(sexList[du.getSex()]);
					dup.setProjectId(du.getProjectId());
					outputs.add(dup);
				}
				result.setData(outputs);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<DepartmentUserPojo> getDepartmentUserById(String token, Long id) {
		// TODO Auto-generated method stub
		DataWrapper<DepartmentUserPojo> result = new DataWrapper<DepartmentUserPojo>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			if(id!=null){
				DepartmentUser du = departmentUserDao.getById(id);
				if(du!=null){
					DepartmentUserPojo dup = new DepartmentUserPojo();
					dup.setName(du.getName());
					dup.setId(du.getId());
					Department de=departmentDao.getById(du.getDepartmentId());
					if(de!=null){
						dup.setDepartment(de.getName());
					}
					WorkType wt = workTypeDao.getById(du.getWorkTypeId());
					if(wt!=null){
						dup.setWorkTypeName(wt.getName());
					}
					if(du.getIdCardImgZ()!=null){
						Files idCardZ = fileService.getById(du.getIdCardImgZ());
						if(idCardZ!=null){
							dup.setIdCardImgZ(idCardZ.getUrl());
						}
					}
					if(du.getIdCardImgF()!=null){
						Files idCardF = fileService.getById(du.getIdCardImgF());
						if(idCardF!=null){
							dup.setIdCardImgF(idCardF.getUrl());
						}
					}
					User createUser = userDao.getById(du.getCreateUser());
					if(createUser!=null){
						dup.setCreateUser(createUser.getRealName());
					}
					if(du.getUpdateDate()!=null){
						dup.setUpdateDate(Parameters.getSdf().format(du.getUpdateDate()));
					}
					if(du.getUpdateUser()!=null){
						User updateUser = userDao.getById(du.getUpdateUser());
						if(updateUser!=null){
							dup.setUpdateUser(updateUser.getRealName());
						}
					}
					dup.setCreateDate(Parameters.getSdf().format(du.getCreateDate()));
					dup.setIdCard(du.getIdCard());
					dup.setTel(du.getTel());
					dup.setSalary(du.getSalary());
					dup.setSex(sexList[du.getSex()]);
					dup.setProjectId(du.getProjectId());
					result.setData(dup);
				}else{
					result.setErrorCode(ErrorCodeEnum.Error);
				}
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<Void> updateDepartmentUser(String token, DepartmentUser departmentUser,MultipartFile idCardImgZ,MultipartFile idCardImgF,HttpServletRequest request) {
		// TODO Auto-generated method stub
		DataWrapper<Void> result = new DataWrapper<Void>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			if(departmentUser!=null){
				if(departmentUser.getId()!=null){
					DepartmentUser get = departmentUserDao.getById(departmentUser.getId());
					if(get!=null){
						get.setUpdateDate(new Date());
						get.setUpdateUser(userInMemory.getId());
						if(departmentUser.getName()!=null){
							get.setName(departmentUser.getName());
						}
						if(departmentUser.getTel()!=null){
							get.setTel(departmentUser.getTel());
						}
						if(departmentUser.getWorkTypeId()!=null){
							get.setWorkTypeId(departmentUser.getWorkTypeId());
						}
						if(departmentUser.getSalary()!=null){
							get.setSalary(departmentUser.getSalary());
						}
						if(departmentUser.getIdCard()!=null){
							get.setIdCard(departmentUser.getIdCard());
						}
						if(departmentUser.getSex()!=null){
							get.setSex(departmentUser.getSex());
						}
						if(departmentUser.getDepartmentId()!=null){
							get.setDepartmentId(departmentUser.getDepartmentId());
						}
						if(idCardImgZ!=null){
							Files file1 = fileService.uploadFile(filePath, idCardImgZ, 5, request);
							if(file1!=null){
								get.setIdCardImgZ(file1.getId());
							}
						}
						if(idCardImgF!=null){
							Files file2 = fileService.uploadFile(filePath, idCardImgF, 5, request);
							if(file2!=null){
								get.setIdCardImgZ(file2.getId());
							}
						}
						if(!departmentUserDao.updateDepartmentUser(get)){
							result.setErrorCode(ErrorCodeEnum.Error);
						}
					}else{
						result.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
					}
				}
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

}
