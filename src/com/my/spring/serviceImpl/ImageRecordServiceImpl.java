package com.my.spring.serviceImpl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.my.spring.DAO.ImageRecordDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.ImageRecord;
import com.my.spring.model.User;
import com.my.spring.service.ImageRecordService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

/**
* @author 徐雨祥
* @version 创建时间：2018年10月31日 下午1:52:12
* 类说明
*/
public class ImageRecordServiceImpl implements ImageRecordService{

	@Autowired
	ImageRecordDao imageRecordDao;
    @Autowired
    UserDao userDao;
	@Override
	public DataWrapper<Void> addImageRecord(String token, ImageRecord imageRecord) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			if(imageRecord!=null){
				imageRecord.setCreateDate(new Date());
				imageRecord.setCreateUser(userInMemory.getId());
				if(!imageRecordDao.addImageRecord(imageRecord)){
					result.setErrorCode(ErrorCodeEnum.Error);
				}
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<Void> updateImageRecord(String token, ImageRecord imageRecord) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			if(imageRecord!=null){
				if(imageRecord.getId()!=null){
					ImageRecord ir = imageRecordDao.getImageRecordById(imageRecord.getId());
					if(imageRecord.getContent()!=null){
						ir.setContent(imageRecord.getContent());
					}
					if(imageRecord.getName()!=null){
						ir.setName(imageRecord.getName());
					}
					if(!imageRecordDao.updateImageRecord(ir)){
						result.setErrorCode(ErrorCodeEnum.Error);
					}
				}
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<Void> deleteImageRecord(String token, Long id) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			if(id!=null){
				if(!imageRecordDao.deleteImageRecordById(id)){
					result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
				}
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<List<ImageRecord>> getImageRecordList(String token, ImageRecord imageRecord) {
		DataWrapper<List<ImageRecord>> result = new DataWrapper<List<ImageRecord>>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

}
