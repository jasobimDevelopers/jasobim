package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.MaterialImportLogDao;
import com.my.spring.model.MaterialImportLog;
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
public class MaterialImportLogDaoImpl extends BaseDao<MaterialImportLog> implements MaterialImportLogDao {

    @Override
    public boolean addMaterialImportLog(MaterialImportLog MaterialImportLog) {
        return save(MaterialImportLog);
    }

    @Override
    public boolean deleteMaterialImportLog(Long id) {
        return delete(get(id));
    }

   

	@Override
    public DataWrapper<List<MaterialImportLog>> getMaterialImportLogList(Long projectId, Integer pageSize, Integer pageIndex) {
        DataWrapper<List<MaterialImportLog>> retDataWrapper = new DataWrapper<List<MaterialImportLog>>();
        List<MaterialImportLog> ret = new ArrayList<MaterialImportLog>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(MaterialImportLog.class);
        ///////////////////////////////
        criteria.add(Restrictions.eq("projectId", projectId));
        
        /////////////////////////////////////
   
        if (pageSize == null) {
			pageSize = 1000;
		}
        if (pageIndex == null) {
			pageIndex = 1;
		}
        
        // 取总页数
        
        criteria.setProjection(Projections.rowCount());
        
        int totalMaterialImportLogNum = ((Long)criteria.uniqueResult()).intValue();
        int totalPageNum = DaoUtil.getTotalPageNumber(totalMaterialImportLogNum, pageSize);

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
        retDataWrapper.setTotalNumber(totalMaterialImportLogNum);
        retDataWrapper.setCurrentPage(pageIndex);
        retDataWrapper.setTotalPage(totalPageNum);
        retDataWrapper.setNumberPerPage(pageSize);
        return retDataWrapper;
    }

	@Override
	public boolean deleteMaterialImportLogByPorjectId(Long projectId) {
		// TODO Auto-generated method stub
		return false;
	}

	

   
}
