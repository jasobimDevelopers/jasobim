package com.my.spring.DAO;

import com.my.spring.model.BuildingPointNums;
import com.my.spring.model.PaperPointInfo;
import com.my.spring.model.PaperPointNumsLog;
import com.my.spring.utils.DataWrapper;
import java.util.List;

public interface PaperPointInfoDao {
    boolean addPaperPointInfo(PaperPointInfo building);
    boolean deletePaperPointInfo(Long id);
    boolean updatePaperPointInfo(PaperPointInfo building);
    DataWrapper<List<PaperPointInfo>> getPaperPointInfoList();
	List<PaperPointInfo> getPaperPointInfoByProjectId(Long projectId,Long paperId);
	boolean deletePaperPointInfoByProjectId(Long id);
	DataWrapper<List<PaperPointNumsLog>> getCountPointNumsList(PaperPointNumsLog countLog,Long userId);
	PaperPointNumsLog findCountPointNumsList(PaperPointNumsLog countLog);
	List<BuildingPointNums> getCountAllPointNumsList(Long projectId);
	PaperPointInfo getById(Long pointId);
	List<BuildingPointNums> getCountPointNumsList(String buildingNames, String checkTypes, Long projectId);
	List<BuildingPointNums> getCountPointNumsListGroup(String buildingNames, String checkTypes, Long projectId);
	List<BuildingPointNums> getCountPointNumsListGroupBySite(Long bfmId, String checkTypes, Long projectId);
	List<PaperPointInfo> getPaperPointInfoBySiteId(Long siteId, String checkTypes, Long projectId);
	boolean addPaperPointInfoList(List<PaperPointInfo> ppi);
	DataWrapper<List<PaperPointNumsLog>> getCountPointNumsListByConditions(Long projectId, Long id);
}
