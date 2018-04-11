package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.SignUserInfoDao;
import com.my.spring.model.SignUserInfo;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SignUserInfoDaoImpl extends BaseDao<SignUserInfo> implements SignUserInfoDao {


    @Override
    public boolean deleteSignUserInfo(Long id) {
        return delete(get(id));
    }


    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<SignUserInfo>> getSignUserInfoList(Integer pageSize,Integer pageIndex,SignUserInfo item) {
        DataWrapper<List<SignUserInfo>> retDataWrapper = new DataWrapper<List<SignUserInfo>>();
        List<SignUserInfo> ret = new ArrayList<SignUserInfo>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(SignUserInfo.class);
        
        ///////////////////////////////
    
    	criteria.add(Restrictions.eq("mobile", item.getMobile()));
        criteria.add(Restrictions.eq("code", item.getCode()));
        	
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

    //////////根据项目id删除构件和相应的工程量
	@SuppressWarnings("unused")
	@Override
	public boolean deleteSignUserInfoByMobile(String mobile) {
		boolean bool =true;
		String sql="delete * from sign_user_info where mobile="+mobile;
		Session session=getSession();
		 try{
	            Query query=session.createSQLQuery(sql);
	            session.getTransaction().commit();
	            session.flush();
	        }catch(Exception e){
	            e.printStackTrace();
	            session.getTransaction().rollback();
	            bool=false;
	        }
		 
		return bool;
	}

	@Override
	public SignUserInfo getSignUserInfoById(Long id) {
		@SuppressWarnings("unused")
		DataWrapper<SignUserInfo> dataWrapper = new DataWrapper<SignUserInfo>();
		return get(id);
	}


	@Override
	public boolean addSignUserInfo(SignUserInfo Item) {
		// TODO Auto-generated method stub
		return save(Item);
	}


	@Override
	public boolean updateSignUserInfo(SignUserInfo Item) {
		// TODO Auto-generated method stub
		return update(Item);
	}
	
}
