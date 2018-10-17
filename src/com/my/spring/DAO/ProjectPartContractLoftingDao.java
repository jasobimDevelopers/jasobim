package com.my.spring.DAO;
import com.my.spring.model.ProjectPartContractLofting;
import com.my.spring.model.UserIndexs;
import com.my.spring.utils.DataWrapper;
import java.util.List;
public interface ProjectPartContractLoftingDao {
	ProjectPartContractLofting getById(Long id);
	boolean deleteProjectPartContractLofting(Long id);
	boolean addProjectPartContractLofting(ProjectPartContractLofting role);
	boolean deleteProjectPartContractLoftingList(String[] ids);
	List<ProjectPartContractLofting> getProjectPartContractLoftingList(Integer pageIndex, Integer pageSize, ProjectPartContractLofting ProjectPartContractLofting);
	boolean updateProjectPartContractLofting(ProjectPartContractLofting dp);
	List<UserIndexs> getProjectPartContractLoftingListByUserId(Long userId);
	boolean addProjectPartContractLoftingList(List<ProjectPartContractLofting> addList);
	List<ProjectPartContractLofting> getProjectPartContractLoftingListByContractLoftingId(Long id);
}
