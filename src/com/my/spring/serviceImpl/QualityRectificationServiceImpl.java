package com.my.spring.serviceImpl;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.my.spring.DAO.AwardReadStateDao;
import com.my.spring.DAO.FileDao;
import com.my.spring.DAO.ManageLogDao;
import com.my.spring.DAO.NatureDao;
import com.my.spring.DAO.ProjectDao;
import com.my.spring.DAO.QualityCheckDao;
import com.my.spring.DAO.QualityCheckReadStateDao;
import com.my.spring.DAO.QualityRectificationDao;
import com.my.spring.DAO.QualityRectificationReadStateDao;
import com.my.spring.DAO.RelationDao;
import com.my.spring.DAO.ReplyDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.jpush.PushExample;
import com.my.spring.model.QualityRectification;
import com.my.spring.model.QualityRectificationPojo;
import com.my.spring.model.QualityRectificationReadState;
import com.my.spring.model.RectifyPojo;
import com.my.spring.model.Relation;
import com.my.spring.model.Reply;
import com.my.spring.model.AwardReadState;
import com.my.spring.model.Files;
import com.my.spring.model.ManageLog;
import com.my.spring.model.ManageStates;
import com.my.spring.model.Nature;
import com.my.spring.model.Project;
import com.my.spring.model.QualityCheck;
import com.my.spring.model.QualityCheckReadState;
import com.my.spring.model.User;
import com.my.spring.model.UserId;
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
	QualityRectificationReadStateDao readDao;
	@Autowired
	QualityCheckReadStateDao readDao2;
	@Autowired
	AwardReadStateDao readDao3;
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
					fDate=fDate+" 00:00:00";
					try {
						role.setFinishedDate(Parameters.getSdf().parse(fDate));
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
						Files file=fileService.uploadFile(filePath+"/voices", vois[i], 3, request);
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
				role.setQualityCheckId(qualityCheckId);
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
						relation.setProjectId(role.getProjectId());
						sendUserList.add(relation);
					}
					if(relationDao.addRelationList(sendUserList)){
						List<QualityRectificationReadState> nl = new ArrayList<QualityRectificationReadState>();
						List<UserId> userIdList = userDao.getAllUserIdListByProjectId(role.getProjectId());
						if(!userIdList.isEmpty()){
							for(UserId s:userIdList){
								QualityRectificationReadState nl2 = new QualityRectificationReadState();
								nl2.setQualityRectificationId(role.getId());
								nl2.setUserId(s.getId());
								nl2.setState(0);
								nl.add(nl2);
							}
							
						}
						readDao.addQualityRectificationReadStateList(nl);
					}
					HashMap<String,String> hq = new HashMap<String,String>();
					List<User> userListCopyUser = new ArrayList<User>();
					if(role.getCopyUser()!=null){
						List<String> aa = new ArrayList<String>();
						for(String s:role.getCopyUser().split(",")){
							aa.add(s);
						}
						for(String str:sendUsers.split(",")){
							aa.add(str);
						}
						userListCopyUser=userDao.findUserLikeProjct(aa);
					}
					
					Project po = projectDao.getById(role.getProjectId());
					hq.put("projectName",po.getName());
					hq.put("aboutId", role.getId().toString());
					hq.put("createDate", Parameters.getSdfs().format(new Date()));
					String content="";
					if(po!=null)
						content=user.getRealName()+"在"+po.getName()+"项目里提交了一个整改单";
					else
						content=user.getRealName()+"提交了一个整改单";
					////推送人包括自己
					String[] userids=new String[userListCopyUser.size()+1];
					for(int b =0;b<userListCopyUser.size();b++){
						userids[b]=userListCopyUser.get(b).getId().toString();
					}
					userids[userids.length-1]=user.getId().toString();
					///0、质量   1、安全   2、施工任务单  3、 预付单  4、留言
					PushExample.testSendPushWithCustomConfig_ios(userids, content,0,hq);
					PushExample.testSendPushWithCustomConfig_android(userids, content,0,hq);
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
			String token,String ids,String start,String end,String find) {
		DataWrapper<List<QualityRectificationPojo>> dp = new DataWrapper<List<QualityRectificationPojo>>();
		List<QualityRectificationPojo> dpp = new ArrayList<QualityRectificationPojo>();
		DataWrapper<List<QualityRectification>> dataWrapper = new DataWrapper<List<QualityRectification>>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			List<User> userss = userDao.getByUserNames(find);
			if(userss!=null){
				dataWrapper=QualityManageDao.getQualityRectificationList(pageIndex, pageSize, qualityManage,ids,start,end,userss);
			}else{
				dataWrapper=QualityManageDao.getQualityRectificationLists(pageIndex, pageSize, qualityManage,ids,start,end,find);
			}
			if(dataWrapper.getData()!=null){
				Relation relation = new Relation();
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
					List<RectifyPojo> relations = new ArrayList<RectifyPojo>();
					relation.setAboutId(dataWrapper.getData().get(i).getId());
					relation.setRelationType(0);
					relation.setProjectId(dataWrapper.getData().get(i).getProjectId());
					relations = relationDao.getRelationListsByAboutId(relation);
					if(!relations.isEmpty()){
						List<String> names = new ArrayList<String>();
						for(RectifyPojo re:relations){
							names.add(re.getUserName());
						}
						pojo.setRectifyUser(names);
					}
					
					User users =userDao.getById(dataWrapper.getData().get(i).getCreateUser());
					if(users!=null){
						pojo.setCreateUserName(users.getRealName());
					}
					if(dataWrapper.getData().get(i).getPictures()!=null && !dataWrapper.getData().get(i).getPictures().equals("")){
						List<Files> files1=fileDao.getByIds(dataWrapper.getData().get(i).getPictures());
						if(!files1.isEmpty()){
							List<String> pics = new ArrayList<String>();
							for(Files f:files1){
								pics.add(f.getUrl());
							}
							pojo.setPictures(pics);
						}
					}
					if(dataWrapper.getData().get(i).getVoices()!=null && !dataWrapper.getData().get(i).getVoices().equals("")){
						List<Files> files2=fileDao.getByIds(dataWrapper.getData().get(i).getVoices());
						if(!files2.isEmpty()){
							List<String> vs = new ArrayList<String>();
							for(Files f:files2){
								vs.add(f.getUrl());
							}
							pojo.setVoices(vs);
						}
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
				readDao.updateAllQualityRectifitionReadStateByUserId(user.getId());
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
						Files file=fileService.uploadFile(filePath+"/voices", voices[i], 3, request);
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
						List<Relation> relations = new ArrayList<Relation>();
						relations=relationDao.getRelationListsByIds(0, qualityId, null);
						for(Relation re:relations){
							re.setState(1);
							relationDao.updateRelation(re);
						}
						QualityRectification qm = QualityManageDao.getById(qualityId);
						qm.setStatus(1);//待复检
						QualityManageDao.updateQualityRectification(qm);
					}
				}
			}else{
				result.setErrorCode(ErrorCodeEnum.AUTH_Error);
			}
		}
		return result;
	}

	@Override
	public DataWrapper<ManageStates> getAboutMeNums(String token, Long projectId, Integer type) {
		// TODO Auto-generated method stub
		DataWrapper<ManageStates> result = new DataWrapper<ManageStates>();
		User user = SessionManager.getSession(token);
		
		if(user!=null){
			ManageStates ms=new ManageStates();
			if(type==0){
				///待复检查找
				List<QualityRectification> gets = new ArrayList<QualityRectification>();
				QualityRectification qr = new QualityRectification();
				qr.setProjectId(projectId);
				qr.setCreateUser(user.getId());
				qr.setStatus(1);
				gets=QualityManageDao.getQualityRectificationList(qr);
				String ids1="";
				for(int i=0;i<gets.size();i++){
					if(i==(gets.size()-1)){
						ids1=ids1+gets.get(i).getId();
					}else{
						ids1=ids1+gets.get(i).getId()+",";
					}
				}
				ms.setCheckIds(ids1);
				ms.setCheckNum(gets.size());
				///待整改查找
				List<Relation> res = new ArrayList<Relation>();
				Relation rl = new Relation();
				rl.setUserId(user.getId());
				rl.setState(0);
				rl.setProjectId(projectId);
				res = relationDao.getRelationLists(rl);
				String ids2="";
				for(int j=0;j<res.size();j++){
					if(j==(res.size()-1)){
						ids2=ids2+res.get(j).getAboutId();
					}else{
						ids2=ids2+res.get(j).getAboutId()+",";
					}
				}
				ms.setRectifyNum(res.size());
				ms.setRectifyIds(ids2);
				///////未读整改单确认
				List<QualityRectificationReadState> aec = new ArrayList<QualityRectificationReadState>();
				QualityRectificationReadState qs = new QualityRectificationReadState();
				qs.setUserId(user.getId());
				qs.setState(0);;
				aec = readDao.getQualityRectificationReadStateLists(qs);
				if(aec.isEmpty()){
					ms.setQualityState(0);//无小红点
				}else{
					ms.setQualityState(1);//有小红点
				}
				///////未读检查单确认
				List<QualityCheckReadState> aec2 = new ArrayList<QualityCheckReadState>();
				QualityCheckReadState qs2 = new QualityCheckReadState();
				qs2.setUserId(user.getId());
				qs2.setState(0);;
				aec2 = readDao2.getQualityCheckReadStateLists(qs2);
				if(aec2.isEmpty()){
					ms.setCheckState(0);
				}else{
					ms.setCheckState(1);
				}
				///////未读奖惩单确认
				List<AwardReadState> aec3 = new ArrayList<AwardReadState>();
				AwardReadState qs3 = new AwardReadState();
				qs3.setUserId(user.getId());
				qs3.setState(0);;
				aec3 = readDao3.getAwardReadStateLists(qs3);
				if(aec3.isEmpty()){
					ms.setAwardState(0);
				}else{
					ms.setAwardState(1);
				}
			}
			result.setData(ms);
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

}
