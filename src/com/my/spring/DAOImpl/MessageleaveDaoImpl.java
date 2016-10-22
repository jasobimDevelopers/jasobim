package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.MessageleaveDao;
import com.my.spring.model.Messageleave;
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
public class MessageleaveDaoImpl extends BaseDao<Messageleave> implements MessageleaveDao {

    @Override
    public boolean addMessageleave(Messageleave Messageleave) {
        return save(Messageleave);
    }

    @Override
    public boolean deleteMessageleave(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updateMessageleave(Messageleave Messageleave) {
        return update(Messageleave);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<Messageleave>> getMessageleaveList() {
        DataWrapper<List<Messageleave>> retDataWrapper = new DataWrapper<List<Messageleave>>();
        List<Messageleave> ret = new ArrayList<Messageleave>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Messageleave.class);
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
