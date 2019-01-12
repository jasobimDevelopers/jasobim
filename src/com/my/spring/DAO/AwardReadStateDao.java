package com.my.spring.DAO;

import java.util.List;
import com.my.spring.model.AwardReadState;
public interface AwardReadStateDao {
	boolean addAwardReadState(AwardReadState role);
	List<AwardReadState> getAwardReadStateListsByIds(Integer AwardReadStateType,Long aboutId,Long userId);
	boolean deleteAwardReadState(AwardReadState role);
	boolean addAwardReadStateList(List<AwardReadState> sendUserList);
	boolean updateAwardReadState(AwardReadState AwardReadStates2);
	List<AwardReadState> getAwardReadStateLists(AwardReadState AwardReadState);
	boolean updateAllAwardReadStateByUserId(Long id);
}
