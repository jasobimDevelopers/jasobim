package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.DuctDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Duct;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DuctDaoImpl extends BaseDao<Duct> implements DuctDao {

    @Override
    public boolean addDuct(Duct Duct) {
        return save(Duct);
    }

    @Override
    public boolean deleteDuct(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updateDuct(Duct Duct) {
        return update(Duct);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<Duct>> getDuctList() {
        DataWrapper<List<Duct>> retDataWrapper = new DataWrapper<List<Duct>>();
        List<Duct> ret = new ArrayList<Duct>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Duct.class);
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
	public DataWrapper<Duct> getDuctByProjectId(Long projectId) {
		DataWrapper<Duct> dataWrapper=new DataWrapper<Duct>();
		List<Duct> ret = new ArrayList<Duct>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Duct.class);
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
	public boolean addDuctList(List<Duct> ductList) {
		return saveList(ductList);
	}


}
