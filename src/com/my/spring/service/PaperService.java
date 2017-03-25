package com.my.spring.service;

import com.my.spring.model.Paper;
import com.my.spring.model.PaperPojo;
import com.my.spring.utils.DataWrapper;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface PaperService {
    DataWrapper<Void> updatePaper(Paper paper,String token);
	DataWrapper<Paper> getPaperDetailsByAdmin(Long paperId,String token);
	DataWrapper<List<PaperPojo>> getPaperList(Long projectId,String token, Integer pageIndex, Integer pageSize, Paper paper);
	DataWrapper<Void> addPaper(Paper paper, String token, MultipartFile file,
			HttpServletRequest request);
	DataWrapper<Void> deletePaper(Long id, Long fileid, String token, HttpServletRequest request);
	DataWrapper<Void> deletePaperByAdmin(Long id, String token, HttpServletRequest request);
	DataWrapper<List<PaperPojo>> getPaperLists(Long projectId, String token, Integer pageIndex, Integer pageSize,
			Paper paper,String content);
	DataWrapper<List<PaperPojo>> getPapers(HttpServletRequest request,Long projectId, String token, Integer pageIndex, Integer pageSize,
			Paper paper);
}
