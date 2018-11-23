package com.my.spring.DAO;

import com.my.spring.model.Project;
import com.my.spring.model.ProjectIndex;
import com.my.spring.model.Projectvs;
import com.my.spring.model.User;
import com.my.spring.model.UserProject;
import com.my.spring.utils.DataWrapper;
import java.util.List;

public interface ProjectDao {
    boolean addProject(Project project);
    boolean deleteProject(Long id);
    boolean updateProject(Project project);
    Project getById(Long id);
	DataWrapper<Project> findProjectLike(Project project);
	DataWrapper<List<Projectvs>> getProjectList(Integer pageSize, Integer pageIndex, Project project, User userInMemory);
	DataWrapper<List<Project>> getProjectList(Integer pageSize, Integer pageIndex, Project project, String content,
			Integer isIos, List<UserProject> up);
	List<Project> getAllProjects();
	List<ProjectIndex> getProjectListByUserId(Long id, Integer pageSize, Integer pageIndex);
}
