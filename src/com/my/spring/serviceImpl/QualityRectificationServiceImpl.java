package com.my.spring.serviceImpl;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.my.spring.DAO.FileDao;
import com.my.spring.DAO.ManageLogDao;
import com.my.spring.DAO.NatureDao;
import com.my.spring.DAO.ProjectDao;
import com.my.spring.DAO.QualityCheckDao;
import com.my.spring.DAO.QualityRectificationDao;
import com.my.spring.DAO.RelationDao;
import com.my.spring.DAO.ReplyDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.QualityRectification;
import com.my.spring.model.QualityRectificationPojo;
import com.my.spring.model.Relation;
import com.my.spring.model.Reply;
import com.my.spring.model.Files;
import com.my.spring.model.ManageLog;
import com.my.spring.model.Nature;
import com.my.spring.model.Project;
import com.my.spring.model.QualityCheck;
import com.my.spring.model.User;
import com.my.spring.parameters.Parameters;
import com.my.spring.service.FileService;
import com.my.spring.service.QualityRectificationService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

@Service("qualityRectificationService")
public class QualityRectificationServiceImpl implements QualityRectificationService  {
	@Autowired
	QualityRectificationDao QualityManageDao;
	@Autowired
	UserDao userDao;
	@Autowired
	ProjectDao projectDao;
	@Autowired
	FileService fileService;
	@Autowired
	FileDao fileDao;
	@Autowired
	ManageLogDao manageLogDao;
	@Autowired
	NatureDao natureDao;
	@Autowired
	RelationDao relationDao;
	@Autowired
	QualityCheckDao checkDao;
	@Autowired
	ReplyDao replyDao;
	private String filePath = "/files/qualityRectification";
	@Override
	public DataWrapper<Void> deleteQualityRectificationById(String token,Long id) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(!QualityManageDao.deleteQualityRectification(id)){
				result.setErrorCode(ErrorCodeEnum.Error);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<QualityRectification> addQualityRectification(String token, String fDate,String sendUsers,MultipartFile[] pics, MultipartFile[] vois, HttpServletRequest request,QualityRectification role,Long qualityCheckId) {
		DataWrapper<QualityRectification> result = new DataWrapper<QualityRectification>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(role!=null){
				role.setCreateUser(user.getId());
				if(fDate!=null){
					try {
						role.setFinishedDate(Parameters.getSdfs().parse(fDate));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				role.setCreateDate(new Date());
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
					role.setPictures(picsStr);
				}
				if(vois!=null){
					String voisStr="";
					for(int i=0;i<vois.length;i++){
						Files file=fileService.uploadFile(filePath+"/voices", pics[i], 3, request);
						if(i==(vois.length-1)){
							voisStr=voisStr+file.getId();
						}else{
							voisStr=voisStr+file.getId()+",";
						}
					}
					role.setVoices(voisStr);
				}
				role.setScore(-1);/*默认评分不存在*/
				if(role.getNoticeType()==2){
					role.setStatus(0);
				}else{
					role.setStatus(2);
				}
				if(!QualityManageDao.addQualityRectification(role)){
					result.setErrorCode(ErrorCodeEnum.Error);
				}else if(sendUsers!=null){
					QualityCheck check = checkDao.getById(qualityCheckId);
					if(check!=null){
						check.setQualityRectificationId(role.getId());
						check.setStatus(1);//已发整改
						checkDao.updateQualityCheck(check);
					}
					String[] users = sendUsers.split(",");
					List<Relation> sendUserList = new ArrayList<Relation>();
					for(int i=0;i<users.length;i++){
						Relation relation = new Relation();
						relation.setRelationType(0);
						relation.setUserId(Long.valueOf(users[i]));
						relation.setAboutId(role.getId());
						relation.setState(0);
						sendUserList.add(relation);
					}
					relationDao.addRelationList(sendUserList);
				}
			}
			
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<Void> deleteQualityRectificationByIdList(String token,String[] id) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(!QualityManageDao.deleteQualityRectificationList(id)){
				result.setErrorCode(ErrorCodeEnum.Error);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}


	@Override
	public DataWrapper<List<QualityRectificationPojo>> getQualityRectificationList(Integer pageIndex, Integer pageSize, QualityRectification qualityManage,
			String token) {
		DataWrapper<List<QualityRectificationPojo>> dp = new DataWrapper<List<QualityRectificationPojo>>();
		List<QualityRectificationPojo> dpp = new ArrayList<QualityRectificationPojo>();
		DataWrapper<List<QualityRectification>> dataWrapper = new DataWrapper<List<QualityRectification>>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			dataWrapper=QualityManageDao.getQualityRectificationList(pageIndex, pageSize, qualityManage);
			if(dataWrapper.getData()!=null){
				for(int i=0;i<dataWrapper.getData().size();i++){
					QualityRectificationPojo pojo=new QualityRectificationPojo();
					pojo.setCheckContent(dataWrapper.getData().get(i).getCheckContent());
					Project project = projectDao.getById(dataWrapper.getData().get(i).getProjectId());
					if(project!=null){
						pojo.setProjectName(project.getName());
					}
					pojo.setCheckList(dataWrapper.getData().get(i).getCheckList());
					pojo.setCreateDate(Parameters.getSdf().format(dataWrapper.getData().get(i).getCreateDate()));
					pojo.setId(dataWrapper.getData().get(i).getId());
					pojo.setFinishedDate(Parameters.getSdf().format(dataWrapper.getData().get(i).getFinishedDate()));
					pojo.setLevel(dataWrapper.getData().get(i).getLevel());
					pojo.setRectificationContent(dataWrapper.getData().get(i).getRectificationContent());
					pojo.setScore(dataWrapper.getData().get(i).getScore());
					pojo.setStatus(dataWrapper.getData().get(i).getStatus());/*0、待整改（默认值，提交时设置 ） 0、待复检  1、已通过*/
					pojo.setNoticeType(dataWrapper.getData().get(i).getNoticeType());
					User users =userDao.getById(dataWrapper.getData().get(i).getCreateUser());
					if(users!=null){
						pojo.setCreateUserName(users.getRealName());
					}
					List<Files> files1=fileDao.getByIds(dataWrapper.getData().get(i).getPictures());
					if(!files1.isEmpty()){
						List<String> pics = new ArrayList<String>();
						for(Files f:files1){
							pics.add(f.getUrl());
						}
						pojo.setPictures(pics);
					}
					List<Files> files2=fileDao.getByIds(dataWrapper.getData().get(i).getVoices());
					if(!files2.isEmpty()){
						List<String> vs = new ArrayList<String>();
						for(Files f:files2){
							vs.add(f.getUrl());
						}
						pojo.setVoices(vs);
					}
					if(dataWrapper.getData().get(i).getNatureId()!=null){
						List<String> natures = new ArrayList<String>();
						for(String s:dataWrapper.getData().get(i).getNatureId().split(",")){
							Nature nature = new Nature();
							nature = natureDao.getById(Long.valueOf(s));
							if(nature!=null){
								natures.add(nature.getContent());
							}
						}
						pojo.setNature(natures);
					}
					if(dataWrapper.getData().get(i).getCopyUser()!=null){
						List<String> userNames = new ArrayList<String>();
						for(String s:dataWrapper.getData().get(i).getCopyUser().split(",")){
							User userd = userDao.getById(Long.valueOf(s));
							if(userd!=null){
								userNames.add(userd.getRealName());
							}
						}
						pojo.setCopyUser(userNames);
					}
					if(dataWrapper.getData().get(i).getUpdateDate()!=null){
						pojo.setUpdateDate(Parameters.getSdf().format(dataWrapper.getData().get(i).getUpdateDate()));
					}
					dpp.add(pojo);
				}
				dp.setData(dpp);
				dp.setCallStatus(dataWrapper.getCallStatus());
				dp.setCurrentPage(dataWrapper.getCurrentPage());
				dp.setNumberPerPage(dataWrapper.getNumberPerPage());
				dp.setTotalNumber(dataWrapper.getTotalNumber());
				dp.setTotalPage(dataWrapper.getTotalPage());
				dp.setErrorCode(dataWrapper.getErrorCode());
			}
		}else{
			dp.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dp;
	}


	@Override
	public DataWrapper<Void> updateQualityRectification(String token,String fDate,MultipartFile[] pics, MultipartFile[] vois, HttpServletRequest request, QualityRectification QualityManage) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(QualityManage!=null){
				QualityRectification dp = new QualityRectification();
				dp = QualityManageDao.getById(QualityManage.getId());
				if(user.getId().equals(dp.getCreateUser()) || user.getUserType().equals(UserTypeEnum.Admin.getType())){
					dp.setUpdateDate(new Date());
					if(QualityManage.getCheckContent()!=null){
						dp.setCheckContent(QualityManage.getCheckContent());
					}
					if(QualityManage.getCheckList()!=null){
						dp.setCheckList(QualityManage.getCheckList());
					}
					if(fDate!=null){
						try {
							dp.setFinishedDate(Parameters.getSdfs().parse(fDate));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if(QualityManage.getNatureId()!=null){
						dp.setNatureId(QualityManage.getNatureId());
					}
					if(QualityManage.getLevel()!=null){
						dp.setLevel(QualityManage.getLevel());
					}
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
						dp.setPictures(picsStr);
					}
					if(vois!=null){
						String voisStr="";
						for(int i=0;i<vois.length;i++){
							Files file=fileService.uploadFile(filePath+"/voices", pics[i], 3, request);
							if(i==(vois.length-1)){
								voisStr=voisStr+file.getId();
							}else{
								voisStr=voisStr+file.getId()+",";
							}
						}
						dp.setVoices(voisStr);
					}
					if(!QualityManageDao.updateQualityRectification(dp)){
						result.setErrorCode(ErrorCodeEnum.Error);
					}
				}else{
					result.setErrorCode(ErrorCodeEnum.AUTH_Error);
				}
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<Void> qualityRectificationCheckAgain(String token, Long qualityId, Integer score, Integer state) {
		// TODO Auto-generated method stub
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			QualityRectification qm = QualityManageDao.getById(qualityId);
			if(qm!=null){
				if(user.getId().equals(qm.getCreateUser()) || user.getUserType().equals(UserTypeEnum.Admin.getType())){
					qm.setScore(score);
					ManageLog manageLog = new ManageLog();
					manageLog.setAboutId(qualityId);
					manageLog.setActionDate(new Date());
					manageLog.setOperateUser(user.getId());
					manageLog.setManageType(0);
					manageLog.setActionType(1);
					if(state==1){
						qm.setStatus(2);//已通过
					}
					if(state==0){
						qm.setStatus(0);//待整改
					}
					if(QualityManageDao.updateQualityRectification(qm)){
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
		return null;
	}

	@Override
	public DataWrapper<Void> qualityRectificationCheck(String token, Long qualityId, Integer schedule, String content,
			MultipartFile[] pics, MultipartFile[] voices,HttpServletRequest request) {
		// TODO Auto-generated method stub
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			List<Relation> qms = relationDao.getRelationListsByIds(0, qualityId, user.getId());
			if(!qms.isEmpty() || user.getUserType()==UserTypeEnum.Admin.getType()){
				Reply reply = new Reply();
				reply.setAboutId(qualityId);
				reply.setReplyType(0);
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
				if(voices!=null){
					String voisStr="";
					for(int i=0;i<voices.length;i++){
						Files file=fileService.uploadFile(filePath+"/voices", pics[i], 3, request);
						if(i==(voices.length-1)){
							voisStr=voisStr+file.getId();
						}else{
							voisStr=voisStr+file.getId()+",";
						}
					}
					reply.setVoices(voisStr);
				}
				if(replyDao.addReply(reply)){
					if(schedule==100){
						QualityRectification qm = QualityManageDao.getById(qualityId);
						qm.setStatus(1);
						QualityManageDao.updateQualityRectification(qm);
					}
				}
			}else{
				result.setErrorCode(ErrorCodeEnum.AUTH_Error);
			}
		}
		return result;
	}

}
