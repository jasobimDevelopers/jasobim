package com.my.spring.serviceImpl;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.my.spring.DAO.NatureDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Nature;
import com.my.spring.model.User;
import com.my.spring.service.NatureService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

@Service("natureService")
public class NatureServiceImpl implements NatureService  {
	@Autowired
	NatureDao NatureDao;
	@Autowired
	UserDao userDao;
	@Override
	public DataWrapper<Void> deleteNatureById(String token,Long id) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(!NatureDao.deleteNature(id)){
				result.setErrorCode(ErrorCodeEnum.Error);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<Nature> addNature(String token,Nature role) {
		DataWrapper<Nature> result = new DataWrapper<Nature>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(role!=null){
				role.setCreateUser(user.getId());
				role.setCreateDate(new Date());
				if(!NatureDao.addNature(role)){
					result.setErrorCode(ErrorCodeEnum.Error);
				}
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}



	@Override
	public DataWrapper<List<Nature>> getNatureList(Integer pageIndex, Integer pageSize, Nature Nature,
			String token) {
		DataWrapper<List<Nature>> dp = new DataWrapper<List<Nature>>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			dp = NatureDao.getNatureListByProjectId(pageIndex,pageSize);
		}else{
			dp.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dp;
	}

	@Override
	public DataWrapper<Void> updateNature(String token, Nature Nature) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(Nature!=null){
				Nature dp = new Nature();
				dp = NatureDao.getById(Nature.getId());
				if(Nature.getContent()!=null){
					dp.setContent(Nature.getContent());
				}
				if(!NatureDao.updateNature(dp)){
					result.setErrorCode(ErrorCodeEnum.Error);
				}
			}
			
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}
}
