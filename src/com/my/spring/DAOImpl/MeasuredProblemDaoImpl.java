package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.MeasuredProblemDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.MeasuredProblem;
import com.my.spring.model.MeasuredProblemEditPojo;
import com.my.spring.model.QualityCheck;
import com.my.spring.model.User;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
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
	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<MeasuredProblem>> getMeasuredProblemByProjectId(Long id,Long projectId,User user,Integer status,List<Long> bfmIds,List<Long> checkTypeIds) {
		DataWrapper<List<MeasuredProblem>> dataWrapper=new DataWrapper<List<MeasuredProblem>>();
		List<MeasuredProblem> ret = new ArrayList<MeasuredProblem>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(MeasuredProblem.class);
        criteria.add(Restrictions.eq("projectId",projectId));
        if(status!=null){
        	 criteria.add(Restrictions.eq("status",status));
        }
        if(id!=null){
        	criteria.add(Restrictions.eq("id", id));
        }
        if(!bfmIds.isEmpty()){
        	criteria.add(Restrictions.in("bfmId",bfmIds));
        }
        if(!checkTypeIds.isEmpty()){
        	String str = "";
        	for(Long n:checkTypeIds){
        		if(str.equals("")){
        			str=n+"";
        		}else{
        			str=str+"%"+n;
        		}
        	}
            criteria.add(Restrictions.like("checkLists",str));
        }
       
        if(user.getUserType()!=UserTypeEnum.Admin.getType()){
        	Disjunction disjunction = Restrictions.disjunction();			
         	disjunction.add(Restrictions.eq("checkUser", user.getId()));
         	disjunction.add(Restrictions.eq("rectifyUser", user.getId()));
         	criteria.add(disjunction);
        }
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

	@Override
	public boolean addMeasuredProblemList(List<MeasuredProblem> mpList) {
		// TODO Auto-generated method stub
		return saveList(mpList);
	}

	@Override
	public List<MeasuredProblem> getMeasuredProblemByIds(List<MeasuredProblemEditPojo> gets) {
		List<MeasuredProblem> ret = new ArrayList<MeasuredProblem>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(MeasuredProblem.class);
        List<Long> ids = new ArrayList<Long>();
        for(MeasuredProblemEditPojo pojo:gets){
        	ids.add(pojo.getId());
        }
        criteria.add(Restrictions.in("id",ids));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
		return ret;
	}

	@Override
	public boolean updateMeasuredProblemList(List<MeasuredProblem> getList) {
		// TODO Auto-generated method stub
		return updateList(getList);
	}

	
}
