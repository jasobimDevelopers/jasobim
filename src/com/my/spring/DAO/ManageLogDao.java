package com.my.spring.DAO;
import java.util.List;

import com.my.spring.model.ManageLog;

public interface ManageLogDao {
	ManageLog getById(Long id);
	boolean deleteManageLog(Long id);
	boolean addManageLog(ManageLog role);
	List<ManageLog> getManageLogList(ManageLog ManageLog);
}
