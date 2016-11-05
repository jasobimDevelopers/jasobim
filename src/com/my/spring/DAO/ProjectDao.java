package com.my.spring.DAO;

import com.my.spring.model.Project;
import com.my.spring.utils.DataWrapper;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface ProjectDao {
    boolean addProject(Project project);
    boolean deleteProject(Long id);
    boolean updateProject(Project project);
    DataWrapper<List<Project>> getProjectList();
    Project getById(Long id);
	DataWrapper<List<Project>> findProjectLike(Project project);
}
