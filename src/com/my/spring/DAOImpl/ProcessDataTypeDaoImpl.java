package com.my.spring.DAOImpl;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.ProcessDataTypeDao;
import com.my.spring.model.ProcessDataType;

@Repository
public class ProcessDataTypeDaoImpl extends BaseDao<ProcessDataType> implements ProcessDataTypeDao {

	
	@Override
	public boolean addProcessDataType(ProcessDataType processDataType) {
		// TODO Auto-generated method stub
		return save(processDataType);
	}

	@Override
	public ProcessDataType getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<ProcessDataType> getProcessDataTypeList() {
		// TODO Auto-generated method stub
        List<ProcessDataType> ret = new ArrayList<ProcessDataType>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(ProcessDataType.class);
        criteria.addOrder(Order.asc("createDate"));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
	}


	@Override
	public boolean deleteProcessDataType(Long id) {
		// TODO Auto-generated method stub
		return delete(get(id));
	}


}
