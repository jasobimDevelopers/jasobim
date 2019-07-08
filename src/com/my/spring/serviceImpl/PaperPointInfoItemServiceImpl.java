package com.my.spring.serviceImpl;

import com.my.spring.DAO.CheckListTypesDao;
import com.my.spring.DAO.PaperPointInfoDao;
import com.my.spring.DAO.PaperPointInfoItemDao;
import com.my.spring.DAO.PaperPointRelationDao;
import com.my.spring.DAO.PointDataInputLogDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.CheckListTypes;
import com.my.spring.model.InputLog;
import com.my.spring.model.PaperPointInfo;
import com.my.spring.model.PaperPointInfoItem;
import com.my.spring.model.PaperPointInfoItemPojo;
import com.my.spring.model.PaperPointRelation;
import com.my.spring.model.PointDataInputLog;
import com.my.spring.model.User;
import com.my.spring.parameters.Parameters;
import com.my.spring.service.PaperPointInfoItemService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


@Service("paperPointInfoItemService")
public class PaperPointInfoItemServiceImpl implements PaperPointInfoItemService {
    @Autowired
    PaperPointInfoItemDao pDao;
    @Autowired
    PointDataInputLogDao logDao;
    @Autowired
    PaperPointInfoDao ppiDao;
    @Autowired
    PaperPointRelationDao pprDao;
    @Autowired
    CheckListTypesDao ctDao;
    @Autowired
    UserDao userDao;
    @Override
    public DataWrapper<Void> addPaperPointInfoItem(PaperPointInfoItem paperPointInfoItem,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(paperPointInfoItem!=null){
				if(!pDao.addPaperPointInfoItem(paperPointInfoItem)){
					dataWrapper.setErrorCode(ErrorCodeEnum.Error);
				}else{
					PaperPointRelation ppr = new PaperPointRelation();
					ppr.setCheckTypeId(paperPointInfoItem.getCheckTypeId());
					ppr.setPointId(paperPointInfoItem.getPointId());
					ppr.setProjectId(paperPointInfoItem.getProjectId());
					if(paperPointInfoItem.getPointId()!=null){
						PaperPointInfo ppi = ppiDao.getById(paperPointInfoItem.getPointId());
						if(ppi!=null){
							ppr.setPaperOfMeasuredId(ppi.getPaperOfMeasuredId());
						}
					}
					pprDao.addPaperPointRelation(ppr);
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
    public DataWrapper<Void> deletePaperPointInfoItem(Long id,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(id!=null){
				PaperPointInfoItem item = pDao.getPaperPointInfoItemById(id);
				if(!pDao.deletePaperPointInfoItem(id)) 
				{ 
					dataWrapper.setErrorCode(ErrorCodeEnum.Error);
				}else{
					pprDao.deletePaperPointRelationByIds(item.getCheckTypeId(),item.getPointId());
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
    public DataWrapper<Void> updatePaperPointInfoItem(PaperPointInfoItem building,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(building!=null){
				if(!pDao.updatePaperPointInfoItem(building)) 
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
	public DataWrapper<List<PaperPointInfoItem>> getPaperPointInfoItemByPointId(Long pointId,String token) {
		// TODO Auto-generated method stub
		DataWrapper<List<PaperPointInfoItem>> dataWrapper = new DataWrapper<List<PaperPointInfoItem>>();
		List<PaperPointInfoItem> results = new ArrayList<PaperPointInfoItem>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
        	results=pDao.getPaperPointInfoItemByPointId(pointId);
        	dataWrapper.setData(results);
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dataWrapper;
	}

	@Override
	public DataWrapper<List<PaperPointInfoItemPojo>> getPaperPointInfoItemListsByPointId(Long pointId, String token) {
		DataWrapper<List<PaperPointInfoItemPojo>> dataWrapper = new DataWrapper<List<PaperPointInfoItemPojo>>();
		List<PaperPointInfoItemPojo> results = new ArrayList<PaperPointInfoItemPojo>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
        	List<PaperPointInfoItem> gets = new ArrayList<PaperPointInfoItem>();
        	List<PointDataInputLog> gets2 = new ArrayList<PointDataInputLog>();
        	gets=pDao.getPaperPointInfoItemByPointId(pointId);
        	gets2=logDao.getPointDataInputLogListByPointId(userInMemory,pointId);
        	if(!gets.isEmpty()){
        		for(int i=0;i<gets.size();i++){
        			PaperPointInfoItemPojo pojo = new PaperPointInfoItemPojo();
        			pojo.setPointItemId(gets.get(i).getId());
        			CheckListTypes ct = ctDao.getById(gets.get(i).getCheckTypeId());
        			pojo.setTitle("实测实量-设备安装-"+ct.getCheckName());
        			pojo.setContent(ct.getCheckName());
        			List<InputLog> logs = new ArrayList<InputLog>();
        			for(int j=0;j<gets2.size();j++){
        				if(gets.get(i).getCheckTypeId().equals(gets2.get(j).getCheckTypeId())){
        					InputLog log = new InputLog();
        					log.setCreateDate(Parameters.getSdf().format(gets2.get(j).getCreateDate()));
        					log.setLogId(gets2.get(j).getId());
        					log.setInputData(gets2.get(j).getInputData());
        					log.setUserName(userDao.getById(gets2.get(j).getCreateUser()).getRealName());
        					log.setState(gets2.get(j).getStatus());
        					logs.add(log);
        				}
        			}
        			pojo.setLogList(logs);
        			results.add(pojo);
        		}
        	}
        	dataWrapper.setData(results);
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dataWrapper;
	}
}
