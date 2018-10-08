package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.UserMenuLogDao;
import com.my.spring.model.UserMenuLog;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
@Repository
public class UserMenuLogDaoImpl extends BaseDao<UserMenuLog> implements UserMenuLogDao {

    @Override
    public boolean addUserMenuLog(UserMenuLog role) {
        return save(role);
    }
    @Override
    public boolean deleteUserMenuLog(Long id) {
        return delete(get(id));
    }


	@Override
	public UserMenuLog getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}
	
	@Override
	public boolean updateUserMenuLog(UserMenuLog dp) {
		// TODO Auto-generated method stub
		return update(dp);
	}

	@Override
	public List<UserMenuLog> getUserMenuLogListByUserId(Long userId) {
        List<UserMenuLog> ret = new ArrayList<UserMenuLog>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(UserMenuLog.class);
        if(userId!=null){
        	criteria.add(Restrictions.eq("userId",userId));
        }
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
	}
	@Override
	public boolean addUserMenuLogList(List<UserMenuLog> gets) {
		// TODO Auto-generated method stub
		return saveList(gets);
	}
	
}
