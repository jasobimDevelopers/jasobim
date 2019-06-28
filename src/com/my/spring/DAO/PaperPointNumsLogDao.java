package com.my.spring.DAO;

import com.my.spring.model.PaperPointNumsLog;
import com.my.spring.utils.DataWrapper;
import java.util.List;

public interface PaperPointNumsLogDao {
    boolean addPaperPointNumsLog(PaperPointNumsLog PaperPointNumsLog);
    boolean deletePaperPointNumsLog(Long id);
    boolean updatePaperPointNumsLog(PaperPointNumsLog PaperPointNumsLog);
	DataWrapper<List<PaperPointNumsLog>> getPaperPointNumsLogListByProjectId(Long projectId);
	PaperPointNumsLog getPaperPointNumsById(Long id);
}
