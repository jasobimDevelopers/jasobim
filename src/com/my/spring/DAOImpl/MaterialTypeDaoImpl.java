package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.MaterialTypeDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.MaterialType;
import com.my.spring.model.Project;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MaterialTypeDaoImpl extends BaseDao<MaterialType> implements MaterialTypeDao {

	@Override
	public boolean addMaterialType(MaterialType m) {
		// TODO Auto-generated method stub
		return save(m);
	}

	@Override
	public boolean deleteMaterialType(Long id) {
		// TODO Auto-generated method stub
		return delete(get(id));
	}

	@Override
	public MaterialType getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

	@Override
	public boolean updateMaterialType(MaterialType m) {
		// TODO Auto-generated method stub
		return update(m);
	}

	@Override
	public DataWrapper<List<MaterialType>> getMaterialTypeList(Integer pageIndex, Integer pageSize, MaterialType m) {
		 DataWrapper<List<MaterialType>> retDataWrapper = new DataWrapper<List<MaterialType>>();
	        List<MaterialType> ret = new ArrayList<MaterialType>();
	        Session session = getSession();
	        Criteria criteria = session.createCriteria(MaterialType.class);
	        if(m!=null){
	        	  if(m.getUserId()!=null){
	  	        	criteria.add(Restrictions.eq("userId", m.getUserId()));
	  	        }
	  	        if(m.getId()!=null){
	  	        	criteria.add(Restrictions.eq("id", m.getId()));
	  	        }
	        }
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
	public MaterialType getMaterialByName(String materialName) {
		List<MaterialType> ret = null;
		MaterialType ss= new MaterialType();
        Session session = getSession();
        Criteria criteria = session.createCriteria(MaterialType.class);
        criteria.add(Restrictions.eq("name", materialName));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (ret != null && ret.size() > 0) {
        	ss=ret.get(0);
		}
		return ss;
	}


}
