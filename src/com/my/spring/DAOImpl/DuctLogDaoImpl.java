package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.DuctLogDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.DuctLog;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DuctLogDaoImpl extends BaseDao<DuctLog> implements DuctLogDao {

    @Override
    public boolean addDuctLog(DuctLog DuctLog) {
        return save(DuctLog);
    }

    @Override
    public boolean deleteDuctLog(Long id) {
        return delete(get(id));
    }
	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<DuctLog>> getDuctLogByDuctId(Long ductId) {
		DataWrapper<List<DuctLog>> dataWrapper=new DataWrapper<List<DuctLog>>();
		List<DuctLog> ret = new ArrayList<DuctLog>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(DuctLog.class);
        criteria.add(Restrictions.eq("ductId",ductId));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (ret != null && ret.size() > 0) {
        	dataWrapper.setData(ret);
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
		}
		return dataWrapper;
	}

	

}
