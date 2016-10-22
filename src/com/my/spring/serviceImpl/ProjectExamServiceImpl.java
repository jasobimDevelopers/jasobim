package com.my.spring.serviceImpl;

import com.my.spring.DAO.ProjectExamDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.ProjectExam;
import com.my.spring.service.ProjectExamService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Service("projectExamService")
public class ProjectExamServiceImpl implements ProjectExamService {
    @Autowired
    ProjectExamDao ProjectExamDao;
    @Override
    public DataWrapper<Void> addProjectExam(ProjectExam projectExam) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        if(!ProjectExamDao.addProjectExam(projectExam)) {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<Void> deleteProjectExam(Long id) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        if(!ProjectExamDao.deleteProjectExam(id)) {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<Void> updateProjectExam(ProjectExam projectExam) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        if(!ProjectExamDao.updateProjectExam(projectExam)) {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<List<ProjectExam>> getProjectExamList() {
        return ProjectExamDao.getProjectExamList();
    }
}
