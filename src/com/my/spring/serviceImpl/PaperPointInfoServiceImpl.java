package com.my.spring.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.my.spring.DAO.PaperOfMeasuredDao;
import com.my.spring.DAO.PaperPointInfoDao;
import com.my.spring.DAO.PaperPointInfoItemDao;
import com.my.spring.DAO.PaperPointNumsLogDao;
import com.my.spring.DAO.PaperPointRelationDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.BuildingPointNums;
import com.my.spring.model.Files;
import com.my.spring.model.PaperOfMeasured;
import com.my.spring.model.PaperPointInfo;
import com.my.spring.model.PaperPointInfoGet;
import com.my.spring.model.PaperPointInfoItem;
import com.my.spring.model.PaperPointInfoPojo;
import com.my.spring.model.PaperPointNumsLog;
import com.my.spring.model.PaperPointRelation;
import com.my.spring.model.PointConditionsCountNums;
import com.my.spring.model.PointInfoPojo;
import com.my.spring.model.User;
import com.my.spring.service.FileService;
import com.my.spring.service.PaperPointInfoService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("paperPointInfoService")
public class PaperPointInfoServiceImpl implements PaperPointInfoService,Runnable {
    @Autowired
    PaperPointInfoDao paperPointInfoDao;
    @Autowired
    PaperPointInfoItemDao paperPointInfoItemDao;
    @Autowired
    PaperPointNumsLogDao ppDao;
    @Autowired
    PaperPointRelationDao relationDao;
    @Autowired
    UserDao userDao;
    @Autowired
    PaperOfMeasuredDao pmDao;
    @Autowired
    FileService fileservice;
    @Override
   	public DataWrapper<Void> addPaperPointInfo(String token,String jsonString) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
        		if(jsonString!=null){
        			List<PaperPointInfoGet> getlist=JSONObject.parseArray(jsonString, PaperPointInfoGet.class);
        			List<PaperPointInfo> ppi = new ArrayList<PaperPointInfo>();
        			List<PaperPointRelation> pprs = new ArrayList<PaperPointRelation>();
        			Map<Integer,List<PaperPointInfoItem>> itemList = new HashMap<Integer,List<PaperPointInfoItem>>();
        			for(int i=0;i<getlist.size();i++){
        				PaperPointInfo pps = new PaperPointInfo();
        				pps.setAbscissa(getlist.get(i).getAbscissa());
        				pps.setCreateUser(userInMemory.getId());
        				pps.setOrdinate(getlist.get(i).getOrdinate());
        				pps.setTag(getlist.get(i).getTag());
        				pps.setProjectId(getlist.get(i).getProjectId());
        				pps.setStatus(0);
        				pps.setPaperOfMeasuredId(getlist.get(i).getPaperOfMeasuredId());
        				ppi.add(pps);
        				if(!getlist.get(i).getItemList().isEmpty()){
        					List<PaperPointInfoItem> itemLists = new ArrayList<PaperPointInfoItem>();
        					for(int j=0;j<getlist.get(i).getItemList().size();j++){
        						PaperPointRelation relation = new PaperPointRelation();
                				relation.setPaperOfMeasuredId(getlist.get(i).getPaperOfMeasuredId());
                				relation.setCheckTypeId(getlist.get(i).getItemList().get(j).getCheckTypeId());
                				relation.setProjectId(getlist.get(i).getProjectId());
        						PaperPointInfoItem items = new PaperPointInfoItem();
        						items.setCheckTypeId(getlist.get(i).getItemList().get(j).getCheckTypeId());
        						items.setErrorLowerLimit(getlist.get(i).getItemList().get(j).getErrorLowerLimit());
        						items.setErrorUpperLimit(getlist.get(i).getItemList().get(j).getErrorUpperLimit());
        						items.setFlag(getlist.get(i).getItemList().get(j).getFlag());
        						items.setProjectId(getlist.get(i).getProjectId());
        						items.setStandardNum(getlist.get(i).getItemList().get(j).getStandardNum());
        						itemLists.add(items);
        						pprs.add(relation);
        					}
        					itemList.put(i,itemLists);
        				}
        			}
        			if(!paperPointInfoDao.addPaperPointInfoList(ppi)){
   					 	dataWrapper.setErrorCode(ErrorCodeEnum.Error);
	   				}else{
	   					List<PaperPointInfoItem> saveList = new ArrayList<>();
	   					for(int k=0;k<ppi.size();k++){
	   						for(int m=0;m<itemList.get(k).size();m++){
	   							itemList.get(k).get(m).setPointId(ppi.get(k).getPointId());
	   							pprs.get(m).setPointId(ppi.get(k).getPointId());
	   							saveList.add(itemList.get(k).get(m));
	   						}
	   					}
	   					if(paperPointInfoItemDao.addPaperPointInfoItemList(saveList)){
	   						relationDao.addPaperPointRelation(pprs);
	   					}
	   					return dataWrapper;
	   				}
        		}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrapper;
    }
    public DataWrapper<Void> deletePaperPointInfo(Long id,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(id!=null){
					if(!paperPointInfoDao.deletePaperPointInfo(id)) 
			            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					else
						return dataWrapper;
			        
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
    public DataWrapper<Void> updatePaperPointInfo(PaperPointInfo info,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
 				if(info!=null){
					if(!paperPointInfoDao.updatePaperPointInfo(info)) 
			            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					else
						return dataWrapper;
			        
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
    public DataWrapper<List<PaperPointInfo>> getPaperPointInfoList() {
        return paperPointInfoDao.getPaperPointInfoList();
    }

	@Override
	public DataWrapper<PaperPointInfo> getPaperPointInfoByProjectId(Long projectId,String token) {
		// TODO Auto-generated method stub
		DataWrapper<PaperPointInfo> dataWrapper = new DataWrapper<PaperPointInfo>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			dataWrapper=paperPointInfoDao.getPaperPointInfoByProjectId(projectId);
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dataWrapper;
	}
	public List<BuildingPointNums> testRunnable(String buildings,String checkTypes,Long projectId,PaperPointInfoDao paperPointInfoDao){
		 MeasuredThreadTest thread = new MeasuredThreadTest(buildings,checkTypes,projectId,paperPointInfoDao);
		 try {
			 thread.start();
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return thread.getTestLists();
	}
	@Override
	public DataWrapper<List<PaperPointNumsLog>> getPaperPointInfoNums(String token, PaperPointNumsLog countLog) {
		DataWrapper<List<PaperPointNumsLog>> result = new DataWrapper<List<PaperPointNumsLog>>();
		List<PaperPointNumsLog> resultList = new ArrayList<PaperPointNumsLog>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			/*测量点位总数查询*/
			/*测量点位已测点位数查询*/
			/*测量点位问题点位数查询*/
			/*条件查询*/
			/*默认无条件筛选项目下的所有查询所有*/
			if(countLog.getBuildingNames()==null && countLog.getCheckTypes()==null){
				List<BuildingPointNums> gets=paperPointInfoDao.getCountAllPointNumsList(countLog.getProjectId());
				PaperPointNumsLog plg = new PaperPointNumsLog();
				plg.setPointNums(gets.get(0).getNums());
				plg.setDoneNums(gets.get(1).getNums());
				plg.setProblemNums(gets.get(2).getNums());
				plg.setProjectId(countLog.getProjectId());
				resultList.add(plg);
				result = paperPointInfoDao.getCountPointNumsList(countLog,user.getId());
				if(!result.getData().isEmpty()){
					for(int i=0;i<result.getData().size();i++){
						PaperPointNumsLog plgs = new PaperPointNumsLog();
						plgs.setCheckTypes(result.getData().get(i).getCheckTypes());
						plgs.setBuildingNames(result.getData().get(i).getBuildingNames());
						plgs.setCreateDate(result.getData().get(i).getCreateDate());
						plgs.setUserId(result.getData().get(i).getUserId());
						plgs.setProjectId(result.getData().get(i).getProjectId());
						plgs.setId(result.getData().get(i).getId());
						List<BuildingPointNums> getList=paperPointInfoDao.getCountPointNumsList(result.getData().get(i).getBuildingNames(),result.getData().get(i).getCheckTypes(),countLog.getProjectId());
						plgs.setPointNums(getList.get(0).getNums());
						plgs.setDoneNums(getList.get(1).getNums());
						plgs.setProblemNums(getList.get(2).getNums());
						resultList.add(plgs);
					}
				}
			}
			if(countLog.getBuildingNames()!=null || countLog.getCheckTypes()!=null){
				/*判断数据库是否存有相同的查询筛选条件*/
				PaperPointNumsLog paperPointNumsLog = paperPointInfoDao.findCountPointNumsList(countLog);
				/*没有记录下来并查询结果返回*/
				PaperPointNumsLog ppl = new PaperPointNumsLog();
				if(paperPointNumsLog==null){
					/*记录新的查询*/
					ppl.setBuildingNames(countLog.getBuildingNames());
					ppl.setCheckTypes(countLog.getCheckTypes());
					ppl.setProjectId(countLog.getProjectId());
					ppl.setUserId(user.getId());
					ppDao.addPaperPointNumsLog(ppl);
				}
				/*根据查询记录重新查询每一个记录查询的最新结果，即条件查询*/
				PaperPointNumsLog plgs = new PaperPointNumsLog();
				plgs.setCheckTypes(countLog.getCheckTypes());
				plgs.setBuildingNames(countLog.getBuildingNames());
				plgs.setCreateDate(countLog.getCreateDate());
				plgs.setUserId(user.getId());
				plgs.setProjectId(countLog.getProjectId());
				plgs.setId(ppl.getId());
				List<BuildingPointNums> getList=paperPointInfoDao.getCountPointNumsList(countLog.getBuildingNames(),countLog.getCheckTypes(),countLog.getProjectId());
				plgs.setPointNums(getList.get(0).getNums());
				plgs.setDoneNums(getList.get(1).getNums());
				plgs.setProblemNums(getList.get(2).getNums());
				resultList.add(plgs);
			}
			result.setData(resultList);
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);;
		}
		return result;
	}
	@Override
	public DataWrapper<List<PointConditionsCountNums>> getPaperPointInfoNumsByConditions(String token,
			PaperPointNumsLog countLog) {
		DataWrapper<List<PointConditionsCountNums>> dataWrapper = new DataWrapper<List<PointConditionsCountNums>>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
        	
			/*根据查询记录重新查询每一个记录查询的最新结果，即条件查询*/
			
			List<BuildingPointNums> getList=paperPointInfoDao.getCountPointNumsListGroup(countLog.getBuildingNames(),countLog.getCheckTypes(),countLog.getProjectId());
			if(!getList.isEmpty()){
				List<PointConditionsCountNums> get2 = new ArrayList<PointConditionsCountNums>();
				List<PointConditionsCountNums> get3 = new ArrayList<PointConditionsCountNums>();
				List<PointConditionsCountNums> get = new ArrayList<PointConditionsCountNums>();
				for(BuildingPointNums items:getList){
					if(items.getState()==0){
						PointConditionsCountNums pcn = new PointConditionsCountNums();
						pcn.setBfmId(items.getBid());
						pcn.setPointNums(items.getNums());
						pcn.setbName(items.getBname());
						pcn.setDoneNums(0);
						pcn.setProblemNums(0);
						get.add(pcn);
					}
					if(items.getState()==1){
						PointConditionsCountNums pcn = new PointConditionsCountNums();
						pcn.setBfmId(items.getBid());
						pcn.setPointNums(items.getNums());
						pcn.setbName(items.getBname());
						get2.add(pcn);
					}
					if(items.getState()==2){
						PointConditionsCountNums pcn = new PointConditionsCountNums();
						pcn.setBfmId(items.getBid());
						pcn.setPointNums(items.getNums());
						pcn.setbName(items.getBname());
						get3.add(pcn);
					}
				}
				for(int i=0;i<get.size();i++){
					get.get(i).setCheckTypes(countLog.getCheckTypes());
					get.get(i).setBuildingNames(countLog.getBuildingNames());
					get.get(i).setProjectId(countLog.getProjectId());
					if(i<get2.size()){
						if(get2.get(i).getBfmId().equals(get.get(i).getBfmId())){
							get.get(i).setDoneNums(get2.get(i).getPointNums());
						}
					}
					if(i<get3.size()){
						if(get3.get(i).getBfmId().equals(get.get(i).getBfmId())){
							get.get(i).setProblemNums(get3.get(i).getPointNums());
						}
					}
				}
				dataWrapper.setData(get);
			}
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dataWrapper;
	}
	@Override
	public DataWrapper<List<PointConditionsCountNums>> getPaperPointInfoNumsBySite(String token,
			PaperPointNumsLog countLog, Long bfmId) {
		DataWrapper<List<PointConditionsCountNums>> dataWrapper = new DataWrapper<List<PointConditionsCountNums>>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
        	
			/*根据查询记录重新查询每一个记录查询的最新结果，即条件查询*/
			
			List<BuildingPointNums> getList=paperPointInfoDao.getCountPointNumsListGroupBySite(bfmId,countLog.getCheckTypes(),countLog.getProjectId());
			if(!getList.isEmpty()){
				List<PointConditionsCountNums> get2 = new ArrayList<PointConditionsCountNums>();
				List<PointConditionsCountNums> get3 = new ArrayList<PointConditionsCountNums>();
				List<PointConditionsCountNums> get = new ArrayList<PointConditionsCountNums>();
				for(BuildingPointNums items:getList){
					if(items.getState()==0){
						PointConditionsCountNums pcn = new PointConditionsCountNums();
						pcn.setBfmId(items.getBid());
						pcn.setPointNums(items.getNums());
						pcn.setbName(items.getBname());
						pcn.setDoneNums(0);
						pcn.setProblemNums(0);
						get.add(pcn);
					}
					if(items.getState()==1){
						PointConditionsCountNums pcn = new PointConditionsCountNums();
						pcn.setBfmId(items.getBid());
						pcn.setPointNums(items.getNums());
						pcn.setbName(items.getBname());
						get2.add(pcn);
					}
					if(items.getState()==2){
						PointConditionsCountNums pcn = new PointConditionsCountNums();
						pcn.setBfmId(items.getBid());
						pcn.setPointNums(items.getNums());
						pcn.setbName(items.getBname());
						get3.add(pcn);
					}
				}
				for(int i=0;i<get.size();i++){
					get.get(i).setCheckTypes(countLog.getCheckTypes());
					get.get(i).setBuildingNames(countLog.getBuildingNames());
					get.get(i).setProjectId(countLog.getProjectId());
					if(i<get2.size()){
						if(get2.get(i).getBfmId().equals(get.get(i).getBfmId())){
							get.get(i).setDoneNums(get2.get(i).getPointNums());
						}
					}
					if(i<get3.size()){
						if(get3.get(i).getBfmId().equals(get.get(i).getBfmId())){
							get.get(i).setProblemNums(get3.get(i).getPointNums());
						}
					}
				}
				dataWrapper.setData(get);
			}
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dataWrapper;
	}
	@Override
	public DataWrapper<PaperPointInfoPojo> getPaperPointInfoList(String token, PaperPointNumsLog countLog,
			Long siteId) {
		DataWrapper<PaperPointInfoPojo> dataWrapper = new DataWrapper<PaperPointInfoPojo>();
		PaperPointInfoPojo resultPojo = new PaperPointInfoPojo();
		List<PaperPointInfo> resultList = new ArrayList<PaperPointInfo>();
		User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
        	resultList=paperPointInfoDao.getPaperPointInfoBySiteId(siteId,countLog.getCheckTypes(),countLog.getProjectId());
        	List<PointInfoPojo> getlist = new ArrayList<PointInfoPojo>();
        	if(!resultList.isEmpty()){
        		Long paperFileId=resultList.get(0).getPaperOfMeasuredId();
        		resultPojo.setPaperOfMeasuredId(paperFileId);
        		PaperOfMeasured mop = pmDao.getById(paperFileId);
        		if(mop.getFileId()!=null){
        			Files files = fileservice.getById(mop.getFileId());
        			if(files!=null){
        				resultPojo.setPaperUrl(files.getUrl());
        			}
        		}
        	}
        	for(PaperPointInfo ppi:resultList){
        		PointInfoPojo pip = new PointInfoPojo();
        		pip.setAbscissa(ppi.getAbscissa());
        		pip.setOrdinate(ppi.getOrdinate());
        		pip.setPaperOfMeasuredId(ppi.getPaperOfMeasuredId());
        		pip.setPointId(ppi.getPointId());
        		pip.setStatus(ppi.getStatus());
        		pip.setTag(ppi.getTag());
        		getlist.add(pip);
        	}
        	resultPojo.setInfo(getlist);
        	dataWrapper.setData(resultPojo);
        }else{
        	dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
        }
        return dataWrapper;
	}
	
	/*public PaperPointInfoServiceImpl(String bids,String cids,Long projectId){
		this.buildingIds=bids;
		this.checkIds=cids;
		this.projectId=projectId;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.resultList=paperPointInfoDao.getCountPointNumsList(this.buildingIds,this.checkIds,this.projectId);
	}*/
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Test!!!!");
		//this.resultList=paperPointInfoDao.getCountPointNumsList(this.buildingIds,this.checkIds,this.projectId);
	}
}
