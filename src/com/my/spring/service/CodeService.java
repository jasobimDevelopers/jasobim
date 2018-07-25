package com.my.spring.service;

import com.my.spring.utils.DataWrapper;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

public interface CodeService {
    
    public DataWrapper<List<String>> batchImport(String name,MultipartFile[] file,HttpServletRequest request);

	public DataWrapper<String> batchImportText(String filePath, String content,
			HttpServletRequest request);
	
}
