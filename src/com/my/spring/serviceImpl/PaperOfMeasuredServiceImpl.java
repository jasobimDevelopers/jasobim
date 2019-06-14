package com.my.spring.serviceImpl;

import com.my.spring.DAO.CheckListTypesDao;
import com.my.spring.DAO.PaperOfMeasuredDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.Files;
import com.my.spring.model.PaperOfMeasured;
import com.my.spring.model.PaperOfMeasuredPojo;
import com.my.spring.model.User;
import com.my.spring.parameters.Parameters;
import com.my.spring.service.FileService;
import com.my.spring.service.PaperOfMeasuredService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service("paperOfMeasuredService")
public class PaperOfMeasuredServiceImpl implements PaperOfMeasuredService {
    @Autowired
    PaperOfMeasuredDao buildingDao;
    @Autowired
    CheckListTypesDao checkListTypesDao;
    @Autowired
    FileService filesService;
    @Autowired
    UserDao userDao;
    private static String filePath="/paperOfMeasuredFiles";
    @Override
    public DataWrapper<Void> addPaperOfMeasured(PaperOfMeasured building,String token,MultipartFile file, HttpServletRequest request) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
        	if(file!=null){
        		Files files = filesService.uploadFile(filePath, file, 0, request);
        		if(files!=null){
        			building.setFileId(files.getId());
        		}
        		building.setCreateDate(new Date());
        		building.setCreateUser(userInMemory.getId());
        		building.setPaperStatus(0);
        		building.setMeasuredNum(0);
				if(building!=null){
					if(!buildingDao.addPaperOfMeasured(building)){
						dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					} else{
						return dataWrapper;
					}
				}
        	}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
			}
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.AUTH_Error);
		}
        return dataWrapper;
    }
    
    public DataWrapper<Void> deletePaperOfMeasured(Long id,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(id!=null){
					if(!buildingDao.deletePaperOfMeasured(id)) 
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
	public DataWrapper<List<PaperOfMeasuredPojo>> getPaperOfMeasuredByProjectId(Long projectId,String token) {
		// TODO Auto-generated method stub
		DataWrapper<List<PaperOfMeasuredPojo>> dataWrapper = new DataWrapper<List<PaperOfMeasuredPojo>>();
		List<PaperOfMeasured> getList = new ArrayList<PaperOfMeasured>();
		List<PaperOfMeasuredPojo> resultList = new ArrayList<PaperOfMeasuredPojo>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
        	getList=buildingDao.getPaperOfMeasuredByProjectId(projectId);
			for(int i=0;i<getList.size();i++){
				PaperOfMeasuredPojo pojo = new PaperOfMeasuredPojo();
				pojo.setPaperId(getList.get(i).getPaperId());
				pojo.setCreateDate(Parameters.getSdfday().format(getList.get(i).getCreateDate()));
				pojo.setCreateUserName(userDao.getById(getList.get(i).getCreateUser()).getRealName());
				pojo.setPaperName(getList.get(i).getPaperName());
				pojo.setPaperStatus(getList.get(i).getPaperStatus());
				pojo.setProjectId(getList.get(i).getProjectId());
				pojo.setMeasuredNum(getList.get(i).getMeasuredNum());
				if(getList.get(i).getFileId()!=null){
					Files paperFiles = filesService.getById(getList.get(i).getFileId());
					if(paperFiles!=null){
						pojo.setFileUrl(paperFiles.getUrl());
					}
				}
				resultList.add(pojo);
			}
			dataWrapper.setData(resultList);
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dataWrapper;
	}
}
