package com.my.spring.DAO;

import com.my.spring.model.PaperPointInfo;
import com.my.spring.utils.DataWrapper;
import java.util.List;

public interface PaperPointInfoDao {
    boolean addPaperPointInfo(PaperPointInfo building);
    boolean deletePaperPointInfo(Long id);
    boolean updatePaperPointInfo(PaperPointInfo building);
    DataWrapper<List<PaperPointInfo>> getPaperPointInfoList();
	DataWrapper<PaperPointInfo> getPaperPointInfoByProjectId(Long projectId);
	boolean deletePaperPointInfoByProjectId(Long id);
}
