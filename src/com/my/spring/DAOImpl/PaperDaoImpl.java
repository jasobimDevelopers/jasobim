package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.PaperDao;
import com.my.spring.model.Paper;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Repository
public class PaperDaoImpl extends BaseDao<Paper> implements PaperDao {

    @Override
    public boolean addPaper(Paper paper) {
        return save(paper);
    }

    @Override
    public boolean deletePaper(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updatePaper(Paper paper) {
        return update(paper);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<Paper>> getPaperList(Long projectId) {
        DataWrapper<List<Paper>> retDataWrapper = new DataWrapper<List<Paper>>();
        List<Paper> ret = new ArrayList<Paper>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Paper.class);
        criteria.add(Restrictions.eq("projectId", projectId));
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
	public Paper getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}
}
