package com.my.spring.DAOImpl;
import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.MaterialLogDao;
import com.my.spring.model.MaterialLog;
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
public class MaterialLogDaoImpl extends BaseDao<MaterialLog> implements MaterialLogDao {

    @Override
    public boolean addMaterialLog(MaterialLog m) {
        return save(m);
    }

    @Override
    public boolean deleteMaterialLog(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updateMaterialLog(MaterialLog m) {
        return update(m);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<MaterialLog>> getMaterialLogList( Integer pageIndex, Integer pageSize, MaterialLog m) {
        DataWrapper<List<MaterialLog>> retDataWrapper = new DataWrapper<List<MaterialLog>>();
        List<MaterialLog> ret = new ArrayList<MaterialLog>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(MaterialLog.class);
        criteria.addOrder(Order.asc("logDate"));
       
        if(m.getUserId()!=null){
        	criteria.add(Restrictions.eq("userId", m.getUserId()));
        }
        if(m.getLogType()!=null){
        	criteria.add(Restrictions.eq("logType", m.getLogType()));
        }
        if(m.getMaterialId()!=null){
        	criteria.add(Restrictions.eq("materialId", m.getMaterialId()));
        }
        /////////////////////////////////////
   
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


	@Override
	public MaterialLog getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

	

	

}
