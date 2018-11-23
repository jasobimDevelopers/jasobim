package com.my.spring.serviceImpl;

import java.util.Date;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSONArray;
import com.my.spring.DAO.ConstructionTaskNewDao;
import com.my.spring.DAO.DepartmentDao;
import com.my.spring.DAO.DepartmentUserDao;
import com.my.spring.DAO.ItemDataDao;
import com.my.spring.DAO.MechanicDao;
import com.my.spring.DAO.MechanicPriceDao;
import com.my.spring.DAO.MenuListDao;
import com.my.spring.DAO.MessageDao;
import com.my.spring.DAO.ProcessDataDao;
import com.my.spring.DAO.ProcessItemDao;
import com.my.spring.DAO.ProcessLogDao;
import com.my.spring.DAO.ProjectDao;
import com.my.spring.DAO.ProjectTenderDao;
import com.my.spring.DAO.QualityQuestionDao;
import com.my.spring.DAO.QuestionDao;
import com.my.spring.DAO.RoleDao;
import com.my.spring.DAO.SignUserInfoDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.DAO.UserProjectDao;
import com.my.spring.DAO.UserTeamDao;
import com.my.spring.controller.UserAvatar;
import com.my.spring.enums.CallStatusEnum;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.example.jsms.api.JSMSExample;
import com.my.spring.model.AllItemData;
import com.my.spring.model.AppLoginBackInfo;
import com.my.spring.model.ConstructionTaskNew;
import com.my.spring.model.ConstructionTaskNewPojo;
import com.my.spring.model.DepartmentUser;
import com.my.spring.model.Files;
import com.my.spring.model.ItemNodeList;
import com.my.spring.model.Mechanic;
import com.my.spring.model.MenuListCopy;
import com.my.spring.model.ProcessLog;
import com.my.spring.model.ProcessLogPojo;
import com.my.spring.model.Project;
import com.my.spring.model.ProjectTender;
import com.my.spring.model.Role;
import com.my.spring.model.SignUserInfo;
import com.my.spring.model.User;
import com.my.spring.model.UserCopy;
import com.my.spring.model.UserProject;
import com.my.spring.model.UserSelect;
import com.my.spring.model.UserSelectPojo;
import com.my.spring.model.UserTeam;
import com.my.spring.model.UserWebPojo;
import com.my.spring.model.UserLog;
import com.my.spring.model.UserPadPojo;
import com.my.spring.model.UserPojo;
import com.my.spring.parameters.Parameters;
import com.my.spring.parameters.ProjectDatas;
import com.my.spring.service.FileService;
import com.my.spring.service.MenuListService;
import com.my.spring.service.UserLogService;
import com.my.spring.service.UserService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.MD5Util;
import com.my.spring.utils.PhoneFormatCheckUtils;
import com.my.spring.utils.SessionManager;



@Service("userService")
public class UserServiceImpl implements UserService {
	private final String salt = "嘉实安装";
	
	@Autowired
	UserDao userDao;
	@Autowired
	UserProjectDao userProjectDao;
	@Autowired
	UserLogService userLogService;
	@Autowired
	ProjectDao projectDao;
	@Autowired
	SignUserInfoDao signDao;
	@Autowired
	RoleDao roleDao;
	@Autowired
	DepartmentDao deparmentDao;
	@Autowired
	MenuListDao menuDao;
	@Autowired
	QuestionDao questionDao;
	@Autowired
	QualityQuestionDao qualityDao;
	@Autowired
	MessageDao messageDao;
	@Autowired
	MenuListService menuService;
	@Autowired
	FileService fileService;
	@Autowired
    MechanicDao mechanicDao;
    @Autowired
    MechanicPriceDao mechanicPriceDao;
    @Autowired
    ConstructionTaskNewDao constructionTasknewDao;
    @Autowired
    DepartmentUserDao departmentUserDao;
    @Autowired
    ProcessDataDao processDataDao;
    @Autowired
    ProjectTenderDao projectTenderDao;
    @Autowired
    ProcessLogDao processLogDao;
    @Autowired
    ItemDataDao itemDataDao;
    @Autowired
    ProcessItemDao processItemDao;
    @Autowired
    ProcessLogDao processDataLogDao;
    @Autowired
    ConstructionTaskNewDao constructionTaskNewDao;
    @Autowired
    UserTeamDao userTeamDao;
	private String filePath="files";
    private Integer fileType=5;
    private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	@Override
	public DataWrapper<Void> register(User user,HttpServletRequest request) {
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
			user.setRoleId((long)4);
			user.setPassword(MD5Util.getMD5String(MD5Util.getMD5String(user.getPassword()) + salt));
			user.setUserName(user.getTel());
			//user.setUserType(UserTypeEnum.User.getType());
			//user.setUserType(user.getUserType());
			if(user.getRealName()!=null){
				Files file=UserAvatar.CreateUserIcon(user.getRealName(),request,"files/userIcons");
				if(file!=null){
					user.setUserIcon(file.getId());
				}
			}
			user.setRegisterDate(new Date());
			if(!userDao.addUser(user)) {
				dataWrapper.setErrorCode(ErrorCodeEnum.Error);
			}
			
		}
		return dataWrapper;
	}

	/**
	 * 需求:多端独立登录、登出，互不影响
	 * 方案：1、
	 * */
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
				if(system!=null){
					UserLog userLog = new UserLog();
					userLog.setActionDate(new Date());
					userLog.setSystemType(system);
					userLog.setUserId(user.getId());
					userLog.setActionType(2);//浏览、增加、登录、修改
					userLog.setProjectPart(ProjectDatas.Login_area.getCode());
					user.setSystemId(system);
				}else{
					user.setSystemId(2);
				}
				SessionManager.removeSessionByUserIdAndSystemId(user.getId(),system);
				String token = SessionManager.newSession(user);
				//String token = SessionManager.getSessionByUserID(user.getId());
				//String token = SessionManager.getSessionByUserIDAndSystem(user.getId(),user.getSystemId());
				/*if(token==null){
					token = SessionManager.newSession(user);
				}*/
				dataWrapper.setToken(token);
				UserPojo users=new UserPojo();
				if(user.getMenuItemList()!=null){
					users.setMenuItemList(user.getMenuItemList().split(","));
				}
				if(user.getUserType()==0 || user.getUserType()==1){
					Integer pageSize=10;
					Integer pageIndex=-1;
					Project projectsb = new Project();
					DataWrapper<List<Project>> projects=projectDao.getProjectList(pageSize, pageIndex, projectsb,null,2,null);
					String[] projectName = new String[projects.getData().size()];
					String[] projectList = new String[projects.getData().size()];
					for(int i=0;i<projects.getData().size();i++){
						projectName[i]=projects.getData().get(i).getName();
					}
					users.setProjectName(projectName);
					for(int i=0;i<projects.getData().size();i++){
						projectList[i]=projects.getData().get(i).getId().toString();
					}
					users.setProjectLists(projectList);
				}else{
					Integer pageSize=10;
					Integer pageIndex=-1;
					List<UserProject> up = new ArrayList<UserProject>();
					up=userProjectDao.getUserProjectListByUserId(user.getId());
					Project projectsb = new Project();
					DataWrapper<List<Project>> projects=projectDao.getProjectList(pageSize, pageIndex, projectsb,null,2,up);
				}
				if(user.getRoleId()!=null){
					Role role = roleDao.getById(user.getRoleId());
					if(role!=null){
						users.setWorkName(role.getName());
					}
				}
				users.setUserName(user.getUserName());
				users.setRealName(user.getRealName());
				users.setUserType(user.getUserType());
				users.setId(user.getId());
				users.setCompany(user.getCompany());
				if(user.getUserIcon()!=null){
					Files file=new Files();
					file=fileService.getById(user.getUserIcon());
					if(file!=null){
						users.setUserIconUrl(file.getUrl());
					}
				}
				if(user.getUserIconUrl()!=null){
					users.setUserIconUrl(user.getUserIconUrl());
				}
				dataWrapper.setData(users);
			}
		}
		
		return dataWrapper;
	}

	@Override
	public DataWrapper<AppLoginBackInfo> updateUser(User user, String token) {
		// TODO Auto-generated method stub
		DataWrapper<AppLoginBackInfo> dataWrapper = new DataWrapper<AppLoginBackInfo>();
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
				if(user.getMenuItemList()!=null){
					userInDB.setMenuItemList(user.getMenuItemList());
				}
				if(user.getCompany()!=null){
					userInDB.setCompany(user.getCompany());
				}
				if(user.getWorkName()!=null){
					userInDB.setWorkName(user.getWorkName());
				}
				if (!userDao.updateUser(userInDB)) {
					dataWrapper.setErrorCode(ErrorCodeEnum.Error);
				}else{
					AppLoginBackInfo back = new AppLoginBackInfo();
					back.setId(userInDB.getId());
					back.setRealName(userInDB.getRealName());
					back.setUserName(userInDB.getUserName());
					back.setUserType(userInDB.getUserType());
					back.setWorkName(userInDB.getWorkName());
					back.setIconUrl(userInDB.getUserIconUrl());
					dataWrapper.setData(back);
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
	public DataWrapper<String> updateUserByAdmin(User user, String token,MultipartFile file,HttpServletRequest request,String projectList) {
		// TODO Auto-generated method stub
		DataWrapper<String> dataWrapper = new DataWrapper<String>();
		User adminInMemory = SessionManager.getSession(token);
		if (adminInMemory != null) {
			Long userId=user.getId();
			Long localUserID=adminInMemory.getId();
			if((adminInMemory.getUserType() == 0) || (userId.equals(localUserID))){
				User userInDB = userDao.getById(user.getId());
				if (userInDB != null){
					if(projectList!=null){
						if(!projectList.equals("") && projectList!=null){
							String[] projectids = projectList.split(",");
							if(userProjectDao.deleteUserProjectByUserId(user.getId())){
								List<UserProject> addList = new ArrayList<UserProject>();
								for(int i=0;i<projectids.length;i++){
									UserProject up = new UserProject();
									up.setProjectId(Long.valueOf(projectids[i]));
									up.setUserId(user.getId());
									addList.add(up);
								}
								if(!addList.isEmpty()){
									userProjectDao.addUserProjectList(addList);
								}
							}else{
								List<UserProject> addList = new ArrayList<UserProject>();
								for(int i=0;i<projectids.length;i++){
									UserProject up = new UserProject();
									up.setProjectId(Long.valueOf(projectids[i]));
									up.setUserId(user.getId());
									addList.add(up);
								}
								if(!addList.isEmpty()){
									userProjectDao.addUserProjectList(addList);
								}
							}
							
						}
					}
					if(file !=null){
							String path=filePath+"/"+"userIcons";
							Files newfile=fileService.uploadFile(path, file,fileType,request);
							userInDB.setUserIcon(newfile.getId());
							userInDB.setUserIconUrl(newfile.getUrl());
					}
					if(user.getUserName() != null && !user.getUserName().equals("")) {
						if(!user.getUserName().equals(userInDB.getUserName())){
							userInDB.setUserName(user.getUserName());
						}
					}
					if(user.getPassword()!=null){
						if(!user.getPassword().equals(userInDB.getPassword())){
							userInDB.setPassword(MD5Util.getMD5String(MD5Util.getMD5String(user.getPassword()) + salt));
						}
					}
					if(user.getRealName() != null && !user.getRealName().equals("")) {
						if(!user.getRealName().equals(userInDB.getRealName())){
							userInDB.setRealName(user.getRealName());
						}
					}
					if(user.getSystemType()!=null){
						if(!user.getSystemType().equals(userInDB.getSystemType())){
							userInDB.setSystemType(user.getSystemType());
						}
					}
					if(user.getEmail() != null && !user.getEmail().equals("")) {
						if(!user.getEmail().equals(userInDB.getEmail())){
							userInDB.setEmail(user.getEmail());
						}
					}
					if (user.getTel() != null && !user.getTel().equals("")) {
						if(!user.getTel().equals(userInDB.getTel())){
							userInDB.setTel(user.getTel());
						}
					}
					if(user.getUserType() !=null && !user.getUserType().equals(""))
					{
						if(!user.getUserType().equals(userInDB.getUserType())){
							userInDB.setUserType(user.getUserType());
						}
					}
					if(user.getTeamId()!=null && !user.getTeamId().equals("")){
						if(!user.getTeamId().equals(userInDB.getTeamId())){
							userInDB.setTeamId(user.getTeamId());
						}
					}
					if(user.getWorkName()!=null && !user.getWorkName().equals("")){
						if(!user.getWorkName().equals(userInDB.getWorkName())){
							userInDB.setWorkName(user.getWorkName());
						}
					}
					if(user.getDepartmentId()!=null && !user.getDepartmentId().equals("")){
						if(!user.getDepartmentId().equals(userInDB.getDepartmentId())){
							userInDB.setDepartmentId(user.getDepartmentId());
						}
					}
					if (!userDao.updateUser(userInDB)) {
						dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					}else{
						dataWrapper.setData(userInDB.getUserIconUrl());
					}
				} else {
					dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Existed);
				}
			}else {
				dataWrapper.setErrorCode(ErrorCodeEnum.AUTH_Error);
			}
			
		} else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
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
			if(adminInMemory.getSystemId()!=null){
				UserLog userLog = new UserLog();
    			userLog.setProjectPart(ProjectDatas.UserTeam_area.getCode());
    			userLog.setActionDate(new Date());
    			userLog.setActionType(0);
    			userLog.setUserId(adminInMemory.getId());
    			userLog.setSystemType(adminInMemory.getSystemId());
    			//userLog.setVersion("3.0");
    			userLogService.addUserLog(userLog,token);
    		}
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
			if(userInMemory.getSystemId()!=null){
				UserLog userLog = new UserLog();
    			userLog.setProjectPart(ProjectDatas.UserTeam_area.getCode());
    			userLog.setActionDate(new Date());
    			userLog.setActionType(0);
    			userLog.setUserId(userInMemory.getId());
    			userLog.setSystemType(userInMemory.getSystemId());
    			//userLog.setVersion("3.0");
    			userLogService.addUserLog(userLog,token);
    		}
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
			if(userId!=null){
				adminInDB=userDao.getById(userId);
				dataWrapper.setData(adminInDB);
				if(dataWrapper.getData()!=null){
					UserPojo userpojo=new UserPojo();
					userpojo.setSystemType(dataWrapper.getData().getSystemType());
					userpojo.setEmail(dataWrapper.getData().getEmail());
					userpojo.setId(dataWrapper.getData().getId());
					userpojo.setPassword(dataWrapper.getData().getPassword());
					userpojo.setRealName(dataWrapper.getData().getRealName());
					userpojo.setRegisterDate(sdf.format(dataWrapper.getData().getRegisterDate()));
					userpojo.setTel(dataWrapper.getData().getTel());
					userpojo.setUserName(dataWrapper.getData().getUserName());
					userpojo.setUserType(dataWrapper.getData().getUserType());
					userpojo.setWorkName(dataWrapper.getData().getWorkName());
					userpojo.setUserIcon(dataWrapper.getData().getUserIcon());
					if(dataWrapper.getData().getUserIcon()!=null){
						Files file=new Files();
						file=fileService.getById(dataWrapper.getData().getUserIcon());
						if(file!=null){
							userpojo.setUserIconUrl(file.getUrl());
						}
					}
					dataWrappers.setData(userpojo);
				}
			}else{
				dataWrappers.setErrorCode(ErrorCodeEnum.Empty_Inputs);
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
			//if (adminInMemory.getUserType() == UserTypeEnum.Admin.getType() || adminInMemory.getTeamId()!=null || adminInMemory.getWorkName().equals("总经理")) {
			if(adminInMemory.getUserType()==0){
				userList=userDao.getUserList(pageSize, pageIndex,user);
			}else{
				userList=userDao.getUserLists(pageSize, pageIndex,user);
			}
			
			if(userList.getData().size()>0){
				for(int i=0;i<userList.getData().size();i++){
					UserPojo userpojo=new UserPojo();
					userpojo.setSystemType(userList.getData().get(i).getSystemType());
					userpojo.setEmail(userList.getData().get(i).getEmail());
					userpojo.setId(userList.getData().get(i).getId());
					userpojo.setPassword(userList.getData().get(i).getPassword());
					userpojo.setRealName(userList.getData().get(i).getRealName());
					userpojo.setTel(userList.getData().get(i).getTel());
					userpojo.setRegisterDate(sdf.format(userList.getData().get(i).getRegisterDate()));
					userpojo.setUserName(userList.getData().get(i).getUserName());
					userpojo.setUserType(userList.getData().get(i).getUserType());
					userpojo.setUserIcon(userList.getData().get(i).getUserIcon());
					userpojo.setDepartmentId(userList.getData().get(i).getDepartmentId());
					userpojo.setTeamId(userList.getData().get(i).getTeamId());
					userpojo.setRoleId(userList.getData().get(i).getRoleId());
					userpojo.setWorkName(userList.getData().get(i).getWorkName());
					if(userList.getData().get(i).getMenuItemList()!=null){
						userpojo.setMenuItemList(userList.getData().get(i).getMenuItemList().split(","));
					}
					if(userList.getData().get(i).getUserIcon()!=null){
						Files file=fileService.getById(userList.getData().get(i).getUserIcon());
						if(file!=null){
							userpojo.setUserIconUrl(file.getUrl());
						}
					}
					List<UserProject> ups = userProjectDao.getUserProjectListByUserId(userList.getData().get(i).getId());
					if(!ups.isEmpty()){
						String projectLists="";
						for(int q=0;q<ups.size();q++){
							if(projectLists.equals("")){
								projectLists=ups.get(q).getProjectId()+"";
							}else{
								projectLists=projectLists+","+ups.get(q).getProjectId();
							}
						}
						userpojo.setProjectList(projectLists);
					}
					if(userList.getData().get(i).getUserIconUrl()!=null){
						userpojo.setUserIconUrl(userList.getData().get(i).getUserIconUrl());
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
				
			/*} else {
				dataWrapper.setErrorCode(ErrorCodeEnum.AUTH_Error);
			}*/
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dataWrapper;
	}
	@Override
	public DataWrapper<List<UserPojo>> getUserLists(Integer pageIndex, Integer pageSize, User user, String token) {
		// TODO Auto-generated method stub
		DataWrapper<List<UserPojo>> dataWrapper = new DataWrapper<List<UserPojo>>();
		List<UserPojo> userpojoList=new ArrayList<UserPojo>();
		DataWrapper<List<User>> userList=new DataWrapper<List<User>>();
		User adminInMemory = SessionManager.getSession(token);
		if (adminInMemory != null) {
			userList=userDao.getUserLists(pageSize, pageIndex,user);
			if(userList.getData().size()>0){
				for(int i=0;i<userList.getData().size();i++){
					UserPojo userpojo=new UserPojo();
					userpojo.setSystemType(userList.getData().get(i).getSystemType());
					userpojo.setEmail(userList.getData().get(i).getEmail());
					userpojo.setId(userList.getData().get(i).getId());
					userpojo.setPassword(userList.getData().get(i).getPassword());
					userpojo.setRealName(userList.getData().get(i).getRealName());
					userpojo.setTel(userList.getData().get(i).getTel());
					userpojo.setRegisterDate(sdf.format(userList.getData().get(i).getRegisterDate()));
					userpojo.setUserName(userList.getData().get(i).getUserName());
					userpojo.setUserType(userList.getData().get(i).getUserType());
					userpojo.setUserIcon(userList.getData().get(i).getUserIcon());
					if(userList.getData().get(i).getMenuItemList()!=null){
						userpojo.setMenuItemList(userList.getData().get(i).getMenuItemList().split(","));
					}
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
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dataWrapper;
	}
	@Override
	public DataWrapper<List<UserPadPojo>> getUserTeam(String token,Long projectId) {
		// TODO Auto-generated method stub
		DataWrapper<List<UserPadPojo>> dataWrapper = new DataWrapper<List<UserPadPojo>>();
		List<UserPadPojo> userpojoList=new ArrayList<UserPadPojo>();
		DataWrapper<List<UserCopy>> userList=new DataWrapper<List<UserCopy>>();
		User adminInMemory = SessionManager.getSession(token);
		if (adminInMemory != null) {
				userList=userDao.getUserTeam(projectId);
				if(userList.getData().size()>0){
					for(int i=0;i<userList.getData().size();i++){
						UserPadPojo userpadpojo=new UserPadPojo();
						userpadpojo.setRealName(userList.getData().get(i).getRealName());
						userpadpojo.setTel(userList.getData().get(i).getTel());
						userpadpojo.setId(userList.getData().get(i).getId());
						if(userList.getData().get(i).getUserIconUrl()!=null){
							userpadpojo.setUserIcon(userList.getData().get(i).getUserIconUrl());
						}else{
							if(userList.getData().get(i).getUserIcon()!=null){
								Files file = fileService.getById(userList.getData().get(i).getUserIcon());
								if(file!=null){
									userpadpojo.setUserIcon(file.getUrl());
								}
							}
						}
						userpojoList.add(userpadpojo);
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
	public DataWrapper<Void> deleteUser(Long userId, String token,String userIdList) {
		DataWrapper<Void> dataWrapper=new DataWrapper<Void>();
		User userInMemory=SessionManager.getSession(token);
		if(userInMemory!=null){
			User adminInDB = userDao.getById(userInMemory.getId());
			if (adminInDB.getUserType() == UserTypeEnum.Admin.getType()) {
				if(userIdList!=null){
					String[] ids = userIdList.split(",");
					if(userDao.deleteUserList(ids)){
						dataWrapper.setErrorCode(ErrorCodeEnum.No_Error);
					}else{
						dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
					}
				}else{
 				    messageDao.deleteMessageByUserId(userId);
 				    questionDao.deleteQuestionByUserId(userId);
 				    qualityDao.deleteQualityQuestionByUserId(userId);
					if(userDao.deleteUser(userId)){
						dataWrapper.setErrorCode(ErrorCodeEnum.No_Error);
					}else{
						dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
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
	public DataWrapper<Void> addUser(User user,String token,MultipartFile file, HttpServletRequest request,String projectList) {
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
						user.setUserIconUrl(newfile.getUrl());
					}
					if(user.getRealName()!=null && file==null){
						String names=user.getRealName().substring(user.getRealName().length()-2, user.getRealName().length());
						String url=UserAvatar.CreateUserIcon(names);
						if(url!=null){
							user.setUserIconUrl(url);
						}
					}
					user.setId(null);
					user.setPassword(MD5Util.getMD5String(MD5Util.getMD5String(user.getPassword()) + salt));
					//user.setUserType(user.getUserType());
					user.setRegisterDate(new Date());
					user.setMenuItemList("0,1,2,3,4,5,6,7,8");
					if(!userDao.addUser(user)) {
						dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					}else{
						if(!projectList.equals("") && projectList!=null){
							String[] projectids = projectList.split(",");
							for(int i=0;i<projectids.length;i++){
								UserProject up = new UserProject();
								up.setProjectId(Long.valueOf(projectids[i]));
								up.setUserId(user.getId());
								userProjectDao.addUserProject(up);
							}
						}
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

	@Override
	public DataWrapper<Object> getUserInfo(String token) {
		DataWrapper<Object> userPojo = new DataWrapper<Object>();
		UserPojo userpo = new UserPojo();
		User userInMeMory = SessionManager.getSession(token);
		if(userInMeMory!=null){
			userpo.setEmail(userInMeMory.getEmail());
			if(userInMeMory.getUserIconUrl()!=null){
				userpo.setUserIconUrl(userInMeMory.getUserIconUrl());
			}else if(userInMeMory.getUserIcon()!=null){
				Files file = fileService.getById(userInMeMory.getUserIcon());
				userpo.setUserIconUrl(file.getUrl());
			}else{
				UserAvatar avatar = new UserAvatar();
				userpo.setUserIconUrl(avatar.CreateUserIcon("游客"));
			}
			userpo.setId(userInMeMory.getId());
			userpo.setRealName(userInMeMory.getRealName());
			userpo.setRegisterDate(sdf.format(userInMeMory.getRegisterDate()));
			userpo.setUserName(userInMeMory.getUserName());
			userpo.setUserType(userInMeMory.getUserType());
			userpo.setTel(userInMeMory.getTel());
			if(userInMeMory.getRoleId()!=null){
				Role role = roleDao.getById(userInMeMory.getRoleId());
				if(role!=null){
					userpo.setReadState(role.getReadState());
					List<MenuListCopy> menu = menuDao.getMenuListByIdList(role.getMenuList().split(",")).getData();
					if(menu!=null){
						if(menu.size()>0){
							String menuList=menuService.getMenuListMapByIdList(menu).getData();
							if(menuList!=null){
								userpo.setMenuList(JSONArray.parse(menuList));
							}
						}
					}
				}
			}
			userPojo.setData(userpo);
		}else{
			userPojo.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return userPojo;
	}

	@Override
	public DataWrapper<Void> getIdentifyingCode(String mobile,Integer systemType) {
		// TODO Auto-generated method stub
		DataWrapper<Void> codeResult = new DataWrapper<Void>();
		PhoneFormatCheckUtils pc = null;
		if(pc.isPhoneLegal(mobile)){
			User user=userDao.getByUserTel(mobile);
			if(user!=null){
				codeResult.setErrorInfo(ErrorCodeEnum.Phone_Existed_Error.getLabel());
				codeResult.setErrorCode(ErrorCodeEnum.Error);
			}else{
				String identifyingCode=String.valueOf(new Random().nextInt(899999) + 100000);
				if(JSMSExample.testSendBatchTemplateSMS(mobile,identifyingCode)){
					SignUserInfo newUser = new SignUserInfo();
					newUser.setCode(identifyingCode);
					newUser.setMobile(mobile);
					newUser.setRegisterDate(new Date());
					newUser.setSystemType(systemType);
					signDao.addSignUserInfo(newUser);
					codeResult.setCallStatus(CallStatusEnum.SUCCEED);
				}
			}
		}else{
			codeResult.setErrorInfo(ErrorCodeEnum.Phone_Format_Error.getLabel());
			codeResult.setErrorCode(ErrorCodeEnum.Error);
		}
		return codeResult;
	}

	@Override
	public DataWrapper<Void> getIdentifyingInfo(String mobile, String code) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		DataWrapper<List<SignUserInfo>> newUserList = new DataWrapper<List<SignUserInfo>>();
		if(code!=null && mobile!=null){
			SignUserInfo newUser = new SignUserInfo();
			newUser.setMobile(mobile);
			newUser.setCode(code);
			newUserList=signDao.getSignUserInfoList(10, -1, newUser);
			if(newUserList.getData()!=null && newUserList.getData().size()>0){
				Date ss = newUserList.getData().get(0).getRegisterDate();
				Date end= new Date();
				long between=(end.getTime()-ss.getTime())/1000;//除以1000是为了转换成秒
				if(between>0 && between<297){
					result.setCallStatus(CallStatusEnum.SUCCEED);
				}else{
					result.setErrorInfo(ErrorCodeEnum.Phone_Time_Error.getLabel());
					result.setErrorCode(ErrorCodeEnum.Error);
				}
			}else{
				result.setErrorInfo(ErrorCodeEnum.Phone_Code_Error.getLabel());
				result.setErrorCode(ErrorCodeEnum.Error);
			}
		}else{
			result.setErrorInfo(ErrorCodeEnum.Empty_Inputs.getLabel());
			result.setErrorCode(ErrorCodeEnum.Error);
		}
		return result;
	}
	@Override
	public DataWrapper<Void> registerUserInfo(User user) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		if(user!=null){
			if(user.getTel()!=null){
				User oldUser = userDao.getByUserTel(user.getTel());
				if(oldUser!=null){
					result.setErrorCode(ErrorCodeEnum.Phone_Existed_Error);
					return result;
				}
			}
			user.setUserType(UserTypeEnum.Visitor.getType());
			
			user.setRegisterDate(new Date());
			user.setPassword(MD5Util.getMD5String(MD5Util.getMD5String(user.getPassword()) + salt));
			user.setMenuItemList("0,1,2,3,4,5,6,7,8");
			if(user.getRealName()!=null){
				String names=user.getRealName().substring(user.getRealName().length()-2, user.getRealName().length());
				String url=UserAvatar.CreateUserIcon(names);
				if(url!=null){
					user.setUserIconUrl(url);
				}
			}
			
			if(!userDao.addUser(user)){
				result.setErrorCode(ErrorCodeEnum.Error);
			}else{
				result.setToken(SessionManager.newSession(user));
			}
			
		}else{
			result.setErrorCode(ErrorCodeEnum.Empty_Inputs);
		}
		return result;
	}

	@Override
	public DataWrapper<Void> getUserInfoSql(User user) {
		// TODO Auto-generated method stub
		DataWrapper<List<User>> test=userDao.getUserListForSql(null, null, user);
		List<String> userIdList= new ArrayList<String>();
		List<String> projectIdList= new ArrayList<String>();
		String[] str = new String[userIdList.size()];
		List<UserProject> up = new ArrayList<UserProject>();
		for(int k=0;k<userIdList.size();k++){
			UserProject strs = new UserProject();
			strs.setUserId(Long.valueOf(userIdList.get(k)));
			strs.setProjectId(Long.valueOf(projectIdList.get(k)));
			up.add(strs);
		}
		userProjectDao.addUserProjectList(up);
		System.out.println("userIdLength:"+userIdList.size());
		System.out.println("projectIdLength:"+projectIdList.size());
		
		return null;
	}

	@Override
	public DataWrapper<List<UserSelectPojo>> getUserByWorkName(String token, String workName,Long projectId) {
		// TODO Auto-generated method stub
		DataWrapper<List<UserSelectPojo>> result = new DataWrapper<List<UserSelectPojo>>();
		List<UserSelectPojo> results = new ArrayList<UserSelectPojo>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			List<UserSelect> gets = userDao.getUserListByWorkName(workName,projectId);
			if(!gets.isEmpty()){
				for(UserSelect us:gets){
					UserSelectPojo usp = new UserSelectPojo();
					usp.setId(us.getId());
					usp.setRealName(us.getReal_name());
					if(usp!=null){
						results.add(usp);
					}
				}
				result.setData(results);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		if(result.getCallStatus()==CallStatusEnum.SUCCEED && result.getData()==null){
	       	List<UserSelectPojo> pas= new ArrayList<UserSelectPojo>();
	       	result.setData(pas);
	    }
		return result;
		
	}

	@Override
	public List<Long> getAllUserIdList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataWrapper<Void> loginOut(Long id, String token, Integer systemId) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(user.getId().equals(id)){
				SessionManager.removeSession(token);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<List<UserWebPojo>> getUserByProjectIds(String token, String projectIds,String flag) {
		DataWrapper<List<UserWebPojo>> dataWrapper = new DataWrapper<List<UserWebPojo>>();
		List<UserWebPojo> userpojoList=new ArrayList<UserWebPojo>();
		DataWrapper<List<UserCopy>> userList=new DataWrapper<List<UserCopy>>();
		User adminInMemory = SessionManager.getSession(token);
		if (adminInMemory != null) {
			if(adminInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				userList=userDao.getUserByProjectIds(projectIds,flag);
				if(!userList.getData().isEmpty()){
					for(int i=0;i<userList.getData().size();i++){
						UserWebPojo userpadpojo=new UserWebPojo();
						userpadpojo.setRealName(userList.getData().get(i).getRealName());
						userpadpojo.setId(userList.getData().get(i).getId());
						userpojoList.add(userpadpojo);
					}
					dataWrapper.setData(userpojoList);
					dataWrapper.setCallStatus(userList.getCallStatus());
					dataWrapper.setCurrentPage(userList.getCurrentPage());
					dataWrapper.setErrorCode(userList.getErrorCode());
					dataWrapper.setNumberPerPage(userList.getNumberPerPage());
					dataWrapper.setTotalNumber(userList.getTotalNumber());
					dataWrapper.setTotalPage(userList.getTotalPage());
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
	public DataWrapper<List<ProcessLogPojo>> getProcessLogByConstructionId(String token, Long id, Long processDataId) {
		DataWrapper<List<ProcessLogPojo>> result = new DataWrapper<List<ProcessLogPojo>>();
		List<ProcessLogPojo> resultList = new ArrayList<ProcessLogPojo>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			List<AllItemData> itemList=processItemDao.getProcessItemListByProcessId(processDataId);
			List<ProcessLog> gets = processLogDao.getProcessLogByAboutIds(processDataId,id);//获取待修改的节点审批记录
			List<ProcessLog> afterGets = new ArrayList<ProcessLog>();
			ConstructionTaskNew ct = constructionTaskNewDao.getById(id);
			if(ct==null){
				result.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
				return result;
			}
			if(!gets.isEmpty()){
				afterGets=processLogDao.getProcessLogListByInfos(id, gets.get(0).getId());//获取最新的待修改节点审批记录后面的审批记录
			}
			if(!gets.isEmpty() && !afterGets.isEmpty()){
				for(int j=0;j<itemList.size();j++){
					ProcessLogPojo plp = new ProcessLogPojo();
					plp.setId(itemList.get(j).getId());
					plp.setCurrentNode(itemList.get(j).getWhich());
					plp.setEndFlag(0);
					plp.setProcessId(processDataId);
					plp.setItemName(itemList.get(j).getName());
					User user = userDao.getById(itemList.get(j).getApprove_user());
					if(user!=null){
						plp.setApproveUser(user.getRealName());
						plp.setApproveUserId(itemList.get(j).getApprove_user());
					}
					if(j==0){
						plp.setNextApproveUser(itemList.get(0).getName());
					}
					plp.setProcessId(processDataId);
					for(int i=0;i<afterGets.size();i++){
						if(afterGets.get(i).getCurrentNode()==itemList.get(j).getWhich()){
							plp.setNote(afterGets.get(i).getNote());
							plp.setItemState(afterGets.get(i).getItemState());
							plp.setCurrentNode(afterGets.get(i).getCurrentNode());
							plp.setApproveDate(Parameters.getSdf().format(afterGets.get(i).getCreateDate()));
							if(afterGets.get(i).getItemState()==1){
								plp.setNextApproveUser(itemList.get(0).getName());
								plp.setEndFlag(2);
							}else if(j<(itemList.size()-1)){
								plp.setNextApproveUser(itemList.get(j+1).getName());
								plp.setEndFlag(0);
							}else if(j==(itemList.size()-1)){
								plp.setEndFlag(1);
							}
						}
					}
					if(plp!=null){
						resultList.add(plp);
					}
					result.setData(resultList);
				}
			}else{
				
				for(int j=0;j<itemList.size();j++){
					ProcessLogPojo plp = new ProcessLogPojo();
					plp.setId(itemList.get(j).getId());
					plp.setCurrentNode(itemList.get(j).getWhich());
					plp.setEndFlag(0);
					plp.setProcessId(processDataId);
					plp.setItemName(itemList.get(j).getName());
					User user = userDao.getById(itemList.get(j).getApprove_user());
					if(user!=null){
						plp.setApproveUser(user.getRealName());
						plp.setApproveUserId(itemList.get(j).getApprove_user());
					}
					if(j==0){
						plp.setNextApproveUser(itemList.get(0).getName());
					}
					if(ct.getUpdateDate()!=null){
						if(ct.getUpdateDate().split(",").length!=gets.size()){
							ProcessLog processLog = new ProcessLog();
							processLog=processLogDao.getProcessLogByItemDataId(itemList.get(j).getId(),id);
							if(processLog!=null){
								plp.setNote(processLog.getNote());
								plp.setItemState(processLog.getItemState());
								plp.setCurrentNode(processLog.getCurrentNode());
								plp.setApproveDate(Parameters.getSdf().format(processLog.getCreateDate()));
								if(processLog.getItemState()==1){
									plp.setNextApproveUser(itemList.get(0).getName());
									plp.setEndFlag(2);
								}else if(j<(itemList.size()-1)){
									plp.setNextApproveUser(itemList.get(j+1).getName());
									plp.setEndFlag(0);
								}else if(j==(itemList.size()-1)){
									plp.setEndFlag(1);
								}
							}
						}
					}else{
						ProcessLog processLog = new ProcessLog();
						processLog=processLogDao.getProcessLogByItemDataId(itemList.get(j).getId(),id);
						if(processLog!=null){
							plp.setNote(processLog.getNote());
							plp.setItemState(processLog.getItemState());
							plp.setCurrentNode(processLog.getCurrentNode());
							plp.setApproveDate(Parameters.getSdf().format(processLog.getCreateDate()));
							if(processLog.getItemState()==1){
								plp.setNextApproveUser(itemList.get(0).getName());
								plp.setEndFlag(2);
							}else if(j<(itemList.size()-1)){
								plp.setNextApproveUser(itemList.get(j+1).getName());
								plp.setEndFlag(0);
							}else if(j==(itemList.size()-1)){
								plp.setEndFlag(1);
							}
						}
					}
					plp.setProcessId(processDataId);
					if(plp!=null){
						resultList.add(plp);
					}
					result.setData(resultList);
				}
			}
	
	
		
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}
	@Override
	public DataWrapper<List<ConstructionTaskNewPojo>> getConstructionTaskNewDetail(String token,
			ConstructionTaskNew constructionTaskNew) {
		// TODO Auto-generated method stub
		DataWrapper<List<ConstructionTaskNewPojo>> result = new DataWrapper<List<ConstructionTaskNewPojo>>();
		List<ConstructionTaskNewPojo> results = new ArrayList<ConstructionTaskNewPojo>();
		List<ConstructionTaskNew> gets = new ArrayList <ConstructionTaskNew>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			if(constructionTaskNew!=null){
				if(constructionTaskNew.getId()!=null){
				
					gets=constructionTaskNewDao.getConstructionTaskNewByIds(constructionTaskNew.getId());
					if(!gets.isEmpty()){
						/////查询已有记录的节点用户信息
						List<ItemNodeList> getNodese = constructionTaskNewDao.getAllItemLog(gets.get(0).getId());
						if(!gets.isEmpty()){
							/////任务单下面的流程节点的所有信息
							List<AllItemData> itemDataList = constructionTaskNewDao.getAllItemData(gets.get(0).getId());	
							
							for(int i=0;i<gets.size();i++){
								ConstructionTaskNewPojo ConstructionTaskNewPojo =new ConstructionTaskNewPojo();
								if(i==0){
									if(!getNodese.isEmpty()){
									////当前节点名称
										Integer currentNode=getNodese.get(getNodese.size()-1).getCurrent_node();
										ConstructionTaskNewPojo.setCurrentNodeName(itemDataList.get(currentNode-1).getName());
										
										///当前节点
										ConstructionTaskNewPojo.setCurrentNodeId(itemDataList.get(currentNode-1).getId());
										/////0、同意  1、不同意
										if(getNodese.get(getNodese.size()-1).getEnd_flag()==2){
											ConstructionTaskNewPojo.setStatus(2);////不同意，即待修改
											User user = userDao.getById(itemDataList.get(0).getApprove_user());///修改人为节点第一个人，即创建人
											if(user!=null){
												ConstructionTaskNewPojo.setApprovalUser(user.getRealName());///当前审批人的姓名
											}
										}else if(getNodese.get(getNodese.size()-1).getEnd_flag()==1){
											ConstructionTaskNewPojo.setStatus(1);///已完成，下一审批人为空
										}else if(getNodese.get(getNodese.size()-1).getEnd_flag()==0){
											ConstructionTaskNewPojo.setStatus(0);////不同意，即待修改
											User user = userDao.getById(itemDataList.get(currentNode-1).getApprove_user());///修改人为节点第一个人，即创建人
											if(user!=null){
												ConstructionTaskNewPojo.setApprovalUser(user.getRealName());///当前审批人的姓名
											}
										}
										ConstructionTaskNewPojo.setApprovalUserId(itemDataList.get(currentNode-1).getApprove_user());
									}else{
										//ConstructionTaskNewPojo.setApprovalUserId(itemDataList.get(0).getApprove_user());
										ConstructionTaskNewPojo.setStatus(0);
									}
								}
							ConstructionTaskNewPojo.setId(gets.get(i).getId());
							ConstructionTaskNewPojo.setConstructContent(gets.get(i).getConstructContent());
							ConstructionTaskNewPojo.setProcessDataId(gets.get(i).getProcessDataId());
							ConstructionTaskNewPojo.setPid(gets.get(i).getPid());
							ConstructionTaskNewPojo.setConstructionName(gets.get(i).getName());
							ConstructionTaskNewPojo.setName(gets.get(i).getName());
							ConstructionTaskNewPojo.setConstructionTaskDate(Parameters.getSdfs().format(gets.get(i).getConstructionTaskDate()));
							ConstructionTaskNewPojo.setConstructPart(gets.get(i).getConstructPart());
							////施工类型，前端写死筛选
							ConstructionTaskNewPojo.setConstructType(gets.get(i).getConstructType());
							ConstructionTaskNewPojo.setCreateDate(Parameters.getSdf().format(gets.get(i).getCreateDate()));
							ConstructionTaskNewPojo.setDayWorkHours(gets.get(i).getDayWorkHours());
							ConstructionTaskNewPojo.setTeamId(gets.get(i).getTeamId());
							if(gets.get(i).getTeamId()!=null){
								UserTeam userTeam = userTeamDao.getById(gets.get(i).getTeamId());
								if(userTeam!=null){
									ConstructionTaskNewPojo.setTeamName(userTeam.getTeamUserName());
								}
							}
							double dayHour=gets.get(i).getDayWorkHours();
							double nightHour=gets.get(i).getNightWorkHours();
							Double day = dayHour/10+nightHour/6;
							day = new BigDecimal(day).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
							ConstructionTaskNewPojo.setNightWorkHours(gets.get(i).getNightWorkHours());
							ConstructionTaskNewPojo.setProcessDataId(gets.get(i).getProcessDataId());
							ConstructionTaskNewPojo.setTeamType(gets.get(i).getTeamType());
							//ConstructionTaskNewPojo.setTeamUserIds(gets.get(i).getTeamUserIds());
							if(gets.get(i).getTeamType()==0){
								if(gets.get(i).getTeamUserIds()!=null){
									String usernames="";
									Double salary=0.0;
									String[] mechanicids = gets.get(i).getTeamUserIds().split(",");
									List<Long> userIdList = new ArrayList<Long>();
									for(int q=0;q<mechanicids.length;q++){
										userIdList.add(Long.valueOf(mechanicids[q]));
										Mechanic mechanic = mechanicDao.getMechanicById(Long.valueOf(mechanicids[q]));
										if(mechanic!=null){
											salary=salary+(mechanic.getDaySalary())*day;
											if(usernames.equals("")){
												usernames=mechanic.getRealName();
											}else{
												usernames=usernames+","+mechanic.getRealName();
											}
										}
									}
									ConstructionTaskNewPojo.setTeamUserIdList(userIdList);
									ConstructionTaskNewPojo.setSalary(new BigDecimal(salary).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
									ConstructionTaskNewPojo.setTeamUserIds(usernames);
								}
							}
							if(gets.get(i).getTeamType()==1){
								if(gets.get(i).getTeamUserIds()!=null){
									String usernames="";
									Double salary=0.0;
									String[] departmentUserIds = gets.get(i).getTeamUserIds().split(",");
									List<Long> userIdList = new ArrayList<Long>();
									for(int q=0;q<departmentUserIds.length;q++){
										userIdList.add(Long.valueOf(departmentUserIds[q]));
										DepartmentUser duser = departmentUserDao.getById(Long.valueOf(departmentUserIds[q]));
										if(duser!=null){
											salary=salary+(duser.getSalary())*day;
											if(usernames.equals("")){
												usernames=duser.getName();
											}else{
												usernames=usernames+","+duser.getName();
											}
										}
									}
									ConstructionTaskNewPojo.setTeamUserIdList(userIdList);
									ConstructionTaskNewPojo.setSalary(new BigDecimal(salary).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
									ConstructionTaskNewPojo.setTeamUserIds(usernames);
								}
							}
							
							ProjectTender tender = projectTenderDao.getProjectTenderById(gets.get(i).getTendersId());
							if(tender!=null){
								ConstructionTaskNewPojo.setTenders(tender.getName());
							}
							if(gets.get(i).getImgs()!=null){
								String[] imgs=gets.get(i).getImgs().split(",");
								if(!imgs[0].equals("")){
									ConstructionTaskNewPojo.setImgs(imgs);
								}else{
									ConstructionTaskNewPojo.setImgs(new String[0]);
								}
								
							}
							if(gets.get(i).getCreateUser()!=null){
								User user= new User();
								user=userDao.getById(gets.get(i).getCreateUser());
								if(user!=null){
									ConstructionTaskNewPojo.setCreateUser(user.getRealName());
								}
							}
							
							results.add(ConstructionTaskNewPojo);
						}
						}
						result.setData(results);
					}
				}
			}else{
				result.setErrorCode(ErrorCodeEnum.Empty_Inputs);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		if(result.getCallStatus()==CallStatusEnum.SUCCEED && result.getData()==null){
		       	List<ConstructionTaskNewPojo> pas= new ArrayList<ConstructionTaskNewPojo>();
		       	result.setData(pas);
		    }
		return result;
	}

	@Override
	public DataWrapper<Void> registerAppUserInfo(User user) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		if(user!=null){
			if(user.getTel()!=null){
				User oldUser = userDao.getByUserTel(user.getTel());
				if(oldUser!=null){
					result.setErrorCode(ErrorCodeEnum.Phone_Existed_Error);
					return result;
				}
			}
			user.setUserType(UserTypeEnum.Visitor.getType());
			user.setRegisterDate(new Date());
			user.setRoleId((long)1);
			user.setPassword(MD5Util.getMD5String(MD5Util.getMD5String(user.getPassword()) + salt));
			user.setMenuItemList("0,1,2,3,4,5,6,7,8");
			user.setUserName(user.getTel());
			if(user.getRealName()!=null){
				String names=user.getRealName().substring(user.getRealName().length()-2, user.getRealName().length());
				String url=UserAvatar.CreateUserIcon(names);
				if(url!=null){
					user.setUserIconUrl(url);
				}
			}else{
				String url=UserAvatar.CreateUserIcon("游客");
				if(url!=null){
					user.setUserIconUrl(url);
				}
			}
			if(!userDao.addUser(user)){
				result.setErrorCode(ErrorCodeEnum.Error);
			}else{
				result.setToken(SessionManager.newSession(user));
			}
			
		}else{
			result.setErrorCode(ErrorCodeEnum.Empty_Inputs);
		}
		return result;
	}

}
