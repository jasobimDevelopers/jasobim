package com.my.spring.service;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.my.spring.model.Folder;
import com.my.spring.model.FolderPojo;
import com.my.spring.utils.DataWrapper;

public interface FolderService {
	Folder getById(Long id);
	DataWrapper<Object> getFolderList(String token,Folder floder);
	DataWrapper<Void> addFloder(String token, Folder floder);
	DataWrapper<Void> uploadFloder(String token, Folder floder, MultipartFile[] fileList, HttpServletRequest request);
	DataWrapper<Void> uploadFloderFiles(String token, String filePath, MultipartFile fileList,
			HttpServletRequest request,Long pid,Long projectId);
	DataWrapper<Void> updateFloder(String token, String name, Long id);
	DataWrapper<List<FolderPojo>> findFileLists(String token, String name, Long projectId);
	DataWrapper<List<FolderPojo>> getFolderIndexList(String token, Long projectId, Long pid);
	DataWrapper<Void> takeFolderTo(String token, Long id, Long pid);
	DataWrapper<Void> batchDownload(String token, String ids,Long pid,Long projectId, HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException, IOException, ServletException, Exception;
	List<Folder> selectByIds(String ids);
	DataWrapper<Void> testDownloadZip(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, Exception;
	DataWrapper<Void> deleteFloder(String token, String id);
}
