package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.ProjectDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Project;
import com.my.spring.model.UserProject;
import com.my.spring.model.Projectvs;
import com.my.spring.model.User;
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
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProjectDaoImpl extends BaseDao<Project> implements ProjectDao {

    @Override
    public boolean addProject(Project project) {
        return save(project);
    }

    @Override
    public boolean deleteProject(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updateProject(Project project) {
        return update(project);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<Project>> getProjectList(Integer pageSize, Integer pageIndex, Project project,String content,Integer isIos,List<UserProject> up) {
        DataWrapper<List<Project>> retDataWrapper = new DataWrapper<List<Project>>();
        List<Project> ret = new ArrayList<Project>();
        Session session = getSession();  
        Criteria criteria = session.createCriteria(Project.class);
        criteria.addOrder(Order.desc("updateDate"));
        Long projectId=(long) 267;
        Long projectId2=(long) 268;
        long projectId3=(long) 270;
        long projectId4=(long) 271;
        if(isIos!=2 && isIos!=null && isIos!=-1){
        	criteria.add(Restrictions.ne("id",projectId));
        	criteria.add(Restrictions.ne("id", projectId2));
        	criteria.add(Restrictions.ne("id", projectId3));
        	criteria.add(Restrictions.ne("id", projectId4));
        }
        if(up!=null){
        	if(!up.isEmpty()){
        		Disjunction dj= Restrictions.disjunction();
        		for(UserProject s:up){
        			dj.add(Restrictions.eq("id", s.getProjectId()));
        		}
        		criteria.add(dj);
        	}
        }
        ////////
        if(content!=null){
        	 Disjunction diss = Restrictions.disjunction();
        	 diss.add(Restrictions.like("name", content, MatchMode.ANYWHERE));
        	 diss.add(Restrictions.like("leader", content,MatchMode.ANYWHERE));
        	 criteria.add(diss);
        }
        
        /////////
        if(project.getName() != null && !project.getName().equals("")) {
        	criteria.add(Restrictions.like("name", "%" + project.getName() + "%"));
        }
        
        if(project.getNum() != null && !project.getNum().equals("")) {
        	criteria.add(Restrictions.like("num", "%" + project.getNum() + "%"));
        }
        if(project.getConstructionUnit() != null && !project.getConstructionUnit().equals("")) {
        	criteria.add(Restrictions.like("constructionUnit", "%" + project.getConstructionUnit() + "%"));
        }
        if(project.getLeader() != null && !project.getLeader().equals("")) {
        	criteria.add(Restrictions.like("leader", "%" + project.getLeader() + "%"));
        }
        if(project.getBuildingUnit() != null && !project.getBuildingUnit().equals("")) {
        	criteria.add(Restrictions.like("buildingUnit", "%" + project.getBuildingUnit() + "%"));
        }
        
        if(project.getPlace() != null && !project.getPlace().equals("")) {
        	criteria.add(Restrictions.like("place", "%" + project.getPlace() + "%"));
        }
        if(project.getDescription() != null && !project.getDescription().equals("")) {
        	criteria.add(Restrictions.like("description", "%" + project.getDescription() + "%"));
        }
        if(project.getDesignUnit() != null && !project.getDesignUnit().equals("")) {
        	criteria.add(Restrictions.like("designUnit", "%" + project.getDesignUnit() + "%"));
        }
        if(project.getVersion() !=null && !project.getVersion().equals("")){
        	criteria.add(Restrictions.like("version", "%" + project.getVersion() + "%"));
        }
        if(project.getStartDate() !=null && !project.getStartDate().equals("")){
        	criteria.add(Restrictions.like("startDate", "%" + project.getStartDate() + "%"));
        }
        if(project.getPhase() !=null && !project.getPhase().equals("")){
        	criteria.add(Restrictions.like("phase", "%" + project.getPhase() + "%"));
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
	public Project getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<Project> findProjectLike(Project project) {
		
			List<Project> ret = null;
			DataWrapper<Project> projects=new DataWrapper<Project>();
	        Session session = getSession();
	        Criteria criteria = session.createCriteria(Project.class);
	        if(project.getName()!=null){
	        	criteria.add(Restrictions.eq("name",project.getName()));
	        }
	        if(project.getNum()!=null){
	        	criteria.add(Restrictions.eq("num", project.getNum()));
	        }
	        if(project.getConstructionUnit()!=null){
	        	criteria.add(Restrictions.eq("constructionUnit", project.getConstructionUnit()));
	        }
	        if(project.getLeader()!=null){
	        	criteria.add(Restrictions.eq("leader", project.getLeader()));
	        }
	        if(project.getBuildingUnit()!=null){
	        	criteria.add(Restrictions.eq("buildingUnit", project.getBuildingUnit()));
	        }
	        if(project.getPicId()!=null){
	        	criteria.add(Restrictions.eq("picId", project.getPicId()));
	        }
	        if(project.getModelId()!=null){
	        	criteria.add(Restrictions.eq("modelId", project.getModelId()));
	        }
	        if(project.getPlace()!=null){
	        	criteria.add(Restrictions.eq("place", project.getPlace()));
	        }
	        if(project.getDescription()!=null){
	        	criteria.add(Restrictions.eq("description", project.getDescription()));
	        }
	        if(project.getDesignUnit()!=null){
	        	criteria.add(Restrictions.eq("designUnit", project.getDesignUnit()));
	        }
	        if(project.getVersion()!=null){
	        	criteria.add(Restrictions.eq("version", project.getVersion()));
	        }
	        if(project.getStartDate()!=null){
	        	criteria.add(Restrictions.eq("startDate", project.getStartDate()));
	        }
	        if(project.getPhase()!=null){
	        	 criteria.add(Restrictions.eq("phase", project.getPhase()));
	        }
	        try {
	            ret = criteria.list();
	        }catch (Exception e){
	            e.printStackTrace();
	        }
	        if (ret != null && ret.size() > 0) {
	        	projects.setData(ret.get(0));;
			}else{
				projects.setErrorCode(ErrorCodeEnum.Error);
			}
			return projects;
		
	}

	@Override
	public DataWrapper<List<Projectvs>> getProjectList(Integer pageSize, Integer pageIndex, Project project,
			User userInMemory) {
		DataWrapper<List<Projectvs>> retDataWrapper = new DataWrapper<List<Projectvs>>();
		String sql = "select a.* from project a,user_project b where a.id=b.project_id and b.user_id="+userInMemory.getId();
		Session session=getSession();
	    try{
		    Query query = session.createSQLQuery(sql)
				 .addScalar("id",StandardBasicTypes.LONG)
				 .addScalar("name", StandardBasicTypes.STRING)
				 .addScalar("num",StandardBasicTypes.STRING)
				 .addScalar("construction_unit",StandardBasicTypes.STRING)
				 .addScalar("construction_unit_user",StandardBasicTypes.STRING)
				 .addScalar("leader", StandardBasicTypes.STRING)
				 .addScalar("building_unit", StandardBasicTypes.STRING)
				 .addScalar("pic_id", StandardBasicTypes.STRING)
				 .addScalar("model_id", StandardBasicTypes.STRING)
				 .addScalar("place", StandardBasicTypes.STRING)
				 .addScalar("description", StandardBasicTypes.STRING)
				 .addScalar("version", StandardBasicTypes.STRING)
				 .addScalar("start_date", StandardBasicTypes.STRING)
				 .addScalar("finished_date", StandardBasicTypes.STRING)
				 .addScalar("state", StandardBasicTypes.STRING)
				 .addScalar("is_ios", StandardBasicTypes.STRING)
				 .addScalar("phase", StandardBasicTypes.STRING)
				 .addScalar("model_part",StandardBasicTypes.STRING)
				 .addScalar("team_list",StandardBasicTypes.STRING)
				 .addScalar("team_id",StandardBasicTypes.STRING)
				 .addScalar("design_unit",StandardBasicTypes.STRING)
				 .addScalar("design_unit_user",StandardBasicTypes.STRING)
				 .addScalar("construction_control_unit",StandardBasicTypes.STRING)
				 .addScalar("construction_control_user",StandardBasicTypes.STRING)
				 .addScalar("short_name", StandardBasicTypes.STRING)
				 .addScalar("update_date",StandardBasicTypes.DATE)
				 .addScalar("create_date",StandardBasicTypes.DATE)
				 .setResultTransformer(Transformers.aliasToBean(Projectvs.class)); 
		    retDataWrapper.setData(query.list());
            
        }catch(Exception e){
        	retDataWrapper.setErrorCode(ErrorCodeEnum.Error);
            e.printStackTrace();
            //dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
        }
	    return retDataWrapper;
	}

	@Override
	public List<Project> getAllProjects() {
		List<Project> ret = null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(Project.class);
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
		return ret;
	
	}

}
