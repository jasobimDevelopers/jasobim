package com.my.spring.bimface.sdk.service;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;
import com.my.spring.utils.DataWrapper;
public interface BimfaceService {
	DataWrapper<String> getViewTokenByFileId(Long fileId);
	DataWrapper<Long> uploadModelFile(MultipartFile file, HttpServletRequest requestion, String token, Long projectId);
	DataWrapper<String> getModeViewTokenByIntegrateId(Long integrateId, HttpServletRequest request, String token,
			Long projectId);
}
