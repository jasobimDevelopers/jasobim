package com.my.spring.DAO;

import com.my.spring.model.FileType;
import com.my.spring.utils.DataWrapper;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface FileTypeDao {
	boolean addFileType(FileType fileType);
    boolean deleteFileType(Long id);
    boolean updateFileType(FileType fileType);
    DataWrapper<List<FileType>> getFileTypeList();
}
