package com.my.spring.DAO;

import com.my.spring.model.MeasuredProblem;
import com.my.spring.model.MeasuredProblemEditPojo;
import com.my.spring.model.MeasuredUserInfo;
import com.my.spring.model.User;
import com.my.spring.utils.DataWrapper;
import java.util.List;

public interface MeasuredProblemDao {
    boolean addMeasuredProblem(MeasuredProblem building);
    boolean deleteMeasuredProblem(Long id);
    boolean updateMeasuredProblem(MeasuredProblem building);
	DataWrapper<List<MeasuredProblem>> getMeasuredProblemByProjectId(Long projectId);
	MeasuredProblem getById(Long measuredId);
	DataWrapper<List<MeasuredProblem>> getMeasuredProblemByProjectId(Long id,Long projectId, User userInMemory,Integer status,List<Long> bfmIds,List<Long> checkTypeIds);
	boolean addMeasuredProblemList(List<MeasuredProblem> mpList);
	List<MeasuredProblem> getMeasuredProblemByIds(List<MeasuredProblemEditPojo> gets);
	boolean updateMeasuredProblemList(List<MeasuredProblem> getList);
	List<MeasuredUserInfo> getAboutUserIcons(Long id);
}
