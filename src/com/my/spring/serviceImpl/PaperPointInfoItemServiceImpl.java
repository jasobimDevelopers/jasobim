package com.my.spring.serviceImpl;

import com.my.spring.DAO.PaperPointInfoDao;
import com.my.spring.DAO.PaperPointInfoItemDao;
import com.my.spring.DAO.PaperPointRelationDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.PaperPointInfo;
import com.my.spring.model.PaperPointInfoItem;
import com.my.spring.model.PaperPointRelation;
import com.my.spring.model.User;
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
    PaperPointInfoDao ppiDao;
    @Autowired
    PaperPointRelationDao pprDao;
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
}
