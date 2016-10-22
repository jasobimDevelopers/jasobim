package com.my.spring.service;

import com.my.spring.model.ProjectEntity;
import com.my.spring.utils.DataWrapper;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface ProjectService {
    DataWrapper<Void> addProject(ProjectEntity project);
    DataWrapper<Void> deleteProject(Long id);
    DataWrapper<Void> updateProject(ProjectEntity project);
    DataWrapper<List<ProjectEntity>> getProjectList();
}
