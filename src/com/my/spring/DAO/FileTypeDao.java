package com.my.spring.DAO;

import com.my.spring.model.Files;
import com.my.spring.utils.DataWrapper;
import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface FileTypeDao {
	boolean addFileType(Files fileType);
    boolean deleteFileType(Long id);
    boolean updateFileType(Files fileType);
    DataWrapper<List<Files>> getFileTypeList();
}
