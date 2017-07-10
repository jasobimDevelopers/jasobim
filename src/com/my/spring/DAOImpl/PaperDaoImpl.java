package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.PaperDao;
import com.my.spring.model.Paper;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PaperDaoImpl extends BaseDao<Paper> implements PaperDao {

    @Override
    public boolean addPaper(Paper paper) {
        return save(paper);
    }

    @Override
    public boolean deletePaper(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updatePaper(Paper paper) {
        return update(paper);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<Paper>> getPaperList(Long projectId,Integer pageSize, Integer pageIndex,Paper paper,String content) {
    	DataWrapper<List<Paper>> retDataWrapper = new DataWrapper<List<Paper>>();
        List<Paper> ret = new ArrayList<Paper>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Paper.class);
        ///////////////////////////////
        criteria.addOrder(Order.desc("id"));
        if(projectId!=null){
        	criteria.add(Restrictions.eq("projectId", projectId));
        }
        if(content!=null){
        	String test = "";
        	for(int i=0;i<content.length();i++){
        		String ss=String.valueOf(content.charAt(i));
        		if(i==0){
        			test=test+'%'+ss+'%';
        		}else{
        			test=test+ss+'%';
        		}
        		
        	}
        	Disjunction dis = Restrictions.disjunction();        	
        	dis.add(Restrictions.like("originName", test,MatchMode.ANYWHERE));
        	criteria.add(dis);
        }
        if(paper.getProfessionType()!=null){
        	criteria.add(Restrictions.eq("professionType", paper.getProfessionType()));
        }
        if(paper.getBuildingNum() !=null){
        	criteria.add(Restrictions.eq("buildingNum",paper.getBuildingNum()));
        }
        if(paper.getFloorNum() !=null){
        	criteria.add(Restrictions.eq("floorNum", paper.getFloorNum()));
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

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<Paper>> getPaperLists(String projectId,Integer pageSize, Integer pageIndex,Paper paper) {
    	DataWrapper<List<Paper>> retDataWrapper = new DataWrapper<List<Paper>>();
        List<Paper> ret = new ArrayList<Paper>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Paper.class);
        ///////////////////////////////
        if(projectId!=null){
        	Disjunction dis = Restrictions.disjunction();
        	String[] testList=projectId.split(",");
        	if(testList.length>0){
        		for(int i=0;i<testList.length;i++){
        			dis.add(Restrictions.eq("projectId",Long.valueOf(testList[i])));
        		}
        	}
        	criteria.add(dis);
        }
        if(paper!=null){
        	criteria.add(Restrictions.eq("professionType", paper.getProfessionType()));
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
	public Paper getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

	@Override
	public boolean deletePaperByProjectId(Long id) {
		String sql = "delete from paper where project_id="+id;
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
