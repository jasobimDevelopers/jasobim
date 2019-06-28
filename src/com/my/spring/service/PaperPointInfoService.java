package com.my.spring.service;

import com.my.spring.model.PaperPointInfo;
import com.my.spring.model.PaperPointInfoPojo;
import com.my.spring.model.PaperPointNumsLog;
import com.my.spring.model.PointConditionsCountNums;
import com.my.spring.utils.DataWrapper;
import java.util.List;

public interface PaperPointInfoService {
    DataWrapper<Void> deletePaperPointInfo(Long id,String token);
    DataWrapper<Void> updatePaperPointInfo(PaperPointInfo building,String token);
    DataWrapper<List<PaperPointInfo>> getPaperPointInfoList();
	DataWrapper<PaperPointInfo> getPaperPointInfoByProjectId(Long projectId,String token);
	DataWrapper<List<PaperPointNumsLog>> getPaperPointInfoNums(String token, PaperPointNumsLog countLog);
	DataWrapper<List<PointConditionsCountNums>> getPaperPointInfoNumsByConditions(String token, PaperPointNumsLog countLog);
	DataWrapper<List<PointConditionsCountNums>> getPaperPointInfoNumsBySite(String token, PaperPointNumsLog countLog,
			Long bfmId);
	DataWrapper<PaperPointInfoPojo> getPaperPointInfoList(String token, PaperPointNumsLog countLog, Long siteId);
	DataWrapper<Void> addPaperPointInfo(String token,String jsonString);
}
