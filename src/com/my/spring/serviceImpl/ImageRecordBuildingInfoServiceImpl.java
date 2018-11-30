package com.my.spring.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.my.spring.DAO.ImageRecordBuildingInfoDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.ImageRecordBuildingInfo;
import com.my.spring.model.User;
import com.my.spring.service.ImageRecordBuildingInfoService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

/**
* @author 徐雨祥
* @version 创建时间：2018年11月2日 上午8:38:22
* 类说明
*/
@Service("imageRecordBuildingInfoService")
public class ImageRecordBuildingInfoServiceImpl implements ImageRecordBuildingInfoService{
	@Autowired
	private ImageRecordBuildingInfoDao infoDao;
	@Override
	public DataWrapper<ImageRecordBuildingInfo> addImageRecordBuildingInfo(String token, ImageRecordBuildingInfo info) {
		DataWrapper<ImageRecordBuildingInfo> result = new DataWrapper<ImageRecordBuildingInfo>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			if(info!=null){
				info.setCreateDate(new Date());
				info.setCreateUser(userInMemory.getId());
				if(!infoDao.addImageRecordBuildingInfo(info)){
					result.setErrorCode(ErrorCodeEnum.Error);
				}else{
					result.setData(info);
				}
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<Void> updateImageRecordBuildingInfo(String token, ImageRecordBuildingInfo info) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			if(info.getId()!=null){
				ImageRecordBuildingInfo infoNew = infoDao.getById(info.getId());
				if(infoNew!=null){
					if(info.getName()!=null){
						infoNew.setName(info.getName());
					}
					if(info.getIndexs()!=null){
						infoNew.setIndexs(info.getIndexs());
					}
					if(!infoDao.updateImageRecordBuildingInfo(infoNew)){
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
	public DataWrapper<Void> deleteImageRecordBuildingInfo(String token, Long id) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			if(id!=null){
				if(!infoDao.deleteImageRecordBuildingInfo(id)){
					result.setErrorCode(ErrorCodeEnum.Error);
				}
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<ImageRecordBuildingInfo> getImageRecordBuildingInfoById(String token, Long id) {
		DataWrapper<ImageRecordBuildingInfo> result = new DataWrapper<ImageRecordBuildingInfo>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			if(id!=null){
				ImageRecordBuildingInfo info = infoDao.getById(id);
				if(info!=null){
					result.setData(info);;
				}
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<List<ImageRecordBuildingInfo>> getImageRecordBuildingInfoList(String token,
			ImageRecordBuildingInfo info) {
		DataWrapper<List<ImageRecordBuildingInfo>> result = new DataWrapper<List<ImageRecordBuildingInfo>>();
		List<ImageRecordBuildingInfo> gets= new ArrayList<ImageRecordBuildingInfo>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			gets = infoDao.getImageRecordBuildingInfoList(info);
			result.setData(gets);
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}
	
}
