package com.my.spring.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.my.spring.model.PaperOfMeasured;
import com.my.spring.model.PaperOfMeasuredPojo;
import com.my.spring.utils.DataWrapper;
public interface PaperOfMeasuredService {
    DataWrapper<Void> addPaperOfMeasured(PaperOfMeasured building, String token, MultipartFile file, HttpServletRequest request);
    DataWrapper<Void> deletePaperOfMeasured(Long id,String token);
	DataWrapper<PaperOfMeasuredPojo> getPaperOfMeasuredByProjectId(Long projectId,String token);
}
