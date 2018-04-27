package com.my.spring.DAOImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.NoticeDao;
import com.my.spring.model.Notice;
@Repository
public class NoticeDaoImpl extends BaseDao<Notice> implements NoticeDao {

	@Override
	public boolean addNotice(Notice notice) {
		// TODO Auto-generated method stub
		return save(notice);
	}

	@Override
	public boolean deleteNotice(Long id) {
		// TODO Auto-generated method stub
		return delete(get(id));
	}

	@Override
	public boolean addNoticeList(List<Notice> notice) {
		// TODO Auto-generated method stub
		return saveList(notice);
	}

	@Override
	public Notice getByAdoutIdAndUserId(Long userId, Long questionId, int noticeType) {
		List<Notice> notice= null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(Notice.class);
        criteria.add(Restrictions.eq("userId", userId));
        criteria.add(Restrictions.eq("aboutId", questionId));
        criteria.add(Restrictions.eq("noticeType",noticeType));
        criteria.addOrder(Order.desc("createDate"));
        try {
        	notice = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(notice.size()==0){
        	return null;
        }else{
        	return notice.get(0);
        }
        
	}

	@Override
	public boolean updateNotice(Notice notice) {
		// TODO Auto-generated method stub
		return update(notice);
	}

}
