package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.RoleDao;
import com.my.spring.model.Role;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
@Repository
public class RoleDaoImpl extends BaseDao<Role> implements RoleDao {

    @Override
    public boolean addRole(Role role) {
        return save(role);
    }
    @Override
    public boolean deleteRole(Long id) {
        return delete(get(id));
    }


    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<Role>> getRoleList() {
        DataWrapper<List<Role>> retDataWrapper = new DataWrapper<List<Role>>();
        List<Role> ret = new ArrayList<Role>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Role.class);
//        criteria.addOrder(Order.desc("publishDate"));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        retDataWrapper.setData(ret);
        return retDataWrapper;
    }

	@Override
	public Role getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}
	@Override
	public boolean updateRole(Role role) {
		// TODO Auto-generated method stub
		return update(role);
	}

	
	@Override
	public boolean deleteRoleList(String[] ids) {
		// TODO Auto-generated method stub
		return deleteList(ids);
	}
}
