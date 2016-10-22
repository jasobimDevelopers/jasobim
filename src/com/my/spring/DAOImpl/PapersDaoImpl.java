package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.PapersDao;
import com.my.spring.model.Papers;
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
public class PapersDaoImpl extends BaseDao<Papers> implements PapersDao {

    @Override
    public boolean addPapers(Papers Papers) {
        return save(Papers);
    }

    @Override
    public boolean deletePapers(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updatePapers(Papers Papers) {
        return update(Papers);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<Papers>> getPapersList() {
        DataWrapper<List<Papers>> retDataWrapper = new DataWrapper<List<Papers>>();
        List<Papers> ret = new ArrayList<Papers>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Papers.class);
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
