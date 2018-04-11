package com.my.spring.serviceImpl;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.my.spring.DAO.QuantityDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.Quantity;
import com.my.spring.model.User;
import com.my.spring.parameters.Parameters;
import com.my.spring.service.QuantityService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.FileOperationsUtil;
import com.my.spring.utils.MD5Util;
import com.my.spring.utils.SessionManager;

@Service("quantityService")
public class QuantityServiceImpl implements QuantityService {
    @Autowired
    QuantityDao quantityDao;
    @Autowired
    UserDao userDao;
    @Override
    public DataWrapper<Void> addQuantity(Quantity quantity,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
				if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(quantity!=null){
					if(!quantityDao.addQuantity(quantity)) 
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
    public DataWrapper<Void> deleteQuantity(Long id,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(id!=null){
					if(!quantityDao.deleteQuantity(id)) 
			            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					else
						return dataWrapper;
			        
				}
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.AUTH_Error);
			}
		} else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrapper;
    }

    @Override
    public DataWrapper<Void> updateQuantity(Quantity quantity,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(quantity!=null){
					if(!quantityDao.updateQuantity(quantity)) 
			            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					else
						return dataWrapper;
			        
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
    public DataWrapper<List<Quantity>> getQuantityList(Long projectId,String token,Integer pageIndex,Integer pageSize,Quantity quantity) {
    	DataWrapper<List<Quantity>> datawrapper=new DataWrapper<List<Quantity>>();
    	User userInMemory=SessionManager.getSession(token);
    	if(userInMemory!=null){
    			datawrapper=quantityDao.getQuantityList(projectId,pageSize, pageIndex,quantity);	
    	}else{
    		datawrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
    	}
    	
    	return datawrapper;
    }

	@Override
	public DataWrapper<Quantity> getQuantityDetailsByAdmin(Long QuantityId, String token) {
		DataWrapper<Quantity> dataWrapper = new DataWrapper<Quantity>();
		 User userInMemory = SessionManager.getSession(token);
	        if (userInMemory != null) {
				if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
					if(QuantityId!=null){
						Quantity quantity=quantityDao.getById(QuantityId);
						if(quantity==null) 
				            dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
						else
							dataWrapper.setData(quantity);
				        
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
	public boolean batchImport(String filePath, MultipartFile file, String token, Long projectId, HttpServletRequest request) {
		if (file == null || filePath == null || filePath.equals("")) {
			return false;
		}
		DataWrapper<Void> dataWrapper=new DataWrapper<Void>();
		boolean b = false;
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
		        String newFileName = MD5Util.getMD5String(file.getOriginalFilename() + new Date() + UUID.randomUUID().toString()).replace(".","")
		                    + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		        //创建处理EXCEL
		        ReadQuantityExcel readExcel=new ReadQuantityExcel();
		        
		        //解析excel，构件信息集合。
		        List<Quantity> quantityList = readExcel.getExcelInfo(newFileName ,file,projectId);
		        if(quantityList.size()>0){
		        /////1.预算量和模型的工程量没有重合部分，添加
		    		if(quantityDao.addQuantityList(quantityList)){
			    		b=true;
		    		}
		    	
		    		/////2.预算量和模型的工程量有重合部分
		    		
		        }
	    		
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.AUTH_Error);
			}
			
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return b;
	}

	@Override
	public DataWrapper<String> exportQuantity(Long projectId, String token, HttpServletRequest request) {
		// TODO Auto-generated method stub
		DataWrapper<String> dataWrapper = new DataWrapper<String>();
		if (projectId == null) {
			dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
		}
			
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null) {
			
				String filePath = Parameters.quantityPath + projectId + "/";
				File fileDir = new File(filePath);
		        if (!fileDir.exists()) {
		            fileDir.mkdirs();
		        }
		        String file = filePath + "quantity.csv";
		        String header = "序号,"
		        		+ "名称,"
		        		+ "专业,"
		        		+ "数值,"
		        		+ "单位,"
		        		+ "项目编号,"
		        		+ "楼栋号,"
		        		+ "楼层号,"
		        		+ "单元号,"
		        		+ "户型,"
		        		+ "familyAndType,"
		        		+ "系统类型,"
		        		+ "设备类型,"
		        		+ "尺寸,"
		        		+ "材质";
		      String result = quantityDao.exportQuantity(projectId);
		      if(result!=null){
		    	  boolean flag = FileOperationsUtil.writeFile(file, header + "\n" + result, false);
					if(flag) {
						dataWrapper.setData("own/" + "quantity/" + projectId + "/quantity.csv");
					} else {
						dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					}
		      }else{
		    	  dataWrapper.setErrorCode(ErrorCodeEnum.Error);
		      }
			
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
			
		return dataWrapper;
	}

	@Override
	public DataWrapper<List<Quantity>> getQuantityListNum(Long projectId, String token, Integer pageIndex,
			Integer pageSize, Quantity quantity) {
		DataWrapper<List<Quantity>> dataWrapper= new DataWrapper<List<Quantity>>();
		User user=SessionManager.getSession(token);
		if(user!=null){
			if(projectId!=null){
				dataWrapper=quantityDao.getQuantityListNums(projectId,pageSize, pageIndex,quantity);
			}
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dataWrapper;
	}
}
