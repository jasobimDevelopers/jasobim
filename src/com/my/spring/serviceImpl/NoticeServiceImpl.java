package com.my.spring.serviceImpl;

import com.my.spring.DAO.AdvancedOrderDao;
import com.my.spring.DAO.ConstructionTaskNewDao;
import com.my.spring.DAO.FileDao;
import com.my.spring.DAO.MessageDao;
import com.my.spring.DAO.MessageFileDao;
import com.my.spring.DAO.NoticeDao;
import com.my.spring.DAO.ProjectDao;
import com.my.spring.DAO.QualityQuestionDao;
import com.my.spring.DAO.QuestionDao;
import com.my.spring.DAO.QuestionFileDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.DAO.UserProjectDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.AdvancedOrder;
import com.my.spring.model.AllItemData;
import com.my.spring.model.CommonNotice;
import com.my.spring.model.ConstructionTaskNew;
import com.my.spring.model.Files;
import com.my.spring.model.Message;
import com.my.spring.model.MessageFile;
import com.my.spring.model.Notice;
import com.my.spring.model.Project;
import com.my.spring.model.QualityQuestion;
import com.my.spring.model.Question;
import com.my.spring.model.QuestionFile;
import com.my.spring.model.User;
import com.my.spring.parameters.Parameters;
import com.my.spring.service.AdvancedOrderService;
import com.my.spring.service.NoticeService;
import com.my.spring.service.QualityQuestionService;
import com.my.spring.service.QuestionService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.DataWrapperDiy;
import com.my.spring.utils.SessionManager;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {
	
	@Autowired
	NoticeDao noticeDao;
	@Autowired
	QuestionService questionService;
	@Autowired
	QualityQuestionService qualityQuestionService;
	@Autowired
	QualityQuestionDao qualityQuestionDao;
	@Autowired
	QuestionDao questionDao;
	@Autowired
	AdvancedOrderDao advancedOrderDao;
	@Autowired
	ConstructionTaskNewDao constructionTaskNewDao;
	@Autowired
	AdvancedOrderService advancedOrderService;
	@Autowired
	QuestionFileDao questionFileDao;
	@Autowired
	ProjectDao projectDao;
	@Autowired
	UserDao userDao;
	@Autowired
	FileDao fileDao;

	@Autowired
	MessageDao messageDao;
	@Autowired
	MessageFileDao messageFileDao;
	@Autowired
	UserProjectDao userProjectDao; 
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
	public DataWrapperDiy<List<CommonNotice>> getAllNoticeList(String token, Integer pageSize, Integer pageIndex) {
		// TODO Auto-generated method stub
		DataWrapperDiy<List<CommonNotice>> result = new DataWrapperDiy<List<CommonNotice>>();
		List<CommonNotice> resultList = new ArrayList<CommonNotice>();
		User user = SessionManager.getSession(token); 
		if(user!=null){
			DataWrapper<List<Notice>> gets = noticeDao.getListByUserId(pageSize,pageIndex,user.getId());
			Integer totalNotRead=0;
			totalNotRead= noticeDao.getListNotRead(user.getId());
			if(gets.getData()!=null){
				if(!gets.getData().isEmpty()){
					result.setCurrentPage(gets.getCurrentPage());
					result.setNumberPerPage(gets.getNumberPerPage());
					result.setTotalNumber(gets.getTotalNumber());
					result.setTotalPage(gets.getTotalPage());
					for(Notice no:gets.getData()){
						//0 质量问题、 1 安全问题、 2 施工任务单、 3 预付单 、4 留言
						switch(no.getNoticeType()){
				        case 0:
				            {
				            	CommonNotice commonNotice = new CommonNotice();
								commonNotice.setCreateDate(Parameters.getSdf().format(no.getCreateDate()));
								commonNotice.setNoticeType(no.getNoticeType());
								commonNotice.setReadState(no.getReadState());
								commonNotice.setAboutId(no.getAboutId());
				            	QualityQuestion qualityQuestion = new QualityQuestion();
				            	qualityQuestion=qualityQuestionDao.getById(no.getAboutId());
				            	if(qualityQuestion!=null){
				            		commonNotice.setAboutCreateUserId(qualityQuestion.getUserId());
				            		User createUser=userDao.getById(qualityQuestion.getUserId());
									if(createUser!=null){
										commonNotice.setCreateUserName(createUser.getRealName());
										if(createUser.getUserIcon()!=null){
											Files urlfiles = fileDao.getById(createUser.getUserIcon());
											if(urlfiles!=null){
												commonNotice.setUserIconUrl(urlfiles.getUrl());
											}
										}else if(createUser.getUserIconUrl()!=null){
											commonNotice.setUserIconUrl(createUser.getUserIconUrl());
										}
									}
									if(qualityQuestion.getState()==0){
										if(createUser!=null){
					            			commonNotice.setTitle(createUser.getRealName()+"提交了一个质量问题已完成");
					            		}
			            			}
			            			if(qualityQuestion.getState()==1){
			            				commonNotice.setTitle(createUser.getRealName()+"提交了一个安全问题已完成");
			            			}
				            		
				            		commonNotice.setContent(qualityQuestion.getIntro());
				            		Project project = projectDao.getById(qualityQuestion.getProjectId());
				            		if(project!=null){
				            			commonNotice.setProjectName(project.getName());
				            		}
				            		if(createUser.getUserIconUrl()!=null){
				            			commonNotice.setUserIconUrl(createUser.getUserIconUrl());
				            		}
				            		if(createUser.getUserIcon()!=null){
				            			Files userIcon = fileDao.getById(createUser.getUserIcon());
				            			if(userIcon!=null){
				            				commonNotice.setUserIconUrl(userIcon.getUrl());
				            			}
				            		}
				            		DataWrapper<List<QuestionFile>> images = questionFileDao.getQuestionFileByQualityId(qualityQuestion.getId());
									if(images.getData()!=null){
										if(!images.getData().isEmpty()){
											Files files = fileDao.getById(images.getData().get(0).getFileId());
											if(files!=null){
												commonNotice.setImagUrl(files.getUrl());
											}
										}
									}
									commonNotice.setName(qualityQuestion.getName());
									if(qualityQuestion.getUserList()!=null){
										String[] strs = qualityQuestion.getUserList().split(",");
										List<String> nameList = new ArrayList<String>();
										for(int q=0;q<strs.length;q++){
											User sendUser = userDao.getById(Long.valueOf(strs[q]));
											if(sendUser!=null){
												nameList.add(sendUser.getRealName());
											}
										}
										commonNotice.setSendUserList(nameList);
									}
									resultList.add(commonNotice);
				            	}
				            	break;
				            }
				        case 1:
				        {
				        	CommonNotice commonNotice = new CommonNotice();
							commonNotice.setCreateDate(Parameters.getSdf().format(no.getCreateDate()));
							commonNotice.setNoticeType(no.getNoticeType());
							commonNotice.setAboutId(no.getAboutId());
							commonNotice.setReadState(no.getReadState());
				        	Question question = new Question();
				        	question=questionDao.getById(no.getAboutId());
			            	if(question!=null){
			            		commonNotice.setAboutCreateUserId(question.getUserId());
			            		User createUser=userDao.getById(question.getUserId());
								if(createUser!=null){
									commonNotice.setCreateUserName(createUser.getRealName());
									if(createUser.getUserIcon()!=null){
										Files urlfiles = fileDao.getById(createUser.getUserIcon());
										if(urlfiles!=null){
											commonNotice.setUserIconUrl(urlfiles.getUrl());
										}
									}else if(createUser.getUserIconUrl()!=null){
										commonNotice.setUserIconUrl(createUser.getUserIconUrl());
									}
								}
			            		if(createUser!=null){
			            			if(question.getState()==0){
			            				commonNotice.setTitle(createUser.getRealName()+"提交了一个安全问题待处理");
			            			}
			            			if(question.getState()==1){
			            				commonNotice.setTitle(createUser.getRealName()+"提交了一个安全问题已完成");
			            			}
			            		}
			            		commonNotice.setContent(question.getIntro());
			            		Project project = projectDao.getById(question.getProjectId());
			            		if(project!=null){
			            			commonNotice.setProjectName(project.getName());
			            		}
			            		DataWrapper<List<QuestionFile>> images = questionFileDao.getQuestionFileByQuestionId(question.getId());
								if(images.getData()!=null){
									if(!images.getData().isEmpty()){
										Files files = fileDao.getById(images.getData().get(0).getFileId());
										if(files!=null){
											commonNotice.setImagUrl(files.getUrl());
										}
									}
								}
								if(question.getUserList()!=null){
									String[] strs = question.getUserList().split(",");
									List<String> nameList = new ArrayList<String>();
									for(int q=0;q<strs.length;q++){
										User sendUser = userDao.getById(Long.valueOf(strs[q]));
										if(sendUser!=null){
											nameList.add(sendUser.getRealName());
										}
									}
									commonNotice.setSendUserList(nameList);
								}
								commonNotice.setName(question.getName());
								resultList.add(commonNotice);
			            	}
				            break;
				        }
				        case 2:
				        {
				        	CommonNotice commonNotice = new CommonNotice();
							commonNotice.setCreateDate(Parameters.getSdf().format(no.getCreateDate()));
							commonNotice.setNoticeType(no.getNoticeType());
							commonNotice.setAboutId(no.getAboutId());
							commonNotice.setReadState(no.getReadState());
							ConstructionTaskNew ct = constructionTaskNewDao.getById(no.getAboutId());
							if(ct!=null){
								commonNotice.setAboutCreateUserId(ct.getCreateUser());
								commonNotice.setContent(ct.getConstructContent());
								Project project = projectDao.getById(ct.getProjectId());
								if(project!=null){
									commonNotice.setProjectName(project.getName());
								}
								if(ct.getImgs()!=null){
									commonNotice.setImagUrl(ct.getImgs().split(",")[0]);
								}
								User createUser=userDao.getById(ct.getCreateUser());
								if(createUser!=null){
									commonNotice.setCreateUserName(createUser.getRealName());
									if(createUser.getUserIcon()!=null){
										Files urlfiles = fileDao.getById(createUser.getUserIcon());
										if(urlfiles!=null){
											commonNotice.setUserIconUrl(urlfiles.getUrl());
										}
									}else if(createUser.getUserIconUrl()!=null){
										commonNotice.setUserIconUrl(createUser.getUserIconUrl());
									}
								}
								if(createUser!=null){
									commonNotice.setTitle(createUser.getRealName()+"提交了一个施工任务单");
								}
								/////任务单下面的流程节点的所有信息
								List<AllItemData> itemDataList = constructionTaskNewDao.getAllItemData(ct.getId());	
								if(!itemDataList.isEmpty()){
									List<String> nameList = new ArrayList<String>();
									User senduser = userDao.getById(itemDataList.get(0).getApprove_user());
									nameList.add(senduser.getRealName());
									commonNotice.setSendUserList(nameList);
								}
								commonNotice.setName(ct.getName());
								resultList.add(commonNotice);
							}
							break;
				        }
				        case 3:
				        {
				        	CommonNotice commonNotice = new CommonNotice();
				        	commonNotice.setReadState(no.getReadState());
							commonNotice.setCreateDate(Parameters.getSdf().format(no.getCreateDate()));
							commonNotice.setNoticeType(no.getNoticeType());
							commonNotice.setAboutId(no.getAboutId());
				        	AdvancedOrder ct = advancedOrderDao.getById(no.getAboutId());
							if(ct!=null){
								commonNotice.setAboutCreateUserId(ct.getSubmitUserId());
								commonNotice.setContent(ct.getQuantityDes());
								Project project = projectDao.getById(ct.getProjectId());
								if(project!=null){
									commonNotice.setProjectName(project.getName());
								}
								if(ct.getContentFilesId()!=null){
									Files files = fileDao.getById(Long.valueOf(ct.getContentFilesId().split(",")[0]));
									if(files!=null){
										commonNotice.setImagUrl(files.getUrl());
									}
								}
								User createUser=userDao.getById(ct.getSubmitUserId());
								if(createUser!=null){
									commonNotice.setCreateUserName(createUser.getRealName());
									if(createUser.getUserIcon()!=null){
										Files urlfiles = fileDao.getById(createUser.getUserIcon());
										if(urlfiles!=null){
											commonNotice.setUserIconUrl(urlfiles.getUrl());
										}
									}else if(createUser.getUserIconUrl()!=null){
										commonNotice.setUserIconUrl(createUser.getUserIconUrl());
									}
								}
								if(createUser!=null){
									commonNotice.setTitle(createUser.getRealName()+"提交了一个预付单");
								}
								if(ct.getNextReceivePeopleId()!=null){
									List<String> nameList = new ArrayList<String>();
									nameList.add(ct.getNextReceivePeopleId());
									commonNotice.setSendUserList(nameList);
								}
								commonNotice.setName(ct.getConstructPart());
								resultList.add(commonNotice);
							}
				        	break;
				        }
				        case 4:
				        {
				        	CommonNotice commonNotice = new CommonNotice();
							commonNotice.setCreateDate(Parameters.getSdf().format(no.getCreateDate()));
							commonNotice.setNoticeType(no.getNoticeType());
							commonNotice.setReadState(no.getReadState());
				        	Message ct = messageDao.getById(no.getAboutId());
							if(ct!=null){
								if(ct.getQuestionType()==0){
									commonNotice.setAboutId(ct.getAboutId());
								}
								if(ct.getQuestionType()==1){
									commonNotice.setAboutId(ct.getAboutId());
								}
								commonNotice.setAboutCreateUserId(ct.getUserId());
								commonNotice.setContent(ct.getContent());
								Project project = projectDao.getById(ct.getProjectId());
								if(project!=null){
									commonNotice.setProjectName(project.getName());
								}
								MessageFile mf = messageFileDao.getMessageFileByMessageId(ct.getId());
								if(mf!=null){
									if(mf.getFileId()!=null){
										Files file = fileDao.getById(mf.getFileId());
										if(file!=null){
											commonNotice.setImagUrl(file.getUrl());
										}
									}
								}
								User createUser=userDao.getById(ct.getUserId());
								if(createUser!=null){
									commonNotice.setCreateUserName(createUser.getRealName());
									if(createUser.getUserIcon()!=null){
										Files urlfiles = fileDao.getById(createUser.getUserIcon());
										if(urlfiles!=null){
											commonNotice.setUserIconUrl(urlfiles.getUrl());
										}
									}else if(createUser.getUserIconUrl()!=null){
										commonNotice.setUserIconUrl(createUser.getUserIconUrl());
									}
								}
								if(createUser!=null){
									commonNotice.setTitle(createUser.getRealName()+"提交了一条留言");
								}
								commonNotice.setMessageType(ct.getQuestionType());
								commonNotice.setName(ct.getContent());
								resultList.add(commonNotice);
							}
				        	break;
				        }
				      }

					}
				}
			}
			result.setData(resultList);
			result.setCurrentPage(gets.getCurrentPage());
			result.setNotRead(totalNotRead);
			result.setTotalNumber(gets.getTotalNumber());
			result.setTotalPage(gets.getTotalPage());
			result.setNumberPerPage(gets.getNumberPerPage());
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapperDiy<Integer> getNotReadNum(String token) {
		
		DataWrapperDiy<Integer> result = new DataWrapperDiy<Integer>();
		User user = SessionManager.getSession(token);
		Integer num=0;
		if(user!=null){
			num=noticeDao.getListNotRead(user.getId());
		}
		result.setData(num);
		return result;
	}
   

}
