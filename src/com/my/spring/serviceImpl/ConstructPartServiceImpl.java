package com.my.spring.serviceImpl;
import com.my.spring.DAO.ConstructPartDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.ConstructPart;
import com.my.spring.model.User;
import com.my.spring.service.ConstructPartService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service("constructPartService")
public class ConstructPartServiceImpl implements ConstructPartService {

    @Autowired
	ConstructPartDao constructPartDao;
	@Autowired
	UserDao userDao;
	@Override
	public DataWrapper<Void> deleteConstructPartById(Long id,String token) {
		// TODO Auto-generated method stub
		DataWrapper<Void> result = new DataWrapper<Void>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			constructPartDao.deleteConstructPart(id);
		}else{
			result.setErrorCode(ErrorCodeEnum.AUTH_Error);
		}
		return result;
	}

	@Override
	public DataWrapper<ConstructPart> addConstructPart(ConstructPart constructPart,String token) {
		DataWrapper<ConstructPart> result = new DataWrapper<ConstructPart>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			if(!constructPartDao.addConstructPart(constructPart)){
				result.setErrorCode(ErrorCodeEnum.Error);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.AUTH_Error);
		}
		return result;
	}

	@Override
	public DataWrapper<List<ConstructPart>> getConstructPartList(String token,ConstructPart constructPart) {
		DataWrapper<List<ConstructPart>> result = new DataWrapper<List<ConstructPart>>();
		List<ConstructPart> getList = new ArrayList<ConstructPart>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			getList = constructPartDao.getConstructPartList(constructPart);
			result.setData(getList);
		}else{
			result.setErrorCode(ErrorCodeEnum.AUTH_Error);
		}
		return result;
	}

	@Override
	public DataWrapper<ConstructPart> getConstructPartById(String token, Long id) {
		// TODO Auto-generated method stub
		DataWrapper<ConstructPart> result = new DataWrapper<ConstructPart>();
		ConstructPart get = new ConstructPart();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			get = constructPartDao.getById(id);
			result.setData(get);
		}else{
			result.setErrorCode(ErrorCodeEnum.AUTH_Error);
		}
		return result;
	}



}
