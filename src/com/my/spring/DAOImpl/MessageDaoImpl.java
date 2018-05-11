package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.MessageDao;
import com.my.spring.model.Message;
import com.my.spring.model.MessageCopy;
import com.my.spring.model.QuestionCopy;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MessageDaoImpl extends BaseDao<Message> implements MessageDao {

    @Override
    public boolean addMessage(Message Message) {
        return save(Message);
    }

    @Override
    public boolean deleteMessage(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updateMessage(Message Message) {
        return update(Message);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<Message>> getMessageList( Integer pageIndex, Integer pageSize, Message message) {
        DataWrapper<List<Message>> retDataWrapper = new DataWrapper<List<Message>>();
        List<Message> ret = new ArrayList<Message>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Message.class);
//      criteria.add(Restrictions.eq("projectId", projectId));
        if(message.getQuestionType()!=null){
        	criteria.add(Restrictions.eq("questionType", message.getQuestionType()));
        }
        if(message.getUserId()!=null){
        	criteria.add(Restrictions.eq("userId", message.getUserId()));
        }
        if(message.getAboutId()!=null){
        	criteria.add(Restrictions.eq("questionId", message.getAboutId()));
        }
        if(message.getProjectId()!=null){
        	criteria.add(Restrictions.eq("projectId", message.getProjectId()));
        }
        criteria.addOrder(Order.asc("messageDate"));
        /////////////////////////////////////
   
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
        retDataWrapper.setData(ret);
        retDataWrapper.setTotalNumber(totalItemNum);
        retDataWrapper.setCurrentPage(pageIndex);
        retDataWrapper.setTotalPage(totalPageNum);
        retDataWrapper.setNumberPerPage(pageSize);
        return retDataWrapper;
    }

	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<Message>> getMessageListByUserId(Long userId) {
		 DataWrapper<List<Message>> retDataWrapper = new DataWrapper<List<Message>>();
	        
	        //ret=find("select * from User where userId=?"+userId);
		List<Message> ret = null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(Message.class);
        criteria.add(Restrictions.eq("userId",userId));
        criteria.addOrder(Order.desc("messageDate"));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (ret != null && ret.size() > 0) {
			retDataWrapper.setData(ret);
		}
		return retDataWrapper;
	}

	@Override
	public Message getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<Message>> getMessageListByQuestionId(Long id) {
	 DataWrapper<List<Message>> retDataWrapper = new DataWrapper<List<Message>>();
	 List<Message> ret = null;
     Session session = getSession();
     Criteria criteria = session.createCriteria(Message.class);
     criteria.add(Restrictions.eq("aboutId",id));
     criteria.add(Restrictions.eq("questionType",1));
     criteria.addOrder(Order.asc("messageDate"));
     try {
         ret = criteria.list();
     }catch (Exception e){
         e.printStackTrace();
     }
     if (ret != null && ret.size() > 0) {
			retDataWrapper.setData(ret);
		}
		return retDataWrapper;
	}
	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<Message>> getMessageListByQualityId(Long id) {
	 DataWrapper<List<Message>> retDataWrapper = new DataWrapper<List<Message>>();
	 List<Message> ret = null;
     Session session = getSession();
     Criteria criteria = session.createCriteria(Message.class);
     criteria.add(Restrictions.eq("aboutId",id));
     criteria.add(Restrictions.eq("questionType",0));
     criteria.addOrder(Order.asc("messageDate"));
     try {
         ret = criteria.list();
     }catch (Exception e){
         e.printStackTrace();
     }
     if (ret != null && ret.size() > 0) {
			retDataWrapper.setData(ret);
		}
		return retDataWrapper;
	}

	@Override
	public boolean deleteMessageByQuestionId(Long questionId) {
		String sql = "delete from message where about_id="+questionId+" and question_type=1";
		Session session=getSession();
		boolean test=false;
		 try{
			 Query query = session.createSQLQuery(sql);
			 int temp=query.executeUpdate();
			 if(temp!=0){
				 test= true;
			 }
			 
	        }catch(Exception e){
	            e.printStackTrace();
	        }
		 
		return test;
	}
	@Override
	public boolean deleteMessageByQualityId(Long qualityId) {
		String sql = "delete from message where quality_id="+qualityId+" and question_type=0";
		Session session=getSession();
		boolean test=false;
		 try{
			 Query query = session.createSQLQuery(sql);
			 int temp=query.executeUpdate();
			 if(temp!=0){
				 test= true;
			 }
			 
	        }catch(Exception e){
	            e.printStackTrace();
	        }
		 
		return test;
	}

	@Override
	public Message getMessageListById(Long id) {
		return get(id);
	}

	@Override
	public List<MessageCopy> getMessageListNotRead(Long id, Integer pageSize, Integer pageIndex) {
		if(pageIndex==null){
			pageIndex=1;
		}
		if(pageSize==null){
			pageSize=10;
		}
		//select a.* from question a where a.project_id in (select c.project_id from user_project c where c.user_id=33)
		List<MessageCopy> retDataWrapper = new ArrayList<MessageCopy>();
		String sql = "select a.* from message a,notice b "
		+"where a.id=b.about_id and b.notice_type=4 and b.read_state=0 "
		+"and b.user_id="+id;
		if(pageIndex!=-1){
			sql = sql +" limit "+(pageSize*pageIndex-pageSize)+","+pageSize;
		}
		Session session=getSession();
	    try{
		    Query query = session.createSQLQuery(sql)
				 .addScalar("id",StandardBasicTypes.LONG)
				 .addScalar("content", StandardBasicTypes.STRING)
				 .addScalar("message_date",StandardBasicTypes.DATE)
				 .addScalar("user_id",StandardBasicTypes.LONG)
				 .addScalar("question_id", StandardBasicTypes.LONG)
				 .addScalar("project_id", StandardBasicTypes.LONG)
				 .addScalar("quality_id", StandardBasicTypes.LONG)
				 .setResultTransformer(Transformers.aliasToBean(MessageCopy.class)); 
		    retDataWrapper=query.list();
            
        }catch(Exception e){
            e.printStackTrace();
            //dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
        }
		return retDataWrapper;
	}
}
