package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.PaperPointInfoItemDao;
import com.my.spring.model.PaperPointInfoItem;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class PaperPointInfoItemDaoImpl extends BaseDao<PaperPointInfoItem> implements PaperPointInfoItemDao {

    @Override
    public boolean addPaperPointInfoItem(PaperPointInfoItem PaperPointInfoItem) {
        return save(PaperPointInfoItem);
    }

    @Override
    public boolean deletePaperPointInfoItem(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updatePaperPointInfoItem(PaperPointInfoItem PaperPointInfoItem) {
        return update(PaperPointInfoItem);
    }

	@Override
	public List<PaperPointInfoItem> getPaperPointInfoItemByPointId(Long id) {
		List<PaperPointInfoItem> ret = new ArrayList<PaperPointInfoItem>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(PaperPointInfoItem.class);
        criteria.add(Restrictions.eq("pointId",id));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
	}

	@Override
	public PaperPointInfoItem getPaperPointInfoItemById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

	@Override
	public boolean addPaperPointInfoItemList(List<PaperPointInfoItem> saveList) {
		// TODO Auto-generated method stub
		return saveList(saveList);
	}
}
