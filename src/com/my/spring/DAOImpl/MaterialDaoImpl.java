package com.my.spring.DAOImpl;
import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.MaterialDao;
import com.my.spring.model.Material;
import com.my.spring.model.MaterialType;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MaterialDaoImpl extends BaseDao<Material> implements MaterialDao {

    @Override
    public boolean addMaterial(Material m) {
        return save(m);
    }

    @Override
    public boolean deleteMaterial(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updateMaterial(Material m) {
        return update(m);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<Material>> getMaterialList( Integer pageIndex, Integer pageSize, Material m) {
        DataWrapper<List<Material>> retDataWrapper = new DataWrapper<List<Material>>();
        List<Material> ret = new ArrayList<Material>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Material.class);
        criteria.addOrder(Order.asc("createDate"));
       
        if(m.getUserId()!=null){
        	criteria.add(Restrictions.eq("userId", m.getUserId()));
        }
        if(m.getProjectId()!=null){
        	criteria.add(Restrictions.eq("projectId", m.getProjectId()));
        }
        if(m.getMaterialType()!=null){
        	criteria.add(Restrictions.eq("materialType", m.getMaterialType()));
        }
        if(m.getMaterialName()!=null){
         	criteria.add(Restrictions.like("materialName", m.getMaterialName(),MatchMode.ANYWHERE));
        }
        /////////////////////////////////////
   
        if (pageSize == null) {
			pageSize = 10;
		}
        if (pageIndex == null) {
			pageIndex = 1;
		}
        
        // 取总页数
        criteria.setProjection(Projections.rowCount());
        int totalItemNum = ((Long)criteria.uniqueResult()).intValue();
        int totalPageNum = DaoUtil.getTotalPageNumber(totalItemNum, pageSize);

        // 真正取值
        criteria.setProjection(null);
        if (pageSize > 0 && pageIndex > 0) {
            criteria.setMaxResults(pageSize);// 最大显示记录数
            criteria.setFirstResult((pageIndex - 1) * pageSize);// 从第几条开始
        }
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        retDataWrapper.setData(ret);
        retDataWrapper.setTotalNumber(totalItemNum);
        retDataWrapper.setCurrentPage(pageIndex);
        retDataWrapper.setTotalPage(totalPageNum);
        retDataWrapper.setNumberPerPage(pageSize);
        return retDataWrapper;
    }


	@Override
	public Material getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}


	

	

}
