package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.AwardTicketDao;
import com.my.spring.model.AwardTicket;
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
public class AwardTicketDaoImpl extends BaseDao<AwardTicket> implements AwardTicketDao {

    @Override
    public boolean addAwardTicket(AwardTicket role) {
        return save(role);
    }
    @Override
    public boolean deleteAwardTicket(Long id) {
        return delete(get(id));
    }


    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<AwardTicket>> getAwardTicketList(Integer pageIndex, Integer pageSize, AwardTicket at) {
        DataWrapper<List<AwardTicket>> dataWrapper = new DataWrapper<List<AwardTicket>>();
        List<AwardTicket> ret = new ArrayList<AwardTicket>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(AwardTicket.class);
        criteria.addOrder(Order.desc("createDate"));
        if(at.getAwardType()!=null){
        	criteria.add(Restrictions.eq("awardType", at.getAwardType()));
        }
        if(at.getTicketType()!=null){
        	criteria.add(Restrictions.eq("ticketType", at.getTicketType()));
        }
        if(at.getAboutId()!=null){
        	criteria.add(Restrictions.eq("aboutId", at.getAboutId()));
        }
        if(at.getId()!=null){
        	criteria.add(Restrictions.eq("id", at.getId()));
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
	public AwardTicket getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}
	
	@Override
	public boolean deleteAwardTicketList(String[] ids) {
		// TODO Auto-generated method stub
		return deleteList(ids);
	}
	@Override
	public boolean updateAwardTicket(AwardTicket dp) {
		// TODO Auto-generated method stub
		return update(dp);
	}
	
}
