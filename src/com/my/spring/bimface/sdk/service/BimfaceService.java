package com.my.spring.bimface.sdk.service;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import com.bimface.sdk.bean.response.CategoryBean;
import com.my.spring.utils.DataWrapper;
public interface BimfaceService {
	DataWrapper<String> getViewTokenByFileId(Long fileId);
	DataWrapper<Long> uploadModelFile(MultipartFile file, HttpServletRequest requestion, String token, Long projectId,Integer modeType);
	DataWrapper<String> getModeViewTokenByIntegrateId(Long integrateId, HttpServletRequest request, String token,
			Long projectId);
	DataWrapper<List<CategoryBean>> getCategory(Long fileId, String token, Long projectId);
}
