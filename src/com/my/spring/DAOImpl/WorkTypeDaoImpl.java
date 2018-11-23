package com.my.spring.DAOImpl;
import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.WorkTypeDao;
import com.my.spring.model.WorkType;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class WorkTypeDaoImpl extends BaseDao<WorkType> implements WorkTypeDao {

    @Override
    public boolean addWorkType(WorkType WorkType) {
        return save(WorkType);
    }

    @Override
    public boolean deleteWorkType(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updateWorkType(WorkType WorkType) {
        return update(WorkType);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<WorkType>> getWorkTypeList( Integer pageIndex, Integer pageSize, WorkType WorkType) {
        DataWrapper<List<WorkType>> retDataWrapper = new DataWrapper<List<WorkType>>();
        List<WorkType> ret = new ArrayList<WorkType>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(WorkType.class);
        if(WorkType.getName()!=null){
        	criteria.add(Restrictions.like("name", "%"+WorkType.getName()+"%"));
        }
        if(WorkType.getTeamType()!=null){
        	criteria.add(Restrictions.eq("teamType", WorkType.getTeamType()));
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
	public WorkType getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}	

}
