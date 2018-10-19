package com.my.spring.serviceImpl;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSONArray;
import com.my.spring.DAO.ContractLoftingDao;
import com.my.spring.DAO.ProjectPartContractLoftingDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.DAO.UserIndexDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.ContractLofting;
import com.my.spring.model.ContractLoftingPojo;
import com.my.spring.model.MaxIndex;
import com.my.spring.model.ProjectPartContractLofting;
import com.my.spring.model.User;
import com.my.spring.model.UserIndex;
import com.my.spring.model.UserIndexUserId;
import com.my.spring.parameters.Parameters;
import com.my.spring.service.ContractLoftingService;
import com.my.spring.service.UserIndexService;
import com.my.spring.utils.ContractLoftingNodeUtil;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.MD5Util;
import com.my.spring.utils.ReadContractLoftingExcel;
import com.my.spring.utils.SessionManager;
import com.my.spring.utils.TestUtil;

@Service("contractLoftingService")
public class ContractLoftingServiceImpl implements ContractLoftingService  {
	@Autowired
	ContractLoftingDao ContractLoftingDao;
	@Autowired
	ProjectPartContractLoftingDao pLoftingDao;
	@Autowired
	UserIndexService userIndexService;
	@Autowired
	UserIndexDao userIndexDao;
	@Autowired
	UserDao userDao;
	
	@SuppressWarnings("static-access")
	@Override
	public DataWrapper<Void> deleteContractLoftingById(String token,Long id,Long projectId) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(id!=null){
				List<ContractLofting> all = ContractLoftingDao.getAllContractLoftings(projectId);
				List<ContractLofting> clist = new ArrayList<ContractLofting>();
				ContractLofting pitem = new ContractLofting();
				pitem.setId(id);
				clist.add(pitem);
				TestUtil util = new TestUtil(all);
				List<Long> ids=util.getList(clist);
				ids.add(id);
				ContractLoftingDao.deleteContractLoftingByIds(ids);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<ContractLofting> addContractLofting(String token,ContractLofting role) {
		DataWrapper<ContractLofting> result = new DataWrapper<ContractLofting>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(role!=null){
				role.setCreateUser(user.getId());
				role.setCreateDate(new Date());
				if(!ContractLoftingDao.addContractLofting(role)){
					result.setErrorCode(ErrorCodeEnum.Error);
				}
			}
			
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<Void> deleteContractLoftingByIdList(String token,String[] id) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(!ContractLoftingDao.deleteContractLoftingList(id)){
				result.setErrorCode(ErrorCodeEnum.Error);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}


	@Override
	public DataWrapper<List<ContractLofting>> getContractLoftingList(Integer pageIndex, Integer pageSize, ContractLofting ContractLofting,
			String token) {
		DataWrapper<List<ContractLofting>> dp = new DataWrapper<List<ContractLofting>>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			dp = ContractLoftingDao.getContractLoftingList(pageIndex, pageSize, ContractLofting);
		}else{
			dp.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dp;
	}

	@Override
	public DataWrapper<ContractLofting> getContractLoftingById(String token, Long id) {
		DataWrapper<ContractLofting> dp = new DataWrapper<ContractLofting>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			ContractLofting dt = ContractLoftingDao.getById(id);
			dp.setData(dt);
		}else{
			dp.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dp;
	}

	@Override
	public DataWrapper<ContractLofting> updateContractLofting(String token, ContractLofting ContractLofting) {
		DataWrapper<ContractLofting> result = new DataWrapper<ContractLofting>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(ContractLofting!=null){
				ContractLofting dp = new ContractLofting();
				dp = ContractLoftingDao.getById(ContractLofting.getId());
				if(ContractLofting.getName()!=null){
					dp.setName(ContractLofting.getName());
				}
				if(ContractLofting.getLimitCoefficient()!=null){
					dp.setLimitCoefficient(ContractLofting.getLimitCoefficient());
				}
				if(ContractLofting.getLimitNum()!=null){
					dp.setLimitNum(ContractLofting.getLimitNum());
				}
				if(!ContractLoftingDao.updateContractLofting(dp)){
					result.setErrorCode(ErrorCodeEnum.Error);
				}else{
					result.setData(dp);
				}
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<Void> importContractLoftingByProjectId(String token, Long projectId,
			MultipartFile files, HttpServletRequest request) {
		// TODO Auto-generated method stub
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			ReadContractLoftingExcel read = new ReadContractLoftingExcel();
			String newFileName = MD5Util.getMD5String(files.getOriginalFilename() + new Date() + UUID.randomUUID().toString()).replace(".","")
                    + files.getOriginalFilename().substring(files.getOriginalFilename().lastIndexOf("."));
			List<ContractLofting> gets=read.getExcelInfo(newFileName, files, projectId);
			Date nowDate = new Date();
			for(int i=0;i<gets.size();i++){
				gets.get(i).setCreateDate(nowDate);
				gets.get(i).setCreateUser(user.getId());
			}
			HashMap<String,List<ProjectPartContractLofting>> gets2 = read.getProjectPartContractLoftingExcelInfo(newFileName, files, projectId);
			/************数组切分**************/
			/*(1)contractLofting实体集先存，然后返回实体的id*/
			/*(2)contractLofting实体集,先存储一级实体，返回id入contractLofting二级实体集*/
			/*(3)存二级实体集，返回id入contractLofting三级实体集*/
			/*(4)存三级实体集，返回id入contractLofting四级实体集*/
			/*(5)存四级实体集*/
			/*(6)存入ProjectPartContractLofting的contractLoftingId,ProjectPartContractLofting的实体集再存*/
			List<ContractLofting> parrent1 = gets.subList(0, 4);
			List<Integer> indexList = new ArrayList<Integer>();
			if(ContractLoftingDao.addContractLoftingList(parrent1)){
				for(int i=0;i<gets.size();i++){
					if(i<4){
						gets.get(i).setPid(parrent1.get(i).getId());
					}
					if(gets.get(i).getName().contains("系统")){
						indexList.add(i);///记录二级实体的位置信息
						gets.get(i).setPid(parrent1.get(3).getId());
						ContractLoftingDao.addContractLofting(gets.get(i));//存储二级目录实体
					}
				}
				for(int p=0;p<indexList.size();p++){
					if(p!=(indexList.size()-1)){
						int startIndex=indexList.get(p)+1;
						int endIndex=indexList.get(p+1);
						List<Integer> childList = new ArrayList<Integer>();
						for(int k=startIndex;k<endIndex;k++){
							if(!gets.get(k).getRemark().equals("")){
								childList.add(k);////记录三级目录下标
								gets.get(k).setPid(gets.get(indexList.get(p)).getId());//存入二级目录id
								ContractLoftingDao.addContractLofting(gets.get(k));//存储三级目录实体
							}
						}
						for(int q=0;q<childList.size();q++){
							int startIndex2=childList.get(q)+1;
							int endIndex2=0;
							if(q==(childList.size()-1)){
								endIndex2=indexList.get(p+1);
							}else{
								endIndex2=childList.get(q+1);
							}
							if(startIndex2!=endIndex2){
								for(int n=startIndex2;n<endIndex2;n++){
									gets.get(n).setPid(gets.get(childList.get(q)).getId());//存入三级目录id
								}
								ContractLoftingDao.addContractLoftingList(gets.subList(startIndex2, endIndex2));//存储四级目录实体
							}
						}
					}else{
						int startIndex=indexList.get(p)+1;
						int endIndex=gets.size();
						List<Integer> childList = new ArrayList<Integer>();
						for(int k=startIndex;k<endIndex;k++){
							if(!gets.get(k).getRemark().equals("")){
								childList.add(k);////记录三级目录下标
								gets.get(k).setPid(gets.get(indexList.get(p)).getId());//存入二级目录id
								ContractLoftingDao.addContractLofting(gets.get(k));//存储三级目录实体
							}
						}
						for(int q=0;q<childList.size();q++){
							int startIndex2=childList.get(q)+1;
							int endIndex2=0;
							if(q==(childList.size()-1)){
								endIndex2=gets.size();
							}else{
								 endIndex2=childList.get(q+1);
							}
							if(startIndex2!=endIndex2){
								for(int n=startIndex2;n<endIndex2;n++){
									gets.get(n).setPid(gets.get(childList.get(q)).getId());//存入三级目录id
								}
								ContractLoftingDao.addContractLoftingList(gets.subList(startIndex2-1, endIndex2));//存储四级目录实体
							}
						}
					}
				}
			}
			List<ProjectPartContractLofting> saveItems = new ArrayList<ProjectPartContractLofting>();
			
			for(int i=0;i<gets.size();i++){
				List<ProjectPartContractLofting> addList = new ArrayList<ProjectPartContractLofting>();
				saveItems=gets2.get(gets.get(i).getName());
				for(int j=0;j<saveItems.size();j++){
					saveItems.get(j).setCreateDate(new Date());
					saveItems.get(j).setpName(gets.get(i).getName());
					saveItems.get(j).setCreateUser(user.getId());
					saveItems.get(j).setContractLoftingId(gets.get(i).getId());
					saveItems.get(j).setProjectId(projectId);
					addList.add(saveItems.get(j));
				}
				pLoftingDao.addProjectPartContractLoftingList(addList);
			}
			
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<Void> deleteContractLoftingById(String token, String name) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(!ContractLoftingDao.deleteContractLoftingByName(name)){
				result.setErrorCode(ErrorCodeEnum.Error);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	
}
