package com.my.spring.service;


import javax.servlet.http.HttpServletRequest;

import com.my.spring.model.Folder;
import com.my.spring.utils.DataWrapper;

public interface FolderService {
	boolean deleteFloderByPath(String filePathAndName,HttpServletRequest request);
	Folder getById(Long id);
	boolean deleteFloderById(Long id);
	boolean addFloder(Folder file);
	DataWrapper<Object> getFolderList(String token,Folder floder);
}
