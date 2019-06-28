package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.PaperPointNumsLogDao;
import com.my.spring.model.PaperPointNumsLog;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class PaperPointNumsLogDaoImpl extends BaseDao<PaperPointNumsLog> implements PaperPointNumsLogDao {

    @Override
    public boolean addPaperPointNumsLog(PaperPointNumsLog PaperPointNumsLog) {
        return save(PaperPointNumsLog);
    }

    @Override
    public boolean deletePaperPointNumsLog(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updatePaperPointNumsLog(PaperPointNumsLog PaperPointNumsLog) {
        return update(PaperPointNumsLog);
    }


	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<PaperPointNumsLog>> getPaperPointNumsLogListByProjectId(Long projectId) {
		DataWrapper<List<PaperPointNumsLog>> dataWrapper=new DataWrapper<List<PaperPointNumsLog>>();
		List<PaperPointNumsLog> ret = new ArrayList<PaperPointNumsLog>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(PaperPointNumsLog.class);
        criteria.add(Restrictions.eq("projectId",projectId));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        dataWrapper.setData(ret);
		return dataWrapper;
	}

	@Override
	public PaperPointNumsLog getPaperPointNumsById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

}
