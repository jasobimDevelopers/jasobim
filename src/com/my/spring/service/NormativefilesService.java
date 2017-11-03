package com.my.spring.service;

import com.my.spring.model.Normativefiles;
import com.my.spring.model.NormativefilesPojo;
import com.my.spring.utils.DataWrapper;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;


public interface NormativefilesService {
    DataWrapper<List<NormativefilesPojo>> getNormativefilesList(String token, Integer pageIndex, Integer pageSize, Normativefiles normativefiles);
    DataWrapper<List<Normativefiles>> getNormativefilesListByUserId(Long userId,String token);
	DataWrapper<Void> deleteNormativefiles(Long id, String token);
	DataWrapper<Void> addNormativefiles(Normativefiles Normativefiles, String token, MultipartFile[] fileList,
			Integer fileType, HttpServletRequest request);
}
