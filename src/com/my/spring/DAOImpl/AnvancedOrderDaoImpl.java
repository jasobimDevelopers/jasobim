package com.my.spring.DAOImpl;
import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.AdvancedOrderDao;
import com.my.spring.model.AdvancedOrder;
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
public class AnvancedOrderDaoImpl extends BaseDao<AdvancedOrder> implements AdvancedOrderDao {

    @Override
    public boolean addAdvancedOrder(AdvancedOrder AdvancedOrder) {
        return save(AdvancedOrder);
    }

    @Override
    public boolean deleteAdvancedOrder(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updateAdvancedOrder(AdvancedOrder AdvancedOrder) {
        return update(AdvancedOrder);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<AdvancedOrder>> getAdvancedOrdersList( Integer pageIndex, Integer pageSize, AdvancedOrder AdvancedOrder,int adminFlag) {
        DataWrapper<List<AdvancedOrder>> retDataWrapper = new DataWrapper<List<AdvancedOrder>>();
        List<AdvancedOrder> ret = new ArrayList<AdvancedOrder>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(AdvancedOrder.class);
        criteria.addOrder(Order.desc("createDate"));
        if(AdvancedOrder.getStatus()!=null){
        	//////待审批
        	if(AdvancedOrder.getStatus()==2){
        		 criteria.add(Restrictions.eq("nextReceivePeopleId", AdvancedOrder.getNextReceivePeopleId()));
        	}
        	/////已审批
        	if(AdvancedOrder.getStatus()==3){
        		criteria.add(Restrictions.like("approvalPeopleName", "%"+AdvancedOrder.getApprovalPeopleName()+"%"));
        	}
        	criteria.add(Restrictions.eq("status", AdvancedOrder.getStatus()));
        }
        if(adminFlag!=-1){
        	criteria.add(Restrictions.or(Restrictions.like("approvalPeopleName", "%"+AdvancedOrder.getApprovalPeopleName()+"%"),
                    Restrictions.eq("nextReceivePeopleId", AdvancedOrder.getNextReceivePeopleId()),
					 Restrictions.eq("createUserName", AdvancedOrder.getCreateUserName())));
        }
        if(AdvancedOrder.getId()!=null){
        	criteria.add(Restrictions.eq("id", AdvancedOrder.getId()));
        }
        if(AdvancedOrder.getProjectId()!=null){
        	criteria.add(Restrictions.eq("projectId", AdvancedOrder.getProjectId()));
        }
        if(AdvancedOrder.getSubmitUserId()!=null){
        	criteria.add(Restrictions.eq("submitUserId", AdvancedOrder.getSubmitUserId()));
        }
        if(AdvancedOrder.getStatus()!=null){
        	criteria.add(Restrictions.eq("status", AdvancedOrder.getStatus()));
        }
        if(AdvancedOrder.getMonth()!=null){
        	criteria.add(Restrictions.eq("month", AdvancedOrder.getMonth()));
        }
        if(AdvancedOrder.getConstructPart()!=null){
        	criteria.add(Restrictions.like("constructPart","%"+ AdvancedOrder.getConstructPart()+"%"));
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
        retDataWrapper.setData(ret);
        retDataWrapper.setTotalNumber(totalItemNum);
        retDataWrapper.setCurrentPage(pageIndex);
        retDataWrapper.setTotalPage(totalPageNum);
        retDataWrapper.setNumberPerPage(pageSize);
        return retDataWrapper;
    }

	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<AdvancedOrder>> getAdvancedOrderByUserId(Long submitUserId) {
		DataWrapper<List<AdvancedOrder>> retDataWrapper = new DataWrapper<List<AdvancedOrder>>();
		List<AdvancedOrder> ret = null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(AdvancedOrder.class);
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
	public AdvancedOrder getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

	@Override
	public boolean updateConstructionTask(AdvancedOrder ct) {
		 return update(ct);
	}

	

	

}
