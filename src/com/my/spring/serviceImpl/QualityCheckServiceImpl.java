package com.my.spring.serviceImpl;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.my.spring.DAO.QualityCheckReadStateDao;
import com.my.spring.DAO.RelationDao;
import com.my.spring.DAO.ReplyDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.jpush.PushExample;
import com.my.spring.model.QualityCheck;
import com.my.spring.model.QualityCheckPartPojo;
import com.my.spring.model.QualityCheckPojo;
import com.my.spring.model.QualityCheckReadState;
import com.my.spring.model.Files;
import com.my.spring.model.Nature;
import com.my.spring.model.Project;
import com.my.spring.model.User;
import com.my.spring.model.UserId;
import com.my.spring.parameters.Parameters;
import com.my.spring.service.FileService;
import com.my.spring.service.QualityCheckService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

@Service("QualityCheckService")
public class QualityCheckServiceImpl implements QualityCheckService  {
	@Autowired
	QualityCheckDao QualityManageDao;
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
	QualityCheckReadStateDao readDao;
	@Autowired
	NatureDao natureDao;
	@Autowired
	RelationDao relationDao;
	@Autowired
	ReplyDao replyDao;
	private String filePath = "/files/qualityCheck";
	@Override
	public DataWrapper<Void> deleteQualityCheckById(String token,Long id) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(!QualityManageDao.deleteQualityCheck(id)){
				result.setErrorCode(ErrorCodeEnum.Error);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<Long> addQualityCheck(String token,MultipartFile[] pics, MultipartFile[] vois, HttpServletRequest request,QualityCheck role) {
		DataWrapper<Long> result = new DataWrapper<Long>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(role!=null){
				role.setCreateUser(user.getId());
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
				if(role.getNoticeType()==2){
					role.setStatus(0);///未发整改
				}else{
					role.setStatus(2);///空状态
				}
				if(!QualityManageDao.addQualityCheck(role)){
					result.setErrorCode(ErrorCodeEnum.Error);
				}else{
					result.setData(role.getId());
					List<QualityCheckReadState> nl = new ArrayList<QualityCheckReadState>();
					List<UserId> userIdList = userDao.getAllUserIdListByProjectId(role.getProjectId());
					if(!userIdList.isEmpty()){
						for(UserId s:userIdList){
							QualityCheckReadState nl2 = new QualityCheckReadState();
							nl2.setQualityCheckId(role.getId());
							nl2.setUserId(s.getId());
							nl2.setState(0);
							nl.add(nl2);
						}
						
					}
					readDao.addQualityCheckReadStateList(nl);
					HashMap<String,String> hq = new HashMap<String,String>();
					List<User> userListCopyUser = new ArrayList<User>();
					if(role.getInformUser()!=null){
						List<String> aa = new ArrayList<String>();
						for(String s:role.getInformUser().split(",")){
							aa.add(s);
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
	public DataWrapper<Void> deleteQualityCheckByIdList(String token,String[] id) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(!QualityManageDao.deleteQualityCheckList(id)){
				result.setErrorCode(ErrorCodeEnum.Error);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}


	@Override
	public DataWrapper<List<QualityCheckPojo>> getQualityCheckList(Integer pageIndex, Integer pageSize, QualityCheck qualityManage,
			String token) {
		DataWrapper<List<QualityCheckPojo>> dp = new DataWrapper<List<QualityCheckPojo>>();
		List<QualityCheckPojo> dpp = new ArrayList<QualityCheckPojo>();
		DataWrapper<List<QualityCheck>> dataWrapper = new DataWrapper<List<QualityCheck>>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			dataWrapper=QualityManageDao.getQualityCheckList(pageIndex, pageSize, qualityManage);
			if(dataWrapper.getData()!=null){
				for(int i=0;i<dataWrapper.getData().size();i++){
					QualityCheckPojo pojo=new QualityCheckPojo();
					pojo.setCheckContent(dataWrapper.getData().get(i).getCheckContent());
					Project project = projectDao.getById(dataWrapper.getData().get(i).getProjectId());
					if(project!=null){
						pojo.setProjectName(project.getName());
					}
					pojo.setCheckList(dataWrapper.getData().get(i).getCheckList());
					pojo.setCreateDate(Parameters.getSdf().format(dataWrapper.getData().get(i).getCreateDate()));
					pojo.setId(dataWrapper.getData().get(i).getId());
					pojo.setStatus(dataWrapper.getData().get(i).getStatus());/*0、待整改（默认值，提交时设置 ） 0、待复检  1、已通过*/
					pojo.setNoticeType(dataWrapper.getData().get(i).getNoticeType());
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
						pojo.setNatureId(natures);
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
				readDao.updateAllQualityCheckReadStateByUserId(user.getId());
			}
		}else{
			dp.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dp;
	}


	@Override
	public DataWrapper<Void> updateQualityCheck(String token,MultipartFile[] pics, MultipartFile[] vois, HttpServletRequest request, QualityCheck QualityManage) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(QualityManage!=null){
				QualityCheck dp = new QualityCheck();
				dp = QualityManageDao.getById(QualityManage.getId());
				if(user.getId().equals(dp.getCreateUser()) || user.getUserType().equals(UserTypeEnum.Admin.getType())){
					dp.setUpdateDate(new Date());
					if(QualityManage.getCheckContent()!=null){
						dp.setCheckContent(QualityManage.getCheckContent());
					}
					if(QualityManage.getCheckList()!=null){
						dp.setCheckList(QualityManage.getCheckList());
					}
					
					if(QualityManage.getNatureId()!=null){
						dp.setNatureId(QualityManage.getNatureId());
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
					if(!QualityManageDao.updateQualityCheck(dp)){
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
	public DataWrapper<List<QualityCheckPartPojo>> getQualityCheckPartList(Integer pageIndex, Integer pageSize,String token,Long projectId) {
		DataWrapper<List<QualityCheckPartPojo>> dp = new DataWrapper<List<QualityCheckPartPojo>>();
		List<QualityCheckPartPojo> dpp = new ArrayList<QualityCheckPartPojo>();
		DataWrapper<List<QualityCheck>> dataWrapper = new DataWrapper<List<QualityCheck>>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			QualityCheck qc = new QualityCheck();
			qc.setProjectId(projectId);
			dataWrapper=QualityManageDao.getQualityCheckList(pageIndex, pageSize, qc);
			if(dataWrapper.getData()!=null){
				for(int i=0;i<dataWrapper.getData().size();i++){
					QualityCheckPartPojo pojo=new QualityCheckPartPojo();
					Project project = projectDao.getById(dataWrapper.getData().get(i).getProjectId());
					if(project!=null){
						pojo.setProjectName(project.getName());
					}
					pojo.setCheckList(dataWrapper.getData().get(i).getCheckList());
					pojo.setCreateDate(Parameters.getSdf().format(dataWrapper.getData().get(i).getCreateDate()));
					pojo.setId(dataWrapper.getData().get(i).getId());
					User users =userDao.getById(dataWrapper.getData().get(i).getCreateUser());
					if(users!=null){
						pojo.setCreateUserName(users.getRealName());
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

}
