package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.MessagefilesDao;
import com.my.spring.model.Messagefiles;
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
public class MessagefilesDaoImpl extends BaseDao<Messagefiles> implements MessagefilesDao {

    @Override
    public boolean addMessagefiles(Messagefiles Messagefiles) {
        return save(Messagefiles);
    }

    @Override
    public boolean deleteMessagefiles(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updateMessagefiles(Messagefiles Messagefiles) {
        return update(Messagefiles);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<Messagefiles>> getMessagefilesList() {
        DataWrapper<List<Messagefiles>> retDataWrapper = new DataWrapper<List<Messagefiles>>();
        List<Messagefiles> ret = new ArrayList<Messagefiles>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Messagefiles.class);
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
