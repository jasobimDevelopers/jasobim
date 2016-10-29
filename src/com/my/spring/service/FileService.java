package com.my.spring.service;

import org.springframework.web.multipart.MultipartFile;

import com.my.spring.model.Files;

public interface FileService {
	boolean deleteFileByPath(String filePathAndName);
	/*File getById(Long id);
	boolean deleteFileById(Long id);
	boolean addFile(File file);
	DataWrapper<List<File>> getFileList();*/
	Files uploadFile(String filePath, MultipartFile file, Integer fileType);
}
