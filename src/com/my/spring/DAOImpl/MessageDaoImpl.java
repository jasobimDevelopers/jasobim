package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.MessageDao;
import com.my.spring.model.Message;
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
public class MessageDaoImpl extends BaseDao<Message> implements MessageDao {

    @Override
    public boolean addMessage(Message Message) {
        return save(Message);
    }

    @Override
    public boolean deleteMessage(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updateMessage(Message Message) {
        return update(Message);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<Message>> getMessageList() {
        DataWrapper<List<Message>> retDataWrapper = new DataWrapper<List<Message>>();
        List<Message> ret = new ArrayList<Message>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Message.class);
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
