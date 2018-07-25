package com.my.spring.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.my.spring.model.DepartmentUser;
import com.my.spring.model.DepartmentUserPojo;
import com.my.spring.utils.DataWrapper;

public interface DepartmentUserService {
	DataWrapper<Void> deleteDepartmentUserById(String token, Long id);
	DataWrapper<Void> deleteDepartmentByIdList(String token, String[] id);
	DataWrapper<List<DepartmentUserPojo>> getDepartmentUserList(Integer pageIndex, Integer pageSize,
			DepartmentUser departmentUser, String token);
	DataWrapper<DepartmentUserPojo> getDepartmentUserById(String token, Long id);
	DataWrapper<Void> updateDepartmentUser(String token, DepartmentUser departmentUser, MultipartFile idCardImgZ,
			MultipartFile idCardImgF, HttpServletRequest request);
	DataWrapper<Void> addDepartmentUser(String token, DepartmentUser dt, MultipartFile idCardImgZ,
			MultipartFile idCardImgF, HttpServletRequest request);
}
