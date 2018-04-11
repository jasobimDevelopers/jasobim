package com.my.spring.DAO;

import com.my.spring.model.ProjectStudy;
import com.my.spring.utils.DataWrapper;

import java.util.List;
public interface ProjectStudyDao {
	boolean addProjectStudy(ProjectStudy ps);
    boolean deleteProjectStudy(Long id);
    boolean updateProjectStudy(ProjectStudy ps);
    ProjectStudy getById(Long id);
    DataWrapper<List<ProjectStudy>> getProjectStudysList(Integer pageIndex, Integer pageSize, ProjectStudy news);
    DataWrapper<List<ProjectStudy>> getProjectStudysListByUserId(Long userId);
}
