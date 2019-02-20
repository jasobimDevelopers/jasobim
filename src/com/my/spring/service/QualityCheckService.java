package com.my.spring.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.my.spring.model.QualityCheck;
import com.my.spring.model.QualityCheckPartPojo;
import com.my.spring.model.QualityCheckPojo;
import com.my.spring.utils.DataWrapper;
public interface QualityCheckService {
	DataWrapper<Void> deleteQualityCheckById(String token,Long id);
	DataWrapper<Void> deleteQualityCheckByIdList(String token,String[] id);
	DataWrapper<List<QualityCheckPojo>> getQualityCheckList(Integer pageIndex, Integer pageSize, QualityCheck QualityManage,
			String token,String start,String end,String find);
	DataWrapper<Void> updateQualityCheck(String token, MultipartFile[] pics, MultipartFile[] vois,
			HttpServletRequest request, QualityCheck QualityManage);
	DataWrapper<Long> addQualityCheck(String token, MultipartFile[] pics, MultipartFile[] vois,
			HttpServletRequest request, QualityCheck role);
	DataWrapper<List<QualityCheckPartPojo>> getQualityCheckPartList(Integer pageIndex, Integer pageSize, String token,Long projectId);
}
