package com.my.spring.DAO;

import java.util.Date;
import java.util.List;

import com.my.spring.model.Duct;
import com.my.spring.model.DuctPojos;
import com.my.spring.utils.DataWrapper;

public interface DuctDao {
	boolean addDuct(Duct duct);
    boolean deleteDuct(Long id);
    boolean updateDuct(Duct Duct);
    DataWrapper<List<Duct>> getDuctList(Integer pageSize,Integer pageIndex,Duct duct,String content,Date dateStart,Date dateFinished);
	DataWrapper<List<Duct>> getDuctByProjectId(Long projectId,Duct duct);
	boolean addDuctList(List<Duct> ductList);
	boolean exportDuct(String tempFile, Long projectId,String dateStart,String dateFinished);
	Duct getDuctById(Long id);
	DataWrapper<List<DuctPojos>> getDuctLists();
	DataWrapper<Duct> getDuctBySelfId(Long id);
}
