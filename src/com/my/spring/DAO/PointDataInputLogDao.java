package com.my.spring.DAO;
import java.util.List;

import com.my.spring.model.PointDataInputLog;
import com.my.spring.model.User;

public interface PointDataInputLogDao {
	  boolean addPointDataInputLog(PointDataInputLog building);
      boolean deletePointDataInputLog(Long id);
      boolean updatePointDataInputLog(PointDataInputLog building);
      List<PointDataInputLog> getPointDataInputLogListByPointId(User user,Long pointId);
      boolean addPointDataInputLogList(List<PointDataInputLog> pointDataInputLog);
	  List<PointDataInputLog> getPointDataInputLogListByPointId(Long pointId);
	  PointDataInputLog getPointDataInputLogListByOptions(Long pointId, Long checkListId, Long inputUserId);
}
