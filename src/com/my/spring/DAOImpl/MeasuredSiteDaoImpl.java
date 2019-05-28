package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.MeasuredSiteDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.MeasuredSite;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
@Repository
public class MeasuredSiteDaoImpl extends BaseDao<MeasuredSite> implements MeasuredSiteDao {

    @Override
    public boolean addMeasuredSite(MeasuredSite building) {
        return save(building);
    }

    @Override
    public boolean deleteMeasuredSite(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updateMeasuredSite(MeasuredSite building) {
        return update(building);
    }


	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<MeasuredSite> getMeasuredSiteListByBuildingId(Long buildingId) {
		DataWrapper<MeasuredSite> dataWrapper=new DataWrapper<MeasuredSite>();
		List<MeasuredSite> ret = new ArrayList<MeasuredSite>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(MeasuredSite.class);
        criteria.add(Restrictions.eq("bfmId",buildingId));
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
	public boolean addMeasuredSiteList(List<MeasuredSite> building) {
		// TODO Auto-generated method stub
		return saveList(building);
	}

}
