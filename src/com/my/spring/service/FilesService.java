package com.my.spring.service;

import com.my.spring.model.Files;
import com.my.spring.utils.DataWrapper;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface FilesService {
	DataWrapper<Void> addFiles(Files files);
    DataWrapper<Void> deleteFiles(Long id);
    DataWrapper<Void> updateFiles(Files files);
    DataWrapper<List<Files>> getFilesList();
}
