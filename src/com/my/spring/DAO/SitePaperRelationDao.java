package com.my.spring.DAO;

import com.my.spring.model.SitePaperRelation;
import com.my.spring.utils.DataWrapper;
import java.util.List;

public interface SitePaperRelationDao {
    boolean addSitePaperRelation(SitePaperRelation SitePaperRelation);
    boolean deleteSitePaperRelation(Long id);
    DataWrapper<List<SitePaperRelation>> getSitePaperRelationList(SitePaperRelation spr);
	boolean deleteSitePaperRelationByProjectId(Long id);
	boolean addSitePaperRelation(List<SitePaperRelation> sprList);
	boolean deleteSitePaperRelationBySiteId(Long id);
}
