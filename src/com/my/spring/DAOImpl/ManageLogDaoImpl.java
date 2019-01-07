package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.ManageLogDao;
import com.my.spring.model.ManageLog;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
@Repository
public class ManageLogDaoImpl extends BaseDao<ManageLog> implements ManageLogDao {

    @Override
    public boolean addManageLog(ManageLog role) {
        return save(role);
    }
    @Override
    public boolean deleteManageLog(Long id) {
        return delete(get(id));
    }


    @SuppressWarnings("unchecked")
	@Override
    public List<ManageLog> getManageLogList(ManageLog manageLog) {
        List<ManageLog> ret = new ArrayList<ManageLog>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(ManageLog.class);
        criteria.addOrder(Order.desc("actionDate"));
        criteria.add(Restrictions.eq("manageType", manageLog.getManageType()));
        if(manageLog.getAboutId()!=null){
        	criteria.add(Restrictions.eq("aboutId", manageLog.getAboutId()));
        }
        if(manageLog.getActionType()!=null){
        	criteria.add(Restrictions.eq("actionType", manageLog.getActionType()));
        }
        if(manageLog.getOperateUser()!=null){
        	criteria.add(Restrictions.eq("operateUser", manageLog.getOperateUser()));
        }
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        
        return ret;
    }

	@Override
	public ManageLog getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}
	
}
