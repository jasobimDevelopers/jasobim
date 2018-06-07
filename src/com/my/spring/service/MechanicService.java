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
	 DataWrapper<Void> addMechanic(Mechanic duct, String token, MultipartFile file, HttpServletRequest request);
	 DataWrapper<Void> deleteMechanic(Long id,String token);
	 DataWrapper<Void> updateMechanic(Mechanic duct,String token, MultipartFile file, HttpServletRequest request);
	DataWrapper<List<MechanicPojo>> getMechanicList(String token, Mechanic ps, Integer pageSize,
			Integer pageIndex) throws ParseException;
	DataWrapper<List<MechanicPojos>> getMechanicInfos(String token, Mechanic ps) throws ParseException;
}
