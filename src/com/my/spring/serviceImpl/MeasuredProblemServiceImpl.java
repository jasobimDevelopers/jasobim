package com.my.spring.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.my.spring.DAO.CheckListTypesDao;
import com.my.spring.DAO.CheckListsDao;
import com.my.spring.DAO.CommentDao;
import com.my.spring.DAO.FileDao;
import com.my.spring.DAO.ManageLogDao;
import com.my.spring.DAO.MeasuredProblemDao;
import com.my.spring.DAO.PointDataInputLogDao;
import com.my.spring.DAO.RelationDao;
import com.my.spring.DAO.ReplyDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.CheckListTypes;
import com.my.spring.model.Comment;
import com.my.spring.model.Files;
import com.my.spring.model.ManageLog;
import com.my.spring.model.MeasuredProblem;
import com.my.spring.model.MeasuredProblemEditPojo;
import com.my.spring.model.MeasuredProblemPojo;
import com.my.spring.model.MeasuredUserInfo;
import com.my.spring.model.PointDataInputLog;
import com.my.spring.model.PointInputLog;
import com.my.spring.model.Relation;
import com.my.spring.model.Reply;
import com.my.spring.model.User;
import com.my.spring.parameters.Parameters;
import com.my.spring.service.FileService;
import com.my.spring.service.MeasuredProblemService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


@Service("measuredProblemService")
public class MeasuredProblemServiceImpl implements MeasuredProblemService,Runnable {
	public MeasuredProblemServiceImpl(){
		
	}
    @Autowired
    MeasuredProblemDao mpDao;
    @Autowired
    PointDataInputLogDao dataDao;
    @Autowired
    CheckListTypesDao checkDao;
    @Autowired
    UserDao userDao;
    @Autowired
    ReplyDao replyDao;
    @Autowired
	FileService fileService;
    @Autowired
    RelationDao relationDao;
    @Autowired
    CommentDao commentDao;
	@Autowired
	FileDao fileDao;
	@Autowired
	ManageLogDao manageLogDao;
	private static String filePath = "/files/measuredProblem";
    @Override
    public DataWrapper<Void> addMeasuredProblem(MeasuredProblem building,String token,MultipartFile[] files,MultipartFile[] vois,HttpServletRequest request,String fDate) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(building!=null){
				if(!fDate.equals("") && fDate!=null){
					try {
						building.setFinishedDate(Parameters.getSdfs().parse(fDate));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(files!=null){
					String filesStr="";
					for(int i=0;i<files.length;i++){
						Files file=fileService.uploadFile(filePath+"/pictures", files[i], 3, request);
						if(i==(files.length-1)){
							filesStr=filesStr+file.getId();
						}else{
							filesStr=filesStr+file.getId()+",";
						}
					}
					building.setFiles(filesStr);;
				}
				if(vois!=null){
					String filesStr="";
					for(int i=0;i<vois.length;i++){
						Files file=fileService.uploadFile(filePath+"/voices", vois[i], 3, request);
						if(i==(vois.length-1)){
							filesStr=filesStr+file.getId();
						}else{
							filesStr=filesStr+file.getId()+",";
						}
					}
					building.setVoices(filesStr);;
				}
				building.setCreateDate(new Date());
				building.setCheckUser(userInMemory.getId());
				building.setCreateUser(userInMemory.getId());
				if(!mpDao.addMeasuredProblem(building)){
					dataWrapper.setErrorCode(ErrorCodeEnum.Error);
				} 
				else{
					return dataWrapper;
				}
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrapper;
    }
    @Override
    public DataWrapper<Void> deleteMeasuredProblem(Long id,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(id!=null){
				if(!mpDao.deleteMeasuredProblem(id)) 
		            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
				else
					return dataWrapper;
		        
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrapper;
    }

	@Override
	public DataWrapper<List<MeasuredProblemPojo>> getMeasuredProblemByProjectId(Long id,Long projectId,String token,Integer status,String bfmIds,String checkTypeIds) {
		DataWrapper<List<MeasuredProblemPojo>> dataWrapper = new DataWrapper<List<MeasuredProblemPojo>>();
		DataWrapper<List<MeasuredProblem>> results = new DataWrapper<List<MeasuredProblem>>();
		List<MeasuredProblemPojo> datas = new ArrayList<MeasuredProblemPojo>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
        	List<Long> bids = new ArrayList<>();
        	List<Long> cids = new ArrayList<>();
        	if(bfmIds!=null){
        		if(!bfmIds.equals("")){
        			String[] ids = bfmIds.split(",");
        			for(int i=0;i<ids.length;i++){
        				bids.add(Long.valueOf(ids[i]));
        			}
        		}
        	}
        	if(checkTypeIds!=null){
        		if(!checkTypeIds.equals("")){
        			String[] ctids = checkTypeIds.split(",");
        			for(int i=0;i<ctids.length;i++){
        				cids.add(Long.valueOf(ctids[i]));
        			}
        		}
        	}
        	results=mpDao.getMeasuredProblemByProjectId(id,projectId,userInMemory,status,bids,cids);
        	if(results.getData()!=null){
        		for(int i=0;i<results.getData().size();i++){
        			MeasuredProblemPojo pojo = new MeasuredProblemPojo();
        			pojo.setId(results.getData().get(i).getId());
        			pojo.setCheckSite(results.getData().get(i).getCheckSite());
        			pojo.setDetail(results.getData().get(i).getDetail());
        			pojo.setStatus(results.getData().get(i).getStatus());
        			pojo.setScore(results.getData().get(i).getScore());
        			pojo.setProcess(results.getData().get(i).getProcess());
        			CheckListTypes clt = new CheckListTypes();
        			if(results.getData().get(i).getCheckListId()!=null){
        				clt = checkDao.getById(results.getData().get(i).getCheckListId());
        				if(clt!=null){
        					pojo.setCheckLists(clt.getCheckName());
        				}
        			}
        			if(results.getData().get(i).getPointId()!=null){
        				pojo.setPointId(results.getData().get(i).getPointId());
        				PointDataInputLog dataLogs = dataDao.getPointDataInputLogListByOptions(results.getData().get(i).getPointId(),results.getData().get(i).getCheckListId(),results.getData().get(i).getInputUserId());
        				if(dataLogs!=null){
        					String logString=results.getData().get(i).getCheckSite()+" 实测实量-设备安装-";
    						String checkNames= clt.getCheckName();
    						logString=logString+checkNames;
    						pojo.setTitle(logString);
        						/////////
    						String content = "";
    						User users = userDao.getById(dataLogs.getCreateUser());
    						if(users!=null){
    							content=content+users.getRealName()+"-"+users.getWorkName();
    						}
    						content=content+Parameters.getSdf().format(dataLogs.getCreateDate());
    						pojo.setContent(content);
    						/////
    						String content2 = checkNames;
    						content2=content2+":【"+dataLogs.getInputData()+"】";
    						pojo.setContentDetail(content2);
        					
        				}
        			}
        		    
        			if(results.getData().get(i).getCheckDate()!=null){
        				String checkDates = Parameters.getSdfs().format(results.getData().get(i).getCheckDate());
        				pojo.setCheckDate(checkDates);
        			}
        			if(results.getData().get(i).getFinishedDate()!=null){
        				String finishedDates = Parameters.getSdfs().format(results.getData().get(i).getFinishedDate());
        				pojo.setFinishedDate(finishedDates);
        			}
        			String createDate = Parameters.getSdfs().format(results.getData().get(i).getCreateDate());
    				pojo.setCreateDate(createDate);
    				if(id!=null){
        				List<MeasuredUserInfo> users = mpDao.getAboutUserIcons(id);
        				pojo.setAboutIcons(users);
        				if(results.getData().get(i).getCheckUser()!=null){
            				User checkUser = userDao.getById(results.getData().get(i).getCheckUser());
            				if(checkUser!=null){
            					pojo.setCheckUser(checkUser.getRealName());
            				}
            			}
            			if(results.getData().get(i).getCreateUser()!=null){
            				User createUser = userDao.getById(results.getData().get(i).getCreateUser());
            				if(createUser!=null){
            					pojo.setCreateUser(createUser.getRealName());
            				}
            			}
            			if(results.getData().get(i).getFiles()!=null){
            				List<String> files = new ArrayList<String>();
            				String[] filesstr=results.getData().get(i).getFiles().split(",");
            				for(int j=0;j<filesstr.length;j++)
            				{
            					Files file1 = fileService.getById(Long.valueOf(filesstr[j]));
            					if(file1!=null){
            						files.add(file1.getUrl());
            					}
            				}
            				pojo.setFiles(files);
            			}
            			if(results.getData().get(i).getVoices()!=null){
            				List<String> files = new ArrayList<String>();
            				String[] filesstr=results.getData().get(i).getVoices().split(",");
            				for(int j=0;j<filesstr.length;j++)
            				{
            					Files file1 = fileService.getById(Long.valueOf(filesstr[j]));
            					if(file1!=null){
            						files.add(file1.getUrl());
            					}
            				}
            				pojo.setVoices(files);
            			}
        			}
        			
        			if(results.getData().get(i).getRectifyUser()!=null){
        				pojo.setRectifyUser(results.getData().get(i).getRectifyUser());
        			}
        			
        			datas.add(pojo);
        		}
        		dataWrapper.setData(datas);
        	}
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dataWrapper;
	}
	@Override
	public DataWrapper<Void> qualityRectificationCheckAgain(String token, Long measuredId, Integer score,
			Integer state) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			MeasuredProblem qm = mpDao.getById(measuredId);
			if(qm!=null){
				if(user.getId().equals(qm.getCheckUser()) || user.getUserType().equals(UserTypeEnum.Admin.getType())){
					qm.setScore(score);
					Comment comment = new Comment();
					ManageLog manageLog = new ManageLog();
					manageLog.setAboutId(measuredId);
					manageLog.setActionDate(new Date());
					manageLog.setOperateUser(user.getId());
					manageLog.setManageType(2);
					manageLog.setActionType(1);
					if(state==1){
						qm.setStatus(2);//已通过
					}
					if(state==0){
						qm.setStatus(0);//待整改
					}
					if(mpDao.updateMeasuredProblem(qm)){
						manageLogDao.addManageLog(manageLog);
						comment.setCommentUser(user.getId());;
						comment.setAboutId(measuredId);
						comment.setReplyType(2);
						comment.setType(2);
						comment.setCreateDate(new Date());
						comment.setCommentContent("验收通过");
						comment.setCommentContent("验收不通过");
						commentDao.addComment(comment);
					}
				}else{
					result.setErrorCode(ErrorCodeEnum.AUTH_Error);
				}
			}else{
				result.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}
	@Override
	public DataWrapper<Void> qualityRectificationCheck(String token, Long measuredId, Integer schedule, String content,
			MultipartFile[] pics, MultipartFile[] vois, HttpServletRequest request) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			List<Relation> qms = relationDao.getRelationListsByIds(2, measuredId, user.getId());
			if(!qms.isEmpty() || user.getUserType()==UserTypeEnum.Admin.getType()){
				Reply reply = new Reply();
				reply.setAboutId(measuredId);
				reply.setReplyType(2);
				reply.setCreateDate(new Date());
				reply.setReplyContent(content);
				reply.setReplyUser(user.getId());
				reply.setSchedule(schedule);
				if(pics!=null){
					String picsStr="";
					for(int i=0;i<pics.length;i++){
						Files file=fileService.uploadFile(filePath+"/pictures", pics[i], 3, request);
						if(i==(pics.length-1)){
							picsStr=picsStr+file.getId();
						}else{
							picsStr=picsStr+file.getId()+",";
						}
					}
					reply.setPictures(picsStr);
				}
				if(vois!=null){
					String voisStr="";
					for(int i=0;i<vois.length;i++){
						Files file=fileService.uploadFile(filePath+"/voices", vois[i], 3, request);
						if(i==(vois.length-1)){
							voisStr=voisStr+file.getId();
						}else{
							voisStr=voisStr+file.getId()+",";
						}
					}
					reply.setVoices(voisStr);
				}
				if(replyDao.addReply(reply)){
					Comment comment = new Comment();
					comment.setCommentUser(reply.getReplyUser());;
					comment.setAboutId(measuredId);
					comment.setReplyType(2);
					comment.setType(1);
					comment.setCreateDate(new Date());
					comment.setCommentContent("工作已完成"+schedule+"%");
					if(schedule==100){
						comment.setCommentContent("工作已完成"+schedule+"%,请尽快验收！");
						MeasuredProblem qm = mpDao.getById(measuredId);
						qm.setStatus(1);//进行中
						mpDao.updateMeasuredProblem(qm);
					}
					commentDao.addComment(comment);
				}
			}else{
				result.setErrorCode(ErrorCodeEnum.AUTH_Error);
			}
		}
		return result;
	}
	@Override
	public DataWrapper<Void> updateMeasuredProblem(MeasuredProblem building, String token, MultipartFile[] files,
			MultipartFile[] vois, HttpServletRequest request, String fDate) {
		 DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
	        User userInMemory = SessionManager.getSession(token);
	        if (userInMemory != null) {
				if(building.getId()!=null){
					MeasuredProblem mps = mpDao.getById(building.getId());
					if(mps!=null){
						if(!fDate.equals("") && fDate!=null){
							try {
								mps.setFinishedDate(Parameters.getSdfs().parse(fDate));
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						if(files!=null){
							String filesStr="";
							for(int i=0;i<files.length;i++){
								Files file=fileService.uploadFile(filePath+"/pictures", files[i], 3, request);
								if(i==(files.length-1)){
									filesStr=filesStr+file.getId();
								}else{
									filesStr=filesStr+file.getId()+",";
								}
							}
							mps.setFiles(filesStr);;
						}
						if(vois!=null){
							String filesStr="";
							for(int i=0;i<vois.length;i++){
								Files file=fileService.uploadFile(filePath+"/voices", vois[i], 3, request);
								if(i==(vois.length-1)){
									filesStr=filesStr+file.getId();
								}else{
									filesStr=filesStr+file.getId()+",";
								}
							}
							mps.setVoices(filesStr);;
						}
						mps.setDetail(building.getDetail());
						mps.setStatus(1);
						mps.setRectifyUser(building.getRectifyUser());
						if(!mpDao.updateMeasuredProblem(mps)){
							dataWrapper.setErrorCode(ErrorCodeEnum.Error);
						} 
						else{
							/*添加相关人员记录表*/
							List<Relation> relationList = new ArrayList<Relation>();
							if(building.getRectifyUser()!=null){
								String[] rectifyUsers = building.getRectifyUser().split(",");
								for(int n=0;n<rectifyUsers.length;n++){
									Relation relation2 = new Relation();
									relation2.setAboutId(building.getId());
									relation2.setProjectId(building.getProjectId());
									relation2.setRelationType(2);
									relation2.setUserId(Long.valueOf(rectifyUsers[n]));
									relationList.add(relation2);
								}
								relationDao.addRelationList(relationList);
							}
							return dataWrapper;
						}
					}else{
						dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
					}
				}else{
					dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
				}
			} else {
				dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
			}
	        return dataWrapper;
	}
	@Override
	public DataWrapper<Void> addMeasuredProblemList(List<MeasuredProblem> mpList) {
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		if(!mpList.isEmpty()){
			if(!mpDao.addMeasuredProblemList(mpList)){
				dataWrapper.setErrorCode(ErrorCodeEnum.Error);
			} 
			else{
				/*添加相关人员记录表*/
				List<Relation> relationList = new ArrayList<Relation>();
				for(int i=0;i<mpList.size();i++){
					Relation relation = new Relation();
					relation.setAboutId(mpList.get(i).getId());
					relation.setProjectId(mpList.get(i).getProjectId());
					relation.setRelationType(2);
					relation.setUserId(mpList.get(i).getCreateUser());
					relationList.add(relation);
				}
				relationDao.addRelationList(relationList);
				return dataWrapper;
			}
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
		}
        return dataWrapper;
	}
	@Override
	public DataWrapper<MeasuredProblemPojo> getMeasuredProblemByPointId(Long pointId, String token) {
		// TODO Auto-generated method stub
		return null;
	}
	public void test(){
		new Thread(new Runnable() { @Override public void run() { System.out.println("线程同步测试！！！！！！！！！！！！！！！！！！！！！！！！"); } }).start();
	}
	@Override
	public void run() {
		System.out.println("线程同步测试！！！！！！！！！！！！！！！！！！！！！！！！");
	}
	@Override
	public DataWrapper<Void> updateMeasuredProblemList(String token, String editString) {
		// TODO Auto-generated method stub
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			List<MeasuredProblemEditPojo> gets = JSONObject.parseArray(editString, MeasuredProblemEditPojo.class);
			if(!gets.isEmpty()){
				List<MeasuredProblem> getList = mpDao.getMeasuredProblemByIds(gets);
				if(!getList.isEmpty()){
					for(int i=0;i<getList.size();i++){
						try {
							if(gets.get(i).getFinishedDate()!=null){
								getList.get(i).setFinishedDate(Parameters.getSdfday().parse(gets.get(i).getFinishedDate()));
							}
							if(gets.get(i).getRectifyUserId()!=null){
								getList.get(i).setRectifyUser(gets.get(i).getRectifyUserId());
							}
							if(gets.get(i).getFinishedDate()!=null && gets.get(i).getRectifyUserId()!=null){
								getList.get(i).setStatus(1);
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					if(!mpDao.updateMeasuredProblemList(getList)){
						dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					}else{
						List<Relation> relationList = new ArrayList<Relation>();
						for(int i=0;i<gets.size();i++){
							if(gets.get(i).getRectifyUserId()!=null){
								String[] rectifyUsers = gets.get(i).getRectifyUserId().split(",");
								for(int j=0;j<rectifyUsers.length;j++){
									Relation relation2 = new Relation();
									relation2.setAboutId(getList.get(j).getId());
									relation2.setProjectId(getList.get(j).getProjectId());
									relation2.setRelationType(2);
									relation2.setUserId(Long.valueOf(rectifyUsers[j]));
									relationList.add(relation2);
								}
							}
						}
						relationDao.addRelationList(relationList);
					}
				}
			}
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		
		
		return dataWrapper;
	}
}
