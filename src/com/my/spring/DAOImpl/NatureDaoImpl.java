package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.NatureDao;
import com.my.spring.model.Nature;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
@Repository
public class NatureDaoImpl extends BaseDao<Nature> implements NatureDao {

    @Override
    public boolean addNature(Nature role) {
        return save(role);
    }
    @Override
    public boolean deleteNature(Long id) {
        return delete(get(id));
    }

	@Override
	public Nature getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}
		
	@Override
	public boolean updateNature(Nature dp) {
		// TODO Auto-generated method stub
		return update(dp);
	}
	@Override
	public DataWrapper<List<Nature>> getNatureListByProjectId(Integer pageIndex,Integer pageSize) {
		DataWrapper<List<Nature>> dataWrapper=new DataWrapper<List<Nature>>();
		List<Nature> gets=new ArrayList<Nature>();
		Session session=getSession();
        Criteria criteria = session.createCriteria(Nature.class);
        criteria.addOrder(Order.desc("createDate"));
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
            gets = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        dataWrapper.setData(gets);
        dataWrapper.setTotalNumber(totalItemNum);
        dataWrapper.setCurrentPage(pageIndex);
        dataWrapper.setTotalPage(totalPageNum);
        dataWrapper.setNumberPerPage(pageSize);
        
        return dataWrapper;
	}
	
}
