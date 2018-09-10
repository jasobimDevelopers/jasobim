package com.my.spring.serviceImpl;

import com.my.spring.DAO.FileDao;
import com.my.spring.DAO.ItemDataDao;
import com.my.spring.DAO.MechanicDao;
import com.my.spring.DAO.MechanicPriceDao;
import com.my.spring.DAO.NoticeDao;
import com.my.spring.DAO.ProcessDataDao;
import com.my.spring.DAO.ProcessItemDao;
import com.my.spring.DAO.ProcessLogDao;
import com.my.spring.DAO.ProjectDao;
import com.my.spring.DAO.ProjectTenderDao;
import com.my.spring.DAO.ConstructionTaskNewDao;
import com.my.spring.DAO.DepartmentUserDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.DAO.UserTeamDao;
import com.my.spring.controller.UserAvatar;
import com.my.spring.enums.CallStatusEnum;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.AllItemData;
import com.my.spring.model.AllProcessLogPojo;
import com.my.spring.model.ConstructionTaskNew;
import com.my.spring.model.ConstructionTaskNewPojo;
import com.my.spring.model.DepartmentUser;
import com.my.spring.model.Files;
import com.my.spring.model.ItemData;
import com.my.spring.model.ItemDataGet;
import com.my.spring.model.ItemIdMode;
import com.my.spring.model.ItemNodeList;
import com.my.spring.model.Mechanic;
import com.my.spring.model.MechanicPrice;
import com.my.spring.model.ProcessData;
import com.my.spring.model.ProcessLog;
import com.my.spring.model.ProcessLogPojo;
import com.my.spring.model.ProcessLogSql;
import com.my.spring.model.Project;
import com.my.spring.model.ProjectTender;
import com.my.spring.model.User;
import com.my.spring.model.UserTeam;
import com.my.spring.parameters.Parameters;
import com.my.spring.service.ConstructionTaskNewService;
import com.my.spring.service.FileService;
import com.my.spring.service.UserService;
import com.my.spring.utils.ConstructionTaskExportUtil;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.Date;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
@Service("constructionTaskNewService")
public class ConstructionTaskNewServiceImpl implements ConstructionTaskNewService {
    @Autowired
    ConstructionTaskNewDao constructionTaskNewDao;
    @Autowired
    FileDao fileDao;
    @Autowired
    UserDao userDao;
    @Autowired
    UserTeamDao userTeamDao;
    @Autowired
    ProjectDao projectDao;
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
    NoticeDao noticeDao;
    @Autowired
    ProcessItemDao processItemDao;
    @Autowired
    ProcessLogDao processDataLogDao;
    @Autowired
    FileService fileService;
    //@Autowired
    UserService taskService;
    @Override
    public DataWrapper<ConstructionTaskNew> addConstructionTaskNew(ConstructionTaskNew ConstructionTaskNew,String token,MultipartFile[] imgs,HttpServletRequest request,String constructionDate) {
    	DataWrapper<ConstructionTaskNew> dataWrapper = new DataWrapper<ConstructionTaskNew>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(ConstructionTaskNew!=null){
				if(constructionDate!=null){
					try {
						ConstructionTaskNew.setConstructionTaskDate(Parameters.getSdfs().parse(constructionDate));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				String imgsList="";
				if(imgs!=null){
					for(MultipartFile s:imgs){
						Files file = fileService.uploadFile("constructionFiles/"+ConstructionTaskNew.getProjectId(), s, 5, request);
						if(file!=null){
							if(imgsList.equals("")){
								imgsList=file.getUrl();
							}else{
								imgsList=imgsList+","+file.getUrl();
							}
						}
					}
					ConstructionTaskNew.setImgs(imgsList);
				}
				ConstructionTaskNew.setCreateUser(userInMemory.getId());
				ConstructionTaskNew.setCreateDate(new Date(System.currentTimeMillis()));
				if(!constructionTaskNewDao.addConstructionTaskNew(ConstructionTaskNew)) 
				{
					dataWrapper.setErrorCode(ErrorCodeEnum.Error);
				}else{
					dataWrapper.setData(ConstructionTaskNew);
					/*//////通知栏
					List<Notice> noticeList = new ArrayList<Notice>();
					List<UserId> userIdList = userDao.getAllUserIdListByProjectId(ConstructionTaskNew.getProjectId());
					if(!userIdList.isEmpty()){
						for(UserId s:userIdList){
							Notice nl2 = new Notice();
							nl2.setAboutId(ConstructionTaskNew.getId());
							nl2.setCreateDate(new Date());
							nl2.setUserId(s.getId());
							nl2.setNoticeType(2);
							nl2.setProjectId(ConstructionTaskNew.getProjectId());
							nl2.setReadState(0);
							noticeList.add(nl2);
						}
						
					}
					noticeDao.addNoticeList(noticeList);
					/////////////////推送
					HashMap<String,String> hq = new HashMap<String,String>();
					hq.put("title", ConstructionTaskNew.getName());
					hq.put("content", ConstructionTaskNew.getConstructContent());
					
					if(!imgsList.equals(""))
					{
						String[] imgLists = imgsList.split(",");
						for(int k=0;k<imgLists.length;k++){
							hq.put("imagUrl", imgLists[k]);
						}
							
					}
					
					/////////////////////////
					///////////////////////////
					Project po = projectDao.getById(ConstructionTaskNew.getProjectId());
					hq.put("createUserName", userInMemory.getRealName());
					if(userInMemory.getUserIconUrl()!=null){
						hq.put("userIconUrl", userInMemory.getUserIconUrl());
					}else{
						if(userInMemory.getUserIcon()!=null){
							Files files = fileService.getById(userInMemory.getUserIcon());
							hq.put("userIconUrl", files.getUrl());
						}
					}
					hq.put("projectName", "来自  "+po.getName());
					hq.put("aboutId", ConstructionTaskNew.getId().toString());
					hq.put("createDate", Parameters.getSdfs().format(new Date()));
					String content="";
					if(po!=null){
						content=userInMemory.getRealName()+"在"+po.getName()+"项目里提交了一个名称为："+ConstructionTaskNew.getName()+"的任务单";
					}else{
						content=userInMemory.getRealName()+"提交了一个名称为："+ConstructionTaskNew.getName()+"的任务单";
					}
					String[] userIds = new String[userIdList.size()];
					if(!userIdList.isEmpty()){
						for(int k=0;k<userIdList.size();k++){
							userIds[k]=String.valueOf(userIdList.get(k));
						}
					}*/
				
					///0、质量   1、安全   2、施工任务单  3、 预付单  4、留言
					//PushExample.testSendPushWithCustomConfig_ios(userIds, content,1,hq);
					//PushExample.testSendPushWithCustomConfig_android(userIds, content,1,hq);
					//notice.setRemark(remark);
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
    public DataWrapper<Void> deleteConstructionTaskNew(Long id,String token ) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(id!=null){
					if(!constructionTaskNewDao.deleteConstructionTaskNew(id)){
						dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					}
				}else{
					dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
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
    public DataWrapper<Void> updateConstructionTaskNew(ConstructionTaskNew constructionTaskNew,String token,MultipartFile[] imags,HttpServletRequest request) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
        	if(constructionTaskNew.getId()!=null){
        		ConstructionTaskNew constructionTask = constructionTasknewDao.getById(constructionTaskNew.getId());
        		if(constructionTask!=null){
        			if(userInMemory.getId().equals(constructionTask.getCreateUser())){
        				if(constructionTaskNew.getConstructContent()!=null){
        					constructionTask.setConstructContent(constructionTaskNew.getConstructContent());
        				}
        				if(constructionTaskNew.getConstructionTaskDate()!=null){
        					constructionTask.setConstructionTaskDate(constructionTaskNew.getConstructionTaskDate());
        				}
        				if(constructionTaskNew.getConstructPart()!=null){
        					constructionTask.setConstructPart(constructionTaskNew.getConstructPart());
        				}
        				if(constructionTaskNew.getConstructType()!=null){
        					constructionTask.setConstructType(constructionTaskNew.getConstructType());
        				}
        				if(constructionTaskNew.getDayWorkHours()!=null){
        					constructionTask.setDayWorkHours(constructionTask.getDayWorkHours());
        				}
        				if(constructionTaskNew.getName()!=null){
        					constructionTask.setName(constructionTaskNew.getName());
        				}
        				if(constructionTaskNew.getNightWorkHours()!=null){
        					constructionTask.setNightWorkHours(constructionTaskNew.getNightWorkHours());
        				}
        				if(constructionTaskNew.getTeamId()!=null){
        					constructionTask.setTeamId(constructionTaskNew.getTeamId());
        				}
        				if(constructionTaskNew.getTeamType()!=null){
        					constructionTask.setTeamType(constructionTaskNew.getTeamType());
        				}
        				if(constructionTaskNew.getTeamUserIds()!=null){
        					constructionTask.setTeamUserIds(constructionTaskNew.getTeamUserIds());
        				}
        				if(constructionTaskNew.getTendersId()!=null){
        					constructionTask.setTendersId(constructionTaskNew.getTendersId());
        				}
        				String imgsList="";
        				if(imags!=null){
    						for(MultipartFile s:imags){
    							Files file = fileService.uploadFile("constructionFiles/"+constructionTask.getProjectId(), s, 5, request);
    							if(file!=null){
    								if(imgsList.equals("")){
    									imgsList=file.getUrl();
    								}else{
    									imgsList=imgsList+","+file.getUrl();
    								}
    							}
    						}
    						constructionTask.setImgs(imgsList);
    					}
        				if(constructionTask.getUpdateDate()==null){
        					constructionTask.setUpdateDate(Parameters.getSdf().format(new Date()));
        				}else{
        					constructionTask.setUpdateDate(constructionTask.getUpdateDate()+","+Parameters.getSdf().format(new Date()));
        				}
        				
        				if(!constructionTaskNewDao.updateConstructionTaskNew(constructionTask)) {
        				    dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        				}else{
        					
        				}
        			}else{
        				dataWrapper.setErrorCode(ErrorCodeEnum.AUTH_Error);
        			}
        		}
        	}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrapper;
    }

    @Override
    public DataWrapper<List<ConstructionTaskNewPojo>> getConstructionTaskNewList(String token , Integer pageIndex, Integer pageSize, ConstructionTaskNew ConstructionTaskNew,String start,String end,Integer status) {
    	DataWrapper<List<ConstructionTaskNewPojo>> dataWrappers = new DataWrapper<List<ConstructionTaskNewPojo>>();
    	DataWrapper<List<ConstructionTaskNew>> dataWrapper = new DataWrapper<List<ConstructionTaskNew>>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
	        	Date dateStarts=null;
		    	Date dateFinisheds=null;
				SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd");
	    		if(start!=null){
	    			try {
	    				dateStarts=sdfs.parse(start);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    		}
	    		if(end!=null){
	    			try {
						dateFinisheds=sdfs.parse(end);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    		}
        		List<ProcessLogSql> processLogs = new ArrayList<ProcessLogSql>();
	        	if(status!=null){
	        		processLogs=processLogDao.getProcessLogByStatus(status);
	        	}
				dataWrapper=constructionTaskNewDao.getConstructionTaskNewList(pageIndex,pageSize,ConstructionTaskNew,processLogs,dateStarts,dateFinisheds,status);
				//List<Long> users = new ArrayList<Long>();
				if(dataWrapper.getData()!=null && !dataWrapper.getData().isEmpty()){
					/////任务单下面的流程节点的所有信息
					//List<ProcessLog> logs = new ArrayList<ProcessLog>();
					List<ConstructionTaskNewPojo> ConstructionTaskNewPojoList = new ArrayList<ConstructionTaskNewPojo>();
					for(int i=0;i<dataWrapper.getData().size();i++){
						List<AllItemData> itemDataList = constructionTaskNewDao.getAllItemData(dataWrapper.getData().get(i).getId());	
						ConstructionTaskNewPojo ConstructionTaskNewPojo =new ConstructionTaskNewPojo();
						ProjectTender tender = projectTenderDao.getProjectTenderById(dataWrapper.getData().get(i).getTendersId());
						if(tender!=null){
							ConstructionTaskNewPojo.setTenders(tender.getName());
						}
						ConstructionTaskNewPojo.setCreateUserId(dataWrapper.getData().get(i).getCreateUser());
						if(dataWrapper.getData().get(i).getCreateUser()!=null){
							User user = userDao.getById(dataWrapper.getData().get(i).getCreateUser());
							if(user!=null){
								String realName = user.getRealName().substring(user.getRealName().length()-2);
								if(user.getUserIconUrl()!=null){
									ConstructionTaskNewPojo.setCreateUserIcon(user.getUserIconUrl());
								}else{
									if(user.getUserIcon()!=null){
										Files file = fileService.getById(user.getUserIcon());
										if(file!=null){
											ConstructionTaskNewPojo.setCreateUserIcon(file.getUrl());
										}else{
											UserAvatar avatar= new UserAvatar();
											String url=avatar.CreateUserIcon(realName);
											ConstructionTaskNewPojo.setCreateUserIcon(url);
											user.setUserIconUrl(url);
											userDao.updateUser(user);
										}
									}else{
										UserAvatar avatar= new UserAvatar();
										String url=avatar.CreateUserIcon(realName);
										ConstructionTaskNewPojo.setCreateUserIcon(url);
										user.setUserIconUrl(url);
										userDao.updateUser(user);
									}
								}
							} 
							
						}
						ConstructionTaskNewPojo.setId(dataWrapper.getData().get(i).getId());
						ConstructionTaskNewPojo.setConstructContent(dataWrapper.getData().get(i).getConstructContent());
						ConstructionTaskNewPojo.setConstructionName(dataWrapper.getData().get(i).getName());
						ConstructionTaskNewPojo.setName(dataWrapper.getData().get(i).getName());
						ConstructionTaskNewPojo.setConstructionTaskDate(Parameters.getSdfs().format(dataWrapper.getData().get(i).getConstructionTaskDate()));
						ConstructionTaskNewPojo.setConstructPart(dataWrapper.getData().get(i).getConstructPart());
						////施工类型，前端写死筛选
						ConstructionTaskNewPojo.setConstructType(dataWrapper.getData().get(i).getConstructType());
						ConstructionTaskNewPojo.setCreateDate(Parameters.getSdf().format(dataWrapper.getData().get(i).getCreateDate()));
						ConstructionTaskNewPojo.setDayWorkHours(dataWrapper.getData().get(i).getDayWorkHours());
						ConstructionTaskNewPojo.setNightWorkHours(dataWrapper.getData().get(i).getNightWorkHours());
						ConstructionTaskNewPojo.setProcessDataId(dataWrapper.getData().get(i).getProcessDataId());
						ConstructionTaskNewPojo.setTeamType(dataWrapper.getData().get(i).getTeamType());
						ConstructionTaskNewPojo.setTeamUserIds(dataWrapper.getData().get(i).getTeamUserIds());
						String teamUserIds = dataWrapper.getData().get(i).getTeamUserIds();
						if(teamUserIds!=null){
							List<Long> userIdList = new ArrayList<Long>();
							for(int q=0;q<teamUserIds.split(",").length;q++){
								userIdList.add(Long.valueOf(teamUserIds.split(",")[q]));
							}
							ConstructionTaskNewPojo.setTeamUserIdList(userIdList);
						}
						
						ConstructionTaskNewPojo.setTeamId(dataWrapper.getData().get(i).getTeamId());
						if(dataWrapper.getData().get(i).getTeamId()!=null){
							UserTeam userTeam = userTeamDao.getById(dataWrapper.getData().get(i).getTeamId());
							if(userTeam!=null){
								ConstructionTaskNewPojo.setTeamName(userTeam.getTeamUserName());
							}
						}
						if(dataWrapper.getData().get(i).getImgs()!=null){
							String[] imgList =dataWrapper.getData().get(i).getImgs().split(",");
							if(!imgList[0].equals("")){
								ConstructionTaskNewPojo.setImgs(imgList);
							}else{
								ConstructionTaskNewPojo.setImgs(new String[0]);
							}
							
						}
						if(dataWrapper.getData().get(i).getCreateUser()!=null){
							User user= new User();
							user=userDao.getById(dataWrapper.getData().get(i).getCreateUser());
							if(user!=null){
								ConstructionTaskNewPojo.setCreateUser(user.getRealName());
							}
						}
						ConstructionTaskNewPojo.setStatus(0);
						ItemDataGet itemData =itemDataDao.getFirstItemByProcessDataId(dataWrapper.getData().get(i).getProcessDataId());
						if(itemData!=null){
							User appoveUser = userDao.getById(itemData.getApproveUser());
							if(appoveUser!=null){
								ConstructionTaskNewPojo.setApprovalUser(appoveUser.getRealName());///当前审批人的姓名
							}
							ConstructionTaskNewPojo.setCurrentNodeName(itemData.getName());
						}
						if(ConstructionTaskNewPojo!=null){
							if(!itemDataList.isEmpty()){
								/////查询任务单已有记录的所有节点信息
									
									List<ItemNodeList> gets = constructionTaskNewDao.getAllItemLog(dataWrapper.getData().get(i).getId());
									if(!gets.isEmpty()){
									////当前节点名称
										Integer currentNode=gets.get(gets.size()-1).getCurrent_node();
										ConstructionTaskNewPojo.setCurrentNodeName(itemDataList.get(currentNode-1).getName());
										/////0、同意  1、不同意
										if(gets.get(gets.size()-1).getEnd_flag()==2){
											ConstructionTaskNewPojo.setStatus(2);////不同意，即待修改
											User user = userDao.getById(itemDataList.get(0).getApprove_user());///修改人为节点第一个人，即创建人
											if(user!=null){
												ConstructionTaskNewPojo.setApprovalUser(user.getRealName());///当前审批人的姓名
											}
										}else if(gets.get(gets.size()-1).getEnd_flag()==1){
											ConstructionTaskNewPojo.setStatus(1);///已完成，下一审批人为空
										}else if(gets.get(gets.size()-1).getEnd_flag()==0){
											ConstructionTaskNewPojo.setStatus(0);////不同意，即待修改
											User user = userDao.getById(itemDataList.get(currentNode-1).getApprove_user());///修改人为节点第一个人，即创建人
											if(user!=null){
												ConstructionTaskNewPojo.setApprovalUser(user.getRealName());///当前审批人的姓名
											}
										}
										ConstructionTaskNewPojo.setApprovalUserId(itemDataList.get(currentNode-1).getApprove_user());
									}
									List<ProcessLog> getsNot = processLogDao.getProcessLogByAboutIds(dataWrapper.getData().get(i).getProcessDataId(),dataWrapper.getData().get(i).getId());//获取待修改的节点审批记录
									List<ProcessLog> afterGets = new ArrayList<ProcessLog>();
									if(!gets.isEmpty()){
										if(!getsNot.isEmpty()){
											afterGets=processLogDao.getProcessLogListByInfos(dataWrapper.getData().get(i).getId(), getsNot.get(0).getId());//获取最新的待修改节点审批记录后面的审批记录
										}
									}
									if(dataWrapper.getData().get(i).getUpdateDate()!=null){
										if(dataWrapper.getData().get(i).getUpdateDate().split(",").length==getsNot.size()){
											if(afterGets.isEmpty()){
												ConstructionTaskNewPojo.setStatus(0);
											}
										}
									}
							}
							ConstructionTaskNewPojoList.add(ConstructionTaskNewPojo);
						}
					}
					if(ConstructionTaskNewPojoList!=null && ConstructionTaskNewPojoList.size()>0){
						dataWrappers.setData(ConstructionTaskNewPojoList);
						dataWrappers.setTotalNumber(dataWrapper.getTotalNumber());
						dataWrappers.setCurrentPage(dataWrapper.getCurrentPage());
						dataWrappers.setTotalPage(dataWrapper.getTotalPage());
						dataWrappers.setNumberPerPage(dataWrapper.getNumberPerPage());
					}else{
						dataWrappers.setErrorCode(ErrorCodeEnum.Error);
					}
				}
			}else{
				dataWrappers.setErrorCode(ErrorCodeEnum.User_Not_Logined);
			}
        if(dataWrappers.getCallStatus()==CallStatusEnum.SUCCEED && dataWrappers.getData()==null){
	       	List<ConstructionTaskNewPojo> pas= new ArrayList<ConstructionTaskNewPojo>();
	       	dataWrappers.setData(pas);
	    }
        return dataWrappers;
    }

	@Override
	public DataWrapper<ProcessLogPojo> approveConstructionTaskNew(String token,Long id,String note,Integer idea,Long processDataId,Integer currentNode) {
		////idea:0、同意   1、不同意
		DataWrapper<ProcessLogPojo> result = new DataWrapper<ProcessLogPojo>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			ProcessData pd = new ProcessData();
			//////当当前节点和流程节点相同，并且当前审批人已同意的时候，流程结束，存入施工任务单工单记录（mechanicPrice）
			///夜班工时
			if(processDataId!=null){
				pd = processDataDao.getById(processDataId);
				if(pd!=null){
					if(pd.getItemNum()!=null){
						if(pd.getItemNum()==currentNode && idea==0){
							if(id!=null){
								List<ConstructionTaskNew> gets=constructionTaskNewDao.getConstructionTaskNewByIds(id);
								if(!gets.isEmpty()){
									List<MechanicPrice> idps = new ArrayList<MechanicPrice>();
									for(ConstructionTaskNew ctn:gets){
											MechanicPrice mp = new MechanicPrice();
											mp.setMechanicType(ctn.getTeamType());
											mp.setCreateDate(new Date());
											mp.setHour(ctn.getDayWorkHours());
											mp.setNightHour(ctn.getNightWorkHours());
											mp.setProjectId(ctn.getProjectId());
											if(ctn.getTeamUserIds()!=null){
												String[] ids=ctn.getTeamUserIds().split(",");
												for(String s:ids){
													mp.setMechanicId(Long.valueOf(s));
													idps.add(mp);
												}
												
											}
									}
									mechanicPriceDao.addMechanicPriceList(idps);
								}
							}
						}
					}
				}
			}
			/////////
			ProcessLog pl = new ProcessLog();
			pl.setCreateDate(new Date());
			pl.setNote(note);
			pl.setItemState(idea);
			pl.setAboutId(id);
			pl.setType(0);
			pl.setProcessId(processDataId);
			/////传过来的节点num+1
			///当为不同意时，传过来的下一审核节点id为第一个节点id
			pl.setCurrentNode(currentNode);	
			ItemIdMode itemId = processItemDao.getProcessItemByNode(currentNode,processDataId);
			if(itemId!=null){
				pl.setItemId(itemId.getItem_id());
			}
			////当审批人不同意时，设置待修改和未完成
			if(idea==1){
				pl.setEndFlag(2);
			}else{
				if(pd.getItemNum()==currentNode){///当前节点和流程节点相等时，并且审批人同意是设置为已完成
					pl.setEndFlag(1);
				}else{
					pl.setEndFlag(0);///当前节点和流程节点不想相等时，并且审批人同意是设置为未完成
				}
			}
			if(!processLogDao.addProcessLog(pl)){
				result.setErrorCode(ErrorCodeEnum.Error);
			}else{
				ProcessLogPojo plo = new ProcessLogPojo();
				plo.setApproveDate(Parameters.getSdf().format(pl.getCreateDate()));
				plo.setId(pl.getId());
				plo.setCurrentNode(pl.getCurrentNode());
				plo.setEndFlag(pl.getEndFlag());
				plo.setItemName(itemDataDao.getById(pl.getItemId()).getName());
				plo.setItemState(pl.getItemState());
				plo.setNote(pl.getNote());
				plo.setProcessId(pl.getProcessId());
				result.setData(plo);
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
							ConstructionTaskNewPojo.setNightWorkHours(gets.get(i).getNightWorkHours());
							ConstructionTaskNewPojo.setDayNums(new BigDecimal(day).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
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
	public DataWrapper<List<AllProcessLogPojo>> getAllProcessLogByConstructionId(String token, Long id,
			Long processDataId) {
		// TODO Auto-generated method stub
		DataWrapper<List<AllProcessLogPojo>> result = new DataWrapper<List<AllProcessLogPojo>>();
		List<AllProcessLogPojo> resultList = new ArrayList<AllProcessLogPojo>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			
			List<ProcessLog> processLogList = processLogDao.getProcessLogByAboutId(processDataId,id);
			if(processLogList!=null){
			
				for(int i=0;i<processLogList.size();i++){
					AllProcessLogPojo plp = new AllProcessLogPojo();
					if(processLogList.get(i).getItemId()!=null){
						ItemData itemData = itemDataDao.getById(processLogList.get(i).getItemId());
						if(itemData.getApproveUser()!=null){
							User user = userDao.getById(itemData.getApproveUser());
							if(user!=null){
								plp.setApproveUser(user.getRealName());
							}
						}
					}
					plp.setNote(processLogList.get(i).getNote());
					plp.setItemState(processLogList.get(i).getItemState());
					plp.setApproveDate(Parameters.getSdf().format(processLogList.get(i).getCreateDate()));
					resultList.add(plp);
				}		
			}
			result.setData(resultList);
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<String> exportConstructionTask(String token, Long id,Long processDataId,Long projectId) {
		DataWrapper<String> result = new DataWrapper<String>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			Project project = projectDao.getById(projectId);
			ConstructionTaskNew ct = new ConstructionTaskNew();
			ct.setId(id);
			ct.setProcessDataId(processDataId);
			DataWrapper<List<ConstructionTaskNewPojo>> taskList = getConstructionTaskNewDetail(token, ct);
			
			DataWrapper<List<ProcessLogPojo>> logList = getProcessLogByConstructionId(token, id, processDataId);
			ConstructionTaskExportUtil exportUtil = new ConstructionTaskExportUtil();
			FileOutputStream fout;
			try {
				fout = new FileOutputStream("D://jasobim/tomcat_8080/webapps/ROOT/constructionTask/taskLog.xls");
				exportUtil.getValue(logList.getData(), taskList.getData(), project.getName(), fout);
				result.setData("constructionTask/taskLog.xls");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}


}
