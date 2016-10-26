package com.my.spring.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.my.spring.utils.DataWrapper;

public interface FileService {
	String uploadFile(String filePath, MultipartFile file);
	boolean deleteFile(String filePathAndName);
}
