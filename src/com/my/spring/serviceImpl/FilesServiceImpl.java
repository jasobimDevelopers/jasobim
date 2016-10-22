package com.my.spring.serviceImpl;

import com.my.spring.DAO.FilesDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Files;
import com.my.spring.service.FilesService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Service("filesService")
public class FilesServiceImpl implements FilesService {
    @Autowired
    FilesDao FilesDao;
    @Override
    public DataWrapper<Void> addFiles(Files files) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        if(!FilesDao.addFiles(files)) {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<Void> deleteFiles(Long id) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        if(!FilesDao.deleteFiles(id)) {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<Void> updateFiles(Files files) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        if(!FilesDao.updateFiles(files)) {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<List<Files>> getFilesList() {
        return FilesDao.getFilesList();
    }
}
