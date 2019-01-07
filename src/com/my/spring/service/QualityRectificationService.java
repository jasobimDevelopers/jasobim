package com.my.spring.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.my.spring.model.QualityRectification;
import com.my.spring.model.QualityRectificationPojo;
import com.my.spring.utils.DataWrapper;
public interface QualityRectificationService {
	DataWrapper<Void> deleteQualityRectificationById(String token,Long id);
	DataWrapper<Void> deleteQualityRectificationByIdList(String token,String[] id);
	DataWrapper<List<QualityRectificationPojo>> getQualityRectificationList(Integer pageIndex, Integer pageSize, QualityRectification QualityManage,
			String token);
	DataWrapper<Void> qualityRectificationCheckAgain(String token, Long qualityId, Integer score, Integer state);
	DataWrapper<QualityRectification> addQualityRectification(String token, String fDate, String sendUsers, MultipartFile[] pics,
			MultipartFile[] vois, HttpServletRequest request, QualityRectification role,Long qualityCheckId);
	DataWrapper<Void> qualityRectificationCheck(String token, Long qualityId, Integer schedule, String content,
			MultipartFile[] pics, MultipartFile[] voices, HttpServletRequest request);
	DataWrapper<Void> updateQualityRectification(String token, String fDate, MultipartFile[] pics, MultipartFile[] vois,
			HttpServletRequest request, QualityRectification QualityManage);
}
