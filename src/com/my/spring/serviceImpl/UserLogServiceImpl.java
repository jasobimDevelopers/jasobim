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
import com.my.spring.DAO.ConstructionTaskDao;
import com.my.spring.DAO.DuctDao;
import com.my.spring.DAO.ItemDao;
import com.my.spring.DAO.NewsDao;
import com.my.spring.DAO.PaperDao;
import com.my.spring.DAO.ProjectDao;
import com.my.spring.DAO.QuestionDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.CallStatusEnum;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.UserLog;
import com.my.spring.model.UserLogPojo;
import com.my.spring.model.UserLogPojos;
import com.my.spring.parameters.Parameters;
import com.my.spring.model.User;
import com.my.spring.service.UserLogService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.FileOperationsUtil;
import com.my.spring.utils.ReflectTest;
import com.my.spring.utils.SessionManager;


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
	NewsDao newsDao;
	@Autowired
	QuestionDao questionDao;
	@Autowired
	VideoDao videoDao;
	@Autowired
	ConstructionTaskDao constructionTaskDao;
	@Autowired
	AdvancedOrderDao advancedOrderDao;
	
	@Override
	public DataWrapper<List<UserLogPojo>> getUserLogList(Integer pageIndex, Integer pageSize, UserLog UserLog, String token,String startDate,String finishedDate,String searchContent) {
		// TODO Auto-generated method stub
		Date dateStarts=null;
    	Date dateFinisheds=null;
		DataWrapper<List<UserLog>> dataWrapper = new DataWrapper<List<UserLog>>();
		List<UserLogPojo> UserLogpojo = new ArrayList<UserLogPojo>();
		String[] projectPart={"模型区域","图纸区域","登录区域","交底区域","预制化区域 ","紧急事项区域","通知区域","产值区域","班组信息区域","施工任务单区域","预付单区域"};
		String[] systemName={"苹果系统","安卓系统"};
		DataWrapper<List<UserLogPojo>> dataWrapperpojo = new DataWrapper<List<UserLogPojo>>();
		User adminInMemory = SessionManager.getSession(token);
		if (adminInMemory != null && adminInMemory.getSystemId()==-1) {
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
				dataWrapper=userLogDao.getUserLogList(pageSize, pageIndex,UserLog,dateStarts,dateFinisheds);
				if(dataWrapper.getData()!=null){
					for(int i=0;i<dataWrapper.getData().size();i++){
						UserLogPojo UserLogpojos=new UserLogPojo();
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
						///////公告公示信息区域
						if(dataWrapper.getData().get(i).getProjectPart()==6){
							if(dataWrapper.getData().get(i).getFileId()!=null){
								UserLogpojos.setFileName(newsDao.getById(dataWrapper.getData().get(i).getFileId()).getTitle());
							}
						}
						//////统计管理区域（各个项目产值）
						if(dataWrapper.getData().get(i).getProjectPart()==7){
							if(dataWrapper.getData().get(i).getFileId()!=null){
								UserLogpojos.setFileName(valueOutputDao.getById(dataWrapper.getData().get(i).getFileId()).getOthers());
							}
						}
						/////班组信息区域(班组消息)
						if(dataWrapper.getData().get(i).getProjectPart()==8){
							if(dataWrapper.getData().get(i).getFileId()!=null){
								UserLogpojos.setFileName(valueOutputDao.getById(dataWrapper.getData().get(i).getFileId()).getOthers());
							}
						}
						////施工任务单区域
						if(dataWrapper.getData().get(i).getProjectPart()==9){
							if(dataWrapper.getData().get(i).getFileId()!=null){
								UserLogpojos.setFileName(constructionTaskDao.getById(dataWrapper.getData().get(i).getFileId()).getCompanyName());
							}
						}
						////预付单区域
						if(dataWrapper.getData().get(i).getProjectPart()==10){
							if(dataWrapper.getData().get(i).getFileId()!=null){
								UserLogpojos.setFileName(advancedOrderDao.getById(dataWrapper.getData().get(i).getFileId()).getProjectName());
							}
						}
						UserLogpojos.setId(dataWrapper.getData().get(i).getId());
						UserLogpojos.setUserName(userDao.getById(dataWrapper.getData().get(i).getUserId()).getRealName());
						if(dataWrapper.getData().get(i).getProjectId()!=null){
							if(projectDao.getById(dataWrapper.getData().get(i).getProjectId())!=null){
								UserLogpojos.setProjectName(projectDao.getById(dataWrapper.getData().get(i).getProjectId()).getName());
							}
						}
						else{
							UserLogpojos.setProjectName("");
						}
						DateFormat df2 = DateFormat.getDateTimeInstance();//可以精确到时分秒 
	    	    		UserLogpojos.setActionDate(df2.format(dataWrapper.getData().get(i).getActionDate()));
	    	    		UserLogpojos.setProjectPart(projectPart[dataWrapper.getData().get(i).getProjectPart()]);
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
               ////////////////
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
              /* InputStreamReader read = new InputStreamReader(
                       new FileInputStream(file), encoding);//考虑到编码格式
               BufferedReader bufferedReader = new BufferedReader(read);
               String lineTxt = null;*/
               /*while ((lineTxt = bufferedReader.readLine()) != null) {
            	  
               }
               read.close();*/
           } else {
               System.out.println("找不到指定的文件");
           }
       } catch (Exception e) {
           System.out.println("读取文件内容出错");
           e.printStackTrace();
       } 
       //Reader reader=new Reader(fileUrl);
      // Writer writer=new Writer(fileUrl);
       //new Thread(reader).start();
       //new Thread(writer).start();
	   dataWrapper.setData(infoBeans);    
	   return dataWrapper;
	}
}
