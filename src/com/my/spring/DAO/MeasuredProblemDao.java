package com.my.spring.DAO;

import com.my.spring.model.MeasuredProblem;
import com.my.spring.utils.DataWrapper;
import java.util.List;

public interface MeasuredProblemDao {
    boolean addMeasuredProblem(MeasuredProblem building);
    boolean deleteMeasuredProblem(Long id);
    boolean updateMeasuredProblem(MeasuredProblem building);
	DataWrapper<List<MeasuredProblem>> getMeasuredProblemByProjectId(Long projectId);
	MeasuredProblem getById(Long measuredId);
}
