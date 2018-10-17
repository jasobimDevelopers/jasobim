package com.my.spring.service;

import java.util.List;

import com.my.spring.model.ProjectPartContractLofting;
import com.my.spring.utils.DataWrapper;
public interface ProjectPartContractLoftingService {
	DataWrapper<Void> deleteProjectPartContractLoftingById(String token,Long id);
	DataWrapper<ProjectPartContractLofting> addProjectPartContractLofting(String token,ProjectPartContractLofting role);
	DataWrapper<Void> deleteProjectPartContractLoftingByIdList(String token,String[] id);
	DataWrapper<List<ProjectPartContractLofting>> getProjectPartContractLoftingList(Integer pageIndex, Integer pageSize, ProjectPartContractLofting ProjectPartContractLofting,
			String token);
	DataWrapper<ProjectPartContractLofting> getProjectPartContractLoftingById(String token, Long id);
	DataWrapper<Void> updateProjectPartContractLofting(String token, ProjectPartContractLofting ProjectPartContractLofting);
}
