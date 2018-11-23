package com.my.spring.DAO;

import com.my.spring.model.Files;
import com.my.spring.utils.DataWrapper;
import java.util.List;

public interface FileDao {
	Files getById(Long id);
	DataWrapper<Files> getByName(String name);
	boolean deleteFiles(Long id);
	boolean addFiles(Files file);
	DataWrapper<List<Files>> getFilesList();
	boolean deleteFilesList(String[] ids);
}
