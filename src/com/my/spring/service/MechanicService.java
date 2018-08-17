package com.my.spring.service;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.my.spring.model.Mechanic;
import com.my.spring.model.MechanicPojo;
import com.my.spring.model.MechanicPojos;
import com.my.spring.utils.DataWrapper;

public interface MechanicService {
	 DataWrapper<Void> deleteMechanic(Long id,String token);
	DataWrapper<List<MechanicPojo>> getMechanicList(String token, Mechanic ps, Integer pageSize,
			Integer pageIndex) throws ParseException;
	DataWrapper<List<MechanicPojos>> getMechanicInfos(String token, Mechanic ps) throws ParseException;
	DataWrapper<Void> addMechanic(Mechanic am, String token, MultipartFile file, MultipartFile file2,
			HttpServletRequest request);
	DataWrapper<Void> updateMechanic(Mechanic am, String token, MultipartFile file, MultipartFile file2,
			HttpServletRequest request);
}
