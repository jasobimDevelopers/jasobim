package com.my.spring.serviceImpl;

import com.my.spring.DAO.AdvancedOrderCollectDao;
import com.my.spring.DAO.AdvancedOrderDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.CallStatusEnum;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.jpush.PushExample;
import com.my.spring.model.AdvancedOrder;
import com.my.spring.model.AdvancedOrderCollect;
import com.my.spring.model.AdvancedOrderPojo;
import com.my.spring.model.ConstructionTask;
import com.my.spring.model.ConstructionTaskPojo;
import com.my.spring.model.Files;
import com.my.spring.model.User;
import com.my.spring.parameters.Parameters;
import com.my.spring.service.AdvancedOrderService;
import com.my.spring.service.FileService;
import com.my.spring.utils.DataExportWordTest;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.NumberToCN;
import com.my.spring.utils.SessionManager;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.APIConnectionException;
import cn.jpush.api.common.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@Service("advancedOrderService")
public class AdvancedOrderServiceImpl implements AdvancedOrderService {
    @Autowired
    AdvancedOrderDao advancedOrderDao;
    @Autowired
    UserDao userDao;
    @Autowired
    AdvancedOrderCollectDao advancedOrderCollectDao;
    @Autowired
    FileService filesService;
    private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
    @SuppressWarnings("unused")
	private NumberToCN numberChange;
    @Override
    public DataWrapper<Void> addAdvancedOrder(AdvancedOrder advancedOrder,String token,MultipartFile[] contentFiles,MultipartFile[] photoOfFinished,HttpServletRequest request) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(advancedOrder!=null){
				if(advancedOrder.getProjectId()!=null){
					if(photoOfFinished.length>0){
						Files photoFile =filesService.uploadFile("advancedOrder/"+advancedOrder.getProjectId(), photoOfFinished[0], 5, request);
						if(photoFile!=null){
							advancedOrder.setPhotoOfFinished(photoFile.getId().toString());
						}
					}
					if(contentFiles.length>0){
						String fileIdList="";
						for(int i=0;i<contentFiles.length;i++){
							Files photoFiles =filesService.uploadFile("advancedOrder/"+advancedOrder.getProjectId(), contentFiles[i], 5, request);
							if(photoFiles!=null){
								if(!fileIdList.equals("")){
									fileIdList+=","+photoFiles.getId();
								}else{
									fileIdList=photoFiles.getId().toString();
								}
							}
						}
						if(!fileIdList.equals("")){
							advancedOrder.setContentFilesId(fileIdList);
						}
					}
					
				}
				
				advancedOrder.setNextApprovalPeopleType("施工员");
				advancedOrder.setSubmitUserId(userInMemory.getId());
				advancedOrder.setCreateUserName(userInMemory.getRealName());
				advancedOrder.setUserProjectIdList(userInMemory.getProjectList());
				advancedOrder.setStatus(0);
				advancedOrder.setCreateDate(new Date(System.currentTimeMillis()));
				if(!advancedOrderDao.addAdvancedOrder(advancedOrder)) 
				{
					dataWrapper.setErrorCode(ErrorCodeEnum.Error);
				}else{
					AdvancedOrderCollect aoc = new AdvancedOrderCollect();
					aoc.setAdvancedOrderId(advancedOrder.getId());
					aoc.setConstructPart(advancedOrder.getConstructPart());
					aoc.setCreateDate(advancedOrder.getCreateDate());
					aoc.setCreateUserName(advancedOrder.getCreateUserName());
					aoc.setLeaderName(advancedOrder.getLeader());
					aoc.setMonth(advancedOrder.getMonth());
					aoc.setProjectName(advancedOrder.getProjectName());
					advancedOrderCollectDao.addAdvancedOrderCollect(aoc);
		///////////////////////////
					List<User> userList = new ArrayList<User>();
					userList=userDao.findUserLikeRealName(advancedOrder.getNextReceivePeopleId()).getData();
					String[] userids= new String[userList.size()];
					for(int b =0;b<userList.size();b++){
						userids[b]=userList.get(b).getId().toString();
					}
					JPushClient jpushClient = new JPushClient(Parameters.masterSecret, Parameters.appKey, 3);
					  String content=userInMemory.getRealName()+"提交了一个预付单需要您审批";
			    	  PushPayload payload = PushExample.buildPushObject_all_alias_alert(userids,content);
			          try {
			              PushResult result = jpushClient.sendPush(payload);
			              System.out.println(result);
			          } catch (APIConnectionException e) {
			            e.printStackTrace();         
			          } catch (APIRequestException e) {
			              System.out.println("Should review the error, and fix the request"+ e);
			              System.out.println("HTTP Status: " + e.getStatus());
			              System.out.println("Error Code: " + e.getErrorCode());
			              System.out.println("Error Message: " + e.getErrorMessage());
			          }
					///////////////////////////////
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
    public DataWrapper<Void> deleteAdvancedOrder(Long id,String token ) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(id!=null){
					AdvancedOrder advancedOrder = advancedOrderDao.getById(id);
					if(advancedOrder!=null){
						if(advancedOrder.getPhotoOfFinished()!=null){
							filesService.deleteFileById(Long.valueOf(advancedOrder.getPhotoOfFinished()));
						}
						if(advancedOrder.getContentFilesId()!=null){
							for(int i=0;i<advancedOrder.getContentFilesId().split(",").length;i++){
								filesService.deleteFileById(Long.valueOf(advancedOrder.getContentFilesId().split(",")[i]));
							}
						}
					}
					if(!advancedOrderDao.deleteAdvancedOrder(id)){
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
    public DataWrapper<List<AdvancedOrderPojo>> getAdvancedOrderList(String token , Integer pageIndex, Integer pageSize, AdvancedOrder advancedOrder) {
    	DataWrapper<List<AdvancedOrderPojo>> dataWrappers = new DataWrapper<List<AdvancedOrderPojo>>();
    	DataWrapper<List<AdvancedOrder>> dataWrapper = new DataWrapper<List<AdvancedOrder>>();
        User userInMemory = SessionManager.getSession(token);
        int adminFlag=1;
        if (userInMemory != null) {
        		if(advancedOrder.getCreateUserName()==null && userInMemory.getUserType()==3){
        			advancedOrder.setCreateUserName(userInMemory.getRealName());
        		}
        		if(advancedOrder.getApprovalPeopleName()==null && userInMemory.getUserType()==3){
        			advancedOrder.setApprovalPeopleName(userInMemory.getRealName());
        		}
        		if(advancedOrder.getNextReceivePeopleId()==null && userInMemory.getUserType()==3){
        			advancedOrder.setNextReceivePeopleId(userInMemory.getRealName());
        		}
        		if(userInMemory.getUserType()==0 || userInMemory.getUserType()==1 || userInMemory.getUserType()==2){
        			adminFlag=-1;
        		}
				dataWrapper=advancedOrderDao.getAdvancedOrdersList(pageIndex,pageSize,advancedOrder,adminFlag);
				if(dataWrapper.getData()!=null){
					List<AdvancedOrderPojo> advancedOrderPojoList = new ArrayList<AdvancedOrderPojo>();
					for(int i=0;i<dataWrapper.getData().size();i++){
						AdvancedOrderPojo advancedOrderPojo =new AdvancedOrderPojo();
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
						advancedOrderPojo.setConstructPart(dataWrapper.getData().get(i).getConstructPart());
						advancedOrderPojo.setCreateDate(sdf.format(dataWrapper.getData().get(i).getCreateDate()));
						advancedOrderPojo.setId(dataWrapper.getData().get(i).getId());
						advancedOrderPojo.setLeader(dataWrapper.getData().get(i).getLeader());
						advancedOrderPojo.setMonth(dataWrapper.getData().get(i).getMonth());
						advancedOrderPojo.setQuantityDes(dataWrapper.getData().get(i).getQuantityDes());
						advancedOrderPojo.setStatus(dataWrapper.getData().get(i).getStatus());
						advancedOrderPojo.setNextApprovalPeopleType(dataWrapper.getData().get(i).getNextApprovalPeopleType());
						advancedOrderPojo.setNextReceivePeopleId(dataWrapper.getData().get(i).getNextReceivePeopleId());
						try {
							long day=0;
							Date endDate=sdf.parse(sdf.format(new Date(System.currentTimeMillis())));
							Date beginDate=sdf.parse(sdf.format(dataWrapper.getData().get(i).getCreateDate()));
							day=(endDate.getTime()-beginDate.getTime())/(60*60*1000); 
							advancedOrderPojo.setLastTime(String.valueOf(day)+"小时");
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						advancedOrderPojo.setProjectName("安装公司"+dataWrapper.getData().get(i).getProjectName()+"项目部"+dataWrapper.getData().get(i).getMonth()+"月份预付单");
						if(dataWrapper.getData().get(i).getApprovalPeopleIdea()!=null){
							String[] approvalPeopleIdeas=dataWrapper.getData().get(i).getApprovalPeopleIdea().split("&");
							advancedOrderPojo.setApprovalPeopleIdea(approvalPeopleIdeas);
						}
						if(dataWrapper.getData().get(i).getApprovalPeopleName()!=null){
							String[] approvalPeopleNames=dataWrapper.getData().get(i).getApprovalPeopleName().split("&");
							advancedOrderPojo.setApprovalPeopleName(approvalPeopleNames);
						}
						if(dataWrapper.getData().get(i).getApprovalPeopleNote()!=null){
							String[] approvalPeopleNotes=dataWrapper.getData().get(i).getApprovalPeopleNote().split("&");
							advancedOrderPojo.setApprovalPeopleNote(approvalPeopleNotes);
						}
						if(dataWrapper.getData().get(i).getApprovalUpdateDate()!=null){
							String[] approvalUpdateDates=dataWrapper.getData().get(i).getApprovalUpdateDate().split("&");
							advancedOrderPojo.setApprovalUpdateDate(approvalUpdateDates);
						}
						if(dataWrapper.getData().get(i).getApprovalPeopleType()!=null){
							String[] approvalPeopleTypes=dataWrapper.getData().get(i).getApprovalPeopleType().split("&");
							advancedOrderPojo.setApprovalPeopleType(approvalPeopleTypes);
						}
						if(dataWrapper.getData().get(i).getContentFilesId()!=null){
							String[] contentFileUrl = dataWrapper.getData().get(i).getContentFilesId().split(",");
							for(int j=0;j<contentFileUrl.length;j++){
								contentFileUrl[j] = filesService.getById(Long.valueOf(contentFileUrl[j])).getUrl();
							}
							advancedOrderPojo.setContentFilesId(contentFileUrl);
						}
						
						if(dataWrapper.getData().get(i).getPhotoOfFinished()!=null){
							String photoFileUrl = dataWrapper.getData().get(i).getPhotoOfFinished();
							advancedOrderPojo.setPhotoOfFinished(filesService.getById(Long.valueOf(photoFileUrl)).getUrl());
						}
						if(dataWrapper.getData().get(i).getSubmitUserId()!=null){
							advancedOrderPojo.setCreateUserName(userDao.getById(dataWrapper.getData().get(i).getSubmitUserId()).getRealName());
						}
						if(advancedOrderPojo!=null){
							advancedOrderPojoList.add(advancedOrderPojo);
						}
					}
					if(advancedOrderPojoList!=null && advancedOrderPojoList.size()>0){
						dataWrappers.setData(advancedOrderPojoList);
						dataWrappers.setTotalNumber(dataWrapper.getTotalNumber());
						dataWrappers.setCurrentPage(dataWrapper.getCurrentPage());
						dataWrappers.setTotalPage(dataWrapper.getTotalPage());
						dataWrappers.setNumberPerPage(dataWrapper.getNumberPerPage());
					}else{
						dataWrappers.setErrorCode(ErrorCodeEnum.Error);
					}
				}
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.AUTH_Error);
			}
        return dataWrappers;
    }

	@Override
	public DataWrapper<List<AdvancedOrder>> getAdvancedOrderListByUserId(Long userId,String token) {
		DataWrapper<List<AdvancedOrder>> dataWrapper = new DataWrapper<List<AdvancedOrder>>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userId!=null){
				if(advancedOrderDao.getAdvancedOrderByUserId(userId)==null) 
		            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
				else
					return advancedOrderDao.getAdvancedOrderByUserId(userId);
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrapper;
	}



	@Override
	public DataWrapper<Void> updateAdvancedOrder(AdvancedOrder ps, String token,HttpServletRequest request,MultipartFile photoFile) {
	/////需要更新的施工任务单的任务状态默认流程都是没有走完的，走完的不能更新
		
			DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
			User userInMemory = SessionManager.getSession(token);
			AdvancedOrder ct = new AdvancedOrder();
			String currentUserName = userInMemory.getRealName();
			if(ps.getId()!=null){
				ct = advancedOrderDao.getById(ps.getId());
				AdvancedOrderCollect advancedOrderCollect =advancedOrderCollectDao.getAdvancedOrderById(ps.getId());
				if(ct.getStatus()==1){
					dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					return dataWrapper;
				}if(ct.getStatus()==(-1)){
					if(ct.getNextApprovalPeopleType().equals("预算员")){
						if(ct.getStatus()==-1){
							///上传图片
							Files photoFiles =filesService.uploadFile("advancedOrder/"+ct.getProjectId(), photoFile, 5, request);
							if(photoFiles!=null){
								ct.setPhotoOfFinished(photoFiles.getId().toString());
								ct.setStatus(1);
								if(!advancedOrderDao.updateConstructionTask(ct)){
									dataWrapper.setErrorCode(ErrorCodeEnum.Error);
								}
							}
						}
						
					}else{
					 dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					 return dataWrapper;
					}
				}else{
				////不同意的任务单返回给创建人，重新填写
					String approvalPeopleName = ct.getNextReceivePeopleId();
					if(approvalPeopleName.equals(currentUserName) || userInMemory.getUserType()==0){
						if(ct.getNextReceivePeopleId().equals("项目负责人")){
							advancedOrderCollect.setConstructPart(ps.getConstructPart());
							if(ps.getProjectName()!=null){
								ct.setProjectName(ps.getProjectName());
							}
							if(ps.getConstructPart()!=null){
								ct.setConstructPart(ps.getConstructPart());
							}
							if(ps.getMonth()!=null){
								ct.setMonth(ps.getMonth());
							}
							if(ps.getLeader()!=null){
								ct.setLeader(ps.getLeader());
							}
							if(ps.getQuantityDes()!=null){
								ct.setQuantityDes(ps.getQuantityDes());
							}
							if(ps.getNextReceivePeopleId()!=null){
								ct.setNextReceivePeopleId(ps.getNextReceivePeopleId());
							}
						}
						if(ct.getNextApprovalPeopleType().equals("预算员")){
						
							if(ps.getApprovalPeopleIdea().equals("同意")){
								ct.setStatus(-1);
							}
							String str=ps.getApprovalPeopleNote().trim();
							String str2="";
							if(str != null && !"".equals(str)){
								for(int i=0;i<str.length();i++){
									if(str.charAt(i)>=48 && str.charAt(i)<=57){
									str2+=str.charAt(i);
									}
								}
								if(!str2.equals("")){
									BigDecimal numberOfMoney = new BigDecimal(str2);
									String newNumber=str2+"("+NumberToCN.number2CNMontrayUnit(numberOfMoney)+")";
									String s =ps.getApprovalPeopleNote();
									ps.setApprovalPeopleNote(s.replace(str2, newNumber));
									advancedOrderCollect.setCurrentFinished(Long.valueOf(str2));
								}
							}
						}
						///审批人姓名
						if(ct.getApprovalPeopleName()!=null){
							ct.setApprovalPeopleName(ct.getApprovalPeopleName()+"&"+currentUserName);
						}else{
							ct.setApprovalPeopleName(currentUserName);
						}
						if(ps.getApprovalPeopleIdea()!=null){
							if(ps.getApprovalPeopleIdea().equals("不同意")){
								ct.setNextReceivePeopleId(ct.getCreateUserName());
							}else{
								ct.setNextReceivePeopleId(ps.getNextReceivePeopleId());
							}
						}
						
						
						////审批时间
						String updateDateList=ct.getApprovalUpdateDate();
						if(updateDateList!=null && !updateDateList.equals("")){
							ct.setApprovalUpdateDate(updateDateList+"&"+sdf.format(new Date(System.currentTimeMillis())));
						}else{
							ct.setApprovalUpdateDate(sdf.format(new Date(System.currentTimeMillis())));
						}
						
						////审批意见
						
						String approvalPeopleIdeaLists=ct.getApprovalPeopleIdea();
						if(approvalPeopleIdeaLists!=null && !approvalPeopleIdeaLists.equals("")){
							ct.setApprovalPeopleIdea(approvalPeopleIdeaLists+"&"+ps.getApprovalPeopleIdea());
						}else{
							ct.setApprovalPeopleIdea(ps.getApprovalPeopleIdea());
						}
						
						////审批批注
						String approvalPeopleNoteList=ct.getApprovalPeopleNote();
						if(approvalPeopleNoteList!=null && !approvalPeopleNoteList.equals("")){
							ct.setApprovalPeopleNote(approvalPeopleNoteList+"&"+ps.getApprovalPeopleNote());
						}else{
							ct.setApprovalPeopleNote(ps.getApprovalPeopleNote());
						}
						
						/////审批岗位
						if(ct.getApprovalPeopleType()!=null && !ct.getApprovalPeopleType().equals("")){
							ct.setApprovalPeopleType(ct.getApprovalPeopleType()+"&"+ct.getNextApprovalPeopleType());
						}else{
							ct.setApprovalPeopleType(ct.getNextApprovalPeopleType());
						}
						
						if(ct.getPhotoOfFinished()!=null){
							ct.setStatus(1);
							ct.setNextApprovalPeopleType("已结束");
						}else{
							ct.setNextApprovalPeopleType(ps.getNextApprovalPeopleType());
						}
						
						if(!advancedOrderDao.updateConstructionTask(ct)){
							dataWrapper.setErrorCode(ErrorCodeEnum.Error);
						}else{
							advancedOrderCollectDao.updateAdvancedOrderCollect(advancedOrderCollect);
						}
					}else{
						dataWrapper.setCallStatus(CallStatusEnum.FAILED);
						dataWrapper.setErrorCode(ErrorCodeEnum.AUTH_Error);
					}
				}
				
			}else{
				dataWrapper.setCallStatus(CallStatusEnum.FAILED);
				dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed); 
			}
			return dataWrapper;
	}

	@Override
	public DataWrapper<AdvancedOrderPojo> getAdvancedOrderById(Long id, String token, String weixin) {
		DataWrapper<AdvancedOrderPojo> dataWrapper = new DataWrapper<AdvancedOrderPojo>();
		AdvancedOrder advancedOrder = new AdvancedOrder();
		if(id!=null){
			if(weixin.equals("weixin")){
				advancedOrder=advancedOrderDao.getById(id);
				if(advancedOrder!=null){

					AdvancedOrderPojo advancedOrderPojo =new AdvancedOrderPojo();
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
					advancedOrderPojo.setConstructPart(advancedOrder.getConstructPart());
					advancedOrderPojo.setCreateDate(sdf.format(advancedOrder.getCreateDate()));
					advancedOrderPojo.setId(advancedOrder.getId());
					advancedOrderPojo.setLeader(advancedOrder.getLeader());
					advancedOrderPojo.setMonth(advancedOrder.getMonth());
					advancedOrderPojo.setQuantityDes(advancedOrder.getQuantityDes());
					advancedOrderPojo.setStatus(advancedOrder.getStatus());
					advancedOrderPojo.setNextApprovalPeopleType(advancedOrder.getNextApprovalPeopleType());
					advancedOrderPojo.setNextReceivePeopleId(advancedOrder.getNextReceivePeopleId());
					try {
						long day=0;
						Date endDate=sdf.parse(sdf.format(new Date(System.currentTimeMillis())));
						Date beginDate=sdf.parse(sdf.format(advancedOrder.getCreateDate()));
						day=(endDate.getTime()-beginDate.getTime())/(60*60*1000); 
						advancedOrderPojo.setLastTime(String.valueOf(day)+"小时");
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					advancedOrderPojo.setProjectName("安装公司"+advancedOrder.getProjectName()+"项目部"+advancedOrder.getMonth()+"月份预付单");
					if(advancedOrder.getApprovalPeopleIdea()!=null){
						String[] approvalPeopleIdeas=advancedOrder.getApprovalPeopleIdea().split("&");
						advancedOrderPojo.setApprovalPeopleIdea(approvalPeopleIdeas);
					}
					if(advancedOrder.getApprovalPeopleName()!=null){
						String[] approvalPeopleNames=advancedOrder.getApprovalPeopleName().split("&");
						advancedOrderPojo.setApprovalPeopleName(approvalPeopleNames);
					}
					if(advancedOrder.getApprovalPeopleNote()!=null){
						String[] approvalPeopleNotes=advancedOrder.getApprovalPeopleNote().split("&");
						advancedOrderPojo.setApprovalPeopleNote(approvalPeopleNotes);
					}
					if(advancedOrder.getApprovalUpdateDate()!=null){
						String[] approvalUpdateDates=advancedOrder.getApprovalUpdateDate().split("&");
						advancedOrderPojo.setApprovalUpdateDate(approvalUpdateDates);
					}
					if(advancedOrder.getApprovalPeopleType()!=null){
						String[] approvalPeopleTypes=advancedOrder.getApprovalPeopleType().split("&");
						advancedOrderPojo.setApprovalPeopleType(approvalPeopleTypes);
					}
					if(advancedOrder.getContentFilesId()!=null){
						String[] contentFileUrl = advancedOrder.getContentFilesId().split(",");
						for(int j=0;j<contentFileUrl.length;j++){
							contentFileUrl[j] = filesService.getById(Long.valueOf(contentFileUrl[j])).getUrl();
						}
						advancedOrderPojo.setContentFilesId(contentFileUrl);
					}
					
					if(advancedOrder.getPhotoOfFinished()!=null){
						String photoFileUrl = advancedOrder.getPhotoOfFinished();
						advancedOrderPojo.setPhotoOfFinished(filesService.getById(Long.valueOf(photoFileUrl)).getUrl());
					}
					if(advancedOrder.getSubmitUserId()!=null){
						advancedOrderPojo.setCreateUserName(userDao.getById(advancedOrder.getSubmitUserId()).getRealName());
					}
					if(advancedOrderPojo!=null){
						dataWrapper.setData(advancedOrderPojo);
					}
				}
			}
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
		}
        
        return dataWrapper;
	}

	@Override
	public DataWrapper<String> exportAdvancedOrder(Long id, String token) {
		DataWrapper<String> dataWrapper = new DataWrapper<String>();
		AdvancedOrder advancedOrder = new AdvancedOrder();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			if(id!=null){
					advancedOrder=advancedOrderDao.getById(id);
					if(advancedOrder!=null){
						AdvancedOrderPojo advancedOrderPojo =new AdvancedOrderPojo();
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
						advancedOrderPojo.setConstructPart(advancedOrder.getConstructPart());
						advancedOrderPojo.setCreateDate(sdf.format(advancedOrder.getCreateDate()));
						advancedOrderPojo.setId(advancedOrder.getId());
						advancedOrderPojo.setLeader(advancedOrder.getLeader());
						advancedOrderPojo.setMonth(advancedOrder.getMonth());
						advancedOrderPojo.setQuantityDes(advancedOrder.getQuantityDes());
						advancedOrderPojo.setStatus(advancedOrder.getStatus());
						advancedOrderPojo.setNextApprovalPeopleType(advancedOrder.getNextApprovalPeopleType());
						advancedOrderPojo.setNextReceivePeopleId(advancedOrder.getNextReceivePeopleId());
						advancedOrderPojo.setProjectName(advancedOrder.getProjectName());
						if(advancedOrder.getApprovalPeopleIdea()!=null){
							String[] approvalPeopleIdeas=advancedOrder.getApprovalPeopleIdea().split("&");
							advancedOrderPojo.setApprovalPeopleIdea(approvalPeopleIdeas);
						}
						if(advancedOrder.getApprovalPeopleName()!=null){
							String[] approvalPeopleNames=advancedOrder.getApprovalPeopleName().split("&");
							advancedOrderPojo.setApprovalPeopleName(approvalPeopleNames);
						}
						if(advancedOrder.getApprovalPeopleNote()!=null){
							String[] approvalPeopleNotes=advancedOrder.getApprovalPeopleNote().split("&");
							advancedOrderPojo.setApprovalPeopleNote(approvalPeopleNotes);
						}
						if(advancedOrder.getApprovalUpdateDate()!=null){
							String[] approvalUpdateDates=advancedOrder.getApprovalUpdateDate().split("&");
							advancedOrderPojo.setApprovalUpdateDate(approvalUpdateDates);
						}
						if(advancedOrder.getApprovalPeopleType()!=null){
							String[] approvalPeopleTypes=advancedOrder.getApprovalPeopleType().split("&");
							advancedOrderPojo.setApprovalPeopleType(approvalPeopleTypes);
						}
						if(advancedOrder.getSubmitUserId()!=null){
							advancedOrderPojo.setCreateUserName(userDao.getById(advancedOrder.getSubmitUserId()).getRealName());
						}
						if(advancedOrderPojo!=null){
							DataExportWordTest de = new DataExportWordTest();
							dataWrapper.setData(de.exportAdvancedOrderToWord(advancedOrderPojo));
						}
					}
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
			}
	        
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		
        return dataWrapper;
	}

	
}
