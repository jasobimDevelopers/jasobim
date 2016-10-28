package com.my.spring.serviceImpl;

import com.my.spring.DAO.FileTypeDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Files;
import com.my.spring.service.FileTypeService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Service("fileTypeService")
public class FileTypeServiceImpl implements FileTypeService {
    @Autowired
    FileTypeDao FileTypeDao;
    @Override
    public DataWrapper<Void> addFileType(Files fileType) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        if(!FileTypeDao.addFileType(fileType)) {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<Void> deleteFileType(Long id) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        if(!FileTypeDao.deleteFileType(id)) {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<Void> updateFileType(Files fileType) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        if(!FileTypeDao.updateFileType(fileType)) {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<List<Files>> getFileTypeList() {
        return FileTypeDao.getFileTypeList();
    }
}
