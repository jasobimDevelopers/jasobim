package com.my.spring.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.my.spring.DAO.PaperPointInfoDao;
import com.my.spring.DAO.PointDataInputLogDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.MeasuredProblem;
import com.my.spring.model.PaperPointInfo;
import com.my.spring.model.PointDataInputItem;
import com.my.spring.model.PointDataInputLog;
import com.my.spring.model.PointDataInputLogGet;
import com.my.spring.model.User;
import com.my.spring.service.MeasuredProblemService;
import com.my.spring.service.PointDataInputLogService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


@Service("pointDataInputLogService")
public class PointDataInputLogServiceImpl implements PointDataInputLogService {
    @Autowired
    PointDataInputLogDao pointDataInputLogDao;
    @Autowired
    PaperPointInfoDao pointDao;
    @Autowired
    MeasuredProblemService measuredProblemService;
    @Autowired
    PaperPointInfoDao ppiDao;
    @Autowired
    UserDao userDao;
    @Override
    public DataWrapper<Void> addPointDataInputLog(String jsonString, String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
				if(jsonString!=null){
					List<PointDataInputLogGet> pd=JSONObject.parseArray(jsonString, PointDataInputLogGet.class);
					List<PointDataInputLog> addList = new ArrayList<PointDataInputLog>();
					List<PaperPointInfo> pointList = new ArrayList<PaperPointInfo>();
					List<PointDataInputLogGet> pdNew = new ArrayList<PointDataInputLogGet>();
					for(int i=0;i<pd.size();i++){
						if(pd.get(i).getPointId()==null){
							pdNew.add(pd.get(i));
							PaperPointInfo ppi = new PaperPointInfo();
							ppi.setAbscissa(pd.get(i).getAbscissa());
							ppi.setOrdinate(pd.get(i).getOrdinate());
							ppi.setCreateUser(userInMemory.getId());
							ppi.setPointType(1);
							ppi.setMeasuredSiteId(pd.get(i).getMeasuredSiteId());
							ppi.setPaperOfMeasuredId(pd.get(i).getPaperOfMeasuredId());
							ppi.setProjectId(pd.get(i).getProjectId());
							ppi.setStatus(0);
							ppi.setTag(pd.get(i).getTag());
							pointList.add(ppi);
						}else{
							if(!pd.get(i).getInputItemList().isEmpty()){
								for(PointDataInputItem item:pd.get(i).getInputItemList()){
									PointDataInputLog pdg = new PointDataInputLog();
									pdg.setBfmId(pd.get(i).getBfmId());
									pdg.setSiteInfo(pd.get(i).getSiteInfo());
									pdg.setCreateUser(userInMemory.getId());
									pdg.setFlag(pd.get(i).getFlag());
									pdg.setMeasuredSiteId(pd.get(i).getMeasuredSiteId());
									pdg.setPointId(pd.get(i).getPointId());
									pdg.setProjectId(pd.get(i).getProjectId());
									pdg.setCheckTypeId(item.getCheckTypeId());
									pdg.setInputData(item.getInputData());
									pdg.setStatus(item.getState());
									if(item.getState()==1){
										pd.get(i).setState(1);
									}
									addList.add(pdg);
								}
							}
						}
					}
					if(!pointList.isEmpty()){
						if(pointDao.addPaperPointInfoList(pointList)){
							for(int i=0;i<pdNew.size();i++){
								if(!pdNew.get(i).getInputItemList().isEmpty()){
									for(PointDataInputItem item:pdNew.get(i).getInputItemList()){
										PointDataInputLog pdg = new PointDataInputLog();
										pdg.setBfmId(pdNew.get(i).getBfmId());
										pdg.setSiteInfo(pdNew.get(i).getSiteInfo());
										pdg.setCreateUser(userInMemory.getId());
										pdg.setFlag(pdNew.get(i).getFlag());
										pdg.setMeasuredSiteId(pdNew.get(i).getMeasuredSiteId());
										pdg.setPointId(pointList.get(i).getPointId());
										pdg.setProjectId(pdNew.get(i).getProjectId());
										pdg.setCheckTypeId(item.getCheckTypeId());
										pdg.setInputData(item.getInputData());
										pdg.setStatus(item.getState());
										addList.add(pdg);
									}
								}
							}
						}
					}
					if(!pointDataInputLogDao.addPointDataInputLogList(addList)) 
					{ 
						dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					}else{
						////新增点位测量记录时，发现有问题点位的同时生成爆点
						List<MeasuredProblem> mpList = new ArrayList<MeasuredProblem>();
						for(int j=0;j<addList.size();j++){
							///当发现不正常数据是设置爆点
							if(addList.get(j).getStatus()==1){
								MeasuredProblem mp = new MeasuredProblem();
								mp.setCheckSite(addList.get(j).getFlag());
								mp.setStatus(0);
								mp.setBfmId(addList.get(j).getBfmId());
								mp.setSiteId(addList.get(j).getMeasuredSiteId());
								mp.setInputUserId(addList.get(j).getCreateUser());
								mp.setProjectId(addList.get(j).getProjectId());
								mp.setCreateUser(userInMemory.getId());
								mp.setPointId(addList.get(j).getPointId());
								mp.setCheckListId(addList.get(j).getCheckTypeId());
								mpList.add(mp);
							}
						}
						measuredProblemService.addMeasuredProblemList(mpList);
						return dataWrapper;
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
    public DataWrapper<Void> deletePointDataInputLog(Long id,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
				if(id!=null){
					if(!pointDataInputLogDao.deletePointDataInputLog(id)) 
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
    public DataWrapper<Void> updatePointDataInputLog(PointDataInputLog PointDataInputLog,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
 				if(PointDataInputLog!=null){
					if(!pointDataInputLogDao.updatePointDataInputLog(PointDataInputLog)) 
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
	public DataWrapper<List<PointDataInputLog>> getPointDataInputLogByPointId(Long pointId, String token) {
		// TODO Auto-generated method stub
		DataWrapper<List<PointDataInputLog>> dataWrapper = new DataWrapper<List<PointDataInputLog>>();
		List<PointDataInputLog> result = new ArrayList<PointDataInputLog>();
		User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
        	result=pointDataInputLogDao.getPointDataInputLogListByPointId(userInMemory,pointId);
        	dataWrapper.setData(result);
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dataWrapper;
	}


	
	
}
