package com.my.spring.serviceImpl;

import com.my.spring.DAO.ProjectDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.ProjectEntity;
import com.my.spring.service.ProjectService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Service("projectService")
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    ProjectDao ProjectDao;
    @Override
    public DataWrapper<Void> addProject(ProjectEntity project) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        if(!ProjectDao.addProject(project)) {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<Void> deleteProject(Long id) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        if(!ProjectDao.deleteProject(id)) {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<Void> updateProject(ProjectEntity project) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        if(!ProjectDao.updateProject(project)) {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<List<ProjectEntity>> getProjectList() {
        return ProjectDao.getProjectList();
    }
}
