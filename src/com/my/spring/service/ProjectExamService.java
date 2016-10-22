package com.my.spring.service;

import com.my.spring.model.ProjectExam;
import com.my.spring.utils.DataWrapper;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface ProjectExamService {
	DataWrapper<Void> addProjectExam(ProjectExam projectExam);
    DataWrapper<Void> deleteProjectExam(Long id);
    DataWrapper<Void> updateProjectExam(ProjectExam projectExam);
    DataWrapper<List<ProjectExam>> getProjectExamList();
}
