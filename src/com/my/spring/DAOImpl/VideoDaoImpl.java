package com.my.spring.DAOImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.VideoDao;
import com.my.spring.model.Video;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;


@Repository
public class VideoDaoImpl extends BaseDao<Video> implements VideoDao {

	@SuppressWarnings("unchecked")
	@Override
	public Video getByVideoName(String VideoName) {
		// TODO Auto-generated method stub
		List<Video> ret = null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(Video.class);
        criteria.add(Restrictions.eq("VideoName",VideoName));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (ret != null && ret.size() > 0) {
			return ret.get(0);
		}
		return null;
	}

	@Override
	public boolean addVideo(Video Video) {
		// TODO Auto-generated method stub
		return save(Video);
	}

	@Override
	public DataWrapper<Video> getById(Long id) {
		DataWrapper<Video> dataWrapper=new DataWrapper<Video>();
		dataWrapper.setData(get(id));
		return dataWrapper;
	}

	@Override
	public boolean updateVideo(Video Video) {
		// TODO Auto-generated method stub
		return update(Video);
	}

	@Override
	public boolean deleteVideo(Long id) {
		return delete(get(id));
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<Video>> getVideoList(Long projectId, Integer pageIndex, Integer pageSize, Video video) {
		DataWrapper<List<Video>> retDataWrapper = new DataWrapper<List<Video>>();
        List<Video> ret = new ArrayList<Video>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Video.class);
        //criteria.addOrder(Order.desc("publishDate"));
        if(video.getProfessionType()!=null){
        	criteria.add(Restrictions.eq("professionType", video.getProfessionType()));
        }
        if(video.getBuildingNum()!=null){
        	criteria.add(Restrictions.eq("buildingNum", video.getBuildingNum()));
        }
        criteria.add(Restrictions.eq("projectId", projectId));
        
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
	public boolean getByProjectId(Long projectId) {
		List<Video> ret = null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(Video.class);
        criteria.add(Restrictions.eq("projectId",projectId));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (ret != null && ret.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteVideoByProjectId(Long id) {
		String sql = "delete from video where project_id="+id;
		Session session=getSession();
		boolean temp=false;
		 try{
			 Query query = session.createSQLQuery(sql);
			 int te=query.executeUpdate();
			 if(te==1){
				 temp= true;
			 }
			 
	        }catch(Exception e){
	            e.printStackTrace();
	        }
		 
		return temp;
	}

}
