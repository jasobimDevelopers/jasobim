package com.my.spring.serviceImpl;

import com.my.spring.DAO.MeasuredSiteDao;
import com.my.spring.DAO.SitePaperRelationDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.MeasuredSite;
import com.my.spring.model.SitePaperRelation;
import com.my.spring.model.User;
import com.my.spring.service.MeasuredSiteService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("measuredSiteService")
//@Component("deleteBuilding")
public class MeasuredSiteServiceImpl implements MeasuredSiteService {
    @Autowired
    MeasuredSiteDao buildingDao;
    @Autowired
    SitePaperRelationDao sprDao;
    @Autowired
    UserDao userDao;
    @Override
    public DataWrapper<Void> addMeasuredSite(MeasuredSite building,String token,String siteDetail) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
				if(building!=null){
					if(siteDetail!=null){
						String[] nameList = siteDetail.split(",");
						List<MeasuredSite> saveList = new ArrayList<MeasuredSite>();
						for(int i=0;i<nameList.length;i++){
							MeasuredSite site = new MeasuredSite();
							site.setBfmId(building.getBfmId());
							site.setProjectId(building.getProjectId());
							site.setPaperOfMeasuredId(building.getPaperOfMeasuredId());
							site.setSiteName(nameList[i]);
							site.setCreateUser(userInMemory.getId());
							site.setCreateDate(new Date());
							saveList.add(site);
						}
						if(!buildingDao.addMeasuredSiteList(saveList)){
							dataWrapper.setErrorCode(ErrorCodeEnum.Error);
						}else{
							List<SitePaperRelation> sprList = new ArrayList<SitePaperRelation>();
							for(int j=0;j<saveList.size();j++){
								SitePaperRelation spr = new SitePaperRelation();
								spr.setBfmId(saveList.get(j).getBfmId());
								spr.setSiteId(saveList.get(j).getSiteId());
								spr.setProjectId(saveList.get(j).getProjectId());
								spr.setPaperOfMeasuredId(saveList.get(j).getPaperOfMeasuredId());
								sprList.add(spr);
							}
							sprDao.addSitePaperRelation(sprList);
						}
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
    public DataWrapper<Void> deleteMeasuredSite(Long id,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(id!=null){
					if(!buildingDao.deleteMeasuredSite(id)) 
					{
						dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					}
					else{
						sprDao.deleteSitePaperRelationBySiteId(id);
						return dataWrapper;
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
    public DataWrapper<Void> updateMeasuredSite(Long id, String token, String siteNewName, Long paperOfMeasuredId,Long checkUser) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(id!=null){
				MeasuredSite newMeasuredSite = buildingDao.getById(id);
				if(newMeasuredSite!=null){
					if(siteNewName!=null){
						newMeasuredSite.setSiteName(siteNewName);
					}
					if(paperOfMeasuredId!=null){
						newMeasuredSite.setPaperOfMeasuredId(paperOfMeasuredId);
					}
					if(checkUser!=null){
						newMeasuredSite.setCheckUser(checkUser);
					}
					if(!buildingDao.updateMeasuredSite(newMeasuredSite)) 
			            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					else
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
	public DataWrapper<List<MeasuredSite>> getMeasuredSiteListByBuildingId(Long buildingId,String token) {
		// TODO Auto-generated method stub
		DataWrapper<List<MeasuredSite>> dataWrapper = new DataWrapper<List<MeasuredSite>>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			dataWrapper=buildingDao.getMeasuredSiteListByBuildingId(buildingId);
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dataWrapper;
	}
}
