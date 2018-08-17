package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.NoticeRelationDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.NoticeRelation;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class NoticeRelationDaoImpl extends BaseDao<NoticeRelation> implements NoticeRelationDao {

    @Override
    public boolean addNoticeRelation(NoticeRelation NoticeRelation) {
        return save(NoticeRelation);
    }

    @Override
    public boolean deleteNoticeRelation(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updateNoticeRelation(NoticeRelation NoticeRelation) {
        return update(NoticeRelation);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<NoticeRelation>> getNoticeRelationList() {
        DataWrapper<List<NoticeRelation>> retDataWrapper = new DataWrapper<List<NoticeRelation>>();
        List<NoticeRelation> ret = new ArrayList<NoticeRelation>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(NoticeRelation.class);
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
	public DataWrapper<NoticeRelation> getNoticeRelationByProjectId(Long projectId) {
		DataWrapper<NoticeRelation> dataWrapper=new DataWrapper<NoticeRelation>();
		List<NoticeRelation> ret = new ArrayList<NoticeRelation>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(NoticeRelation.class);
        criteria.add(Restrictions.eq("projectId",projectId));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (ret != null && ret.size() > 0) {
        	dataWrapper.setData(ret.get(0));
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
		}
		return dataWrapper;
	}

	@Override
	public boolean deleteNoticeRelationByProjectId(Long id) {
		String sql = "delete from notice_relation where project_id="+id;
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

	@Override
	public boolean addNoticeRelationList(List<NoticeRelation> NoticeRelation) {
		// TODO Auto-generated method stub
		return saveList(NoticeRelation);
	}
}
