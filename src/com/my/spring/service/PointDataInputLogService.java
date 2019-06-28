package com.my.spring.service;

import java.util.List;

import com.my.spring.model.PointDataInputLog;
import com.my.spring.utils.DataWrapper;

public interface PointDataInputLogService {
    DataWrapper<Void> addPointDataInputLog(String jsonString, String token);
    DataWrapper<Void> deletePointDataInputLog(Long id,String token);
    DataWrapper<Void> updatePointDataInputLog(PointDataInputLog building,String token);
	DataWrapper<List<PointDataInputLog>> getPointDataInputLogByPointId(Long pointId,String token);
}
