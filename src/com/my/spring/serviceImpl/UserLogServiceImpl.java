package com.my.spring.serviceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.spring.DAO.UserLogDao;
import com.my.spring.DAO.ValueOutputDao;
import com.my.spring.DAO.VideoDao;
import com.my.spring.DAO.AdvancedOrderDao;
import com.my.spring.DAO.DuctDao;
import com.my.spring.DAO.ItemDao;
import com.my.spring.DAO.PaperDao;
import com.my.spring.DAO.ProjectDao;
import com.my.spring.DAO.QuestionDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.CallStatusEnum;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.UserLog;
import com.my.spring.model.UserLogCount;
import com.my.spring.model.UserLogMonth;
import com.my.spring.model.UserLogPart;
import com.my.spring.model.UserLogPojo;
import com.my.spring.model.UserLogPojos;
import com.my.spring.parameters.Parameters;
import com.my.spring.parameters.ProjectDatas;
import com.my.spring.model.User;
import com.my.spring.service.UserLogService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.FileOperationsUtil;
import com.my.spring.utils.ReflectTest;
import com.my.spring.utils.SessionManager;
import com.my.spring.utils.UserLogAllToExcel;
import com.my.spring.utils.UserLogEchartsToExcel;


@Service("userLogService")
public class UserLogServiceImpl implements UserLogService {
	@Autowired
	UserLogDao userLogDao;
	@Autowired
	UserDao userDao;
	@Autowired
	ProjectDao projectDao;
	@Autowired
	PaperDao paperDao;
	@Autowired
	ItemDao itemDao;
	@Autowired
	DuctDao ductDao;
	@Autowired
	ValueOutputDao valueOutputDao;

	@Autowired
	QuestionDao questionDao;
	@Autowired
	VideoDao videoDao;

	@Autowired
	AdvancedOrderDao advancedOrderDao;
	
	@Override
	public DataWrapper<List<UserLogPojo>> getUserLogList(Integer pageIndex, Integer pageSize, UserLog UserLog, String token,String startDate,
			String finishedDate,String searchContent,String projectIds,String userIds,String userType) {
		// TODO Auto-generated method stub
		Date dateStarts=null;
    	Date dateFinisheds=null;
		DataWrapper<List<UserLog>> dataWrapper = new DataWrapper<List<UserLog>>();
		List<UserLogPojo> UserLogpojo = new ArrayList<UserLogPojo>();
		String[] projectPart={"模型","图纸","首页","交底","进度管理 ","安全问题","消息通知","统计管理","个人中心","规范查阅","模型构建信息","质量问题","新闻资讯","实测实量","云盘管理","物资管理","劳动力监测","考勤管理","工程量变更","进程管理","预付单","施工任务单"};
		String[] systemName={"苹果系统","安卓系统","电脑端"};
		DataWrapper<List<UserLogPojo>> dataWrapperpojo = new DataWrapper<List<UserLogPojo>>();
		User adminInMemory = SessionManager.getSession(token);
		if (adminInMemory != null) {
				if(searchContent!=null && searchContent!=""){
					User users=userDao.getByUserName(searchContent);
					if(users!=null){
						UserLog.setUserId(users.getId());
					}
				}
				SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    		if(startDate!=null){
	    			try {
	    				dateStarts=sdfs.parse(startDate);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    		}
	    		if(finishedDate!=null){
	    			try {
						dateFinisheds=sdfs.parse(finishedDate);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    		}
				dataWrapper=userLogDao.getUserLogList(pageSize, pageIndex,UserLog,dateStarts,dateFinisheds,projectIds,userIds,userType);
				if(dataWrapper.getData()!=null){
					for(int i=0;i<dataWrapper.getData().size();i++){
						UserLogPojo UserLogpojos=new UserLogPojo();
						UserLogpojos.setUserId(dataWrapper.getData().get(i).getUserId());
						UserLogpojos.setActionType(dataWrapper.getData().get(i).getActionType());
						//{"模型区域","图纸查看","登录区域","交底区域","进度管理区域 ","质安管理区域","通知区域","产值区域","班组信息区域","施工任务单区域","预付单区域"};
						//////图纸文件跟踪
						if(dataWrapper.getData().get(i).getProjectPart()==1){
							if(dataWrapper.getData().get(i).getFileId()!=null){
								UserLogpojos.setFileName(paperDao.getById(dataWrapper.getData().get(i).getFileId()).getOriginName());
							}
						}
						/////交底文件跟踪
						if(dataWrapper.getData().get(i).getProjectPart()==3){
							if(dataWrapper.getData().get(i).getFileId()!=null){
								UserLogpojos.setFileName(videoDao.getById(dataWrapper.getData().get(i).getFileId()).getData().getOriginName());
							}
						}
						/////预制化文件进度管理跟踪
						if(dataWrapper.getData().get(i).getProjectPart()==4){
							if(dataWrapper.getData().get(i).getFileId()!=null){
								UserLogpojos.setFileName(ductDao.getDuctById(dataWrapper.getData().get(i).getFileId()).getName());
							}
						}
						/////质安管理文件跟踪
						if(dataWrapper.getData().get(i).getProjectPart()==5){
							if(dataWrapper.getData().get(i).getFileId()!=null){
								UserLogpojos.setFileName(questionDao.getById(dataWrapper.getData().get(i).getFileId()).getName());
							}
						}
						UserLogpojos.setId(dataWrapper.getData().get(i).getId());
						User users = userDao.getById(dataWrapper.getData().get(i).getUserId());
						if(users!=null){
							UserLogpojos.setUserName(users.getRealName());
						}
						if(dataWrapper.getData().get(i).getProjectId()!=null){
							if(projectDao.getById(dataWrapper.getData().get(i).getProjectId())!=null){
								UserLogpojos.setProjectName(projectDao.getById(dataWrapper.getData().get(i).getProjectId()).getName());
							}
						}
						else{
							UserLogpojos.setProjectName("");
						}
						DateFormat df2 = DateFormat.getDateTimeInstance();//可以精确到时分秒 
						if(dataWrapper.getData().get(i).getActionDate()!=null){
							UserLogpojos.setActionDate(df2.format(dataWrapper.getData().get(i).getActionDate()));
						}
	    	    		if(dataWrapper.getData().get(i).getProjectPart()!=null){
	    	    			UserLogpojos.setProjectPart(projectPart[dataWrapper.getData().get(i).getProjectPart()]);
	    	    		}
	    	    		if(dataWrapper.getData().get(i).getSystemType()!=null){
	    	    			UserLogpojos.setSystemType(systemName[dataWrapper.getData().get(i).getSystemType()]);
		    	    		UserLogpojos.setVersion(dataWrapper.getData().get(i).getVersion());
		    	    		UserLogpojo.add(UserLogpojos);
	    	    		}
					}
					dataWrapperpojo.setData(UserLogpojo);
					dataWrapperpojo.setCallStatus(dataWrapper.getCallStatus());
					dataWrapperpojo.setCurrentPage(dataWrapper.getCurrentPage());
					dataWrapperpojo.setNumberPerPage(dataWrapper.getNumberPerPage());
					dataWrapperpojo.setTotalNumber(dataWrapper.getTotalNumber());
					dataWrapperpojo.setTotalPage(dataWrapper.getTotalPage());
					dataWrapperpojo.setErrorCode(dataWrapper.getErrorCode());
				}
		} else {
			dataWrapperpojo.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dataWrapperpojo;
	}


	@Override
	public DataWrapper<Void> deleteUserLog(String ids, String token) {
		DataWrapper<Void> dataWrapper=new DataWrapper<Void>();
		User userInMemory=SessionManager.getSession(token);
		String[] idList=null;
		idList=ids.split(",");
		if(userInMemory!=null){
			User adminInDB = userDao.getById(userInMemory.getId());
			if (adminInDB.getUserType() == UserTypeEnum.Admin.getType()) {
				if(userLogDao.deleteUserLog(idList)){
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
	public DataWrapper<Void> addUserLog(UserLog userLog,String token) {
		DataWrapper<Void> dataWrapper = new DataWrapper<>();
		User userInMemory=SessionManager.getSession(token);
		if(userInMemory!=null){
			userLog.setActionDate(new Date());
			userLog.setUserId(userInMemory.getId());
			if(!userLogDao.addUserLog(userLog)) {
				dataWrapper.setErrorCode(ErrorCodeEnum.Error);
			}
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		
		return dataWrapper;
	}


	@Override
	public DataWrapper<String> exportUserLog(String token, HttpServletRequest request, String dateStart,
			String dateFinished) {
		// TODO Auto-generated method stub
		DataWrapper<String> dataWrapper = new DataWrapper<String>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null) {
			
				String filePath = Parameters.userLogFilePath + userInMemory.getId() + "/";
				File fileDir = new File(filePath);
		        if (!fileDir.exists()) {
		            fileDir.mkdirs();
		        }
		        String file = filePath + "userlog.csv";
		        String header = "真实姓名,"
		        		+ "功能模块,"
		        		+ "版本号,"
		        		+ "访问量,"
		        		+ "系统类型,"
		        		+ "项目名称,"
		        		+ "时间";
		        String result = userLogDao.exportUserLog(dateStart, dateFinished);
		        if(result == null) {
		        	dataWrapper.setErrorCode(ErrorCodeEnum.Error);
		        } else {
					boolean flag = FileOperationsUtil.writeFile(file, header + "\n" + result, false);
					if(flag) {
						dataWrapper.setData("own/" + "userLog/" + userInMemory.getId() + "/userlog.csv");
					} else {
						dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					}
					
		        }

			
			
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
			
		return dataWrapper;
	}


	@Override
	public DataWrapper<List<UserLogPojos>> getUserLogCountSum(String token,String startTime,String finishedTime,Long projectId,Integer projectPart,Integer systemType) {
		DataWrapper<List<UserLogPojos>> dataWrapper = new DataWrapper<List<UserLogPojos>>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			dataWrapper=userLogDao.getUserLogLists(startTime,finishedTime,projectId,projectPart,systemType);
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dataWrapper;
	}


	@SuppressWarnings("static-access")
	@Override
	public DataWrapper<String> writeUserLogInFile(UserLog userLog) {
		// TODO Auto-generated method stub
		DataWrapper<String> dataWrapper = new DataWrapper<String>();
		userLog.setActionDate(new Date());
		String fileUrl="C:/filetest/newfile.txt";
		File file = new File("C:/filetest/newfile.txt");
		ReflectTest reflect = null;
		List<String> str = null;
		try {
			str = reflect.reflectTest(userLog);
		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String content = "";
		for(int i=0;i<str.size();i++){
			if(content.equals("")){
				content=content+str.get(i);
			}else{
				content+=","+str.get(i);
			}
		}
		try (FileOutputStream fop = new FileOutputStream(file,true)) {

		   // if file doesn't exists, then create it
		   if (!file.exists()) {
		    file.createNewFile();
		   }
		   else{
			   if(file.length() != 0)
				   content="\r\n"+content;  
		   } 
		   // get the content in bytes
		   byte[] contentInBytes = content.getBytes();

		   fop.write(contentInBytes);
		   fop.flush();
		   fop.close();
		   System.out.println("Done");

		  } catch (IOException e) {
		   e.printStackTrace();
		  }
		dataWrapper.setData(fileUrl);
		return dataWrapper;
	}


	@Override
	public DataWrapper<List<UserLog>> readUserLogFromFile() {
	   DataWrapper<List<UserLog>> dataWrapper = new DataWrapper<List<UserLog>>();
       
       List<UserLog> infoBeans=new ArrayList<UserLog>();
       String fileUrl="C:/filetest/newfile.csv";
       try {
           Scanner sc = null;
           File file = new File(fileUrl);
           if (file.isFile() && file.exists()){ //判断文件是否存在
        	   FileInputStream inputStream = new FileInputStream(fileUrl);
        	   sc = new Scanner(inputStream, "UTF-8");
               while(sc.hasNextLine()){
            	   String line = sc.nextLine();
            	   UserLog infoBean=new UserLog();
                   String[] s=line.split(",");
                   infoBean.setUserId(Long.valueOf(s[1]));
                   infoBean.setProjectId(Long.valueOf(s[2]));
                   infoBean.setProjectPart(Integer.valueOf(s[3]));
                   infoBean.setVersion(s[4]);
                   SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                   infoBean.setActionDate(sdf.parse(s[5]));
                   infoBean.setSystemType(Integer.valueOf(s[6]));  
                   System.out.println(line);
                   infoBeans.add(infoBean);
               }
               //////////////////////批量存入insert into user_log infoBeans
               if(userLogDao.addUserLogList(infoBeans)){
            	   dataWrapper.setCallStatus(CallStatusEnum.SUCCEED);
               }else{
            	   dataWrapper.setCallStatus(CallStatusEnum.FAILED);
               }
               ////////////////
               ///////////
               /////////////////////load file into tables
               if(userLogDao.loadUserLogFile(fileUrl)){
            	   dataWrapper.setCallStatus(CallStatusEnum.SUCCEED);
               }else{
            	   dataWrapper.setCallStatus(CallStatusEnum.FAILED);
               }
               
               if(sc.ioException() != null)
               {
            	   throw sc.ioException();

               }
               inputStream.close();
               sc.close();
           } else {
               System.out.println("找不到指定的文件");
           }
       } catch (Exception e) {
           System.out.println("读取文件内容出错");
           e.printStackTrace();
       } 
	   dataWrapper.setData(infoBeans);    
	   return dataWrapper;
	}


	@Override
	public DataWrapper<List<UserLogPart>> countUserLogByPart(String token, String startTime, String finishedTime,
			String projectIdList) {
		DataWrapper<List<UserLogPart>> result = new DataWrapper<List<UserLogPart>>();
		List<UserLogPart> gets = new ArrayList<UserLogPart>();
		List<UserLogPart> gets2 = new ArrayList<UserLogPart>();
		User user = SessionManager.getSession(token);
		for(int i=0;i<22;i++){
			UserLogPart userLogPart = new UserLogPart();
			userLogPart.setProjectPart(i);
			userLogPart.setNums(0);
			gets.add(userLogPart);
		}
		if(user!=null){
			if(user.getUserType()==UserTypeEnum.Admin.getType()){
				gets2 = userLogDao.getCountNumsByPart(startTime,finishedTime,projectIdList);
				for(int j=0;j<gets2.size();j++){
					for(int k=0;k<22;k++){
						if(gets2.get(j).getProjectPart()==gets.get(k).getProjectPart()){
							gets.get(k).setNums(gets2.get(j).getNums());
						}
					}
				}
				result.setData(gets);
			}else{
				result.setErrorCode(ErrorCodeEnum.AUTH_Error);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}
	@Override
	public DataWrapper<List<UserLogPart>> countPersonLogByPart(String token, String startTime, String finishedTime,Long userId) {
		DataWrapper<List<UserLogPart>> result = new DataWrapper<List<UserLogPart>>();
		List<UserLogPart> gets = new ArrayList<UserLogPart>();
		List<UserLogPart> gets2 = new ArrayList<UserLogPart>();
		User user = SessionManager.getSession(token);
		for(int i=0;i<22;i++){
			UserLogPart userLogPart = new UserLogPart();
			userLogPart.setProjectPart(i);
			userLogPart.setNums(0);
			gets.add(userLogPart);
		}
		if(user!=null){
			if(user.getUserType()==UserTypeEnum.Admin.getType()){
				gets2 = userLogDao.getCountPersonNumsByPart(startTime,finishedTime,userId);
				for(int j=0;j<gets2.size();j++){
					for(int k=0;k<22;k++){
						if(gets2.get(j).getProjectPart()==gets.get(k).getProjectPart()){
							gets.get(k).setNums(gets2.get(j).getNums());
						}
					}
				}
				result.setData(gets);
			}else{
				result.setErrorCode(ErrorCodeEnum.AUTH_Error);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}


	@Override
	public DataWrapper<List<UserLogMonth>> countUserLogByMonth(String token, String projectIdList,Integer year) {
		
		DataWrapper<List<UserLogMonth>> result = new DataWrapper<List<UserLogMonth>>();
		List<UserLogMonth> gets = new ArrayList<UserLogMonth>();
		List<UserLogMonth> realDatagets = new ArrayList<UserLogMonth>();
		List<UserLogMonth> boxs = new ArrayList<UserLogMonth>();
		String[] monthsDate = {year+"-01",year+"-02",year+"-03",year+"-04",year+"-05",year+"-06",year+"-07",year+"-08",year+"-09",year+"-10",year+"-11",year+"-12"};
		for(int i=0;i<12;i++){
			UserLogMonth item = new UserLogMonth();
			item.setDate(monthsDate[i]);
			item.setRealNum(0);
			item.setNum(0);
			boxs.add(item);
		}
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(user.getUserType()==UserTypeEnum.Admin.getType()){
				gets = userLogDao.getCountNumsByMonth(projectIdList,year);
				realDatagets = userLogDao.getCountRealNumsByMonth(projectIdList,year);
				if(!gets.isEmpty()){
					for(int i=0;i<12;i++){
						for(int j=0;j<gets.size();j++){
							if(monthsDate[i].equals(gets.get(j).getDate())){
								boxs.get(i).setNum(gets.get(j).getNum());
							}
						}
					}
					for(int i=0;i<12;i++){
						for(int j=0;j<realDatagets.size();j++){
							if(monthsDate[i].equals(realDatagets.get(j).getDate())){
								boxs.get(i).setRealNum(realDatagets.get(j).getNum());
							}
						}
					}
				}
				result.setData(boxs);
			}else{
				result.setErrorCode(ErrorCodeEnum.AUTH_Error);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}


	@Override
	public DataWrapper<List<UserLogMonth>> countUserLogByUserId(String token, Long userId, Integer year) {
		DataWrapper<List<UserLogMonth>> result = new DataWrapper<List<UserLogMonth>>();
		List<UserLogMonth> gets = new ArrayList<UserLogMonth>();
		List<UserLogMonth> realDatagets = new ArrayList<UserLogMonth>();
		List<UserLogMonth> boxs = new ArrayList<UserLogMonth>();
		String[] monthsDate = {year+"-01",year+"-02",year+"-03",year+"-04",year+"-05",year+"-06",year+"-07",year+"-08",year+"-09",year+"-10",year+"-11",year+"-12"};
		User user = SessionManager.getSession(token);
		for(int i=0;i<12;i++){
			UserLogMonth item = new UserLogMonth();
			item.setDate(monthsDate[i]);
			item.setRealNum(0);
			item.setNum(0);
			boxs.add(item);
		}
		if(user!=null){
			if(user.getUserType()==UserTypeEnum.Admin.getType()){
				gets = userLogDao.getCountNumsByUserId(userId,year);
				realDatagets = userLogDao.getCountRealNumsByUserId(userId,year);
				if(!gets.isEmpty()){
					for(int i=0;i<12;i++){
						for(int j=0;j<gets.size();j++){
							if(monthsDate[i].equals(gets.get(j).getDate())){
								boxs.get(i).setNum(gets.get(j).getNum());
							}
						}
					}
					for(int i=0;i<12;i++){
						for(int j=0;j<realDatagets.size();j++){
							if(monthsDate[i].equals(realDatagets.get(j).getDate())){
								boxs.get(i).setRealNum(realDatagets.get(j).getNum());
							}
						}
					}
				}
				result.setData(boxs);
			}else{
				result.setErrorCode(ErrorCodeEnum.AUTH_Error);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}


	@SuppressWarnings("static-access")
	@Override
	public DataWrapper<String> exportUserLogList(String token,String dateStart, String dateFinished,String projectIds, String userIds) {
		// TODO Auto-generated method stub
	    	DataWrapper<String> result = new DataWrapper<String>();
			List<UserLogCount> userLogCounts = new ArrayList<UserLogCount>();
			User adminInMemory = SessionManager.getSession(token);
			if (adminInMemory != null) {
		    		userLogCounts=userLogDao.countUserLogNum(dateStart,dateFinished,projectIds,userIds);
		             try {
		            	 FileOutputStream fout = new FileOutputStream("D://jasobim/tomcat_8080/webapps/userLog/userLog.xls");
			    		 UserLogAllToExcel userLogExcel = new UserLogAllToExcel();
			    		 userLogExcel.getValue(userLogCounts,fout,dateStart,dateFinished);
						 fout.close();
						 result.setData("/userLog/userLog.xls");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			} else {
				result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
			}
			return result;
	}


	@SuppressWarnings("static-access")
	@Override
	public DataWrapper<String> exportPersonLogList(String token, Long userId, Integer year, String startTime,
			String finishedTime) {
		DataWrapper<String> result = new DataWrapper<String>();
		//////////按月统计用户的所有数据和真实数据
		List<UserLogMonth> monthGets = new ArrayList<UserLogMonth>();
		List<UserLogMonth> realDatagets = new ArrayList<UserLogMonth>();
		List<UserLogMonth> boxs = new ArrayList<UserLogMonth>();
		String[] monthsDate = {year+"-01",year+"-02",year+"-03",year+"-04",year+"-05",year+"-06",year+"-07",year+"-08",year+"-09",year+"-10",year+"-11",year+"-12"};
		for(int i=0;i<12;i++){
			UserLogMonth item = new UserLogMonth();
			item.setDate(monthsDate[i]);
			item.setRealNum(0);
			item.setNum(0);
			boxs.add(item);
		}
		////////////////////
		
		///////////按功能区域统计用户各区域的所有数据数量
		List<UserLogPart> gets = new ArrayList<UserLogPart>();
		List<UserLogPart> gets2 = new ArrayList<UserLogPart>();
		User user = SessionManager.getSession(token);
		for(int i=0;i<22;i++){
			UserLogPart userLogPart = new UserLogPart();
			userLogPart.setProjectPart(i);
			userLogPart.setNums(0);
			gets.add(userLogPart);
		}
		////////////
		if(user!=null){
			if(user.getUserType()==UserTypeEnum.Admin.getType()){
				//////按月统计用户的所有数据和真实数据
				monthGets = userLogDao.getCountNumsByUserId(userId,year);
				realDatagets = userLogDao.getCountRealNumsByUserId(userId,year);
				if(!monthGets.isEmpty()){
					for(int i=0;i<12;i++){
						for(int j=0;j<monthGets.size();j++){
							if(monthsDate[i].equals(monthGets.get(j).getDate())){
								boxs.get(i).setNum(monthGets.get(j).getNum());
							}
						}
					}
					for(int i=0;i<12;i++){
						for(int j=0;j<realDatagets.size();j++){
							if(monthsDate[i].equals(realDatagets.get(j).getDate())){
								boxs.get(i).setRealNum(realDatagets.get(j).getNum());
							}
						}
					}
				}
				///////////////
				gets2 = userLogDao.getCountPersonNumsByPart(startTime,finishedTime,userId);
				for(int j=0;j<gets2.size();j++){
					for(int k=0;k<22;k++){
						if(gets2.get(j).getProjectPart()==gets.get(k).getProjectPart()){
							gets.get(k).setNums(gets2.get(j).getNums());
						}
					}
				}
			}else{
				result.setErrorCode(ErrorCodeEnum.AUTH_Error);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		User logUser = userDao.getById(userId);
		UserLogEchartsToExcel util = new UserLogEchartsToExcel();
		try {
     	 	 FileOutputStream fout = new FileOutputStream("D://jasobim/tomcat_8080/webapps/userLog/userPersonalLog.xls");
     	 	 util.getValue(gets, boxs, fout, logUser.getRealName()+"的个人数据分析表", year, startTime, finishedTime);				
     	 	 fout.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		result.setData("/userLog/userPersonalLog.xls");	
		return result;
	}


	@SuppressWarnings("static-access")
	@Override
	public DataWrapper<String> exportUserLogEcharts(String token, String dateStart, String dateFinished,
			String projectIds, String userIds, Integer year) {
		DataWrapper<String> result = new DataWrapper<String>();
		//////////////////////////////////////////////////////////////
		List<UserLogPart> partGets = new ArrayList<UserLogPart>();
		List<UserLogPart> partGets2 = new ArrayList<UserLogPart>();
		for(int i=0;i<22;i++){
			UserLogPart userLogPart = new UserLogPart();
			userLogPart.setProjectPart(i);
			userLogPart.setNums(0);
			partGets.add(userLogPart);
		}
		/////////////////////////////////////////////
		List<UserLogMonth> gets = new ArrayList<UserLogMonth>();
		List<UserLogMonth> realDatagets = new ArrayList<UserLogMonth>();
		List<UserLogMonth> boxs = new ArrayList<UserLogMonth>();
		String[] monthsDate = {year+"-01",year+"-02",year+"-03",year+"-04",year+"-05",year+"-06",year+"-07",year+"-08",year+"-09",year+"-10",year+"-11",year+"-12"};
		for(int i=0;i<12;i++){
			UserLogMonth item = new UserLogMonth();
			item.setDate(monthsDate[i]);
			item.setRealNum(0);
			item.setNum(0);
			boxs.add(item);
		}
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(user.getUserType()==UserTypeEnum.Admin.getType()){
				/////////////////////////////////////////////
				partGets2 = userLogDao.getCountNumsByPart(dateStart,dateFinished,projectIds);
				for(int j=0;j<partGets2.size();j++){
					for(int k=0;k<22;k++){
						if(partGets2.get(j).getProjectPart()==partGets.get(k).getProjectPart()){
							partGets.get(k).setNums(partGets2.get(j).getNums());
						}
					}
				}
				//////////////////////////////////////////
				gets = userLogDao.getCountNumsByMonth(projectIds,year);
				realDatagets = userLogDao.getCountRealNumsByMonth(projectIds,year);
				if(!gets.isEmpty()){
					for(int i=0;i<12;i++){
						for(int j=0;j<gets.size();j++){
							if(monthsDate[i].equals(gets.get(j).getDate())){
								boxs.get(i).setNum(gets.get(j).getNum());
							}
						}
					}
					for(int i=0;i<12;i++){
						for(int j=0;j<realDatagets.size();j++){
							if(monthsDate[i].equals(realDatagets.get(j).getDate())){
								boxs.get(i).setRealNum(realDatagets.get(j).getNum());
							}
						}
					}
				}
			}else{
				result.setErrorCode(ErrorCodeEnum.AUTH_Error);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		UserLogEchartsToExcel util = new UserLogEchartsToExcel();
		  try {
       	 	 FileOutputStream fout = new FileOutputStream("D://jasobim/tomcat_8080/webapps/userLog/projectLog.xls");
       	 	 util.getValue(partGets, boxs, fout, "项目操作数据分析表", year, dateStart, dateFinished);				
       	 	 fout.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		result.setData("/userLog/projectLog.xls");
		return result;
	}


	
}
