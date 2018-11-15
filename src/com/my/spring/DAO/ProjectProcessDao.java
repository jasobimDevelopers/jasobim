package com.my.spring.DAO;
import com.my.spring.model.ProjectProcess;
import com.my.spring.model.UserIndexs;
import com.my.spring.utils.DataWrapper;
import java.util.List;
public interface ProjectProcessDao {
	ProjectProcess getById(Long id);
	boolean deleteProjectProcess(Long id);
	boolean addProjectProcess(ProjectProcess role);
	boolean deleteProjectProcessList(String[] ids);
	DataWrapper<List<ProjectProcess>> getProjectProcessList(Integer pageIndex, Integer pageSize, ProjectProcess ProjectProcess);
	boolean updateProjectProcess(ProjectProcess dp);
	List<UserIndexs> getProjectProcessListByUserId(Long userId);
	List<ProjectProcess> getProjectProcessList(ProjectProcess projectProcess);
	boolean deleteProjectProcessByProjectId(Long projectId);
	List<ProjectProcess> getProjectProcessListByName(String name, Long projectId);
	
}
