package com.my.spring.serviceImpl;

import com.my.spring.DAO.MaterialDao;
import com.my.spring.DAO.MaterialLogDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Material;
import com.my.spring.model.MaterialLog;
import com.my.spring.model.MaterialLogPojo;
import com.my.spring.model.User;
import com.my.spring.parameters.Parameters;
import com.my.spring.service.MaterialLogService;
import com.my.spring.service.UserLogService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service("materialLogService")
public class MaterialLogServiceImpl implements MaterialLogService {
    @Autowired
    MaterialLogDao materialLogDao;
    @Autowired
    MaterialDao materialDao;
    @Autowired
    UserDao userDao;
    @Autowired
    UserLogService userLogSerivce;
    @Override
    public DataWrapper<Void> addMaterialLog(MaterialLog m,String token,String date) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(m!=null){
				if(date!=null){
					try {
						m.setLogDate(Parameters.getSdfs().parse(date));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					m.setLogDate(new Date());
				}
				m.setUserId(userInMemory.getId());
				m.setSum(m.getIntro().split(",").length);
				if(m.getMaterialId()!=null){
					Material material=materialDao.getById(m.getMaterialId());
					if(material!=null){
						if(m.getLogType()==0){
							if(m.getNum()!=null){
								if(material.getInNum()==null){
									material.setInNum(m.getNum());
								}else{
									material.setInNum(material.getInNum()+m.getNum());
								}
								if(material.getLeaveNum()==null){
									material.setLeaveNum(m.getNum());
								}else{
									material.setLeaveNum(material.getLeaveNum()+m.getNum());
								}
							}
						}
						if(m.getLogType()==1){
							if(material.getLeaveNum()!=null){
								if(m.getNum()!=null){
									if(m.getNum()<=material.getLeaveNum()){
										material.setOutNum(material.getOutNum()+m.getNum());
										material.setLeaveNum(material.getLeaveNum()-m.getNum());
									}else{
										dataWrapper.setErrorCode(ErrorCodeEnum.Error);
										return dataWrapper;
									}
								}
							}
							
						}
						materialDao.updateMaterial(material);
					}
				}
				if(!materialLogDao.addMaterialLog(m)){
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
    public DataWrapper<Void> deleteMaterialLog(Long id,String token ) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if(userInMemory != null) {
			if(id!=null){
				if(!materialLogDao.deleteMaterialLog(id)){
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
    public DataWrapper<Void> updateMaterialLog(MaterialLog m,String token) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
        	m.setUserId(userInMemory.getId());
			if(!materialLogDao.updateMaterialLog(m)) {
			    dataWrapper.setErrorCode(ErrorCodeEnum.Error);
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrapper;
    }

    @Override
    public DataWrapper<List<MaterialLogPojo>> getMaterialLogList(String token , Integer pageIndex, Integer pageSize, MaterialLog m) {
    	DataWrapper<List<MaterialLogPojo>> dataWrappers = new DataWrapper<List<MaterialLogPojo>>();
    	DataWrapper<List<MaterialLog>> dataWrapper = new DataWrapper<List<MaterialLog>>();
    	List<MaterialLogPojo> mPojoList = new ArrayList<MaterialLogPojo>();
    	User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) 
        {
			dataWrapper=materialLogDao.getMaterialLogList(pageIndex,pageSize,m);
			if(dataWrapper.getData()!=null)
			{		
				if(dataWrapper.getData().size()>0)
				{
					for(int i=0;i<dataWrapper.getData().size();i++)
					{
						MaterialLogPojo mPojo =new MaterialLogPojo();
						mPojo.setMaterialName(materialDao.getById(dataWrapper.getData().get(i).getMaterialId()).getMaterialName());
						mPojo.setLogDate(Parameters.getSdfs().format(dataWrapper.getData().get(i).getLogDate()));
						mPojo.setId(dataWrapper.getData().get(i).getId());
						mPojo.setIntro(dataWrapper.getData().get(i).getIntro());
						mPojo.setNum(dataWrapper.getData().get(i).getNum());
						mPojo.setSum(dataWrapper.getData().get(i).getSum());
						mPojo.setLogType(dataWrapper.getData().get(i).getLogType());
						if(dataWrapper.getData().get(i).getUserId()!=null){
							User user= new User();
							user=userDao.getById(dataWrapper.getData().get(i).getUserId());
							if(user!=null){
								mPojo.setUserName(user.getUserName());
							}
						}
						if(mPojo!=null){
							mPojoList.add(mPojo);
						}
					}
				}
				if(mPojoList!=null && mPojoList.size()>0){
					dataWrappers.setData(mPojoList);
					dataWrappers.setTotalNumber(dataWrapper.getTotalNumber());
					dataWrappers.setCurrentPage(dataWrapper.getCurrentPage());
					dataWrappers.setTotalPage(dataWrapper.getTotalPage());
					dataWrappers.setNumberPerPage(dataWrapper.getNumberPerPage());
				}
			}
		}else{
			dataWrappers.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrappers;
    }

	
}
