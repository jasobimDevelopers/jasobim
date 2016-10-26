package com.my.spring.service;
import org.springframework.web.multipart.MultipartFile;
public interface FileService {
	String uploadFile(String filePath, MultipartFile file);
	boolean deleteFile(String filePathAndName);
}
