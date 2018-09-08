package com.my.spring.DAOImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.my.spring.DAO.ConstructPartDao;
import com.my.spring.DAO.BaseDao;
import com.my.spring.model.ConstructPart;
/**
* @author 徐雨祥
* @version 创建时间：2018年8月31日 下午1:51:31
* 类说明
*/
@Repository
public class ConstructPartDaoImpl extends BaseDao<ConstructPart> implements ConstructPartDao {

	@Override
	public boolean addConstructPart(ConstructPart am) {
		// TODO Auto-generated method stub
		return save(am);
	}

	@Override
	public boolean deleteConstructPart(Long id) {
		// TODO Auto-generated method stub
		return delete(get(id));
	}

	@Override
	public boolean updateConstructPart(ConstructPart am) {
		// TODO Auto-generated method stub
		return update(am);
	}
	@Override
	public ConstructPart getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConstructPart> getConstructPartList(ConstructPart am) {
		// TODO Auto-generated method stub
        List<ConstructPart> ret = new ArrayList<ConstructPart>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(ConstructPart.class);
	    if(am.getProjectId()!=null){
	    	criteria.add(Restrictions.eq("projectId", am.getProjectId()));
	    }
        
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
	}

	
}
