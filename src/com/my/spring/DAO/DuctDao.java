package com.my.spring.DAO;

import java.util.List;

import com.my.spring.model.Duct;
import com.my.spring.utils.DataWrapper;

public interface DuctDao {
	boolean addDuct(Duct duct);
    boolean deleteDuct(Long id);
    boolean updateDuct(Duct Duct);
    DataWrapper<List<Duct>> getDuctList();
	DataWrapper<Duct> getDuctByProjectId(Long projectId);
	boolean addDuctList(List<Duct> ductList);
}
