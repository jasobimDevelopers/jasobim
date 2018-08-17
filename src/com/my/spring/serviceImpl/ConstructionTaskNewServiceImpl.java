package com.my.spring.serviceImpl;

import com.my.spring.DAO.FileDao;
import com.my.spring.DAO.ItemDataDao;
import com.my.spring.DAO.MechanicDao;
import com.my.spring.DAO.MechanicPriceDao;
import com.my.spring.DAO.ProcessDataDao;
import com.my.spring.DAO.ProcessItemDao;
import com.my.spring.DAO.ProcessLogDao;
import com.my.spring.DAO.ProjectTenderDao;
import com.my.spring.DAO.ConstructionTaskNewDao;
import com.my.spring.DAO.DepartmentUserDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.CallStatusEnum;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.AllItemData;
import com.my.spring.model.ConstructionTaskNew;
import com.my.spring.model.ConstructionTaskNewPojo;
import com.my.spring.model.ConstructionTaskNewUser;
import com.my.spring.model.DepartmentUser;
import com.my.spring.model.Files;
import com.my.spring.model.ItemDataPojo;
import com.my.spring.model.ItemIdMode;
import com.my.spring.model.ItemNodeList;
import com.my.spring.model.Mechanic;
import com.my.spring.model.MechanicPrice;
import com.my.spring.model.NewsInfoPojo;
import com.my.spring.model.ProcessData;
import com.my.spring.model.ProcessItem;
import com.my.spring.model.ProcessLog;
import com.my.spring.model.ProcessLogPojo;
import com.my.spring.model.ProjectTender;
import com.my.spring.model.User;
import com.my.spring.model.UserLog;
import com.my.spring.parameters.Parameters;
import com.my.spring.service.ConstructionTaskNewService;
import com.my.spring.service.FileService;
import com.my.spring.service.UserLogService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
@Service("constructionTaskNewService")
public class ConstructionTaskNewServiceImpl implements ConstructionTaskNewService {
    @Autowired
    ConstructionTaskNewDao ConstructionTaskNewDao;
    @Autowired
    FileDao fileDao;
    @Autowired
    UserDao userDao;
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
    ProcessItemDao processItemDao;
    @Autowired
    ProcessLogDao processDataLogDao;
    @Autowired
    UserLogService userLogSerivce;
    @Autowired
    FileService fileService;
    @Override
    public DataWrapper<Void> addConstructionTaskNew(ConstructionTaskNew ConstructionTaskNew,String token,MultipartFile[] imgs,HttpServletRequest request) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(ConstructionTaskNew!=null){
				if(imgs!=null){
					String imgsList="";
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
				if(!ConstructionTaskNewDao.addConstructionTaskNew(ConstructionTaskNew)) 
				{
					dataWrapper.setErrorCode(ErrorCodeEnum.Error);
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
					if(!ConstructionTaskNewDao.deleteConstructionTaskNew(id)){
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
    public DataWrapper<Void> updateConstructionTaskNew(ConstructionTaskNew ConstructionTaskNew,String token) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				ConstructionTaskNew.setCreateUser(userInMemory.getId());
				if(!ConstructionTaskNewDao.updateConstructionTaskNew(ConstructionTaskNew)) {
				    dataWrapper.setErrorCode(ErrorCodeEnum.Error);
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
    public DataWrapper<List<ConstructionTaskNewPojo>> getConstructionTaskNewList(String token , Integer pageIndex, Integer pageSize, ConstructionTaskNew ConstructionTaskNew) {
    	DataWrapper<List<ConstructionTaskNewPojo>> dataWrappers = new DataWrapper<List<ConstructionTaskNewPojo>>();
    	DataWrapper<List<ConstructionTaskNew>> dataWrapper = new DataWrapper<List<ConstructionTaskNew>>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
	        	if(ConstructionTaskNew.getId()!=null){
	        		UserLog userLog = new UserLog();
	        		userLog.setActionDate(new Date());
	        		userLog.setFileId(ConstructionTaskNew.getId());
	        		userLog.setProjectPart(6);
	        		userLog.setUserId(userInMemory.getId());
	        		userLog.setVersion("-1");
	        		userLogSerivce.addUserLog(userLog, token);
	        	}
				dataWrapper=ConstructionTaskNewDao.getConstructionTaskNewList(pageIndex,pageSize,ConstructionTaskNew);
				//List<Long> users = new ArrayList<Long>();
				if(dataWrapper.getData()!=null && !dataWrapper.getData().isEmpty()){
					/////任务单下面的流程节点的所有信息
					List<AllItemData> itemDataList = ConstructionTaskNewDao.getAllItemData(dataWrapper.getData().get(0).getId());	
					List<ConstructionTaskNewPojo> ConstructionTaskNewPojoList = new ArrayList<ConstructionTaskNewPojo>();
					for(int i=0;i<dataWrapper.getData().size();i++){
						ConstructionTaskNewPojo ConstructionTaskNewPojo =new ConstructionTaskNewPojo();
						ProjectTender tender = projectTenderDao.getProjectTenderById(dataWrapper.getData().get(i).getTendersId());
						if(tender!=null){
							ConstructionTaskNewPojo.setTenders(tender.getName());
						}
						ConstructionTaskNewPojo.setId(dataWrapper.getData().get(i).getId());
						ConstructionTaskNewPojo.setConstructContent(dataWrapper.getData().get(i).getConstructContent());
						ConstructionTaskNewPojo.setConstructionName(dataWrapper.getData().get(i).getName());
						ConstructionTaskNewPojo.setName(dataWrapper.getData().get(i).getName());
						ConstructionTaskNewPojo.setConstructionTaskDate(dataWrapper.getData().get(i).getConstructionTaskDate());
						ConstructionTaskNewPojo.setConstructPart(dataWrapper.getData().get(i).getConstructPart());
						////施工类型，前端写死筛选
						ConstructionTaskNewPojo.setConstructType(dataWrapper.getData().get(i).getConstructType());
						ConstructionTaskNewPojo.setCreateDate(Parameters.getSdf().format(dataWrapper.getData().get(i).getCreateDate()));
						ConstructionTaskNewPojo.setDayWorkHours(dataWrapper.getData().get(i).getDayWorkHours());
						ConstructionTaskNewPojo.setNightWorkHours(dataWrapper.getData().get(i).getNightWorkHours());
						ConstructionTaskNewPojo.setProcessDataId(dataWrapper.getData().get(i).getProcessDataId());
						ConstructionTaskNewPojo.setTeamType(dataWrapper.getData().get(i).getTeamType());
						ConstructionTaskNewPojo.setTeamUserIds(dataWrapper.getData().get(i).getTeamUserIds());
						if(dataWrapper.getData().get(i).getImgs()!=null){
							ConstructionTaskNewPojo.setImgs(dataWrapper.getData().get(i).getImgs().split(","));
						}
						if(dataWrapper.getData().get(i).getCreateUser()!=null){
							User user= new User();
							user=userDao.getById(dataWrapper.getData().get(i).getCreateUser());
							if(user!=null){
								ConstructionTaskNewPojo.setCreateUser(user.getUserName());
							}
						}
						if(ConstructionTaskNewPojo!=null){
							if(!itemDataList.isEmpty()){
							/////查询任务单已有记录的所有节点信息
								List<ItemNodeList> gets = ConstructionTaskNewDao.getAllItemLog(dataWrapper.getData().get(i).getId());
								if(!gets.isEmpty()){
								////当前节点名称
									Integer currentNode=gets.get(gets.size()-1).getCurrent_node();
									ConstructionTaskNewPojo.setCurrentNodeName(itemDataList.get(currentNode-1).getName());
									
									///当前节点
									ConstructionTaskNewPojo.setCurrentNodeId(itemDataList.get(currentNode-1).getId());
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
									ConstructionTaskNewPojoList.add(ConstructionTaskNewPojo);
								}
							}
							
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
								List<ConstructionTaskNew> gets=ConstructionTaskNewDao.getConstructionTaskNewByIds(id);
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
					gets=ConstructionTaskNewDao.getConstructionTaskNewByIds(constructionTaskNew.getId());
					if(!gets.isEmpty()){
						/////查询已有记录的节点用户信息
						List<ItemNodeList> getNodes = ConstructionTaskNewDao.getAllItemLog(gets.get(0).getId());
						if(!gets.isEmpty()){
							/////任务单下面的流程节点的所有信息
							List<AllItemData> itemDataList = ConstructionTaskNewDao.getAllItemData(gets.get(0).getId());	
							
							for(int i=0;i<gets.size();i++){
							ConstructionTaskNewPojo ConstructionTaskNewPojo =new ConstructionTaskNewPojo();
							if(i==0){
							////当前节点名称
								Integer currentNode=getNodes.get(getNodes.size()-1).getCurrent_node();
								ConstructionTaskNewPojo.setCurrentNodeName(itemDataList.get(currentNode-1).getName());
								
								///当前节点
								ConstructionTaskNewPojo.setCurrentNodeId(itemDataList.get(currentNode-1).getId());
								/////0、同意  1、不同意
								if(getNodes.get(getNodes.size()-1).getEnd_flag()==2){
									ConstructionTaskNewPojo.setStatus(2);////不同意，即待修改
									User user = userDao.getById(itemDataList.get(0).getApprove_user());///修改人为节点第一个人，即创建人
									if(user!=null){
										ConstructionTaskNewPojo.setApprovalUser(user.getRealName());///当前审批人的姓名
									}
								}else if(getNodes.get(getNodes.size()-1).getEnd_flag()==1){
									ConstructionTaskNewPojo.setStatus(1);///已完成，下一审批人为空
								}else if(getNodes.get(getNodes.size()-1).getEnd_flag()==0){
									ConstructionTaskNewPojo.setStatus(0);////不同意，即待修改
									User user = userDao.getById(itemDataList.get(currentNode-1).getApprove_user());///修改人为节点第一个人，即创建人
									if(user!=null){
										ConstructionTaskNewPojo.setApprovalUser(user.getRealName());///当前审批人的姓名
									}
								}
								ConstructionTaskNewPojo.setApprovalUserId(itemDataList.get(currentNode-1).getApprove_user());
							}
							
							ConstructionTaskNewPojo.setId(gets.get(i).getId());
							ConstructionTaskNewPojo.setConstructContent(gets.get(i).getConstructContent());
							ConstructionTaskNewPojo.setProcessDataId(gets.get(i).getProcessDataId());
							ConstructionTaskNewPojo.setPid(gets.get(i).getPid());
							ConstructionTaskNewPojo.setConstructionName(gets.get(i).getName());
							ConstructionTaskNewPojo.setName(gets.get(i).getName());
							ConstructionTaskNewPojo.setConstructionTaskDate(gets.get(i).getConstructionTaskDate());
							ConstructionTaskNewPojo.setConstructPart(gets.get(i).getConstructPart());
							////施工类型，前端写死筛选
							ConstructionTaskNewPojo.setConstructType(gets.get(i).getConstructType());
							ConstructionTaskNewPojo.setCreateDate(Parameters.getSdf().format(gets.get(i).getCreateDate()));
							ConstructionTaskNewPojo.setDayWorkHours(gets.get(i).getDayWorkHours());
							ConstructionTaskNewPojo.setNightWorkHours(gets.get(i).getNightWorkHours());
							ConstructionTaskNewPojo.setProcessDataId(gets.get(i).getProcessDataId());
							ConstructionTaskNewPojo.setTeamType(gets.get(i).getTeamType());
							ConstructionTaskNewPojo.setTeamUserIds(gets.get(i).getTeamUserIds());
							ProjectTender tender = projectTenderDao.getProjectTenderById(gets.get(i).getTendersId());
							if(tender!=null){
								ConstructionTaskNewPojo.setTenders(tender.getName());
							}
							if(gets.get(i).getImgs()!=null){
								ConstructionTaskNewPojo.setImgs(gets.get(i).getImgs().split(","));
							}
							if(gets.get(i).getCreateUser()!=null){
								User user= new User();
								user=userDao.getById(gets.get(i).getCreateUser());
								if(user!=null){
									ConstructionTaskNewPojo.setCreateUser(user.getUserName());
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
			List<AllItemData> itemDataList = ConstructionTaskNewDao.getAllItemData(id);	 
			if(!itemDataList.isEmpty()){
				for(int i=0;i<itemDataList.size();i++){
					ProcessLogPojo plp = new ProcessLogPojo();
					plp.setId(itemDataList.get(i).getId());
					plp.setItemName(itemDataList.get(i).getName());
					User user = userDao.getById(itemDataList.get(i).getApprove_user());
					if(user!=null){
						plp.setApproveUser(user.getRealName());
					}
					ProcessLog processLog = processLogDao.getProcessLogByItemDataId(itemDataList.get(i).getId(),id);
					if(processLog!=null){
						plp.setNote(processLog.getNote());
						plp.setItemState(processLog.getItemState());
						plp.setCurrentNode(processLog.getCurrentNode());
						plp.setApproveDate(Parameters.getSdf().format(processLog.getCreateDate()));
						if(processLog.getItemState()==1){
							plp.setNextApproveUser(itemDataList.get(0).getName());
							plp.setEndFlag(2);
						}else if(i<(itemDataList.size()-1)){
							plp.setNextApproveUser(itemDataList.get(i+1).getName());
							plp.setEndFlag(0);
						}else if(i==(itemDataList.size()-1)){
							plp.setEndFlag(1);
						}
						plp.setProcessId(processDataId);
					}
					if(plp!=null){
						resultList.add(plp);
					}
				}
				result.setData(resultList);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<List<ProcessLogPojo>> getAllProcessLogByConstructionId(String token, Long id,
			Long processDataId) {
		// TODO Auto-generated method stub
		DataWrapper<List<ProcessLogPojo>> result = new DataWrapper<List<ProcessLogPojo>>();
		List<ProcessLogPojo> resultList = new ArrayList<ProcessLogPojo>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			
			List<ProcessLog> processLogList = processLogDao.getProcessLogByAboutId(processDataId,id);
			if(processLogList!=null){
				List<AllItemData> itemDataList = ConstructionTaskNewDao.getAllItemData(id);	 
				for(int i=0;i<processLogList.size();i++){
					for(int j=0;j<itemDataList.size();j++){
						ProcessLogPojo plp = new ProcessLogPojo();
						plp.setId(itemDataList.get(j).getId());
						plp.setItemName(itemDataList.get(j).getName());
						User user = userDao.getById(itemDataList.get(j).getApprove_user());
						if(user!=null){
							plp.setApproveUser(user.getRealName());
						}
						plp.setNote(processLogList.get(i).getNote());
						plp.setItemState(processLogList.get(i).getItemState());
						plp.setCurrentNode(processLogList.get(i).getCurrentNode());
						plp.setApproveDate(Parameters.getSdf().format(processLogList.get(i).getCreateDate()));
						if(processLogList.get(i).getItemState()==1){
							plp.setNextApproveUser(itemDataList.get(0).getName());
							plp.setEndFlag(2);
						}else if(i<(itemDataList.size()-1)){
							plp.setNextApproveUser(itemDataList.get(i+1).getName());
							plp.setEndFlag(0);
						}else if(i<(itemDataList.size()-1)){
							plp.setEndFlag(1);
						}
						plp.setProcessId(processDataId);
						if(plp!=null){
							resultList.add(plp);
						}
					}
				}
			}
			result.setData(resultList);
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}


}
