package com.my.spring.serviceImpl;

import com.my.spring.DAO.ProjectTenderDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.DAO.UserIndexDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.MaxIndex;
import com.my.spring.model.ProjectTender;
import com.my.spring.model.ProjectTenderIndex;
import com.my.spring.model.ProjectTenderPojo;
import com.my.spring.model.User;
import com.my.spring.model.UserIndex;
import com.my.spring.model.UserIndexUserId;
import com.my.spring.model.UserIndexs;
import com.my.spring.service.ProjectTenderService;
import com.my.spring.service.UserIndexService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service("projectTenderService")
public class ProjectTenderServiceImpl implements ProjectTenderService {
    @Autowired
    ProjectTenderDao projectTenderDao;
    @Autowired
    UserDao userDao;
    @Autowired
    UserIndexDao userIndexDao;
    @Autowired
    UserIndexService userIndexService;
    @Override
    public DataWrapper<Void> addProjectTender(ProjectTender ProjectTender,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(ProjectTender!=null){
					ProjectTender.setCreateDate(new Date());
					ProjectTender.setCreateUserId(userInMemory.getId());
					if(!projectTenderDao.addProjectTender(ProjectTender)) 
			            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					else{
						List<UserIndex> indexList = new ArrayList<UserIndex>();
						List<UserIndexUserId> idList=userIndexDao.getUserIndexListByGroup();
						if(!idList.isEmpty()){
							for(int i=0;i<idList.size();i++){
								MaxIndex max=userIndexDao.getIndexMaxByUserId(idList.get(i).getId());
								UserIndex userIndex = new UserIndex();
								userIndex.setAboutType(5);
								userIndex.setIndexs(max.getIndexs()+1);
								userIndex.setUserId(idList.get(i).getId());
								userIndex.setAboutId(ProjectTender.getId());
								indexList.add(userIndex);
							}
							userIndexDao.addUserIndexList(indexList);
						}
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
    public DataWrapper<Void> deleteProjectTender(Long id,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(id!=null){
					if(!projectTenderDao.deleteProjectTender(id)) 
			            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					else{
						userIndexService.deleteUserIndexByAboutId(5, id);
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
    public DataWrapper<Void> updateProjectTender(ProjectTender projectTender,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
 				if(projectTender!=null){
 					if(projectTender.getId()!=null){
 						projectTender.setUpdateDate(new Date());
 	 					projectTender.setUpdateUserId(userInMemory.getId());
 	 					ProjectTender updateItem=projectTenderDao.getProjectTenderById(projectTender.getId());
 	 					if(updateItem!=null){
 	 						if(projectTender.getName()!=null){
 	 							updateItem.setName(projectTender.getName());
 	 						}
 	 						if(projectTender.getProjectId()!=null){
 	 							updateItem.setProjectId(projectTender.getProjectId());
 	 						}
 	 						if(!projectTenderDao.updateProjectTender(updateItem)){
 	 							dataWrapper.setErrorCode(ErrorCodeEnum.Error);
 	 						}
 	 					}
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
    public DataWrapper<List<ProjectTender>> getProjectTenderList() {
        return projectTenderDao.getProjectTenderList();
    }

	@Override
	public DataWrapper<List<ProjectTenderPojo>> getProjectTenderList(Long projectId,String token,Integer pageSize,Integer pageIndex) {
		// TODO Auto-generated method stub
		DataWrapper<List<ProjectTenderPojo>> dataWrapper = new DataWrapper<List<ProjectTenderPojo>>();
		List<ProjectTenderPojo> resultList = new ArrayList<ProjectTenderPojo>();
		DataWrapper<List<ProjectTender>> getList = new DataWrapper<List<ProjectTender>>();
        User userInMemory = SessionManager.getSession(token);
        if(userInMemory != null) {
        		List<ProjectTenderIndex> get=projectTenderDao.getProjectTenderListByUserId(userInMemory.getId(),projectId,pageSize,pageIndex);
        		if(get.isEmpty()){
        			getList=projectTenderDao.getProjectTenderByProjectId(projectId,pageSize,pageIndex);
            		if(getList.getData()!=null){
            			dataWrapper.setCurrentPage(getList.getCurrentPage());
            			dataWrapper.setNumberPerPage(getList.getNumberPerPage());
            			dataWrapper.setTotalNumber(getList.getTotalNumber());
            			dataWrapper.setTotalPage(getList.getTotalPage());
            			if(!getList.getData().isEmpty()){
            				int indexs=1;
            				for(ProjectTender pt:getList.getData()){
            					ProjectTenderPojo ptp = new ProjectTenderPojo();
            					ptp.setId(pt.getId());
            					ptp.setName(pt.getName());
            					ptp.setProjectId(pt.getProjectId());
            					ptp.setIndexs((long)indexs);
            					resultList.add(ptp);
            					indexs++;
            				}
            			}
            		}
        		}else{
        			for(ProjectTenderIndex pt:get){
    					ProjectTenderPojo ptp = new ProjectTenderPojo();
    					ptp.setId(pt.getId());
    					ptp.setIndexs(pt.getIndexs());
    					ptp.setName(pt.getName());
    					ptp.setProjectId(pt.getProjectId());
    					resultList.add(ptp);
    				}
        		}
        		
    			
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
       
        dataWrapper.setData(resultList);
		return dataWrapper;
	}


}
