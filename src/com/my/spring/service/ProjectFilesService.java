package com.my.spring.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.my.spring.model.PaperPojo;
import com.my.spring.model.ProjectFiles;
import com.my.spring.model.ProjectFilesPojo;
import com.my.spring.utils.DataWrapper;

public interface ProjectFilesService {
	DataWrapper<Void> addProjectFiles(ProjectFiles paper, String token, MultipartFile file, HttpServletRequest request);

	DataWrapper<Void> deleteProjectFiles(Long id, String token, HttpServletRequest request);


	DataWrapper<List<ProjectFilesPojo>> getProjectFilesList(String token, Integer pageIndex, Integer pageSize,
			ProjectFiles projectFiles);
}
