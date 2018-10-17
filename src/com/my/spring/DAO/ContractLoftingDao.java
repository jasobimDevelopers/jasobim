package com.my.spring.DAO;
import com.my.spring.model.ContractLofting;
import com.my.spring.model.UserIndexs;
import com.my.spring.utils.DataWrapper;
import java.util.List;
public interface ContractLoftingDao {
	ContractLofting getById(Long id);
	boolean deleteContractLofting(Long id);
	boolean addContractLofting(ContractLofting role);
	boolean deleteContractLoftingList(String[] ids);
	DataWrapper<List<ContractLofting>> getContractLoftingList(Integer pageIndex, Integer pageSize, ContractLofting ContractLofting);
	boolean updateContractLofting(ContractLofting dp);
	List<UserIndexs> getContractLoftingListByUserId(Long userId);
	boolean addContractLoftingList(List<ContractLofting> parrent1);
}
