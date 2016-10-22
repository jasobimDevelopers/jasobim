package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.model.UserEntity;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Repository
public class UserDaoImpl extends BaseDao<UserEntity> implements UserDao {

    @Override
    public boolean addUser(UserEntity user) {
        return save(user);
    }

    @Override
    public boolean deleteUser(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updateUser(UserEntity user) {
        return update(user);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<UserEntity>> getUserList() {
        DataWrapper<List<UserEntity>> retDataWrapper = new DataWrapper<List<UserEntity>>();
        List<UserEntity> ret = new ArrayList<UserEntity>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(UserEntity.class);
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
	public UserEntity findByUserName(String userName) {
		Session session = getSession();
        Criteria criteria = session.createCriteria(UserEntity.class);
        criteria.add(Restrictions.eq("userName", userName));
        List<UserEntity> result = criteria.list();
        if (result != null && result.size() > 0) {
            return (UserEntity) result.get(0);
        }
        return null;
	}

	@Override
	public UserEntity getUserById(Long id) {
		 return get(id);
	}

	@Override
	public UserEntity getUserByToken(String token) {
		String sql = "select user.* from t_user user,t_token token where token.token = " + token + " and token.user_id = user.id";
        Session session = getSession();
        Query query = session.createSQLQuery(sql)
                .addEntity(UserEntity.class);

        List<UserEntity> userList = query.list();
        if(userList != null && userList.size() > 0) {
            return userList.get(0);
        }else {
            return null;
        }
	}
}
