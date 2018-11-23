package com.my.spring.serviceImpl.ThreadServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.my.spring.DAO.NoticeDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.model.Notice;
import com.my.spring.model.QualityQuestion;
import com.my.spring.model.UserId;

public class QualityNoticeThreadService implements Runnable {

	@Autowired
    UserDao userDao;
	@Autowired
	NoticeDao noticeDao;
	private QualityQuestion quality;
	
	public QualityNoticeThreadService(QualityQuestion quality){
		this.quality=quality;
	}
	public void run() {
		List<Notice> nl = new ArrayList<Notice>();
		List<UserId> userIdList = userDao.getAllUserIdList(quality.getUserList());
		if(!userIdList.isEmpty()){
			for(UserId s:userIdList){
				Notice nl2 = new Notice();
				nl2.setAboutId(quality.getId());
				nl2.setCreateDate(new Date());
				nl2.setUserId(s.getId());
				nl2.setNoticeType(1);
				nl2.setProjectId(quality.getProjectId());
				nl2.setReadState(0);
				nl.add(nl2);
			}
			noticeDao.addNoticeList(nl);
		}
	}


}
