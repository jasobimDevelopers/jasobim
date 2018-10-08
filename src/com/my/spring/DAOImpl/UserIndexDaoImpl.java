package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.UserIndexDao;
import com.my.spring.model.MaxIndex;
import com.my.spring.model.User;
import com.my.spring.model.UserIndex;
import com.my.spring.model.UserIndexUserId;
import com.my.spring.model.UserLogPojos;
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
public class UserIndexDaoImpl extends BaseDao<UserIndex> implements UserIndexDao {

    @Override
    public boolean addUserIndex(UserIndex role) {
        return save(role);
    }
    @Override
    public boolean deleteUserIndex(Long id) {
        return delete(get(id));
    }


    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<UserIndex>> getUserIndexList(Integer pageIndex, Integer pageSize, UserIndex UserIndex) {
        DataWrapper<List<UserIndex>> dataWrapper = new DataWrapper<List<UserIndex>>();
        List<UserIndex> ret = new ArrayList<UserIndex>();
        List<UserIndex> ret2 = new ArrayList<UserIndex>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(UserIndex.class);
       
//        criteria.addOrder(Order.desc("publishDate"));
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
        if(ret.isEmpty()){
        	Criteria criteria2 = session.createCriteria(UserIndex.class);
            criteria2.add(Restrictions.isNull("userId"));
            // 取总页数
            criteria2.setProjection(Projections.rowCount());
            totalItemNum = ((Long)criteria2.uniqueResult()).intValue();
            totalPageNum = DaoUtil.getTotalPageNumber(totalItemNum, pageSize);

            // 真正取值
            criteria2.setProjection(null);
            if (pageSize > 0 && pageIndex > 0) {
                criteria2.setMaxResults(pageSize);// 最大显示记录数
                criteria2.setFirstResult((pageIndex - 1) * pageSize);// 从第几条开始
            }
            try {
                ret2 = criteria2.list();
            }catch (Exception e){
                e.printStackTrace();
            }
            dataWrapper.setData(ret2);
            dataWrapper.setTotalNumber(totalItemNum);
            dataWrapper.setCurrentPage(pageIndex);
            dataWrapper.setTotalPage(totalPageNum);
            dataWrapper.setNumberPerPage(pageSize);
        }
        
        return dataWrapper;
    }

	@Override
	public UserIndex getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}
	
	@Override
	public boolean deleteUserIndexList(String[] ids) {
		// TODO Auto-generated method stub
		return deleteList(ids);
	}
	@Override
	public boolean updateUserIndex(UserIndex dp) {
		// TODO Auto-generated method stub
		return update(dp);
	}
	@Override
	public boolean addUserIndexList(List<UserIndex> newList) {
		// TODO Auto-generated method stub
		return saveList(newList);
	}
	@Override
	public boolean deleteUserIndexByAboutType(Long id,Integer type) {
		String sql = "delete from user_index where user_id="+id+" and about_type="+type;
		Session session=getSession();
		 try{
			 Query query = session.createSQLQuery(sql);
			 if(query.executeUpdate()>=0){
				 return true;
			 }
			 
	        }catch(Exception e){
	            e.printStackTrace();
	        }
		 
		return false;
	}
	@Override
	public List<UserIndex> getUserIndexListOfAbout(Long userId, Integer aboutType) {
		List<UserIndex> ret = new ArrayList<UserIndex>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(UserIndex.class);
        criteria.addOrder(Order.asc("indexNum"));
        criteria.add(Restrictions.eq("userId",userId));
        criteria.add(Restrictions.eq("aboutType",aboutType));
        
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
		return ret;
	}
	@Override
	public boolean deleteUserIndexByAboutId(Long id, Integer type) {
		String sql = "delete from user_index where about_type="+type+" and about_id="+id;
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
	public List<UserIndexUserId> getUserIndexListByGroup() {
		List<UserIndexUserId> gets = new ArrayList<UserIndexUserId>();
		String sql="select user_id as id from user_index GROUP BY user_id";
		Session session=getSession();
		 try{
			 Query query = session.createSQLQuery(sql)
					 .addScalar("id", StandardBasicTypes.LONG)
					 .setResultTransformer(Transformers.aliasToBean(UserIndexUserId.class));
			 	gets=query.list();
	        }catch(Exception e){
	            e.printStackTrace();
	        }
		 
		return gets;
	}
	@Override
	public MaxIndex getIndexMaxByUserId(Long id) {
		List<MaxIndex> gets = new ArrayList<MaxIndex>();
		String sql="select count(1) as indexs from user_index where user_id="+id;
		Session session=getSession();
		 try{
			 Query query = session.createSQLQuery(sql)
					 .addScalar("indexs", StandardBasicTypes.LONG)
					 .setResultTransformer(Transformers.aliasToBean(MaxIndex.class));
			 	gets=query.list();
	        }catch(Exception e){
	            e.printStackTrace();
	        }
		 if(!gets.isEmpty()){
			 return gets.get(0);
		 }
		return null;
	}
}
