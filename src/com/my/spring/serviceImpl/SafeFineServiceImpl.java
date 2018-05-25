package com.my.spring.serviceImpl;

import com.my.spring.DAO.SafeFineDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.CallStatusEnum;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.Files;
import com.my.spring.model.QualityFine;
import com.my.spring.model.QualityFinePojo;
import com.my.spring.model.QualityQuestionPojo;
import com.my.spring.model.SafeFine;
import com.my.spring.model.SafeFinePojo;
import com.my.spring.model.User;
import com.my.spring.parameters.Parameters;
import com.my.spring.service.FileService;
import com.my.spring.service.SafeFineService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


@Service("safeFineService")
public class SafeFineServiceImpl implements SafeFineService {
    @Autowired
    SafeFineDao fineDao;
    @Autowired
    UserDao userDao;
    @Autowired
    FileService fileService;
    private static String filePath="files/safeFine/";
    @Override
    public DataWrapper<Void> addSafeFine(SafeFine building,String token,MultipartFile[] files,HttpServletRequest request) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(building!=null){
					String ids="";
					if(files!=null){
						for(MultipartFile file:files){
							Files filess = fileService.uploadFile(filePath, file, 13, request);
							if(filess!=null){
								if(ids.equals("")){
									ids=filess.getId()+"";
								}else{
									ids=ids+","+filess.getId();
								}
							}
						}
						building.setFileIds(ids);
					}
					building.setCreateDate(new Date());
					building.setUserId(userInMemory.getId());
					if(!fineDao.addSafeFine(building)) 
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
    
    
  
    
    public DataWrapper<Void> deleteSafeFine(Long id,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(id!=null){
					if(!fineDao.deleteSafeFine(id)) 
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
    public DataWrapper<Void> updateSafeFine(SafeFine building,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
 				if(building!=null){
					if(!fineDao.updateSafeFine(building)) 
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
	public DataWrapper<List<SafeFinePojo>> getSafeFineList(Integer pageIndex, Integer pageSize, SafeFine fine,
			String token) {
		DataWrapper<List<SafeFinePojo>> result = new DataWrapper<List<SafeFinePojo>>();
		List<SafeFinePojo> results = new ArrayList<SafeFinePojo>();
		DataWrapper<List<SafeFine>> gets = new DataWrapper<List<SafeFine>>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			gets = fineDao.getSafeFineList(pageIndex,pageSize,fine);
			if(gets.getData()!=null){
				if(gets.getData().size()>0){
					for(SafeFine qfs:gets.getData()){
						SafeFinePojo qfp = new SafeFinePojo();
						qfp.setId(qfs.getId());
						qfp.setForfeit(qfs.getForfeit());
						qfp.setCreateDate(Parameters.getSdf().format(qfs.getCreateDate()));
						qfp.setDetail(qfs.getDetail());
						qfp.setLevel(qfs.getLevel());
						qfp.setProjectId(qfs.getProjectId());
						qfp.setCheckDate(qfs.getCheckDate());
						if(qfs.getFileIds()!=null){
							String[] ids=qfs.getFileIds().split(",");
							String[] fileids=new String[ids.length];
							for(int i=0;i<ids.length;i++){
								Files fileQuality = fileService.getById(Long.valueOf(ids[i]));
								if(fileQuality!=null){
									fileids[i]=fileQuality.getUrl();
								}
							}
							qfp.setFileUrls(fileids);
						}
						if(qfs.getUserId()!=null){
							User users = userDao.getById(qfs.getUserId());
							if(users!=null){
								qfp.setUserName(users.getRealName());
							}
						}
						results.add(qfp);
					}
					result.setData(results);
					result.setNumberPerPage(gets.getNumberPerPage());
					result.setTotalNumber(gets.getTotalNumber());
					result.setTotalPage(gets.getTotalPage());
					result.setCurrentPage(gets.getCurrentPage());
				}
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		if(result.getCallStatus()==CallStatusEnum.SUCCEED && result.getData()==null){
	       	List<SafeFinePojo> pas= new ArrayList<SafeFinePojo>();
	       	result.setData(pas);
	    }
		return result;
	}

    
}
