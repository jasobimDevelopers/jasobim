package com.my.spring.DAOImpl;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.PointDataInputLogDao;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.PointDataInputLog;
import com.my.spring.model.User;
@Repository
public class PointDataInputLogDaoImpl extends BaseDao<PointDataInputLog> implements PointDataInputLogDao{

	@Override
	public boolean addPointDataInputLog(PointDataInputLog pl) {
		// TODO Auto-generated method stub
		return save(pl);
	}

	@Override
	public boolean deletePointDataInputLog(Long id) {
		// TODO Auto-generated method stub
		return delete(get(id));
	}

	@Override
	public boolean updatePointDataInputLog(PointDataInputLog pl) {
		// TODO Auto-generated method stub
		return update(pl);
	}

	@Override
	public List<PointDataInputLog> getPointDataInputLogListByPointId(User user, Long pointId) {
		// TODO Auto-generated method stub
		List<PointDataInputLog> ret = new ArrayList<PointDataInputLog>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(PointDataInputLog.class);
        /*if(user.getUserType()!=UserTypeEnum.Admin.getType()){
        	criteria.add(Restrictions.eq("createUser",user.getId()));
        }*/
        criteria.add(Restrictions.eq("createUser",user.getId()));
        criteria.add(Restrictions.eq("pointId",pointId));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
		return ret;
	}

	@Override
	public boolean addPointDataInputLogList(List<PointDataInputLog> pointDataInputLog) {
		// TODO Auto-generated method stub
		return saveList(pointDataInputLog);
	}

	@Override
	public List<PointDataInputLog> getPointDataInputLogListByPointId(Long pointId) {
		List<PointDataInputLog> ret = new ArrayList<PointDataInputLog>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(PointDataInputLog.class);
        criteria.add(Restrictions.eq("pointId",pointId));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
		return ret;
	}


	@Override
	public PointDataInputLog getPointDataInputLogListByOptions(Long pointId, Long checkListId, Long inputUserId) {
		// TODO Auto-generated method stub
		List<PointDataInputLog> ret = new ArrayList<PointDataInputLog>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(PointDataInputLog.class);
        criteria.add(Restrictions.eq("pointId",pointId));
        criteria.add(Restrictions.eq("checkTypeId",checkListId));
        criteria.add(Restrictions.eq("createUser",inputUserId));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(!ret.isEmpty()){
        	return ret.get(0);
        }
		return null;
	}

}
