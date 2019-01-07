package com.my.spring.DAOImpl;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.ReplyDao;
import com.my.spring.model.Reply;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;

@Repository
public class ReplyDaoImpl extends BaseDao<Reply> implements ReplyDao {

	
	@Override
	public boolean addReply(Reply Reply) {
		// TODO Auto-generated method stub
		return save(Reply);
	}

	@Override
	public Reply getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}


	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<Reply>> getReplyList(Integer pageSize, Integer pageIndex,Reply Reply) {
		// TODO Auto-generated method stub
		DataWrapper<List<Reply>> dataWrapper = new DataWrapper<List<Reply>>();
        List<Reply> ret = null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(Reply.class);
        criteria.addOrder(Order.asc("createDate"));
        if(Reply!=null){
        	if(Reply.getReplyType()!=null){
        		criteria.add(Restrictions.eq("replyType", Reply.getReplyType()));
        	}
        	if(Reply.getAboutId()!=null){
        		criteria.add(Restrictions.eq("aboutId",Reply.getAboutId()));
        	}
        	if(Reply.getReplyUser()!=null){
        		criteria.add(Restrictions.eq("ReplyUser", Reply.getReplyUser()));
        	}
        }
        
        if (pageSize == null) {
			pageSize = 10;
		}
        if (pageIndex == null) {
			pageIndex = 1;
		}
        
        // 取总页数
        criteria.setProjection(Projections.rowCount());
        int totalItemNum = ((Long)criteria.uniqueResult()).intValue();
        int totalPageNum = DaoUtil.getTotalPageNumber(totalItemNum, pageSize);

        // 真正取值
        criteria.setProjection(null);
        if (pageSize > 0 && pageIndex > 0) {
            criteria.setMaxResults(pageSize);// 最大显示记录数
            criteria.setFirstResult((pageIndex - 1) * pageSize);// 从第几条开始
        }
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        dataWrapper.setData(ret);
        dataWrapper.setTotalNumber(totalItemNum);
        dataWrapper.setCurrentPage(pageIndex);
        dataWrapper.setTotalPage(totalPageNum);
        dataWrapper.setNumberPerPage(pageSize);

        return dataWrapper;
	}

}
