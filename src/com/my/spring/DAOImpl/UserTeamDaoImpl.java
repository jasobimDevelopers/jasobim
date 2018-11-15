package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.UserTeamDao;
import com.my.spring.model.RoleIndex;
import com.my.spring.model.UserTeam;
import com.my.spring.model.UserTeamIndex;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserTeamDaoImpl extends BaseDao<UserTeam> implements UserTeamDao {

    @Override
    public boolean addUserTeam(UserTeam Item) {
        return save(Item);
    }

    @Override
    public boolean deleteUserTeam(Long id) {
        return delete(get(id));
    }


	@Override
	public UserTeam getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

	@Override
	public DataWrapper<List<UserTeam>> getUserTeamList(Integer pageSize,Integer pageIndex,UserTeam userTeam) {
		DataWrapper<List<UserTeam>> retDataWrapper = new DataWrapper<List<UserTeam>>();
        List<UserTeam> ret = new ArrayList<UserTeam>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(UserTeam.class);
        if(userTeam.getProjectId()!=null){
        	criteria.add(Restrictions.eq("projectId", userTeam.getProjectId()));
        }
        if (pageSize == null) {
			pageSize = 1000;
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
	public boolean deleteUserTeamList(String[] ids) {
		return deleteList(ids);
	}

	@Override
	public boolean updateUserTeamList(UserTeam userTeam) {
		// TODO Auto-generated method stub
		return update(userTeam);
	}

	@Override
	public boolean deleteUserTeamByUserId(Long id) {
		String sql = "delete from user_team where user_id="+id;
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
	public boolean addUserTeamList(List<UserTeam> newList2) {
		// TODO Auto-generated method stub
		return saveList(newList2);
	}

	@Override
	public List<UserTeamIndex> getUserTeamListByUserId(Long id) {
		List<UserTeamIndex> gets=new ArrayList<UserTeamIndex>();
		String sql = "select b.name,b.id,b.team_user_name as teamUserName,b.project_id as projectId,a.indexs,b.create_date as createDate,b.create_user as createUser from user_index a,user_team b where a.about_type=2 and a.about_id=b.id and a.user_id="
		+id+" ORDER BY a.indexs asc";
		Session session=getSession();
		try{
			 Query query = session.createSQLQuery(sql)
					 .addScalar("id", StandardBasicTypes.LONG)
					 .addScalar("name", StandardBasicTypes.STRING)
					 .addScalar("indexs", StandardBasicTypes.LONG)
					 .addScalar("teamUserName", StandardBasicTypes.STRING)
					 .addScalar("projectId", StandardBasicTypes.LONG)
					 .addScalar("createDate",StandardBasicTypes.TIMESTAMP)
					 .addScalar("createUser", StandardBasicTypes.LONG)
					 .setResultTransformer(Transformers.aliasToBean(UserTeamIndex.class));
			 	gets=query.list();
	        }catch(Exception e){
	            e.printStackTrace();
	    }
		return gets;
	}
}
