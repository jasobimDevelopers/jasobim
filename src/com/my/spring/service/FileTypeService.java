package com.my.spring.service;

import com.my.spring.model.FileType;
import com.my.spring.utils.DataWrapper;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface FileTypeService {
	DataWrapper<Void> addFileType(FileType fileType);
    DataWrapper<Void> deleteFileType(Long id);
    DataWrapper<Void> updateFileType(FileType fileType);
    DataWrapper<List<FileType>> getFileTypeList();
}
