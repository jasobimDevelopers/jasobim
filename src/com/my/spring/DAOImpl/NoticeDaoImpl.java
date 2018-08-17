package com.my.spring.DAOImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.NoticeDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Notice;
import com.my.spring.model.NoticePojo;
import com.my.spring.model.Paper;
import com.my.spring.model.Projectvs;
import com.my.spring.model.UserProject;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;
@Repository
public class NoticeDaoImpl extends BaseDao<Notice> implements NoticeDao {

	@Override
	public boolean addNotice(Notice notice) {
		// TODO Auto-generated method stub
		return save(notice);
	}

	@Override
	public boolean deleteNotice(Long id) {
		// TODO Auto-generated method stub
		return delete(get(id));
	}

	@Override
	public boolean addNoticeList(List<Notice> notice) {
		// TODO Auto-generated method stub
		return saveList(notice);
	}

	@Override
	public Notice getByAdoutIdAndUserId(Long userId, Long questionId, int noticeType) {
		List<Notice> notice= null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(Notice.class);
        criteria.add(Restrictions.eq("userId", userId));
        criteria.add(Restrictions.eq("aboutId", questionId));
        criteria.add(Restrictions.eq("noticeType",noticeType));
        criteria.addOrder(Order.desc("createDate"));
        try {
        	notice = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(notice.size()==0){
        	return null;
        }else{
        	return notice.get(0);
        }
        
	}

	@Override
	public boolean updateNotice(Notice notice) {
		// TODO Auto-generated method stub
		return update(notice);
	}

	@Override
	public DataWrapper<List<Notice>> getListByUserId(Integer pageSize, Integer pageIndex, Long id,List<UserProject> ups) {
		DataWrapper<List<Notice>> get = new DataWrapper<List<Notice>>();
        List<Notice> ret = new ArrayList<Notice>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Notice.class);
        ///////////////////////////////
        criteria.addOrder(Order.desc("createDate"));
        criteria.add(Restrictions.eq("userId", id));
        if(!ups.isEmpty()){
        	Disjunction disjunction = Restrictions.disjunction();
        	for(UserProject up:ups){
        		disjunction.add(Restrictions.eq("projectId", up.getProjectId()));
        	}
        	criteria.add(disjunction);
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
        get.setData(ret);
        get.setTotalNumber(totalItemNum);
        get.setCurrentPage(pageIndex);
        get.setTotalPage(totalPageNum);
        get.setNumberPerPage(pageSize);
        return get;
	}

	@Override
	public DataWrapper<List<NoticePojo>> getListByProjectId(Integer pageSize, Integer pageIndex,List<UserProject> projectList) {
		/*DataWrapper<List<Notice>> get = new DataWrapper<List<Notice>>();
        List<Notice> ret = new ArrayList<Notice>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Notice.class);
        ///////////////////////////////
        if(projectList!=null){
        	if(!projectList.isEmpty()){
            	Disjunction dis = Restrictions.disjunction();
            	for(UserProject up:projectList){
            		dis.add(Restrictions.eq("projectId",up.getProjectId()));
            	}
            	criteria .add(dis);
            }
        }
        ProjectionList projectionList = Projections.projectionList(); 
		projectionList.add(Projections.groupProperty("aboutId"));
		projectionList.add(Projections.groupProperty("noticeType"));
		criteria.setProjection(projectionList);
		criteria.addOrder(Order.desc("createDate"));
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
        get.setData(ret);
        get.setTotalNumber(totalItemNum);
        get.setCurrentPage(pageIndex);
        get.setTotalPage(totalPageNum);
        get.setNumberPerPage(pageSize);
        return get;*/
		if(pageIndex==null){
			pageIndex=1;
		}
		if(pageSize==null){
			pageSize=10;
		}
		
		DataWrapper<List<NoticePojo>> retDataWrapper = new DataWrapper<List<NoticePojo>>();
		String sqlor="";
		String sql="";
	    if(projectList!=null){
        	if(!projectList.isEmpty()){
            	for(UserProject up:projectList){
            		if(sqlor.equals("")){
            			sqlor=" project_id="+up.getProjectId();
            		}else{
            			sqlor=sqlor+" or project_id="+up.getProjectId();
            		}
            	}
            }
        }
	    if(sqlor.equals("")){
	    	sql = "select * from notice GROUP BY notice_type,about_id ORDER BY create_date DESC";
	    }else{
	    	sql = "select * from notice where"+sqlor+" GROUP BY notice_type,about_id ORDER BY create_date DESC";
	    }
	    if(pageIndex!=-1){
			sql = sql +" limit "+(pageSize*pageIndex-pageSize)+","+pageSize;
		}
		
		Session session=getSession();
	    try{
		    Query query = session.createSQLQuery(sql)
				 .addScalar("id",StandardBasicTypes.LONG)
				 .addScalar("user_id", StandardBasicTypes.LONG)
				 .addScalar("project_id",StandardBasicTypes.LONG)
				 .addScalar("about_id",StandardBasicTypes.LONG)
				 .addScalar("notice_type",StandardBasicTypes.INTEGER)
				 .addScalar("read_state", StandardBasicTypes.INTEGER)
				 .addScalar("create_date", StandardBasicTypes.TIMESTAMP)
				 .addScalar("update_date", StandardBasicTypes.TIMESTAMP)
				 .addScalar("remark", StandardBasicTypes.STRING)
				 .setResultTransformer(Transformers.aliasToBean(NoticePojo.class)); 
		    retDataWrapper.setData(query.list());
        }catch(Exception e){
        	retDataWrapper.setErrorCode(ErrorCodeEnum.Error);
            e.printStackTrace();
            //dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
        }
	    return retDataWrapper;
	}

	@Override
	public Integer getListNotRead(Long userId) {
		Session session=getSession();
		Query query=session.createSQLQuery("select count(*) from notice where user_id=" + userId+" and read_state=0");
		return  Integer.parseInt(query.list().get(0).toString());
	}


}
