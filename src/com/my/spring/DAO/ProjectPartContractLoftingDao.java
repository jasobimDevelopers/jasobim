package com.my.spring.DAO;
import com.my.spring.model.ProjectPartContractLofting;
import com.my.spring.model.ProjectPartContractLoftingPojo;
import com.my.spring.model.UserIndexs;
import java.util.List;
public interface ProjectPartContractLoftingDao {
	ProjectPartContractLofting getById(Long id);
	boolean deleteProjectPartContractLofting(Long id);
	boolean addProjectPartContractLofting(ProjectPartContractLofting role);
	boolean deleteProjectPartContractLoftingList(String[] ids);
	boolean updateProjectPartContractLofting(ProjectPartContractLofting dp);
	List<UserIndexs> getProjectPartContractLoftingListByUserId(Long userId);
	boolean addProjectPartContractLoftingList(List<ProjectPartContractLofting> addList);
	List<ProjectPartContractLofting> getProjectPartContractLoftingListByContractLoftingId(Long id);
	List<ProjectPartContractLoftingPojo> getProjectPartContractLoftingList(Long projectId);
	List<ProjectPartContractLoftingPojo> getProjectPartContractLoftingList(
			ProjectPartContractLofting ProjectPartContractLofting);
	boolean deleteProjectPartContractLoftingByName(String name, Long projectId);
	boolean deleteProjectPartContractLoftingByProjectId(Long projectId);
}
