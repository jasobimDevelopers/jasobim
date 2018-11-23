package com.my.spring.DAO;

import com.my.spring.model.QualityFine;
import com.my.spring.utils.DataWrapper;
import java.util.List;

public interface QualityFineDao {
    boolean addQualityFine(QualityFine building);
    boolean deleteQualityFine(Long id);
    boolean updateQualityFine(QualityFine building);
	DataWrapper<QualityFine> getQualityFineByProjectId(Long projectId);
	boolean deleteQualityFineByProjectId(Long id);
	DataWrapper<List<QualityFine>> getQualityFineList(Integer pageIndex, Integer pageSize, QualityFine qf);
}
