package com.my.spring.serviceImpl;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.my.spring.DAO.UserIndexDao;
import com.alibaba.fastjson.JSONArray;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.UserIndex;
import com.my.spring.model.User;
import com.my.spring.service.UserIndexService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

@Service("UserIndexService")
public class UserIndexServiceImpl implements UserIndexService  {
	@Autowired
	UserIndexDao userIndexDao;
	@Autowired
	UserDao userDao;
	@Override
	public DataWrapper<Void> deleteUserIndexByAboutId(Integer type,Long id) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		if(!userIndexDao.deleteUserIndexByAboutId(id,type)){
			result.setErrorCode(ErrorCodeEnum.Error);
		}
		return result;
	}

	@Override
	public DataWrapper<UserIndex> addUserIndex(String token,UserIndex role) {
		DataWrapper<UserIndex> result = new DataWrapper<UserIndex>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(role!=null){
				if(!userIndexDao.addUserIndex(role)){
					result.setErrorCode(ErrorCodeEnum.Error);
				}else{
					result.setData(role);
				}
			}
			
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<Void> deleteUserIndexByIdList(String token,String[] id) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(!userIndexDao.deleteUserIndexList(id)){
				result.setErrorCode(ErrorCodeEnum.Error);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}


	@Override
	public DataWrapper<List<UserIndex>> getUserIndexList(Integer pageIndex, Integer pageSize, UserIndex UserIndex,
			String token) {
		DataWrapper<List<UserIndex>> dp = new DataWrapper<List<UserIndex>>();
		List<UserIndex> dpp = new ArrayList<UserIndex>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(UserIndex.getAboutType()!=null){
				switch(UserIndex.getAboutType()){
			        case 0://组织架构获取
			        {
			        	dpp=userIndexDao.getUserIndexListOfAbout(UserIndex.getUserId(),0);
			        	break;
			        }
			        case 1:
			        {
			        	dpp=userIndexDao.getUserIndexListOfAbout(UserIndex.getUserId(),1);
			        	break;
			        }
			        case 2:
			        {
			        	dpp=userIndexDao.getUserIndexListOfAbout(UserIndex.getUserId(),2);
			        	break;
			        }
			        case 3:
			        {
			        	dpp=userIndexDao.getUserIndexListOfAbout(UserIndex.getUserId(),3);
			        	break;
			        }
			        case 4:
			        {
			        	dpp=userIndexDao.getUserIndexListOfAbout(UserIndex.getUserId(),4);
			        	break;
			        }
			        case 5:
			        {
			        	dpp=userIndexDao.getUserIndexListOfAbout(UserIndex.getUserId(),5);
			        	break;
			        }
				}
				dp.setData(dpp);
			}
		}else{
			dp.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dp;
	}

	@Override
	public DataWrapper<Void> addUserIndexList(String newList, String token) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			List<UserIndex> gets = JSONArray.parseArray(newList, UserIndex.class);
			if(userIndexDao.deleteUserIndexByAboutType(user.getId(),gets.get(0).getAboutType())){
				if(!userIndexDao.addUserIndexList(gets)){
					result.setErrorCode(ErrorCodeEnum.Error);
				}
			}else{
				result.setErrorCode(ErrorCodeEnum.Error);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}



	
}
