package com.my.spring.serviceImpl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.my.spring.DAO.MaterialPlanDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.FolderPojo;
import com.my.spring.model.MaterialPlan;
import com.my.spring.model.MaterialPlanPojo;
import com.my.spring.model.User;
import com.my.spring.parameters.Parameters;
import com.my.spring.service.FileService;
import com.my.spring.service.MaterialPlanService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.MaterialPlanNodeUtil;
import com.my.spring.utils.MenuRecursion;
import com.my.spring.utils.NodeUtil;
import com.my.spring.utils.SessionManager;

@Service("materialPlanService")
public class MaterialPlanServiceImpl implements MaterialPlanService  {
	@Autowired
	MaterialPlanDao mpDao;
	@Autowired
	UserDao userDao;
	@Autowired
	FileService fileService;

	@Override
	public MaterialPlan getById(Long id) {
		// TODO Auto-generated method stub
		return mpDao.getById(id);
	}



	@Override
	public DataWrapper<Object> getMaterialPlanList(String token, MaterialPlan floder,String dates) {
		// TODO Auto-generated method stub
		DataWrapper<Object> foldersList = new DataWrapper<Object>();
		DataWrapper<List<MaterialPlan>> mps = new DataWrapper<List<MaterialPlan>>();
		User userInMemory=SessionManager.getSession(token); 
		if(userInMemory!=null){
			mps=mpDao.getMaterialPlanList(floder,dates);
			if(mps.getData()!=null)
			{
				List dataList = new ArrayList();  
				for(MaterialPlan ss:mps.getData()){
					HashMap dataRecord1 = new HashMap();  
					dataRecord1.put("id", ss.getId().toString());
					dataRecord1.put("name", ss.getName());
					dataRecord1.put("remark", ss.getRemark());
					if(ss.getModel()!=null){
						dataRecord1.put("model", ss.getModel());
					}else{
						dataRecord1.put("model", "");
					}
					if(ss.getStandard()!=null){
						dataRecord1.put("standard", ss.getStandard());
					}else{
						dataRecord1.put("standard", "");
					}
					if(ss.getUnit()!=null){
						dataRecord1.put("unit",ss.getUnit());
					}else{
						dataRecord1.put("unit","");
					}
					if(ss.getNum()!=null){
						dataRecord1.put("num", ss.getNum()+"");
					}else{
						dataRecord1.put("num","");
					}
					if(ss.getGetTime()!=null){
						dataRecord1.put("getTime", ss.getGetTime());
					}else{
						dataRecord1.put("getTime", "");
					}
					if(ss.getOutPlace()!=null){
						dataRecord1.put("outPlace", ss.getOutPlace());
					}else{
						dataRecord1.put("outPlace", "");
					}
					if(ss.getUsePlace()!=null){
						dataRecord1.put("usePlace", ss.getUsePlace());
					}else{
						dataRecord1.put("usePlace", "");
					}
					if(ss.getPid()==0){
						dataRecord1.put("pid", "");
					}else{
						dataRecord1.put("pid", ss.getPid().toString());
					}
					dataList.add(dataRecord1);
				}
				MaterialPlanNodeUtil nodeUtil = new MaterialPlanNodeUtil();
				
				String resultString=nodeUtil.getJasonStringOfMaterialPlan(dataList);
				if(resultString!=null){
					foldersList.setData(JSONArray.parse(resultString));
				}
				
			}
			FolderPojo folderPojo = new FolderPojo();
			
			
		}else{
			foldersList.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		
		return foldersList;  
		  
	}

	@Override
	public DataWrapper<Void> addMaterialPlan(String token, MaterialPlan floder,String start,String end) throws ParseException {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(floder!=null){
				if(floder.getPid()!=null){
					MaterialPlan pmp = mpDao.getById(floder.getPid());
					if(pmp!=null){
						floder.setStartTime(pmp.getStartTime());
						floder.setEndTime(pmp.getEndTime());
					}
				}
				if(start!=null){
					floder.setStartTime(Parameters.getSdfs().parse(start));
				}
				if(end!=null){
					floder.setEndTime(Parameters.getSdfs().parse(end));
				}
				floder.setCreateDate(new Date());
				floder.setUserId(user.getId());
				if(floder.getPid()==null){
					floder.setPid((long) 0);
				}
				if(!mpDao.addMaterialPlan(floder)){
					result.setErrorCode(ErrorCodeEnum.Error);
				}
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}
	@Override
	public DataWrapper<Void> updateMaterialPlan(String token, MaterialPlan mp) {
		// TODO Auto-generated method stub
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(mp.getId()!=null){
				MaterialPlan f=mpDao.getById(mp.getId());
				f.setUpdateDate(new Date());
				if(mp.getName()!=null){
					f.setName(mp.getName());
				}
				if(mp.getStandard()!=null){
					f.setStandard(mp.getStandard());
				}
				if(mp.getModel()!=null){
					f.setModel(mp.getModel());
				}
				if(mp.getNum()!=null){
					f.setNum(mp.getNum());
				}
				if(mp.getOutPlace()!=null){
					f.setOutPlace(mp.getOutPlace());
				}
				if(mp.getUnit()!=null){
					f.setUnit(mp.getUnit());
				}
				if(mp.getUsePlace()!=null){
					f.setUsePlace(mp.getUsePlace());
				}
				if(mp.getGetTime()!=null){
					f.setGetTime(mp.getGetTime());
				}
				if(!mpDao.updateMaterialPlan(f)){
					result.setErrorCode(ErrorCodeEnum.Error);
				}
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	


	@Override
	public DataWrapper<Void> deleteMaterialPlan(String token, Long id) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(id!=null){
				List<MaterialPlan> alls = mpDao.getAllMaterialPlan();
				List<MaterialPlan> allChildrens = MenuRecursion.treeChildListOfMaterialPlan(alls, id);
				if(!allChildrens.isEmpty()){
					String[] ids =null;
					
					ids=new String[allChildrens.size()+1];
					ids[0]=id+"";
					for(int i=0;i<allChildrens.size();i++){
						ids[i+1]=allChildrens.get(i).getId()+"";
					}
					if(!mpDao.deleteMaterialPlanList(ids)){
						result.setErrorCode(ErrorCodeEnum.Error);
					}
				}else{
					if(!mpDao.deleteMaterialPlan(id)){
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
	public DataWrapper<List<MaterialPlanPojo>> getMaterialPlanIndexList(String token, Long projectId, Long pid) {
		DataWrapper<List<MaterialPlanPojo>> result = new DataWrapper<List<MaterialPlanPojo>>();
		List<MaterialPlanPojo> resultGets = new ArrayList<MaterialPlanPojo>();
		List<MaterialPlan> resultGet = new ArrayList<MaterialPlan>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(projectId!=null){
				resultGet=mpDao.getMaterialPlanIndexList(projectId, pid);
				if(!resultGet.isEmpty()){
					for(MaterialPlan floder:resultGet){
						MaterialPlanPojo fp = new MaterialPlanPojo();
						fp.setName(floder.getName());
						fp.setPid(floder.getPid());
						fp.setUnit(floder.getUnit());
						if(floder.getCreateDate()!=null){
							fp.setCreateDate(Parameters.getSdf().format(floder.getCreateDate()));
						}
						resultGets.add(fp);
					}
					result.setData(resultGets);
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
