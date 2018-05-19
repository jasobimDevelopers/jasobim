package com.my.spring.service;

import com.my.spring.model.SafeFine;
import com.my.spring.model.SafeFinePojo;
import com.my.spring.utils.DataWrapper;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

public interface SafeFineService {
    DataWrapper<Void> deleteSafeFine(Long id,String token);
    DataWrapper<Void> updateSafeFine(SafeFine building,String token);
	DataWrapper<List<SafeFinePojo>> getSafeFineList(Integer pageIndex, Integer pageSize, SafeFine duct, String token);
	DataWrapper<Void> addSafeFine(SafeFine building, String token, MultipartFile[] files, HttpServletRequest request);
}
