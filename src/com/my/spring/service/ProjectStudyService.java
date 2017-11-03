package com.my.spring.service;

import com.my.spring.model.ProjectStudy;
import com.my.spring.model.ProjectStudyPojo;
import com.my.spring.utils.DataWrapper;

import java.util.List;


public interface ProjectStudyService {
    DataWrapper<List<ProjectStudyPojo>> getProjectStudyList(String token, Integer pageIndex, Integer pageSize, ProjectStudy ProjectStudy);
    DataWrapper<List<ProjectStudy>> getProjectStudyListByUserId(Long userId,String token);
	DataWrapper<Void> addProjectStudy(ProjectStudy ProjectStudy, String token);
	DataWrapper<Void> deleteProjectStudy(Long id, String token);
}
