package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.PaperTypeDao;
import com.my.spring.model.PaperType;
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
public class PaperTypeDaoImpl extends BaseDao<PaperType> implements PaperTypeDao {

    @Override
    public boolean addPaperType(PaperType PaperType) {
        return save(PaperType);
    }

    @Override
    public boolean deletePaperType(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updatePaperType(PaperType PaperType) {
        return update(PaperType);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<PaperType>> getPaperTypeList() {
        DataWrapper<List<PaperType>> retDataWrapper = new DataWrapper<List<PaperType>>();
        List<PaperType> ret = new ArrayList<PaperType>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(PaperType.class);
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
