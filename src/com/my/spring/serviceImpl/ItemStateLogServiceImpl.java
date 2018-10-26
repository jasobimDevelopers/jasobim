package com.my.spring.serviceImpl;

import com.my.spring.DAO.ItemDao;
import com.my.spring.DAO.ItemStateLogDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.Item;
import com.my.spring.model.ItemCount;
import com.my.spring.model.ItemStateLog;
import com.my.spring.model.ItemStateLogPojo;
import com.my.spring.model.User;
import com.my.spring.service.ItemStateLogService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service("itemStateLogService")
public class ItemStateLogServiceImpl implements ItemStateLogService {
    @Autowired
    ItemStateLogDao ItemStateLogDao;
    @Autowired
    UserDao userDao;
    @Autowired
    ItemDao itemDao;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    public DataWrapper<ItemStateLog> addItemStateLog(ItemStateLog ItemStateLog,String token,String selfIdList) {
        DataWrapper<ItemStateLog> dataWrapper = new DataWrapper<ItemStateLog>();
        User userInMemory = SessionManager.getSession(token);
        List<ItemStateLog> gets = new ArrayList<ItemStateLog>();
        if (userInMemory != null) {
			if(ItemStateLog!=null && selfIdList!=null){
				List<Item> item =  itemDao.getItemBySelfId(ItemStateLog,selfIdList);
				if(!item.isEmpty()){
					for(int i=0;i<item.size();i++){
						ItemStateLog newone = new ItemStateLog();
						item.get(i).setState(ItemStateLog.getStatus());
						newone.setActionDate(new Date());
						newone.setProjectId(ItemStateLog.getProjectId());
						newone.setSelfId(item.get(i).getSelfId());
						newone.setStatus(ItemStateLog.getStatus());
						newone.setUserId(userInMemory.getId());
						gets.add(newone);
						itemDao.updateItem(item.get(i));
					}
				}
				if(ItemStateLogDao.addList(gets)){
					dataWrapper.setData(ItemStateLog);
				} 
				
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrapper;
    }
    
    public DataWrapper<Void> deleteItemStateLog(Long id,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(id!=null){
					if(!ItemStateLogDao.deleteItemStateLog(id)) 
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
    public DataWrapper<Void> updateItemStateLog(ItemStateLog ItemStateLog,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
 				if(ItemStateLog!=null){
					if(!ItemStateLogDao.updateItemStateLog(ItemStateLog)) 
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
    public DataWrapper<List<ItemStateLog>> getItemStateLogList() {
        return ItemStateLogDao.getItemStateLogList();
    }

	@Override
	public DataWrapper<ItemStateLog> getItemStateLogByProjectId(Long projectId,String token) {
		// TODO Auto-generated method stub
		DataWrapper<ItemStateLog> dataWrapper = new DataWrapper<ItemStateLog>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
				dataWrapper=ItemStateLogDao.getItemStateLogByProjectId(projectId);
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dataWrapper;
	}

	@Override
	public DataWrapper<List<ItemStateLogPojo>> getItemStateLogList(String token, Long projectId, String idList) {
		// TODO Auto-generated method stub
		DataWrapper<List<ItemStateLogPojo>> dataWrapper = new DataWrapper<List<ItemStateLogPojo>>();
		List<ItemStateLogPojo> getList = new ArrayList<ItemStateLogPojo>();
		List<ItemStateLog> gets = new ArrayList<ItemStateLog>();
		User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
        	gets=ItemStateLogDao.getItemStateLogList(projectId,idList);
        	if(!gets.isEmpty()){
        		List<ItemCount> getItems = itemDao.getNumsGroupBy(projectId);
        		for(int i=0;i<gets.size();i++){
        			ItemStateLogPojo pojo = new ItemStateLogPojo();
        		    //获取String类型的时间
        			pojo.setActionDate(sdf.format(gets.get(i).getActionDate()));
        			User user = userDao.getById(gets.get(i).getUserId());
        			if(user!=null){
        				pojo.setRealName(user.getRealName());
        			}
        			Item mode = itemDao.getItemBySelfId(gets.get(i).getSelfId(),gets.get(i).getProjectId());
        			if(mode!=null){
        				if(!getItems.isEmpty()){
            				for(int j=0;j<getItems.size();j++){
            					if(getItems.get(j).getName().equals(mode.getName()) 
        								&& getItems.get(j).getServiceType().equals(mode.getServiceType())
        								&& getItems.get(j).getSystemType().equals(mode.getSystemType())
        								&& getItems.get(j).getFamilyAndType().equals(mode.getFamilyAndType())
        								&& getItems.get(j).getSize().equals(mode.getSize())){
            						NumberFormat numberFormat = NumberFormat.getInstance();
            						// 设置精确到小数点后2位
            						numberFormat.setMaximumFractionDigits(2);
            						if(mode.getLength()>0){
            							Double num1 =mode.getLength();
                						Double num2 =getItems.get(j).getLength();
                						pojo.setPercent(numberFormat.format(num1 / num2 * 100)+"%");
            						}else if(mode.getArea()>0){
            							Double num1 =mode.getArea();
                						Double num2 =getItems.get(j).getArea();
                						pojo.setPercent(numberFormat.format(num1 / num2 * 100)+"%");
            						}else{
            							Double num1 =1.0;
                						Integer num2 =getItems.get(j).getNum();
                						pojo.setPercent(numberFormat.format(num1 / num2 * 100)+"%");
            						}
            					}
            				}
            			}
        			}
        			getList.add(pojo);
        		}
        		dataWrapper.setData(getList);
        	}
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dataWrapper;
	}

	@Override
	public DataWrapper<List<ItemStateLogPojo>> getAllItemStateLogList(String token, Long projectId) {
		DataWrapper<List<ItemStateLogPojo>> result = new DataWrapper<List<ItemStateLogPojo>>();
		List<ItemStateLog> getList = new ArrayList<ItemStateLog>();
		List<ItemStateLogPojo> resultList = new ArrayList<ItemStateLogPojo>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			ItemStateLog item = new ItemStateLog();
			getList=ItemStateLogDao.getAllItemStateLogByProjectId(projectId);
			if(!getList.isEmpty()){
				for(int i=0;i<getList.size();i++){
					ItemStateLogPojo pojo = new ItemStateLogPojo();
					pojo.setSelfId(getList.get(i).getSelfId());
					pojo.setStatus(getList.get(i).getStatus());
					pojo.setId(getList.get(i).getId());
					pojo.setActionDate(sdf.format(getList.get(i).getActionDate()));
					resultList.add(pojo);
				}
				result.setData(resultList);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}
}
