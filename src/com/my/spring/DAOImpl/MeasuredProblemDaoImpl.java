package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.MeasuredProblemDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.MeasuredProblem;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class MeasuredProblemDaoImpl extends BaseDao<MeasuredProblem> implements MeasuredProblemDao {

    @Override
    public boolean addMeasuredProblem(MeasuredProblem building) {
        return save(building);
    }

    @Override
    public boolean deleteMeasuredProblem(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updateMeasuredProblem(MeasuredProblem building) {
        return update(building);
    }


	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<MeasuredProblem>> getMeasuredProblemByProjectId(Long projectId) {
		DataWrapper<List<MeasuredProblem>> dataWrapper=new DataWrapper<List<MeasuredProblem>>();
		List<MeasuredProblem> ret = new ArrayList<MeasuredProblem>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(MeasuredProblem.class);
        criteria.add(Restrictions.eq("projectId",projectId));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (ret != null) {
        	dataWrapper.setData(ret);
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
		}
		return dataWrapper;
	}

	@Override
	public MeasuredProblem getById(Long measuredId) {
		// TODO Auto-generated method stub
		return get(measuredId);
	}

	
}
