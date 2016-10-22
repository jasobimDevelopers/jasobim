package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.RoleDao;
import com.my.spring.model.RoleEntity;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Repository
public class RoleDaoImpl extends BaseDao<RoleEntity> implements RoleDao {

    @Override
    public boolean addRole(RoleEntity Role) {
        return save(Role);
    }

    @Override
    public boolean deleteRole(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updateRole(RoleEntity Role) {
        return update(Role);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<RoleEntity>> getRoleList() {
        DataWrapper<List<RoleEntity>> retDataWrapper = new DataWrapper<List<RoleEntity>>();
        List<RoleEntity> ret = new ArrayList<RoleEntity>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(RoleEntity.class);
//        criteria.addOrder(Order.desc("publishDate"));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        retDataWrapper.setData(ret);
        return retDataWrapper;
    }
}
