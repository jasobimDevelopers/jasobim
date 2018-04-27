package com.my.spring.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.my.spring.model.Files;

public interface FileService {
	boolean deleteFileByPath(String filePathAndName,HttpServletRequest request);
	Files getById(Long id);
	boolean deleteFileById(Long id);
	boolean addFile(Files file);
	Files uploadFile(String filePath, MultipartFile file, Integer fileType,HttpServletRequest request);
	boolean deleteFileByIdList(String[] id);
	boolean deleteFileByReal(Long id, HttpServletRequest request);
	Files uploadFile(String filePath, MultipartFile file, Integer fileType, HttpServletRequest request, Long userId);
}
