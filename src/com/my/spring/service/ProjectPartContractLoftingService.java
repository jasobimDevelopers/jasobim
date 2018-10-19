package com.my.spring.service;

import java.util.List;

import com.my.spring.model.ProjectPartContractLofting;
import com.my.spring.model.ProjectPartContractLoftingPojo;
import com.my.spring.utils.DataWrapper;
public interface ProjectPartContractLoftingService {
	DataWrapper<Void> deleteProjectPartContractLoftingById(String token,Long id);
	DataWrapper<ProjectPartContractLofting> addProjectPartContractLofting(String token,ProjectPartContractLofting role);
	DataWrapper<Void> deleteProjectPartContractLoftingByIdList(String token,String[] id);
	DataWrapper<List<ProjectPartContractLoftingPojo>> getProjectPartContractLoftingList(Integer pageIndex, Integer pageSize, ProjectPartContractLofting ProjectPartContractLofting,
			String token);
	DataWrapper<ProjectPartContractLofting> getProjectPartContractLoftingById(String token, Long id);
	DataWrapper<ProjectPartContractLofting> updateProjectPartContractLofting(String token, ProjectPartContractLofting ProjectPartContractLofting);
	DataWrapper<List<ProjectPartContractLoftingPojo>> getDefaultList(Long projectId, String token);
	DataWrapper<ProjectPartContractLofting> addProjectPartContractLoftingList(String token,
			ProjectPartContractLofting role);
	DataWrapper<Void> deleteProjectPartContractLoftingByName(String token, String name, Long projectId);
}
