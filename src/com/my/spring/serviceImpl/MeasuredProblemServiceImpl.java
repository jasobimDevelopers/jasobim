package com.my.spring.serviceImpl;

import com.my.spring.DAO.FileDao;
import com.my.spring.DAO.ManageLogDao;
import com.my.spring.DAO.MeasuredProblemDao;
import com.my.spring.DAO.RelationDao;
import com.my.spring.DAO.ReplyDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.Files;
import com.my.spring.model.ManageLog;
import com.my.spring.model.MeasuredProblem;
import com.my.spring.model.MeasuredProblemPojo;
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
public class MeasuredProblemServiceImpl implements MeasuredProblemService {
    @Autowired
    MeasuredProblemDao mpDao;
    @Autowired
    UserDao userDao;
    @Autowired
    ReplyDao replyDao;
    @Autowired
	FileService fileService;
    @Autowired
    RelationDao relationDao;
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
				building.setCreateUser(userInMemory.getId());
				if(!mpDao.addMeasuredProblem(building)) 
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
    public DataWrapper<Void> updateMeasuredProblem(MeasuredProblem building,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
 				if(building!=null){
					if(!mpDao.updateMeasuredProblem(building)) 
			            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					else
						return dataWrapper;
			        
				}else{
					dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
				}
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.AUTH_Error);
			}
        return dataWrapper;
    }

	@Override
	public DataWrapper<List<MeasuredProblemPojo>> getMeasuredProblemByProjectId(Long projectId,String token) {
		DataWrapper<List<MeasuredProblemPojo>> dataWrapper = new DataWrapper<List<MeasuredProblemPojo>>();
		DataWrapper<List<MeasuredProblem>> results = new DataWrapper<List<MeasuredProblem>>();
		List<MeasuredProblemPojo> datas = new ArrayList<MeasuredProblemPojo>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
        	results=mpDao.getMeasuredProblemByProjectId(projectId);
        	if(results.getData()!=null){
        		for(int i=0;i<results.getData().size();i++){
        			MeasuredProblemPojo pojo = new MeasuredProblemPojo();
        			pojo.setId(results.getData().get(i).getId());
        			pojo.setCheckSite(results.getData().get(i).getCheckSite());
        			pojo.setDetail(results.getData().get(i).getDetail());
        			pojo.setStatus(results.getData().get(i).getStatus());
        			pojo.setScore(results.getData().get(i).getScore());
        			pojo.setProcess(results.getData().get(i).getProcess());
        			pojo.setCheckLists(results.getData().get(i).getCheckLists());
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
        			if(results.getData().get(i).getRectifyUser()!=null){
        				User rectifyUser = userDao.getById(results.getData().get(i).getRectifyUser());
        				if(rectifyUser!=null){
        					pojo.setRectifyUser(rectifyUser.getRealName());
        				}
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
					if(schedule==100){
						List<Relation> relations = new ArrayList<Relation>();
						relations=relationDao.getRelationListsByIds(0, measuredId, null);
						for(Relation re:relations){
							re.setState(1);
							relationDao.updateRelation(re);
						}
						MeasuredProblem qm = mpDao.getById(measuredId);
						qm.setStatus(1);//进行中
						mpDao .updateMeasuredProblem(qm);
					}
				}
			}else{
				result.setErrorCode(ErrorCodeEnum.AUTH_Error);
			}
		}
		return result;
	}
}
