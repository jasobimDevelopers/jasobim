package com.my.spring.DAO;


import java.util.List;

import com.my.spring.model.Notice;

public interface NoticeDao {
	boolean addNotice(Notice notice);
	boolean addNoticeList(List<Notice> notice);
    boolean deleteNotice(Long id);
	Notice getByAdoutIdAndUserId(Long id, Long questionId, int noticeType);
	boolean updateNotice(Notice notice);
}
