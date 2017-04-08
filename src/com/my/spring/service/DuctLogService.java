package com.my.spring.service;

import com.my.spring.model.DuctLog;
import com.my.spring.model.DuctLogPojo;
import com.my.spring.utils.DataWrapper;

import java.util.List;



/**
 * Created by Administrator on 2016/6/22.
 */
public interface DuctLogService {
    DataWrapper<Void> addDuctLog(DuctLog DuctLog, String token);
    DataWrapper<Void> deleteDuctLog(Long id,String token);
	DataWrapper<List<DuctLogPojo>> getDuctLogByDuctId(Long DuctId,String token, DuctLog DuctLog);
}
