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
import com.my.spring.DAO.CheckListTypesDao;
import com.my.spring.model.CheckListTypes;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;
@Repository
public class CheckListTypesDaoImpl extends BaseDao<CheckListTypes> implements CheckListTypesDao {

	@Override
	public CheckListTypes getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

	@Override
	public boolean deleteCheckListTypes(Long id) {
		// TODO Auto-generated method stub
		return delete(get(id));
	}

	@Override
	public boolean addCheckListTypes(CheckListTypes role) {
		// TODO Auto-generated method stub
		return save(role);
	}

	@Override
	public DataWrapper<List<CheckListTypes>> getCheckListTypesListByType(Integer pageIndex, Integer pageSize,
			Integer type) {
		// TODO Auto-generated method stub
		DataWrapper<List<CheckListTypes>> dataWrapper=new DataWrapper<List<CheckListTypes>>();
		List<CheckListTypes> gets=new ArrayList<CheckListTypes>();
		Session session=getSession();
        Criteria criteria = session.createCriteria(CheckListTypes.class);
        criteria.addOrder(Order.desc("createDate"));
        criteria.add(Restrictions.eq("checkType", type));
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
