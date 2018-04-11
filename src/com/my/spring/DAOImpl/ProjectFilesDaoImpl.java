package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.ProjectFilesDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.ProjectFiles;
import com.my.spring.model.User;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
@Repository
public class ProjectFilesDaoImpl extends BaseDao<ProjectFiles> implements ProjectFilesDao {

	@Override
	public ProjectFiles getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

	@Override
	public DataWrapper<ProjectFiles> getByName(String name) {
		DataWrapper<ProjectFiles> dataWrapper = new DataWrapper<ProjectFiles>();
		String sql="select * from project_files where name="+name;
		Session session=getSession();
		 try{
	            Query query=session.createSQLQuery(sql).addEntity(ProjectFiles.class);
	            List<ProjectFiles> test=query.list();
	            dataWrapper.setData(test.get(0));
	        }catch(Exception e){
	            e.printStackTrace();
	            dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
	        }
		 
		return dataWrapper;
	}

	@Override
	public boolean deleteProjectFiles(Long id) {
		// TODO Auto-generated method stub
		return delete(get(id));
	}

	@Override
	public boolean addProjectFiles(ProjectFiles file) {
		// TODO Auto-generated method stub
		return save(file);
	}

	

	@Override
	public DataWrapper<List<ProjectFiles>> getProjectFilesList(User user, Integer pageSize, Integer pageIndex,
			ProjectFiles projectFiles) {
		DataWrapper<List<ProjectFiles>> retDataWrapper = new DataWrapper<List<ProjectFiles>>();
        List<ProjectFiles> ret = new ArrayList<ProjectFiles>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(ProjectFiles.class);
        ///////////////////////////////
        criteria.addOrder(Order.desc("id"));
        if(projectFiles.getProjectId()!=null){
        	criteria.add(Restrictions.eq("projectId", projectFiles.getProjectId()));
        }
        if(projectFiles.getTest()!=null){
        	criteria.add(Restrictions.like("test", projectFiles.getTest()));
        }
        if(projectFiles.getTypeName()!=null){
        	criteria.add(Restrictions.eq("typeName", projectFiles.getTypeName()));
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

   
}
