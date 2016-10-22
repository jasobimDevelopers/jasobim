package com.my.spring.DAO;

import com.my.spring.model.ProjectExam;
import com.my.spring.utils.DataWrapper;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface ProjectExamDao {
    boolean addProjectExam(ProjectExam projectExam);
    boolean deleteProjectExam(Long id);
    boolean updateProjectExam(ProjectExam projectExam);
    DataWrapper<List<ProjectExam>> getProjectExamList();
}
