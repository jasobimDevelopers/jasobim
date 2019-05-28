package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.PaperPointInfoDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.PaperPointInfo;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class PaperPointInfoDaoImpl extends BaseDao<PaperPointInfo> implements PaperPointInfoDao {

    @Override
    public boolean addPaperPointInfo(PaperPointInfo building) {
        return save(building);
    }

    @Override
    public boolean deletePaperPointInfo(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updatePaperPointInfo(PaperPointInfo building) {
        return update(building);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<PaperPointInfo>> getPaperPointInfoList() {
        DataWrapper<List<PaperPointInfo>> retDataWrapper = new DataWrapper<List<PaperPointInfo>>();
        List<PaperPointInfo> ret = new ArrayList<PaperPointInfo>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(PaperPointInfo.class);
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
	public DataWrapper<PaperPointInfo> getPaperPointInfoByProjectId(Long projectId) {
		DataWrapper<PaperPointInfo> dataWrapper=new DataWrapper<PaperPointInfo>();
		List<PaperPointInfo> ret = new ArrayList<PaperPointInfo>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(PaperPointInfo.class);
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
	public boolean deletePaperPointInfoByProjectId(Long id) {
		String sql = "delete from building where project_id="+id;
		Session session=getSession();
		 try{
			 Query query = session.createSQLQuery(sql);
			 if(query.executeUpdate()==1){
				 return true;
			 }
			 
	        }catch(Exception e){
	            e.printStackTrace();
	        }
		 
		return false;
	}
}
