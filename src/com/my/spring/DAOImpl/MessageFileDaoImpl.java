package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.MessageFileDao;
import com.my.spring.model.MessageFile;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public class MessageFileDaoImpl extends BaseDao<MessageFile> implements MessageFileDao {

    @Override
    public boolean addMessageFile(MessageFile MessageFile) {
        return save(MessageFile);
    }

    @Override
    public boolean deleteMessageFile(Long id) {
        return delete(get(id));
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<MessageFile>> getMessageFileList() {
        DataWrapper<List<MessageFile>> retDataWrapper = new DataWrapper<List<MessageFile>>();
        List<MessageFile> ret = new ArrayList<MessageFile>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(MessageFile.class);
//        criteria.addOrder(Order.desc("publishDate"));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        retDataWrapper.setData(ret);
        return retDataWrapper;
    }

	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<MessageFile>> getMessageFileListByUserId(Long userId) {
		 DataWrapper<List<MessageFile>> retDataWrapper = new DataWrapper<List<MessageFile>>();
	        
	        //ret=find("select * from User where userId=?"+userId);
		List<MessageFile> ret = null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(MessageFile.class);
        criteria.add(Restrictions.eq("userId",userId));
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
	public boolean deleteMessageFileByMessageId(Long id) {
		String sql = "delete from message_file where message_id="+id;
		Session session=getSession();
		 try{
			 Query query = session.createSQLQuery(sql);
			 if(query.executeUpdate()==1){
				 return true;
			 }
			 
	        }catch(Exception e){
	            e.printStackTrace();
	        }
		 
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<MessageFile>> getMessageFileListByMessageId(Long messageId) {
		DataWrapper<List<MessageFile>> retDataWrapper = new DataWrapper<List<MessageFile>>();
		List<MessageFile> ret = null;
	    Session session = getSession();
	    Criteria criteria = session.createCriteria(MessageFile.class);
	    criteria.add(Restrictions.eq("messageId",messageId));
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
	public boolean deleteMessageFileByMessageIds(Long id) {
		String sql = "delete from message_file where message_id="+id;
		Session session=getSession();
		boolean bool=false;
		 try{
			 Query query = session.createSQLQuery(sql);
			 int temp=query.executeUpdate();
			 if(temp==1){
				 bool=true;
			 }
			 
	        }catch(Exception e){
	            e.printStackTrace();
	        }
		 
		return bool;
	}

	@Override
	public MessageFile getMessageFileByMessageId(Long id) {
		List<MessageFile> ret = null;
	    Session session = getSession();
	    Criteria criteria = session.createCriteria(MessageFile.class);
	    criteria.add(Restrictions.eq("messageId",id));
	    try {
	        ret = criteria.list();
	    }catch (Exception e){
	        e.printStackTrace();
	    }
	    if (ret != null && ret.size() > 0) {
			return ret.get(0);
		}
		return null;
	}
}
