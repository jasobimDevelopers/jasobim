package com.my.spring.DAO;
import java.util.List;

import com.my.spring.model.UserProject;
public interface UserProjectDao {
    boolean addUserProject(UserProject userProject);
    boolean deleteUserProject(Long id);
    UserProject getById(Long id);
	boolean addUserProjectList(List<UserProject> paper);
	List<UserProject> getUserProjectListByUserId(Long id);
	UserProject getUserProjectListByUserIdAndProjectId(Long id, Long valueOf);
}
