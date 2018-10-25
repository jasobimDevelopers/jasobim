package com.my.spring.DAO;
import com.my.spring.model.ProjectProcess;
import com.my.spring.model.UserIndexs;
import com.my.spring.utils.DataWrapper;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
public interface ProjectProcessDao {
	ProjectProcess getById(Long id);
	boolean deleteProjectProcess(Long id);
	boolean addProjectProcess(ProjectProcess role);
	boolean deleteProjectProcessList(String[] ids);
	DataWrapper<List<ProjectProcess>> getProjectProcessList(Integer pageIndex, Integer pageSize, ProjectProcess ProjectProcess);
	boolean updateProjectProcess(ProjectProcess dp);
	List<UserIndexs> getProjectProcessListByUserId(Long userId);
	
}
