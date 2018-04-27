package com.my.spring.DAOImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.my.spring.DAO.MechanicPriceDao;
import com.my.spring.DAO.BaseDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.MechanicPrice;
import com.my.spring.model.Department;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;
@Repository
public class MechanicPriceDaoImpl extends BaseDao<MechanicPrice> implements MechanicPriceDao {

	@Override
	public boolean addMechanicPrice(MechanicPrice am) {
		// TODO Auto-generated method stub
		return save(am);
	}

	@Override
	public boolean deleteMechanicPrice(Long id) {
		// TODO Auto-generated method stub
		return delete(get(id));
	}

	@Override
	public boolean updateMechanicPrice(MechanicPrice am) {
		// TODO Auto-generated method stub
		return update(am);
	}
	@Override
	public MechanicPrice getMechanicPriceById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

	
	@Override
	public DataWrapper<Void> deleteMechanicPriceByMechanicId(Long id) {
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		String sql="delete * from attence_log where project_id="+id;
		Session session=getSession();
		 try{
	            Query query=session.createSQLQuery(sql);
	            session.getTransaction().commit();
	            session.flush();
	        }catch(Exception e){
	            e.printStackTrace();
	            session.getTransaction().rollback();
	            dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
	        }
		 
		return dataWrapper;
	}

	@Override
	public DataWrapper<List<MechanicPrice>> getMechanicPriceList(Integer pageIndex, Integer pageSize, MechanicPrice am) {
		// TODO Auto-generated method stub
		return null;
	}

}
