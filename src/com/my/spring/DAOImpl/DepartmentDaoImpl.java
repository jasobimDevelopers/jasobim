package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.DepartmentDao;
import com.my.spring.model.Department;
import com.my.spring.model.UserIndexs;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
@Repository
public class DepartmentDaoImpl extends BaseDao<Department> implements DepartmentDao {

    @Override
    public boolean addDepartment(Department role) {
        return save(role);
    }
    @Override
    public boolean deleteDepartment(Long id) {
        return delete(get(id));
    }


    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<Department>> getDepartmentList(Integer pageIndex, Integer pageSize, Department department) {
        DataWrapper<List<Department>> dataWrapper = new DataWrapper<List<Department>>();
        List<Department> ret = new ArrayList<Department>();
        List<Department> ret2 = new ArrayList<Department>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Department.class);
       
//        criteria.addOrder(Order.desc("publishDate"));
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
        if(ret.isEmpty()){
        	Criteria criteria2 = session.createCriteria(Department.class);
            criteria2.add(Restrictions.isNull("userId"));
            // 取总页数
            criteria2.setProjection(Projections.rowCount());
            totalItemNum = ((Long)criteria2.uniqueResult()).intValue();
            totalPageNum = DaoUtil.getTotalPageNumber(totalItemNum, pageSize);

            // 真正取值
            criteria2.setProjection(null);
            if (pageSize > 0 && pageIndex > 0) {
                criteria2.setMaxResults(pageSize);// 最大显示记录数
                criteria2.setFirstResult((pageIndex - 1) * pageSize);// 从第几条开始
            }
            try {
                ret2 = criteria2.list();
            }catch (Exception e){
                e.printStackTrace();
            }
            dataWrapper.setData(ret2);
            dataWrapper.setTotalNumber(totalItemNum);
            dataWrapper.setCurrentPage(pageIndex);
            dataWrapper.setTotalPage(totalPageNum);
            dataWrapper.setNumberPerPage(pageSize);
        }
        
        return dataWrapper;
    }

	@Override
	public Department getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}
	
	@Override
	public boolean deleteDepartmentList(String[] ids) {
		// TODO Auto-generated method stub
		return deleteList(ids);
	}
	@Override
	public boolean updateDepartment(Department dp) {
		// TODO Auto-generated method stub
		return update(dp);
	}
	@Override
	public List<UserIndexs> getDepartmentListByUserId(Long userId) {
		List<UserIndexs> gets=new ArrayList<UserIndexs>();
		String sql = "select b.name,b.id,a.indexs from user_index a,department b where a.about_type=0 and a.about_id=b.id and a.user_id="
		+userId+" ORDER BY a.indexs asc";
		Session session=getSession();
		try{
			 Query query = session.createSQLQuery(sql)
					 .addScalar("id", StandardBasicTypes.LONG)
					 .addScalar("name", StandardBasicTypes.STRING)
					 .addScalar("indexs", StandardBasicTypes.LONG)
					 .setResultTransformer(Transformers.aliasToBean(UserIndexs.class));
			 	gets=query.list();
	        }catch(Exception e){
	            e.printStackTrace();
	    }
		return gets;
	}
	
}
