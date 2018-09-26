package com.my.spring.serviceImpl;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.my.spring.DAO.ProjectOutputValueDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.ProjectOutputValue;
import com.my.spring.model.User;
import com.my.spring.service.ProjectOutputValueService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

@Service("projectOutputValueService")
public class ProjectOutputValueServiceImpl implements ProjectOutputValueService  {
	@Autowired
	ProjectOutputValueDao ProjectOutputValueDao;
	@Autowired
	UserDao userDao;
	@Override
	public DataWrapper<Void> deleteProjectOutputValueById(String token,Long id) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(!ProjectOutputValueDao.deleteProjectOutputValue(id)){
				result.setErrorCode(ErrorCodeEnum.Error);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<ProjectOutputValue> addProjectOutputValue(String token,ProjectOutputValue role) {
		DataWrapper<ProjectOutputValue> result = new DataWrapper<ProjectOutputValue>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(role!=null){
				role.setCreateUser(user.getId());
				role.setEditDate(new Date());
				if(!ProjectOutputValueDao.addProjectOutputValue(role)){
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
	public DataWrapper<ProjectOutputValue> getProjectOutputValueById(String token, Long id) {
		DataWrapper<ProjectOutputValue> dp = new DataWrapper<ProjectOutputValue>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			ProjectOutputValue dt = ProjectOutputValueDao.getById(id);
			dp.setData(dt);
		}else{
			dp.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dp;
	}

	@Override
	public DataWrapper<Void> updateProjectOutputValue(String token, ProjectOutputValue ProjectOutputValue) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(ProjectOutputValue!=null){
				ProjectOutputValue dp = new ProjectOutputValue();
				dp = ProjectOutputValueDao.getById(ProjectOutputValue.getId());
				if(dp==null){
					ProjectOutputValue.setEditDate(new Date());
					ProjectOutputValue.setCreateUser(user.getId());
					ProjectOutputValueDao.addProjectOutputValue(ProjectOutputValue);
				}else{
					dp.setEditDate(new Date());
					if(ProjectOutputValue.getArmour()!=null){
						dp.setArmour(ProjectOutputValue.getArmour());
					}
					if(ProjectOutputValue.getContractOutputValue()!=null){
						dp.setContractOutputValue(ProjectOutputValue.getContractOutputValue());
					}
					if(ProjectOutputValue.getContractPrice()!=null){
						dp.setContractPrice(ProjectOutputValue.getContractPrice());
					}
					if(ProjectOutputValue.getPeriodComparison()!=null){
						dp.setPeriodComparison(ProjectOutputValue.getPeriodComparison());
					}
					if(ProjectOutputValue.getVisaChange()!=null){
						dp.setVisaChange(ProjectOutputValue.getVisaChange());
					}
					dp.setEditDate(new Date());
					if(!ProjectOutputValueDao.updateProjectOutputValue(dp)){
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
	public DataWrapper<ProjectOutputValue> getProjectOutputValueByProjectId(Long projectId, String token) {
		DataWrapper<ProjectOutputValue> result = new DataWrapper<ProjectOutputValue>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			ProjectOutputValue get = ProjectOutputValueDao.getByProjectId(projectId);
			result.setData(get);
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	
}
