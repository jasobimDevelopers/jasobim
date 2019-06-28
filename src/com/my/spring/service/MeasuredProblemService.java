package com.my.spring.service;

import com.my.spring.model.MeasuredProblem;
import com.my.spring.model.MeasuredProblemPojo;
import com.my.spring.utils.DataWrapper;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

public interface MeasuredProblemService {
    DataWrapper<Void> addMeasuredProblem(MeasuredProblem building, String token,MultipartFile[] files,MultipartFile[] vois,HttpServletRequest request,String fDate);
    DataWrapper<Void> deleteMeasuredProblem(Long id,String token);
    DataWrapper<Void> updateMeasuredProblem(MeasuredProblem building,String token);
	DataWrapper<Void> qualityRectificationCheckAgain(String token, Long measuredId, Integer score, Integer state);
	DataWrapper<Void> qualityRectificationCheck(String token, Long measuredId, Integer schedule, String content,
			MultipartFile[] pics, MultipartFile[] vois, HttpServletRequest request);
	DataWrapper<List<MeasuredProblemPojo>> getMeasuredProblemByProjectId(Long id,Long projectId, String token, Integer status,
			String bfmIds, String checkTypeIds);
	DataWrapper<Void> updateMeasuredProblem(MeasuredProblem building, String token, MultipartFile[] files,
			MultipartFile[] vois, HttpServletRequest request, String fDate);
	DataWrapper<Void> addMeasuredProblemList(List<MeasuredProblem> mpList);
	DataWrapper<MeasuredProblemPojo> getMeasuredProblemByPointId(Long pointId, String token);
	DataWrapper<Void> updateMeasuredProblemList(String token, String editString);
}
