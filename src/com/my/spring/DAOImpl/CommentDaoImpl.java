package com.my.spring.DAOImpl;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.CommentDao;
import com.my.spring.model.Comment;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;

@Repository
public class CommentDaoImpl extends BaseDao<Comment> implements CommentDao {

	
	@Override
	public boolean addComment(Comment Comment) {
		// TODO Auto-generated method stub
		return save(Comment);
	}

	@Override
	public Comment getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}


	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<Comment>> getCommentList(Integer pageSize, Integer pageIndex,Comment Comment) {
		// TODO Auto-generated method stub
		DataWrapper<List<Comment>> dataWrapper = new DataWrapper<List<Comment>>();
        List<Comment> ret = null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(Comment.class);
        criteria.addOrder(Order.desc("createDate"));
        if(Comment!=null){
        	if(Comment.getReplyType()!=null){
        		criteria.add(Restrictions.eq("replyType", Comment.getReplyType()));
        	}
        	if(Comment.getAboutId()!=null){
        		criteria.add(Restrictions.eq("aboutId",Comment.getAboutId()));
        	}
        	if(Comment.getCommentUser()!=null){
        		criteria.add(Restrictions.eq("commentUser", Comment.getCommentUser()));
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


	@Override
	public boolean deleteComment(String[] id) {
		// TODO Auto-generated method stub
		boolean bool=false;
		for(int i=0;i<id.length;i++){
			bool=delete(get(Long.valueOf(id[i])));
		}
		return bool;
	}


}
