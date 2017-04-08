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
import com.my.spring.service.QuantityService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.FileOperationsUtil;
import com.my.spring.utils.MD5Util;
import com.my.spring.utils.SessionManager;

/**
 * Created by Administrator on 2016/6/22.
 */
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
    		if(userInMemory.getWorkName().equals("投资方")){
    			datawrapper.setErrorCode(ErrorCodeEnum.AUTH_Error);
    		}else{
    			datawrapper=quantityDao.getQuantityList(projectId,pageSize, pageIndex,quantity);	
    		}
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
			
				String filePath = "E:/JasoBim/BimAppDocument/apache-tomcat-8.0.39/webapps/jasobim" + "/out/" + projectId + "/";
				File fileDir = new File(filePath);
		        if (!fileDir.exists()) {
		            fileDir.mkdirs();
		        }
		        String tempFile = filePath + "quantitty_temp.xls";
		        String file = filePath + "quantitty.xls";
		        String header = "序号\t"
		        		+ "名称\t"
		        		+ "专业\t"
		        		+ "数值\t"
		        		+ "单位\t"
		        		+ "项目编号\t"
		        		+ "楼栋号\t"
		        		+ "楼层号\t"
		        		+ "单元号\t"
		        		+ "户型\t"
		        		+ "familyAndType\t"
		        		+ "系统类型\t"
		        		+ "设备类型\t"
		        		+ "尺寸\t"
		        		+ "材质\n";
		        FileOperationsUtil.deleteFile(tempFile);
		        FileOperationsUtil.deleteFile(file);
		        if (quantityDao.exportQuantity(tempFile, projectId)) {
					String content = FileOperationsUtil.readFile(tempFile);
					String newContent = header + content;
					FileOperationsUtil.writeFile(file, newContent, false);
					dataWrapper.setData("out/" + projectId + "/quantitty.xls");
				} else {
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
