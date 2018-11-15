package com.my.spring.serviceImpl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.my.spring.DAO.ImageRecordBuildingInfoDao;
import com.my.spring.DAO.ImageRecordDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.ImageRecord;
import com.my.spring.model.ImageRecordBuildingInfo;
import com.my.spring.model.ImageRecordData;
import com.my.spring.model.ImageRecordPojo;
import com.my.spring.model.User;
import com.my.spring.parameters.Parameters;
import com.my.spring.service.ImageRecordService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
* @author 徐雨祥
* @version 创建时间：2018年10月31日 下午1:52:12
* 类说明
*/
@Service("imageRecordService")
public class ImageRecordServiceImpl implements ImageRecordService{

	@Autowired
	ImageRecordDao imageRecordDao;
	@Autowired
	ImageRecordBuildingInfoDao infoDao;
    @Autowired
    UserDao userDao;
	@Override
	public DataWrapper<Void> addImageRecord(String token, ImageRecord imageRecord) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			if(imageRecord!=null){
				imageRecord.setCreateDate(new Date());
				try {
					imageRecord.setProjectPartTime(Parameters.getSdfs().parse(imageRecord.getProjectPartDate()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
	public DataWrapper<ImageRecord> updateImageRecord(String token, ImageRecord imageRecord) {
		DataWrapper<ImageRecord> result = new DataWrapper<ImageRecord>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			if(imageRecord!=null){
				if(imageRecord.getId()!=null){
					ImageRecord ir = imageRecordDao.getImageRecordById(imageRecord.getId());
					if(imageRecord.getContent()!=null){
						ir.setContent(imageRecord.getContent());
					}
					if(!imageRecordDao.updateImageRecord(ir)){
						result.setErrorCode(ErrorCodeEnum.Error);
					}else{
						result.setData(ir);
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
	public DataWrapper<List<ImageRecordPojo>> getImageRecordList(String token, ImageRecord imageRecord) {
		DataWrapper<List<ImageRecordPojo>> result = new DataWrapper<List<ImageRecordPojo>>();
		List<ImageRecordPojo> ret = new ArrayList<ImageRecordPojo>();
		List<ImageRecordBuildingInfo> buildingList = new ArrayList<ImageRecordBuildingInfo>();
		List<ImageRecordData> buildingList2 = new ArrayList<ImageRecordData>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			if(imageRecord.getProjectId()!=null){
				ImageRecordBuildingInfo infos = new ImageRecordBuildingInfo();
				infos.setProjectId(imageRecord.getProjectId());
				buildingList=infoDao.getImageRecordBuildingInfoList(infos);
				if(!buildingList.isEmpty()){
					if(imageRecord.getBuildingId()==null){
						imageRecord.setBuildingId(buildingList.get(0).getId());
					}
					buildingList2=imageRecordDao.getImageRecordBuildingInfoListByGroupBy(imageRecord);
				}
				if(!buildingList.isEmpty()){
					ImageRecordPojo pojo = new ImageRecordPojo();
					pojo.setBuildingName(buildingList.get(0).getName());
					pojo.setBuildingId(buildingList.get(0).getId());
					List<ImageRecordData> contentList = new ArrayList<ImageRecordData>();
					List<ImageRecord> list = new ArrayList<ImageRecord>();
					list = imageRecordDao.getImageRecordListByProjectIdAndBuildingId(imageRecord.getProjectId(),buildingList.get(0).getId());
					for(int i=0;i<buildingList2.size();i++){
						ImageRecordData datas = new ImageRecordData();
						datas.setProjectPart(buildingList2.get(i).getProjectPart());
						datas.setUnitPart(buildingList2.get(i).getUnitPart());
						List<String> projectPartTimeList = new ArrayList<String>();
						List<String> contents = new ArrayList<String>();
						for(int j=0;j<list.size();j++){
							if((list.get(j).getProjectPart()).equals(buildingList2.get(i).getProjectPart()) && (list.get(j).getUnitPart()).equals(buildingList2.get(i).getUnitPart())){
								projectPartTimeList.add(list.get(j).getProjectPartDate());
								contents.add(list.get(j).getContent());
							}
						}
						datas.setContent(contents);
						datas.setProjectPartDate(projectPartTimeList);
						contentList.add(datas);
					}
					pojo.setContentList(contentList);
					ret.add(pojo);
					for(int k=1;k<buildingList.size();k++){
						ImageRecordPojo pojo1 = new ImageRecordPojo();
						pojo1.setBuildingId(buildingList.get(k).getId());
						pojo1.setBuildingName(buildingList.get(k).getName());
						ret.add(pojo1);
					}
					result.setData(ret);
				}
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<List<ImageRecordData>> getImageRecordListByBuildingId(String token, ImageRecord imageRecord) {
		// TODO Auto-generated method stub
		DataWrapper<List<ImageRecordData>> result = new DataWrapper<List<ImageRecordData>>();
		List<ImageRecordData> buildingList2 = new ArrayList<ImageRecordData>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			buildingList2=imageRecordDao.getImageRecordBuildingInfoListByGroupBy(imageRecord);
			List<ImageRecordData> contentList = new ArrayList<ImageRecordData>();
			List<ImageRecord> list = new ArrayList<ImageRecord>();
			list = imageRecordDao.getImageRecordListByProjectIdAndBuildingId(imageRecord.getProjectId(),imageRecord.getBuildingId());
			for(int i=0;i<buildingList2.size();i++){
				ImageRecordData datas = new ImageRecordData();
				datas.setProjectPart(buildingList2.get(i).getProjectPart());
				datas.setUnitPart(buildingList2.get(i).getUnitPart());
				List<String> projectPartTimeList = new ArrayList<String>();
				List<String> contents = new ArrayList<String>();
				for(int j=0;j<list.size();j++){
					if((list.get(j).getProjectPart()).equals(buildingList2.get(i).getProjectPart()) && (list.get(j).getUnitPart()).equals(buildingList2.get(i).getUnitPart())){
						projectPartTimeList.add(list.get(j).getProjectPartDate());
						contents.add(list.get(j).getContent());
					}
				}
				datas.setContent(contents);
				datas.setProjectPartDate(projectPartTimeList);
				contentList.add(datas);
			}
			result.setData(contentList);
			
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

}
