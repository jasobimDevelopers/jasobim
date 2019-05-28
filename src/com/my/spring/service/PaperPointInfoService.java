package com.my.spring.service;

import com.my.spring.model.PaperPointInfo;
import com.my.spring.utils.DataWrapper;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;


public interface PaperPointInfoService {
    DataWrapper<Void> addPaperPointInfo(PaperPointInfo building,MultipartFile paper,HttpServletRequest request, String token);
    DataWrapper<Void> deletePaperPointInfo(Long id,String token);
    DataWrapper<Void> updatePaperPointInfo(PaperPointInfo building,String token);
    DataWrapper<List<PaperPointInfo>> getPaperPointInfoList();
	DataWrapper<PaperPointInfo> getPaperPointInfoByProjectId(Long projectId,String token);
}
