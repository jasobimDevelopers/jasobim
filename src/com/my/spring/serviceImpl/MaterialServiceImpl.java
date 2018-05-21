package com.my.spring.serviceImpl;

import com.my.spring.DAO.MaterialDao;
import com.my.spring.DAO.MaterialFileDao;
import com.my.spring.DAO.MaterialTypeDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Files;
import com.my.spring.model.ImportMaterial;
import com.my.spring.model.Material;
import com.my.spring.model.MaterialFile;
import com.my.spring.model.MaterialPojo;
import com.my.spring.model.MaterialType;
import com.my.spring.model.User;
import com.my.spring.parameters.Parameters;
import com.my.spring.service.FileService;
import com.my.spring.service.MaterialService;
import com.my.spring.service.UserLogService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.MD5Util;
import com.my.spring.utils.ReadMaterialExcel;
import com.my.spring.utils.SessionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

@Service("materialService")
public class MaterialServiceImpl implements MaterialService {
    @Autowired
    MaterialDao materialDao;
    @Autowired
    MaterialTypeDao materialTypeDao;
    @Autowired
    UserDao userDao;
    @Autowired
    UserLogService userLogSerivce;
    @Autowired
    FileService fileService;
    @Autowired
    MaterialFileDao materialFileDao;
    @Override
    public DataWrapper<Void> addMaterial(Material m,String token) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(m!=null){
				m.setCreateDate(new Date());
				m.setUserId(userInMemory.getId());
				if(m.getInNum()==null){
					m.setInNum(0);
				}
				if(m.getLeaveNum()==null){
					m.setLeaveNum(0);
				}
				if(m.getOutNum()==null){
					m.setOutNum(0);
				}
				if(!materialDao.addMaterial(m)){
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
    public DataWrapper<Void> deleteMaterial(Long id,String token ) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if(userInMemory != null) {
			if(id!=null){
				if(!materialDao.deleteMaterial(id)){
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
    public DataWrapper<Void> updateMaterial(Material m,String token) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
        	m.setUserId(userInMemory.getId());
        	if(m!=null){
        		Material ms = materialDao.getById(m.getId());
        		if(ms!=null){
        			m.setCreateDate(ms.getCreateDate());
        		}
        	}
			if(!materialDao.updateMaterial(m)) {
			    dataWrapper.setErrorCode(ErrorCodeEnum.Error);
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrapper;
    }

    @Override
    public DataWrapper<List<MaterialPojo>> getMaterialList(String token , Integer pageIndex, Integer pageSize, Material m) {
    	DataWrapper<List<MaterialPojo>> dataWrappers = new DataWrapper<List<MaterialPojo>>();
    	DataWrapper<List<Material>> dataWrapper = new DataWrapper<List<Material>>();
    	List<MaterialPojo> mPojoList = new ArrayList<MaterialPojo>();
    	User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) 
        {
			dataWrapper=materialDao.getMaterialList(pageIndex,pageSize,m);
			if(dataWrapper.getData()!=null)
			{		
				if(dataWrapper.getData().size()>0)
				{
					for(int i=0;i<dataWrapper.getData().size();i++)
					{
						MaterialPojo mPojo =new MaterialPojo();
						mPojo.setMaterialName(dataWrapper.getData().get(i).getMaterialName());
						mPojo.setCreateDate(Parameters.getSdf().format(dataWrapper.getData().get(i).getCreateDate()));
						mPojo.setId(dataWrapper.getData().get(i).getId());
						mPojo.setRemark(dataWrapper.getData().get(i).getRemark());
						mPojo.setSize(dataWrapper.getData().get(i).getSize());
						mPojo.setUnit(dataWrapper.getData().get(i).getUnit());
						mPojo.setInNum(dataWrapper.getData().get(i).getInNum());
						mPojo.setOutNum(dataWrapper.getData().get(i).getOutNum());
						mPojo.setLeaveNum(dataWrapper.getData().get(i).getLeaveNum());
						MaterialType mys = materialTypeDao.getById(dataWrapper.getData().get(i).getMaterialType());
						if(mys!=null){
							mPojo.setMaterialType(mys.getName());
							mPojo.setMaterialTypeId(dataWrapper.getData().get(i).getMaterialType());
						}
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
				dataWrappers.setData(mPojoList);
			}
		}else{
			dataWrappers.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrappers;
    }

	@Override
	public DataWrapper<Void> importMaterial(MultipartFile file, HttpServletRequest request, String token,Material material) {
		// TODO Auto-generated method stub
		DataWrapper<Void> result = new DataWrapper<Void>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			if(file!=null){
				material.setCreateDate(new Date());
				material.setUserId(userInMemory.getId());
				List<Material> mst = new ArrayList<Material>();
				List<ImportMaterial> ms = new ArrayList<ImportMaterial>();
				ReadMaterialExcel rm = new ReadMaterialExcel();
				String newFileName = MD5Util.getMD5String(file.getOriginalFilename() + new Date() + UUID.randomUUID().toString()).replace(".","")
	                    + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
				
				ms=rm.getExcelInfo(newFileName, file);
				if(ms!=null){
					Files files=fileService.uploadFile("fileUploads/materialFiles", file, 10, request,userInMemory.getId());
					MaterialFile mf = new MaterialFile();
					mf.setUploadDate(new Date());
					mf.setFileId(files.getId());
					mf.setProjectId(material.getProjectId());
					mf.setUserId(userInMemory.getId());
					materialFileDao.addMaterialFile(mf);
				}
				
				for(ImportMaterial im:ms){
					Material m = new Material();
					MaterialType mq = new MaterialType();
					mq.setCreateDate(new Date());
					mq.setUserId(userInMemory.getId());
					mq.setProjectId(material.getProjectId());
					m.setCreateDate(new Date());
					m.setProjectId(material.getProjectId());
					m.setUserId(userInMemory.getId());
					m.setMaterialName(im.getMaterialName());
					m.setUnit(im.getUnit());
					m.setSize(im.getSize());
					m.setRemark(im.getRemark());
					if(im.getMaterialType()!=null){
						MaterialType sp = new MaterialType();
						sp=materialTypeDao.getMaterialByName(im.getMaterialType());
						if(sp.getId()==null){
							mq.setName(im.getMaterialType());
							if(materialTypeDao.addMaterialType(mq)){
								m.setMaterialType(mq.getId());
							}
						}else{
							m.setMaterialType(sp.getId());
						}
					}
					mst.add(m);
				}
				if(mst.size()>0){
					if(!materialDao.addMaterialList(mst)){
						result.setErrorCode(ErrorCodeEnum.Error);
					}
				}
			}else{
				result.setErrorCode(ErrorCodeEnum.Empty_Inputs);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	
}
