package com.my.spring.DAOImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.DepartmentUserDao;
import com.my.spring.model.DepartmentUser;
import com.my.spring.utils.DaoUtil;
@Repository
public class DepartmentUserDaoImpl extends BaseDao<DepartmentUser> implements DepartmentUserDao {

	@Override
	public DepartmentUser getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

	@Override
	public boolean addDepartmentUser(DepartmentUser departmentUser) {
		// TODO Auto-generated method stub
		return save(departmentUser);
	}

	@Override
	public boolean deleteDepartmentUser(Long id) {
		// TODO Auto-generated method stub
		return delete(get(id));
	}

	@Override
	public boolean updateDepartmentUser(DepartmentUser departmentUser) {
		// TODO Auto-generated method stub
		return update(departmentUser);
	}

	@Override
	public List<DepartmentUser> getDepartmentUserList(Integer pageSize, Integer pageIndex,
			DepartmentUser departmentUser) {
        List<DepartmentUser> ret = new ArrayList<DepartmentUser>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(DepartmentUser.class);
       
        if(departmentUser.getDepartmentId()!=null){
        	criteria.add(Restrictions.eq("departmentId", departmentUser.getDepartmentId()));
        }
        if(departmentUser.getName()!=null){
        	criteria.add(Restrictions.like("name", "%"+departmentUser.getName()+"%"));
        }
        if(departmentUser.getSex()!=null){
        	criteria.add(Restrictions.eq("sex", departmentUser.getSex()));
        }
        if(departmentUser.getProjectId()!=null){
        	criteria.add(Restrictions.eq("projectId", departmentUser.getProjectId()));
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
      
        return ret;
	}

	@Override
	public boolean deleteDepartmentUserList(String[] ids) {
		// TODO Auto-generated method stub
		return deleteList(ids);
	}

	

}
