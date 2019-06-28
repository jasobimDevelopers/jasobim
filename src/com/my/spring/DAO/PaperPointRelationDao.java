package com.my.spring.DAO;

import com.my.spring.model.PaperPointRelation;
import com.my.spring.utils.DataWrapper;
import java.util.List;

public interface PaperPointRelationDao {
    boolean addPaperPointRelation(PaperPointRelation building);
    boolean deletePaperPointRelation(Long id);
    DataWrapper<List<PaperPointRelation>> getPaperPointRelationList(PaperPointRelation ppr);
	boolean deletePaperPointRelationByProjectId(Long id);
	boolean deletePaperPointRelationByIds(Long checkTypeId, Long pointId);
	boolean addPaperPointRelation(List<PaperPointRelation> pprs);
}
