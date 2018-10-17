package com.my.spring.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.my.spring.model.ContractLofting;
import com.my.spring.utils.DataWrapper;
public interface ContractLoftingService {
	DataWrapper<Void> deleteContractLoftingById(String token,Long id);
	DataWrapper<ContractLofting> addContractLofting(String token,ContractLofting role);
	DataWrapper<Void> deleteContractLoftingByIdList(String token,String[] id);
	DataWrapper<List<ContractLofting>> getContractLoftingList(Integer pageIndex, Integer pageSize, ContractLofting ContractLofting,
			String token);
	DataWrapper<ContractLofting> getContractLoftingById(String token, Long id);
	DataWrapper<Void> updateContractLofting(String token, ContractLofting ContractLofting);
	DataWrapper<Void> importContractLoftingByProjectId(String token, Long projectId, MultipartFile files,
			HttpServletRequest request);
}