package com.my.spring.DAO;

import com.my.spring.model.NoticeRelation;
import com.my.spring.utils.DataWrapper;
import java.util.List;

public interface NoticeRelationDao {
    boolean addNoticeRelation(NoticeRelation NoticeRelation);
    boolean addNoticeRelationList(List<NoticeRelation> NoticeRelation);
    boolean deleteNoticeRelation(Long id);
    boolean updateNoticeRelation(NoticeRelation NoticeRelation);
    DataWrapper<List<NoticeRelation>> getNoticeRelationList();
	DataWrapper<NoticeRelation> getNoticeRelationByProjectId(Long projectId);
	boolean deleteNoticeRelationByProjectId(Long id);
}
