package com.my.spring.serviceImpl;

import com.my.spring.DAO.FileDao;
import com.my.spring.DAO.ProjectDao;
import com.my.spring.DAO.QuestionFileDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.CallStatusEnum;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.AdvancedOrderCopy;
import com.my.spring.model.CommonNotice;
import com.my.spring.model.ConstructionTaskCopy;
import com.my.spring.model.Files;
import com.my.spring.model.Notice;
import com.my.spring.model.QuestionCopy;
import com.my.spring.model.QuestionFile;
import com.my.spring.model.User;
import com.my.spring.model.UserLog;
import com.my.spring.parameters.Parameters;
import com.my.spring.parameters.ProjectDatas;
import com.my.spring.service.AdvancedOrderService;
import com.my.spring.service.ConstructionTaskService;
import com.my.spring.service.NoticeService;
import com.my.spring.service.QualityQuestionService;
import com.my.spring.service.QuestionService;
import com.my.spring.service.UserLogService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {
	@Autowired
	QuestionService questionService;
	@Autowired
	QualityQuestionService qualityQuestionService;
	@Autowired
	AdvancedOrderService advancedOrderService;
	@Autowired
	QuestionFileDao questionFileDao;
	@Autowired
	ProjectDao projectDao;
	@Autowired
	UserDao userDao;
	@Autowired
	UserLogService userLogService;
	@Autowired
	FileDao fileDao;
	@Autowired
	ConstructionTaskService constructionTaskService;
	@Override
	public DataWrapper<Notice> getById(String token, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataWrapper<Void> deleteNoticeById(String token, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataWrapper<Void> deleteNoticeByIdList(String token, String[] id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataWrapper<Void> addNotice(String token, Notice file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataWrapper<JSONArray> getMenuListMapByIdList(List<Notice> menu) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataWrapper<List<CommonNotice>> getCommonNotices(String token, Integer pageSize, Integer pageIndex) {
		DataWrapper<List<CommonNotice>> resultList = new DataWrapper<List<CommonNotice>>();
		List<CommonNotice> result = new ArrayList<CommonNotice>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(user.getSystemId()!=null){
				UserLog userLog = new UserLog();
    			userLog.setProjectPart(ProjectDatas.Notification_area.getCode());
    			userLog.setActionDate(new Date());
    			userLog.setActionType(0);
    			userLog.setUserId(user.getId());
    			userLog.setSystemType(user.getSystemId());
    			//userLog.setVersion("3.0");
    			userLogService.addUserLog(userLog,token);
    		}
			DataWrapper<List<QuestionCopy>> questionCopy1 = new DataWrapper<List<QuestionCopy>>();
			DataWrapper<List<QuestionCopy>> questionCopy2 = new DataWrapper<List<QuestionCopy>>();
			DataWrapper<List<AdvancedOrderCopy>> advancedOrderCopy = new DataWrapper<List<AdvancedOrderCopy>>();
			DataWrapper<List<ConstructionTaskCopy>> constructionTaskCopy = new DataWrapper<List<ConstructionTaskCopy>>();
			Integer totalNum=0;
			////////获取质量问题列表
			questionCopy1=qualityQuestionService.getQuestionListOfNotRead(token, pageIndex, pageSize);
			totalNum=questionCopy1.getTotalNumber();
			if(questionCopy1.getData().size()>0){
				if(questionCopy1.getData().get(0).getId()!=null){
					for(QuestionCopy cn:questionCopy1.getData()){
						CommonNotice commonNotice = new CommonNotice();
						commonNotice.setNoticeType(0);
						commonNotice.setAboutId(cn.getId());
						commonNotice.setContent(cn.getIntro());
						User userQuality=userDao.getById(cn.getUser_id());
						if(userQuality!=null){
							commonNotice.setCreateUserName(userQuality.getRealName());
							if(userQuality.getUserIcon()!=null){
								Files urlfiles = fileDao.getById(userQuality.getUserIcon());
								commonNotice.setUserIconUrl(urlfiles.getUrl());
							}else if(userQuality.getUserIconUrl()!=null){
								commonNotice.setUserIconUrl(userQuality.getUserIconUrl());
							}
						}
						commonNotice.setCreateDate(Parameters.getSdf().format(cn.getQuestion_date()));
						commonNotice.setTitle("提交了一个质量整改单");
						commonNotice.setProjectName("来自  "+projectDao.getById(cn.getProject_id()).getName());
						DataWrapper<List<QuestionFile>> images = questionFileDao.getQuestionFileByQualityId(cn.getId());
						if(images.getData()!=null){
							if(images.getData().size()>0){
								Files files = fileDao.getById(images.getData().get(0).getFileId());
								if(files!=null){
									commonNotice.setImagUrl(files.getUrl());
								}
							}
						}
						result.add(commonNotice);
					}
				}
			}
			///////未读安全问题列表获取
			questionCopy2 = questionService.getQuestionListOfNotRead(token, pageIndex, pageSize);
			totalNum+=questionCopy2.getTotalNumber();
			if(questionCopy2.getData().size()>0){
				if(questionCopy2.getData().get(0).getId()!=null){
					for(QuestionCopy cn1:questionCopy2.getData()){
						CommonNotice commonNotice1 = new CommonNotice();
						commonNotice1.setNoticeType(1);
						commonNotice1.setContent(cn1.getIntro());
						commonNotice1.setAboutId(cn1.getId());
						commonNotice1.setCreateDate(Parameters.getSdf().format(cn1.getQuestion_date()));
						commonNotice1.setTitle("提交了一个安全整改单");
						User userQuestion=userDao.getById(cn1.getUser_id());
						if(userQuestion!=null){
							commonNotice1.setCreateUserName(userQuestion.getRealName());
							if(userQuestion.getUserIcon()!=null){
								Files urlfiles = fileDao.getById(userQuestion.getUserIcon());
								commonNotice1.setUserIconUrl(urlfiles.getUrl());
							}else if(userQuestion.getUserIconUrl()!=null){
								commonNotice1.setUserIconUrl(userQuestion.getUserIconUrl());
							}
						}
						commonNotice1.setProjectName("来自  "+projectDao.getById(cn1.getProject_id()).getName());
						DataWrapper<List<QuestionFile>> images = questionFileDao.getQuestionFileByQuestionId(cn1.getId());
						if(images.getData()!=null){
							if(images.getData().size()>0){
								Files files = fileDao.getById(images.getData().get(0).getFileId());
								if(files!=null){
									commonNotice1.setImagUrl(files.getUrl());
								}
							}
						}
						result.add(commonNotice1);
					}
				}
			}
			///////////未读预付单单列表获取
			advancedOrderCopy = advancedOrderService.getAdvancedOrderListOfNotRead(token, pageSize, pageIndex);
			totalNum+=advancedOrderCopy.getTotalNumber();
			if(advancedOrderCopy.getData().size()>0){
				if(advancedOrderCopy.getData().get(0).getId()!=null){
					for(AdvancedOrderCopy cn2:advancedOrderCopy.getData()){
						CommonNotice commonNotice2 = new CommonNotice();
						commonNotice2.setNoticeType(3);
						commonNotice2.setAboutId(cn2.getId());
						User userQuestion=userDao.getById(cn2.getSubmitUserId());
						if(userQuestion!=null){
							commonNotice2.setCreateUserName(userQuestion.getRealName());
							if(userQuestion.getUserIcon()!=null){
								Files urlfiles = fileDao.getById(userQuestion.getUserIcon());
								commonNotice2.setUserIconUrl(urlfiles.getUrl());
							}else if(userQuestion.getUserIconUrl()!=null){
								commonNotice2.setUserIconUrl(userQuestion.getUserIconUrl());
							}
						}
						commonNotice2.setContent(cn2.getQuantityDes());
						commonNotice2.setCreateDate(Parameters.getSdf().format(cn2.getCreateDate()));
						commonNotice2.setTitle("提交了一个预付单");
						commonNotice2.setProjectName("来自  "+projectDao.getById(cn2.getProjectId()).getName());
						if(cn2.getContentFilesId()!=null){
							Files files = fileDao.getById(Long.valueOf(cn2.getContentFilesId().split(",")[0]));
							if(files!=null){
								commonNotice2.setImagUrl(files.getUrl());
							}
						}
						result.add(commonNotice2);
					}
				}
			}
			////////未读施工任务单列表获取
			constructionTaskCopy = constructionTaskService.getConstructionTaskListNotRead(token, pageSize, pageIndex);
			totalNum+=constructionTaskCopy.getTotalNumber();
			if(constructionTaskCopy.getData().size()>0){
				if(constructionTaskCopy.getData().get(0).getId()!=null){
					for(ConstructionTaskCopy cn3:constructionTaskCopy.getData()){
						CommonNotice commonNotice3 = new CommonNotice();
						commonNotice3.setAboutId(cn3.getId());
						commonNotice3.setNoticeType(2);
						commonNotice3.setContent(cn3.getDetailContent());
						User userQuestion=userDao.getById(cn3.getUserId());
						if(userQuestion!=null){
							commonNotice3.setCreateUserName(userQuestion.getRealName());
							if(userQuestion.getUserIcon()!=null){
								Files urlfiles = fileDao.getById(userQuestion.getUserIcon());
								commonNotice3.setUserIconUrl(urlfiles.getUrl());
							}else if(userQuestion.getUserIconUrl()!=null){
								commonNotice3.setUserIconUrl(userQuestion.getUserIconUrl());
							}
						}
						commonNotice3.setCreateDate(Parameters.getSdf().format(cn3.getCreateDate()));
						commonNotice3.setTitle("提交了一个施工任务单");
						commonNotice3.setProjectName("来自  "+projectDao.getById(cn3.getProjectId()).getName());
						if(cn3.getFileIdList()!=null){
							Files files = fileDao.getById(Long.valueOf(cn3.getFileIdList().split(",")[0]));
							if(files!=null){
								commonNotice3.setImagUrl(files.getUrl());
							}
						}
						
						result.add(commonNotice3);
					}
				}
			}
			if(result.size()>0){
				resultList.setData(result);
			}
			resultList.setTotalNumber(totalNum);
		}else{
			resultList.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		if(resultList.getCallStatus()==CallStatusEnum.SUCCEED && resultList.getData()==null){
			List<CommonNotice> str = new ArrayList<CommonNotice>();
			resultList.setData(str);
        }
		return resultList;
	}
   

}
