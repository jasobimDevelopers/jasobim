package com.my.spring.DAOImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.MaterialFileDao;
import com.my.spring.model.Material;
import com.my.spring.model.MaterialFile;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;
@Repository
public class MaterialFileDaoImpl extends BaseDao<MaterialFile> implements MaterialFileDao {

	@Override
	public boolean addMaterialFile(MaterialFile mf) {
		// TODO Auto-generated method stub
		return save(mf);
	}

	@Override
	public boolean deleteMaterialFile(Long id) {
		// TODO Auto-generated method stub
		return delete(get(id));
	}

	@Override
	public DataWrapper<List<MaterialFile>> getMaterialFileList(MaterialFile mf, Integer pageSize, Integer pageIndex) {
		// TODO Auto-generated method stub
		DataWrapper<List<MaterialFile>> result = new DataWrapper<List<MaterialFile>>(); 
		List<MaterialFile> mfs = new ArrayList<MaterialFile>();
		Session session = getSession();
	    Criteria criteria = session.createCriteria(MaterialFile.class); 
	    if(mf.getUserId()!=null){
	    	criteria.add(Restrictions.eq("userId", mf.getUserId()));
	    }
	    if(mf.getProjectId()!=null){
	    	criteria.add(Restrictions.eq("projectId", mf.getProjectId()));
	    }
	    if (pageSize == null) {
			pageSize = 10;
		}
        if (pageIndex == null) {
			
		}
        pageIndex = -1;
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
        	mfs = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        result.setData(mfs);
        result.setTotalNumber(totalItemNum);
        result.setCurrentPage(pageIndex);
        result.setTotalPage(totalPageNum);
        result.setNumberPerPage(pageSize);
		return result;
	}

}
