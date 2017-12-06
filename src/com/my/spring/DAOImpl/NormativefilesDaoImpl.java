package com.my.spring.DAOImpl;
import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.NormativefilesDao;
import com.my.spring.model.Normativefiles;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class NormativefilesDaoImpl extends BaseDao<Normativefiles> implements NormativefilesDao {

    @Override
    public boolean addNormativefiles(Normativefiles Normativefiles) {
        return save(Normativefiles);
    }

    @Override
    public boolean deleteNormativefiles(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updateNormativefiles(Normativefiles Normativefiles) {
        return update(Normativefiles);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<Normativefiles>> getNormativefilessList( Integer pageIndex, Integer pageSize, Normativefiles Normativefiles) {
        DataWrapper<List<Normativefiles>> retDataWrapper = new DataWrapper<List<Normativefiles>>();
        List<Normativefiles> ret = new ArrayList<Normativefiles>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Normativefiles.class);
        if(Normativefiles.getStudyType()!=null){
        	criteria.add(Restrictions.eq("studyType", Normativefiles.getStudyType()));
        }
        criteria.addOrder(Order.desc("submitDate"));
   
        if (pageSize == null) {
			pageSize = 10;
		}
        if (pageIndex == null) {
			pageIndex = 1;
		}
        
        // 取总页数
        criteria.setProjection(Projections.rowCount());
        int totalItemNum = ((Long)criteria.uniqueResult()).intValue();
        int totalPageNum = DaoUtil.getTotalPageNumber(totalItemNum, pageSize);
        if(Normativefiles.getContent()!=null){
        	pageIndex=-1;
        }
        // 真正取值
        criteria.setProjection(null);
        if (pageSize > 0 && pageIndex > 0) {
            criteria.setMaxResults(pageSize);// 最大显示记录数
            criteria.setFirstResult((pageIndex - 1) * pageSize);// 从第几条开始
        }
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        retDataWrapper.setData(ret);
        retDataWrapper.setTotalNumber(totalItemNum);
        retDataWrapper.setCurrentPage(pageIndex);
        retDataWrapper.setTotalPage(totalPageNum);
        retDataWrapper.setNumberPerPage(pageSize);
        return retDataWrapper;
    }

	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<Normativefiles>> getNormativefilessListByUserId(Long submitUserId) {
		DataWrapper<List<Normativefiles>> retDataWrapper = new DataWrapper<List<Normativefiles>>();
		List<Normativefiles> ret = null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(Normativefiles.class);
        criteria.add(Restrictions.eq("submitUserId",submitUserId));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (ret != null && ret.size() > 0) {
			retDataWrapper.setData(ret);
		}
		return retDataWrapper;
	}

	@Override
	public Normativefiles getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

	

	

}
