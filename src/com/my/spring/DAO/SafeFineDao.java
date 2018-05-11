package com.my.spring.DAO;

import com.my.spring.model.SafeFine;
import com.my.spring.utils.DataWrapper;

import java.util.List;

public interface SafeFineDao {
    boolean addSafeFine(SafeFine building);
    boolean deleteSafeFine(Long id);
	DataWrapper<SafeFine> getSafeFineByProjectId(Long projectId);
	boolean deleteSafeFineByProjectId(Long id);
	boolean updateSafeFine(SafeFine building);
	DataWrapper<List<SafeFine>> getSafeFineList(Integer pageIndex, Integer pageSize, SafeFine fine);
}
