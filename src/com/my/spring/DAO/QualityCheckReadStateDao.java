package com.my.spring.DAO;

import java.util.List;
import com.my.spring.model.QualityCheckReadState;
public interface QualityCheckReadStateDao {
	boolean addQualityCheckReadState(QualityCheckReadState role);
	List<QualityCheckReadState> getQualityCheckReadStateListsByIds(Integer QualityCheckReadStateType,Long aboutId,Long userId);
	boolean deleteQualityCheckReadState(QualityCheckReadState role);
	boolean addQualityCheckReadStateList(List<QualityCheckReadState> sendUserList);
	boolean updateQualityCheckReadState(QualityCheckReadState QualityCheckReadStates2);
	List<QualityCheckReadState> getQualityCheckReadStateLists(QualityCheckReadState QualityCheckReadState);
	boolean updateAllQualityCheckReadStateByUserId(Long id);
}
