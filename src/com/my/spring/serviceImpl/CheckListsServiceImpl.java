package com.my.spring.serviceImpl;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.my.spring.DAO.CheckListsDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.CheckLists;
import com.my.spring.model.User;
import com.my.spring.service.CheckListsService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

@Service("checkListsService")
public class CheckListsServiceImpl implements CheckListsService  {
	@Autowired
	CheckListsDao CheckListsDao;
	@Autowired
	UserDao userDao;
	@Override
	public DataWrapper<Void> deleteCheckListsById(String token,Long id) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(!CheckListsDao.deleteCheckLists(id)){
				result.setErrorCode(ErrorCodeEnum.Error);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<CheckLists> addCheckLists(String token,CheckLists role) {
		DataWrapper<CheckLists> result = new DataWrapper<CheckLists>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(role!=null){
				role.setCreateUser(user.getId());
				role.setCreateDate(new Date());
				if(!CheckListsDao.addCheckLists(role)){
					result.setErrorCode(ErrorCodeEnum.Error);
				}
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}



	@Override
	public DataWrapper<List<CheckLists>> getCheckListsList(Integer pageIndex, Integer pageSize, CheckLists CheckLists,
			String token) {
		DataWrapper<List<CheckLists>> dp = new DataWrapper<List<CheckLists>>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			dp = CheckListsDao.getCheckListsListByProjectId(pageIndex,pageSize,CheckLists.getProjectId(),CheckLists.getCheckType());
		}else{
			dp.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dp;
	}

	@Override
	public DataWrapper<Void> updateCheckLists(String token, CheckLists CheckLists) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(CheckLists!=null){
				CheckLists dp = new CheckLists();
				dp = CheckListsDao.getById(CheckLists.getId());
				if(CheckLists.getContent()!=null){
					dp.setContent(CheckLists.getContent());
				}
				if(!CheckListsDao.updateCheckLists(dp)){
					result.setErrorCode(ErrorCodeEnum.Error);
				}
			}
			
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}
}
