package com.my.spring.DAO;

import com.my.spring.model.Files;
import com.my.spring.utils.DataWrapper;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface FilesDao {
    boolean addFiles(Files files);
    boolean deleteFiles(Long id);
    boolean updateFiles(Files files);
    DataWrapper<List<Files>> getFilesList();
}
