package com.my.spring.DAO;

import java.util.List;
import com.my.spring.model.Notice;
import com.my.spring.model.NoticePojo;
import com.my.spring.model.UserProject;
import com.my.spring.utils.DataWrapper;

public interface NoticeDao {
	boolean addNotice(Notice notice);
	boolean addNoticeList(List<Notice> notice);
    boolean deleteNotice(Long id);
	Notice getByAdoutIdAndUserId(Long id, Long questionId, int noticeType);
	boolean updateNotice(Notice notice);
	DataWrapper<List<NoticePojo>> getListByProjectId(Integer pageSize, Integer pageIndex,List<UserProject> projectList);
	Integer getListNotRead(Long userId);
	DataWrapper<List<Notice>> getListByUserId(Integer pageSize, Integer pageIndex, Long id);
}
