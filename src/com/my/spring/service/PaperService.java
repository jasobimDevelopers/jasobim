package com.my.spring.service;

import com.my.spring.model.Paper;
import com.my.spring.utils.DataWrapper;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface PaperService {
    DataWrapper<Void> addPaper(Paper paper, String token,MultipartFile file);
    DataWrapper<Void> updatePaper(Paper paper,String token);
	DataWrapper<Paper> getPaperDetailsByAdmin(Long paperId,String token);
	DataWrapper<List<Paper>> getPaperList(String token);
	DataWrapper<Void> deletePaper(Long id, Long fileid, String token);
}
