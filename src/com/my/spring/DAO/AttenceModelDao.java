package com.my.spring.DAO;

import java.util.List;

import com.my.spring.model.AttenceModel;
import com.my.spring.utils.DataWrapper;

public interface AttenceModelDao {
	 boolean addAttenceModel(AttenceModel am);
     boolean deleteAttenceModel(Long id);
     boolean updateAttenceModel(AttenceModel am);
	 DataWrapper<Void> deleteAttenceModelByProjectId(Long id);
	 AttenceModel getAttenceModelById(Long id);
	 DataWrapper<List<AttenceModel>> getAttenceModelList(Integer pageIndex, Integer pageSize, AttenceModel am);
	AttenceModel getAttenceModelByProjectId(Long projectId);
}
