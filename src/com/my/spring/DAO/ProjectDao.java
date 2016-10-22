package com.my.spring.DAO;

import com.my.spring.model.ProjectEntity;
import com.my.spring.utils.DataWrapper;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface ProjectDao {
    boolean addProject(ProjectEntity project);
    boolean deleteProject(Long id);
    boolean updateProject(ProjectEntity project);
    DataWrapper<List<ProjectEntity>> getProjectList();
}
