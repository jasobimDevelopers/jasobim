package com.my.spring.DAO;

import java.util.List;

import com.my.spring.model.Duct;
import com.my.spring.utils.DataWrapper;

public interface DuctDao {
	boolean addDuct(Duct duct);
    boolean deleteDuct(Long id);
    boolean updateDuct(Duct Duct);
    DataWrapper<List<Duct>> getDuctList(Integer pageSize,Integer pageIndex,Duct duct,String content);
	DataWrapper<List<Duct>> getDuctByProjectId(Long projectId,Duct duct);
	boolean addDuctList(List<Duct> ductList);
	boolean exportQuantity(String tempFile, Long projectId);
	Duct getDuctById(Long id);
}
