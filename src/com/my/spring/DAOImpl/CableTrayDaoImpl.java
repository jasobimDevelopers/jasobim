package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.CableTrayDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.CableTray;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CableTrayDaoImpl extends BaseDao<CableTray> implements CableTrayDao {

    @Override
    public boolean addCableTray(CableTray CableTray) {
        return save(CableTray);
    }

    @Override
    public boolean deleteCableTray(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updateCableTray(CableTray CableTray) {
        return update(CableTray);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<CableTray>> getCableTrayList() {
        DataWrapper<List<CableTray>> retDataWrapper = new DataWrapper<List<CableTray>>();
        List<CableTray> ret = new ArrayList<CableTray>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(CableTray.class);
//        criteria.addOrder(Order.desc("publishDate"));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        retDataWrapper.setData(ret);
        return retDataWrapper;
    }

	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<CableTray> getCableTrayByProjectId(Long projectId) {
		DataWrapper<CableTray> dataWrapper=new DataWrapper<CableTray>();
		List<CableTray> ret = new ArrayList<CableTray>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(CableTray.class);
        criteria.add(Restrictions.eq("projectId",projectId));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (ret != null && ret.size() > 0) {
        	dataWrapper.setData(ret.get(0));
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
		}
		return dataWrapper;
	}

	@Override
	public boolean addCableTrayList(List<CableTray> cableTrayList) {
		// TODO Auto-generated method stub
		return saveList(cableTrayList);
	}


}
