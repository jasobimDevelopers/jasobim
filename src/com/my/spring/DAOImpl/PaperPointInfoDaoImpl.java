package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.PaperPointInfoDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.BuildingPointNums;
import com.my.spring.model.PaperPointInfo;
import com.my.spring.model.PaperPointNumsLog;
import com.my.spring.model.UserLogMonth;
import com.my.spring.model.UserLogPojos;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
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

	@Override
	public DataWrapper<List<PaperPointNumsLog>> getCountPointNumsList(PaperPointNumsLog countLog,Long userId) {
		DataWrapper<List<PaperPointNumsLog>> dataWrapper=new DataWrapper<List<PaperPointNumsLog>>();
		List<PaperPointNumsLog> ret = new ArrayList<PaperPointNumsLog>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(PaperPointNumsLog.class);
        criteria.add(Restrictions.eq("projectId",countLog.getProjectId()));
        criteria.add(Restrictions.eq("userId",userId));
        criteria.addOrder(Order.desc("createDate"));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        dataWrapper.setData(ret);
		return dataWrapper;
	}

	@Override
	public PaperPointNumsLog findCountPointNumsList(PaperPointNumsLog countLog) {
		List<PaperPointNumsLog> ret = new ArrayList<PaperPointNumsLog>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(PaperPointNumsLog.class);
        criteria.add(Restrictions.eq("projectId",countLog.getProjectId()));
        if(countLog.getBuildingNames()!=null){
        	criteria.add(Restrictions.eq("buildingNames",countLog.getBuildingNames()));
        }else{
        	criteria.add(Restrictions.isNull("buildingNames"));
        }
        if(countLog.getCheckTypes()!=null){
        	criteria.add(Restrictions.eq("checkTypes",countLog.getCheckTypes()));
        }else{
        	criteria.add(Restrictions.isNull("checkTypes"));
        }
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(!ret.isEmpty()){
        	return ret.get(0);
        }
		return null;
	}

	@Override
	public List<BuildingPointNums> getCountAllPointNumsList(Long projectId) {
		List<BuildingPointNums> dataWrapper=new ArrayList<BuildingPointNums>();
		String sql = "select IFNULL(sum(measured_num),0) as nums from paper_of_measured where paper_id in"
		+"(select paper_of_measured_id from site_paper_relation where project_id="+projectId+")" 
		+" union all "
		+"select count(a.id) as nums from (select id from point_data_input_log where project_id="+projectId+" GROUP BY point_id) a"
		+" union all "
		+"select count(a.id) as nums from(select id from point_data_input_log where project_id="+projectId+" and status=1 GROUP BY point_id) a";
		Session session=getSession();
		 try{
			 Query query = session.createSQLQuery(sql)
					 .addScalar("nums", StandardBasicTypes.INTEGER)
					 .setResultTransformer(Transformers.aliasToBean(BuildingPointNums.class));
			 dataWrapper=query.list();
	        }catch(Exception e){
	            e.printStackTrace();
	        }
		return dataWrapper;
	}

	@Override
	public PaperPointInfo getById(Long pointId) {
		// TODO Auto-generated method stub
		return get(pointId);
	}

	@Override
	public List<BuildingPointNums> getCountPointNumsList(String buildingNames, String checkTypes, Long projectId) {
		// TODO Auto-generated method stub
		Session session = getSession();
		String sql="select IFNULL(sum(m.measured_num),0) as nums from (select b.paper_id,b.measured_num from site_paper_relation a,paper_of_measured b where a.paper_of_measured_id=b.paper_id";
		if(buildingNames!=null && !buildingNames.equals("")){
			sql=sql+" and a.bfm_id in("+buildingNames+") ";
		}
		sql=sql+" and a.project_id="+projectId+" and a.paper_of_measured_id in"
		+"(select paper_of_measured_id from paper_point_relation where project_id="+projectId;
		if(checkTypes!=null && !checkTypes.equals("")){
			sql=sql+" and check_type_id in("+checkTypes+")";
		}
		sql=sql+") GROUP BY b.paper_id) m union all select count(a.id) as nums from (select id from point_data_input_log where project_id="+projectId;
		if(checkTypes!=null && !checkTypes.equals("")){
			sql=sql+" and check_type_id in("+checkTypes+")";
		}
		if(buildingNames!=null && !buildingNames.equals("")){
			sql=sql+" and bfm_id in("+buildingNames+") ";
		}
		sql=sql+"GROUP BY point_id) a"				
				+" union all "
				+"select count(a.point_id) as nums from (select point_id from point_data_input_log where project_id="+projectId;
		if(buildingNames!=null && !buildingNames.equals("")){
			sql=sql+" and bfm_id in("+buildingNames+")";
		}
		if(checkTypes!=null && !checkTypes.equals("")){
			sql=sql+" and check_type_id in("+checkTypes+")";
		}
		sql=sql+" and status=1 GROUP BY point_id) a";
		Query query = session.createSQLQuery(sql)
		 .addScalar("nums", StandardBasicTypes.INTEGER)
		 .setResultTransformer(Transformers.aliasToBean(BuildingPointNums.class));
		return query.list();
	}

	@Override
	public List<BuildingPointNums> getCountPointNumsListGroup(String buildingNames, String checkTypes, Long projectId) {
		Session session = getSession();
		String sql = "select sum(c.nums) as nums,c.bid,d.b_name as bname,0 as state from (select b.measured_num as nums,a.bfm_id as bid from site_paper_relation a,paper_of_measured b where a.project_id="
		+projectId;
		if(buildingNames!=null && !buildingNames.equals("")){
			sql=sql+" and a.bfm_id in("+buildingNames+") ";
		}
		sql=sql+" and a.paper_of_measured_id in(select paper_of_measured_id from paper_point_relation where project_id="
		+projectId;
		if(checkTypes!=null && !checkTypes.equals("")){
			sql=sql+" and check_type_id in("+checkTypes+")";
		}
		sql=sql+" GROUP BY paper_of_measured_id) and a.paper_of_measured_id=b.paper_id) c,building_of_measured d where d.bfm_id=c.bid GROUP BY c.bid"
				+" union all "
				+"select count(a.id) as nums,a.bfm_id as bid,b.b_name as bname,1 as state from (select id,bfm_id from point_data_input_log where project_id="
				+projectId;
		if(checkTypes!=null && !checkTypes.equals("")){
			sql=sql+" and check_type_id in("+checkTypes+")";
		}
		if(buildingNames!=null && !buildingNames.equals("")){
			sql=sql+" and bfm_id in("+buildingNames+") ";
		}
		sql=sql+ " GROUP BY point_id) a,building_of_measured b where a.bfm_id = b.bfm_id GROUP BY a.bfm_id"
				+" union all "
				+" select count(a.point_id) as nums,a.bfm_id as bid,b.b_name as bname,2 as state from (select point_id,bfm_id from point_data_input_log where project_id="
				+ projectId;
		if(checkTypes!=null && !checkTypes.equals("")){
			sql=sql+" and check_type_id in("+checkTypes+")";
		}
		if(buildingNames!=null && !buildingNames.equals("")){
			sql=sql+" and bfm_id in("+buildingNames+") ";
		}		
		sql=sql+" and status=1 GROUP BY point_id) a,building_of_measured b where a.bfm_id = b.bfm_id GROUP BY a.bfm_id";
		Query query = session.createSQLQuery(sql)
				 .addScalar("nums", StandardBasicTypes.INTEGER)
				 .addScalar("bid", StandardBasicTypes.LONG)
				 .addScalar("bname", StandardBasicTypes.STRING)
				 .addScalar("state", StandardBasicTypes.INTEGER)
				 .setResultTransformer(Transformers.aliasToBean(BuildingPointNums.class));
		return query.list();
	}

	@Override
	public List<BuildingPointNums> getCountPointNumsListGroupBySite(Long bfmId, String checkTypes, Long projectId) {
		Session session = getSession();
		String sql = "select sum(c.nums) as nums,c.bid,d.site_name as bname,0 as state from (select b.measured_num as nums,a.site_id as bid from site_paper_relation a,paper_of_measured b where a.project_id="
		+projectId;
		if(bfmId!=null && !bfmId.equals("")){
			sql=sql+" and a.bfm_id="+bfmId;
		}
		sql=sql+" and a.paper_of_measured_id in(select paper_of_measured_id from paper_point_relation where project_id="
		+projectId;
		if(checkTypes!=null && !checkTypes.equals("")){
			sql=sql+" and check_type_id in("+checkTypes+")";
		}
		sql=sql+")and a.paper_of_measured_id=b.paper_id) c,measured_site d where d.site_id=c.bid GROUP BY c.bid"
				+" union all "
				+"select count(a.id) as nums,a.measured_site_id as bid,b.site_name as bname,1 as state from (select id,measured_site_id from point_data_input_log where project_id="
				+projectId;
		if(checkTypes!=null && !checkTypes.equals("")){
			sql=sql+" and check_type_id in("+checkTypes+")";
		}
		if(bfmId!=null && !bfmId.equals("")){
			sql=sql+" and bfm_id="+bfmId;
		}
		sql=sql+ " GROUP BY point_id) a,measured_site b where a.measured_site_id = b.site_id GROUP BY a.measured_site_id"
				+" union all "
				+" select count(a.point_id) as nums,a.measured_site_id as bid,b.site_name as bname,2 as state from (select point_id,measured_site_id from point_data_input_log where project_id="
				+ projectId;
		if(checkTypes!=null && !checkTypes.equals("")){
			sql=sql+" and check_type_id in("+checkTypes+")";
		}
		if(bfmId!=null && !bfmId.equals("")){
			sql=sql+" and bfm_id ="+bfmId;
		}		
		sql=sql+" and status=1 GROUP BY point_id) a,measured_site b where a.measured_site_id = b.site_id GROUP BY a.measured_site_id";
		Query query = session.createSQLQuery(sql)
				 .addScalar("nums", StandardBasicTypes.INTEGER)
				 .addScalar("bid", StandardBasicTypes.LONG)
				 .addScalar("bname", StandardBasicTypes.STRING)
				 .addScalar("state", StandardBasicTypes.INTEGER)
				 .setResultTransformer(Transformers.aliasToBean(BuildingPointNums.class));
		return query.list();
	}

	@Override
	public List<PaperPointInfo> getPaperPointInfoBySiteId(Long siteId, String checkTypes, Long projectId) {
		Session session = getSession();
		String sql = "select point_id as pointId,project_id as projectId,paper_of_measured_id as paperOfMeasuredId,"
				+"abscissa,ordinate,proportion,paper_length as paperLength,paper_width as paperWidth,create_date as createDate,"
				+"create_user as createUser,tag,status as status"
				+" from paper_point_info where paper_of_measured_id in(select paper_of_measured_id from measured_site where site_id="+siteId+" and project_id="+projectId+")";
		Query query = session.createSQLQuery(sql)
				 .addScalar("pointId", StandardBasicTypes.LONG)
				 .addScalar("projectId", StandardBasicTypes.LONG)
				 .addScalar("paperOfMeasuredId", StandardBasicTypes.LONG)
				 .addScalar("abscissa", StandardBasicTypes.INTEGER)
				 .addScalar("ordinate", StandardBasicTypes.INTEGER)
				 .addScalar("proportion", StandardBasicTypes.DOUBLE)
				 .addScalar("paperLength", StandardBasicTypes.DOUBLE)
				 .addScalar("paperWidth", StandardBasicTypes.DOUBLE)
				 .addScalar("createUser", StandardBasicTypes.LONG)
				 .addScalar("createDate", StandardBasicTypes.TIMESTAMP)
				 .addScalar("tag", StandardBasicTypes.INTEGER)
				 .addScalar("status", StandardBasicTypes.INTEGER)
				 .setResultTransformer(Transformers.aliasToBean(PaperPointInfo.class));
		return query.list();
	}

	@Override
	public boolean addPaperPointInfoList(List<PaperPointInfo> ppi) {
		// TODO Auto-generated method stub
		return saveList(ppi);
	}

}
