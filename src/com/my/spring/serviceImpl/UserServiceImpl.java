package com.my.spring.serviceImpl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.my.spring.DAO.ProjectDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.Files;
import com.my.spring.model.Project;
import com.my.spring.model.User;
import com.my.spring.model.UserPojo;
import com.my.spring.service.FileService;
import com.my.spring.service.UserService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.MD5Util;
import com.my.spring.utils.SessionManager;


@Service("userService")
public class UserServiceImpl implements UserService {
	private final String salt = "嘉实安装";
	
	@Autowired
	UserDao userDao;
	@Autowired
	ProjectDao projectDao;
	@Autowired
	FileService fileService;
	private String filePath="files";
    private Integer fileType=5;
	@Override
	public DataWrapper<Void> register(User user) {
		// TODO Auto-generated method stub
		DataWrapper<Void> dataWrapper = new DataWrapper<>();
		if (user.getUserName() == null ||user.getUserName().equals("")
				|| user.getPassword() == null || user.getPassword().equals("")
				|| user.getRealName() == null || user.getRealName().equals("")) {
			dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
		} else if(userDao.getByUserName(user.getUserName()) != null) {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Existed);
		} else {
			user.setId(null);
			user.setPassword(MD5Util.getMD5String(MD5Util.getMD5String(user.getPassword()) + salt));
			user.setUserType(UserTypeEnum.User.getType());
			//user.setUserType(user.getUserType());
			user.setRegisterDate(new Date(System.currentTimeMillis()));
			if(!userDao.addUser(user)) {
				dataWrapper.setErrorCode(ErrorCodeEnum.Error);
			}
			
		}
		return dataWrapper;
	}

	@Override
	public DataWrapper<UserPojo> login(String userName, String password,Integer system) {
		// TODO Auto-generated method stub
		DataWrapper<UserPojo> dataWrapper = new DataWrapper<UserPojo>();
		if (userName == null || password == null 
				||userName.equals("") || password.equals("")) {
			dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
		} else {
			User user = userDao.getByUserName(userName);
			if (user == null) {
				dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Existed);
			} else if(!user.getPassword().equals(MD5Util.getMD5String(password + salt))) {
				dataWrapper.setErrorCode(ErrorCodeEnum.Password_Error);
			} else {
				SessionManager.removeSessionByUserId(user.getId());
				if(system!=null){
					user.setSystemId(system);
				}else{
					user.setSystemId(-1);
				}
				String token = SessionManager.newSession(user);
				dataWrapper.setToken(token);
				UserPojo users=new UserPojo();
				if(user.getProjectList()!=null){
					String[] projectList=user.getProjectList().split(",");
					String[] projectName = new String[projectList.length];
					for(int i=0;i<projectList.length;i++){
						Project project = new Project();
						project=projectDao.getById(Long.valueOf(projectList[i]));
						if(project!=null){
							projectName[i]=project.getName();
						}
					}
					users.setProjectName(projectName);
				}
				users.setUserName(user.getUserName());
				users.setUserType(user.getUserType());
				users.setId(user.getId());
				if(user.getUserIcon()!=null){
					Files file=new Files();
					file=fileService.getById(user.getUserIcon());
					if(file!=null){
						users.setUserIconUrl(file.getUrl());
					}
				}
				dataWrapper.setData(users);
			}
		}
		
		return dataWrapper;
	}

	@Override
	public DataWrapper<Void> updateUser(User user, String token) {
		// TODO Auto-generated method stub
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		User userInMemory = SessionManager.getSession(token);
		if (userInMemory != null) {
			User userInDB = userDao.getById(userInMemory.getId());
			if (userInDB != null) {
				if(user.getRealName() != null && !user.getRealName().equals("")) {
					userInDB.setRealName(user.getRealName());
				}
				if (user.getEmail() != null && !user.getEmail().equals("")) {
					userInDB.setEmail(user.getEmail());
				}
				if (user.getTel() != null && !user.getTel().equals("")) {
					userInDB.setTel(user.getTel());
				}
				if (!userDao.updateUser(userInDB)) {
					dataWrapper.setErrorCode(ErrorCodeEnum.Error);
				}
			} else {
				dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Existed);
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dataWrapper;
	}
	
	@Override
	public DataWrapper<Void> updateUserByAdmin(User user, String token,MultipartFile file,HttpServletRequest request) {
		// TODO Auto-generated method stub
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		User adminInMemory = SessionManager.getSession(token);
		if (adminInMemory != null && adminInMemory.getUserType() == UserTypeEnum.Admin.getType()) {
			User userInDB = userDao.getById(user.getId());
			if (userInDB != null) {
				if(file !=null){
						String path=filePath+"/"+"userIcons"+"/";
						Files newfile=fileService.uploadFile(path, file,fileType,request);
						userInDB.setUserIcon(newfile.getId());
				}
				if(user.getUserName() != null && !user.getUserName().equals("")) {
					userInDB.setUserName(user.getUserName());
				}
				if(!user.getPassword().equals(userInDB.getPassword())){
					userInDB.setPassword(MD5Util.getMD5String(MD5Util.getMD5String(user.getPassword()) + salt));
				}
				if(user.getRealName() != null && !user.getRealName().equals("")) {
					userInDB.setRealName(user.getRealName());
				}
				if (user.getEmail() != null && !user.getEmail().equals("")) {
					userInDB.setEmail(user.getEmail());
				}
				if(user.getProjectList()!=null){
					userInDB.setProjectList(user.getProjectList());
				}
				if (user.getWorkName() != null && !user.getWorkName().equals("")) {
					userInDB.setWorkName(user.getWorkName());
				}
				if (user.getTel() != null && !user.getTel().equals("")) {
					userInDB.setTel(user.getTel());
				}
				if(user.getUserType() !=null && !user.getUserType().equals(""))
				{
					userInDB.setUserType(user.getUserType());
				}
				if (!userDao.updateUser(userInDB)) {
					dataWrapper.setErrorCode(ErrorCodeEnum.Error);
				}
			} else {
				dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Existed);
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.AUTH_Error);
		}
		return dataWrapper;
	}
	
	@Override
	public DataWrapper<Void> updateUserBySelf(String oldPs, String newPs, String token) {
		// TODO Auto-generated method stub
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		User adminInMemory = SessionManager.getSession(token);
		int flag=-1;
		if (adminInMemory != null) {
			if(oldPs!=null && newPs!=null)
			{
				String oldPsReal=MD5Util.getMD5String(MD5Util.getMD5String(oldPs) + salt);
				if(adminInMemory.getPassword().equals(oldPsReal)){
					flag=1;
					adminInMemory.setPassword(MD5Util.getMD5String(MD5Util.getMD5String(newPs) + salt));
				}else{
					dataWrapper.setErrorCode(ErrorCodeEnum.Password_Not_Fit);
				}
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
			}
			if(flag>0){
				if (!userDao.updateUser(adminInMemory)) {
					dataWrapper.setErrorCode(ErrorCodeEnum.Error);
				}
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dataWrapper;
	}
	@Override
	public DataWrapper<User> getUserDetails(String token) {
		// TODO Auto-generated method stub
		DataWrapper<User> dataWrapper = new DataWrapper<User>();
		User userInMemory = SessionManager.getSession(token);
		if (userInMemory != null) {
			User userInDB = userDao.getById(userInMemory.getId());
			userInDB.setPassword(null);
			dataWrapper.setData(userInDB);
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dataWrapper;
	}

	@Override
	public DataWrapper<UserPojo> getUserDetailsByAdmin(Long userId, String token) {
		// TODO Auto-generated method stub
		DataWrapper<UserPojo> dataWrappers = new DataWrapper<UserPojo>();
		DataWrapper<User> dataWrapper = new DataWrapper<User>();
		User adminInMemory = SessionManager.getSession(token);
		if (adminInMemory != null) {
			User adminInDB = new User();
			if(userId!=null && (adminInMemory.getUserType()==0 || adminInMemory.getWorkName().equals("总经理"))){
				adminInDB = userDao.getById(userId);
			}else{
				adminInDB = userDao.getById(adminInMemory.getId());
			}
			dataWrapper.setData(adminInDB);
			if(dataWrapper.getData()!=null){
				UserPojo userpojo=new UserPojo();
				userpojo.setEmail(dataWrapper.getData().getEmail());
				userpojo.setId(dataWrapper.getData().getId());
				userpojo.setPassword(dataWrapper.getData().getPassword());
				userpojo.setProjectList(dataWrapper.getData().getProjectList());
				userpojo.setRealName(dataWrapper.getData().getRealName());
				userpojo.setRegisterDate(dataWrapper.getData().getRegisterDate());
				userpojo.setTel(dataWrapper.getData().getTel());
				userpojo.setUserName(dataWrapper.getData().getUserName());
				userpojo.setUserType(dataWrapper.getData().getUserType());
				userpojo.setUserIcon(dataWrapper.getData().getUserIcon());
				userpojo.setTeamInformation(dataWrapper.getData().getTeamInformation());
				userpojo.setWorkName(dataWrapper.getData().getWorkName());
				if(dataWrapper.getData().getUserIcon()!=null){
					Files file=new Files();
					file=fileService.getById(dataWrapper.getData().getUserIcon());
					if(file!=null){
						userpojo.setUserIconUrl(file.getUrl());
					}
				}
				dataWrappers.setData(userpojo);
			}
		} else {
			dataWrappers.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dataWrappers;
	}

	@Override
	public DataWrapper<List<UserPojo>> getUserList(Integer pageIndex, Integer pageSize, User user, String token) {
		// TODO Auto-generated method stub
		DataWrapper<List<UserPojo>> dataWrapper = new DataWrapper<List<UserPojo>>();
		List<UserPojo> userpojoList=new ArrayList<UserPojo>();
		DataWrapper<List<User>> userList=new DataWrapper<List<User>>();
		User adminInMemory = SessionManager.getSession(token);
		if (adminInMemory != null) {
			User adminInDB = userDao.getById(adminInMemory.getId());
			if (adminInDB.getUserType() == UserTypeEnum.Admin.getType() || adminInDB.getTeamId()!=null || adminInDB.getWorkName().equals("总经理")) {
				user.setTeamInformation(adminInDB.getTeamInformation());
				userList=userDao.getUserList(pageSize, pageIndex,user);
				if(userList.getData().size()>0){
					for(int i=0;i<userList.getData().size();i++){
						UserPojo userpojo=new UserPojo();
						userpojo.setEmail(userList.getData().get(i).getEmail());
						userpojo.setId(userList.getData().get(i).getId());
						userpojo.setProjectList(userList.getData().get(i).getProjectList());
						userpojo.setPassword(userList.getData().get(i).getPassword());
						userpojo.setRealName(userList.getData().get(i).getRealName());
						userpojo.setTel(userList.getData().get(i).getTel());
						userpojo.setRegisterDate(userList.getData().get(i).getRegisterDate());
						userpojo.setUserName(userList.getData().get(i).getUserName());
						userpojo.setUserType(userList.getData().get(i).getUserType());
						userpojo.setUserIcon(userList.getData().get(i).getUserIcon());
						userpojo.setWorkName(userList.getData().get(i).getWorkName());
						userpojo.setTeamInformation(userList.getData().get(i).getTeamInformation());
						if(userList.getData().get(i).getUserIcon()!=null){
							Files file=fileService.getById(userList.getData().get(i).getUserIcon());
							if(file!=null){
								userpojo.setUserIconUrl(file.getUrl());
							}
						}
						userpojoList.add(i, userpojo);
					}
					dataWrapper.setData(userpojoList);
					dataWrapper.setCallStatus(userList.getCallStatus());
					dataWrapper.setCurrentPage(userList.getCurrentPage());
					dataWrapper.setErrorCode(userList.getErrorCode());
					dataWrapper.setNumberPerPage(userList.getNumberPerPage());
					dataWrapper.setTotalNumber(userList.getTotalNumber());
					dataWrapper.setTotalPage(userList.getTotalPage());
				}
				
			} else {
				dataWrapper.setErrorCode(ErrorCodeEnum.AUTH_Error);
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dataWrapper;
	}

	@Override
	public DataWrapper<Void> changeUserTypeByAdmin(Long userId, Integer userType, String token) {
		// TODO Auto-generated method stub
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		User adminInMemory = SessionManager.getSession(token);
		if (adminInMemory != null) {
			User adminInDB = userDao.getById(adminInMemory.getId());
			if (adminInDB.getUserType() == UserTypeEnum.Admin.getType()) {
				User userInDB = userDao.getById(userId);
				userInDB.setUserType(userType);
				if (!userDao.updateUser(userInDB)) {
					dataWrapper.setErrorCode(ErrorCodeEnum.Error);
				}
			} else {
				dataWrapper.setErrorCode(ErrorCodeEnum.AUTH_Error);
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dataWrapper;
	}

	@Override
	public DataWrapper<User> findUserLike(User user, String token) {
		// TODO Auto-generated method stub
		DataWrapper<User> dataWrapper = new DataWrapper<User>();
		User adminInMemory = SessionManager.getSession(token);
		if(adminInMemory!=null){
			if(user!=null){
				dataWrapper=userDao.findUserLike(user);
				if(dataWrapper!=null && dataWrapper.getData()!=null){
					dataWrapper.setErrorCode(ErrorCodeEnum.Error);
				}
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
			}
			
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dataWrapper;
	}

	@Override
	public DataWrapper<Void> deleteUser(Long userId, String token) {
		DataWrapper<Void> dataWrapper=new DataWrapper<Void>();
		User userInMemory=SessionManager.getSession(token);
		if(userInMemory!=null){
			User adminInDB = userDao.getById(userInMemory.getId());
			if (adminInDB.getUserType() == UserTypeEnum.Admin.getType()) {
				if(userDao.deleteUser(userId)){
					dataWrapper.setErrorCode(ErrorCodeEnum.No_Error);
				}else{
					dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
				}
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.AUTH_Error);
			}
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dataWrapper;
	}

	@Override
	public DataWrapper<Void> addUser(User user,String token,MultipartFile file, HttpServletRequest request) {
		DataWrapper<Void> dataWrapper = new DataWrapper<>();
		User userInMemory=SessionManager.getSession(token);
		if(userInMemory!=null){
			User adminInDB = userDao.getById(userInMemory.getId());
			if (adminInDB.getUserType() == UserTypeEnum.Admin.getType()) {
				if (user.getUserName() == null ||user.getUserName().equals("")
						|| user.getPassword() == null || user.getPassword().equals("")
						|| user.getRealName() == null || user.getRealName().equals("")) {
					dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
				} else if(userDao.getByUserName(user.getUserName()) != null) {
					dataWrapper.setErrorCode(ErrorCodeEnum.User_Existed);
				} else {
					if(file !=null){
						String path=filePath+"/"+"userIcons"+"/";
						Files newfile=fileService.uploadFile(path, file,fileType,request);
						user.setUserIcon(newfile.getId());
					}
					user.setId(null);
					user.setPassword(MD5Util.getMD5String(MD5Util.getMD5String(user.getPassword()) + salt));
					//user.setUserType(user.getUserType());
					user.setRegisterDate(new Date(System.currentTimeMillis()));
					if(user.getWorkName()!=null){
						String[] teamId=user.getWorkName().split(",");
						String team="";
						for(int i=0;i<teamId.length;i++){
							if(i==0){
								team=team+i;
							}else{
								team=team+","+i;
							}
							
						}
						user.setTeamId(0);
					}
					if(!userDao.addUser(user)) {
						dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					}
					
				}
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.AUTH_Error);
			}
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		
		return dataWrapper;
	}

	@Override
	public DataWrapper<User> FindPs(User user) {
		// TODO Auto-generated method stub
		DataWrapper<User> dataWrapper = new DataWrapper<User>(); 
		dataWrapper=userDao.findUserLike(user);
		return dataWrapper;
	}

}
