package com.my.spring.DAO;

import com.my.spring.model.File;
import com.my.spring.utils.DataWrapper;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface FileTypeDao {
	boolean addFileType(File fileType);
    boolean deleteFileType(Long id);
    boolean updateFileType(File fileType);
    DataWrapper<List<File>> getFileTypeList();
}
