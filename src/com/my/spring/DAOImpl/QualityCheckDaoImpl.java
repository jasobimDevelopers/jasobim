package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.QualityCheckDao;
import com.my.spring.model.QualityCheck;
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
public class QualityCheckDaoImpl extends BaseDao<QualityCheck> implements QualityCheckDao {

    @Override
    public boolean addQualityCheck(QualityCheck role) {
        return save(role);
    }
    @Override
    public boolean deleteQualityCheck(Long id) {
        return delete(get(id));
    }


    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<QualityCheck>> getQualityCheckList(Integer pageIndex, Integer pageSize, QualityCheck qualityManage) {
        DataWrapper<List<QualityCheck>> dataWrapper = new DataWrapper<List<QualityCheck>>();
        List<QualityCheck> ret = new ArrayList<QualityCheck>();
        Session session = getSession();
        
        Criteria criteria = session.createCriteria(QualityCheck.class);
        criteria.addOrder(Order.desc("createDate"));
        criteria.add(Restrictions.eq("projectId", qualityManage.getProjectId()));
        if(qualityManage.getId()!=null){
        	criteria.add(Restrictions.eq("id", qualityManage.getId()));
        }
        if(qualityManage.getStatus()!=null){
        	criteria.add(Restrictions.eq("status", qualityManage.getStatus()));
        }
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
        dataWrapper.setData(ret);
        dataWrapper.setTotalNumber(totalItemNum);
        dataWrapper.setCurrentPage(pageIndex);
        dataWrapper.setTotalPage(totalPageNum);
        dataWrapper.setNumberPerPage(pageSize);
        
        
        return dataWrapper;
    }

	@Override
	public QualityCheck getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}
	
	@Override
	public boolean deleteQualityCheckList(String[] ids) {
		// TODO Auto-generated method stub
		return deleteList(ids);
	}
	@Override
	public boolean updateQualityCheck(QualityCheck dp) {
		// TODO Auto-generated method stub
		return update(dp);
	}
	
	
}
