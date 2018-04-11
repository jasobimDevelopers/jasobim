package com.my.spring.serviceImpl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.my.spring.DAO.FolderDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Folder;
import com.my.spring.model.FolderPojo;
import com.my.spring.model.User;
import com.my.spring.service.FolderService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.NodeUtil;
import com.my.spring.utils.SessionManager;

@Service("Floderervice")
public class FolderServiceImpl implements FolderService  {
	@Autowired
	FolderDao folderDao;
	@Autowired
	UserDao userDao;
	//子节点  
    static  List<Folder> childMenu=new ArrayList<Folder>();  
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	@Override
	public boolean deleteFloderByPath(String filePathAndName,HttpServletRequest request) {
		// TODO Auto-generated method stub
		boolean flag = false;
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		File file = new File(rootPath + filePathAndName);
        try {
            flag = file.delete();
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return flag;
	}

	@Override
	public Folder getById(Long id) {
		// TODO Auto-generated method stub
		return folderDao.getById(id);
	}

	@Override
	public boolean deleteFloderById(Long id) {
		// TODO Auto-generated method stub
		return folderDao.deleteFloder(id);
	}

	@Override
	public boolean addFloder(Folder file) {
		// TODO Auto-generated method stub
		return folderDao.addFloder(file);
	}

	@Override
	public DataWrapper<Object> getFolderList(String token, Folder floder) {
		// TODO Auto-generated method stub
		DataWrapper<Object> foldersList = new DataWrapper<Object>();
		DataWrapper<List<Folder>> folders = new DataWrapper<List<Folder>>();
		List<FolderPojo> folderPojos = new ArrayList<FolderPojo>();
		List<Folder> folderss = new ArrayList<Folder>();
		User userInMemory=SessionManager.getSession(token); 
		if(userInMemory!=null){
			folders=folderDao.getFolderList(floder);
			if(folders.getData()!=null)
			{
				List dataList = new ArrayList();  
				for(Folder ss:folders.getData()){
					HashMap dataRecord1 = new HashMap();  
					dataRecord1.put("id", ss.getId().toString());
					dataRecord1.put("name", ss.getName());
					if(ss.getParrentId()==0){
						dataRecord1.put("parentId", "");
					}else{
						dataRecord1.put("parentId", ss.getParrentId().toString());
					}
					String str=sdf.format(ss.getCreateDate());
					dataRecord1.put("uploadDate", str);
					if(ss.getUserId()!=null){
						User user = userDao.getById(ss.getUserId());
						if(user!=null){
							String userName=user.getUserName();
							dataRecord1.put("userName", userName);
						}else{
							dataRecord1.put("userName", "");
						}
						
					}
					dataList.add(dataRecord1);
				}
				NodeUtil nodeUtil = new NodeUtil();
				
				String resultString=nodeUtil.getJasonString(dataList);
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
	
}
