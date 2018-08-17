package com.my.spring.DAOImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.MenuListCopy;
import com.my.spring.model.Projectvs;
import com.my.spring.model.User;
import com.my.spring.model.UserCopy;
import com.my.spring.model.UserId;
import com.my.spring.model.UserSelect;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.MD5Util;


@Repository
public class UserDaoImpl extends BaseDao<User> implements UserDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getByUserNames(String userName) {
		// TODO Auto-generated method stub
		List<User> ret = null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.like("userName",'%'+userName+'%'));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (ret != null && ret.size() > 0) {
			return ret;
		}
		return null;
	}
	@Override
	public User getByUserRealName(String userName) {
		// TODO Auto-generated method stub
		List<User> ret = null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.eq("realName",userName));
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
	public User getByUserName(String userName) {
		// TODO Auto-generated method stub
		List<User> ret = null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.eq("userName",userName));
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
	public User getByUserTel(String mobile) {
		// TODO Auto-generated method stub
		List<User> ret = null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.eq("tel",mobile));
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
	public boolean addUser(User user) {
		// TODO Auto-generated method stub
		return save(user);
	}

	@Override
	public User getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

	@Override
	public boolean updateUser(User user) {
		// TODO Auto-generated method stub
		return update(user);
	}
	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<User>> getUserListByAdmin(Integer pageSize, Integer pageIndex,User user) {
		// TODO Auto-generated method stub
		DataWrapper<List<User>> dataWrapper = new DataWrapper<List<User>>();
        List<User> ret = null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(User.class);
        if(user.getUserName() != null && !user.getUserName().equals("")) {
        	criteria.add(Restrictions.like("userName", "%" + user.getUserName() + "%"));
        }
        
        if(user.getRealName() != null && !user.getRealName().equals("")) {
        	criteria.add(Restrictions.like("realName", "%" + user.getRealName() + "%"));
        }
        if(user.getEmail() != null && !user.getEmail().equals("")) {
        	criteria.add(Restrictions.like("email", "%" + user.getEmail() + "%"));
        }
        if(user.getTel() != null && !user.getTel().equals("")) {
        	criteria.add(Restrictions.like("tel", "%" + user.getTel() + "%"));
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
        dataWrapper.setData(ret);
        dataWrapper.setTotalNumber(totalItemNum);
        dataWrapper.setCurrentPage(pageIndex);
        dataWrapper.setTotalPage(totalPageNum);
        dataWrapper.setNumberPerPage(pageSize);

        return dataWrapper;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<User>> getUserList(Integer pageSize, Integer pageIndex,User user) {
		// TODO Auto-generated method stub
		DataWrapper<List<User>> dataWrapper = new DataWrapper<List<User>>();
        List<User> ret = null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(User.class);
    	if(user.getUserName() != null && !user.getUserName().equals("")) {
        	criteria.add(Restrictions.like("userName", "%" + user.getUserName() + "%"));
        }
        
        if(user.getRealName() != null && !user.getRealName().equals("")) {
        	criteria.add(Restrictions.like("realName", "%" + user.getRealName() + "%"));
        }
        if(user.getEmail() != null && !user.getEmail().equals("")) {
        	criteria.add(Restrictions.like("email", "%" + user.getEmail() + "%"));
        }
        if(user.getTel() != null && !user.getTel().equals("")) {
        	criteria.add(Restrictions.like("tel", "%" + user.getTel() + "%"));
        }
        if(user.getWorkName()!=null){
        	criteria.add(Restrictions.like("workName", user.getWorkName()));
        } 
        if(user.getTeamId()!=null){
        	criteria.add(Restrictions.eq("teamId", user.getTeamId()));
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
        dataWrapper.setData(ret);
        dataWrapper.setTotalNumber(totalItemNum);
        dataWrapper.setCurrentPage(pageIndex);
        dataWrapper.setTotalPage(totalPageNum);
        dataWrapper.setNumberPerPage(pageSize);

        return dataWrapper;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<User>> getUserListForSql(Integer pageSize, Integer pageIndex,User user) {
		// TODO Auto-generated method stub
		DataWrapper<List<User>> dataWrapper = new DataWrapper<List<User>>();
        List<User> ret = null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.like("projectList", "%,%"));
        criteria.add(Restrictions.eq("userType", 3));
        if (pageSize == null) {
			pageSize = 10;
		}
        if (pageIndex == null) {
			pageIndex = -1;
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
        dataWrapper.setData(ret);
        dataWrapper.setTotalNumber(totalItemNum);
        dataWrapper.setCurrentPage(pageIndex);
        dataWrapper.setTotalPage(totalPageNum);
        dataWrapper.setNumberPerPage(pageSize);

        return dataWrapper;
	}
	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<User>> getUserLists(Integer pageSize, Integer pageIndex,User user) {
		// TODO Auto-generated method stub
		DataWrapper<List<User>> dataWrapper = new DataWrapper<List<User>>();
        List<User> ret = null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(User.class);
        
        if(user.getTeamId()!=null){
        	criteria.add(Restrictions.eq("teamId", user.getTeamId()));
        }
        if(user.getWorkName()!=null){
        	criteria.add(Restrictions.like("workName", user.getWorkName()));
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
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        dataWrapper.setData(ret);
        dataWrapper.setTotalNumber(0);
        dataWrapper.setCurrentPage(pageIndex);
        dataWrapper.setTotalPage(0);
        dataWrapper.setNumberPerPage(pageSize);

        return dataWrapper;
	}


	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<User> findUserLike(User user) {
		List<User> ret = null;
		DataWrapper<User> users=new DataWrapper<User>();
		DataWrapper<List<User>> userList=new DataWrapper<List<User>>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.eq("userName",user.getUserName()))
        .add(Restrictions.eq("realName", user.getRealName()))
        .add(Restrictions.eq("email", user.getEmail()))
        .add(Restrictions.eq("tel", user.getTel()));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (ret != null && ret.size() > 0) {
        	userList.setData(ret);
        	users.setData(userList.getData().get(0));
        	User us=new User();
        	us=userList.getData().get(0);
        	us.setPassword(MD5Util.getMD5String(MD5Util.getMD5String("123456") + "嘉实安装"));
        	update(us);
		}else{
			users.setErrorCode(ErrorCodeEnum.Error);
		}
        
		return users;
	}

	@Override
	public boolean deleteUser(Long userid) {
		// TODO Auto-generated method stub
		
		return delete(get(userid));
	}
	@Override
	public boolean deleteUserList(String[] userList) {
		// TODO Auto-generated method stub
		
		return deleteList(userList);
	}
	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<User>> findUserLikeRealName(String username) {
		List<User> ret = null;
		DataWrapper<List<User>> users=new DataWrapper<List<User>>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.like("realName","%"+username+"%"));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (ret != null && ret.size() > 0) {
			users.setData(ret);;
		}else{
			users.setErrorCode(ErrorCodeEnum.Error);
		}
		return users;
	}
	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<User>> findGetPushUsers(String username,int adminFlag) {
		List<User> ret = null;
		DataWrapper<List<User>> users=new DataWrapper<List<User>>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(User.class);
        if(adminFlag==-1){
        	Disjunction dis = Restrictions.disjunction();
        	dis.add(Restrictions.eq("id",(long)0));
        	dis.add(Restrictions.eq("id",(long)1));
        	dis.add(Restrictions.like("realName","%"+username+"%"));
        	criteria.add(dis);
        }else{
        	criteria.add(Restrictions.like("realName","%"+username+"%"));
        }
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (ret != null && ret.size() > 0) {
			users.setData(ret);;
		}else{
			users.setErrorCode(ErrorCodeEnum.Error);
		}
		return users;
	}

	@Override
	public DataWrapper<List<UserCopy>> getUserTeam(Long projectId) {
        Session session = getSession();
        String sql="select * from user where id in (select user_id from user_project where project_id="+projectId+")";
        DataWrapper<List<UserCopy>> dataWrapper=new DataWrapper<List<UserCopy>>();
		 try{
			 Query query = session.createSQLQuery(sql)
					 .addScalar("id",StandardBasicTypes.LONG)
					 .addScalar("user_name", StandardBasicTypes.STRING)
					 .addScalar("password",StandardBasicTypes.STRING)
					 .addScalar("real_name",StandardBasicTypes.STRING)
					 .addScalar("user_type", StandardBasicTypes.INTEGER)
					 .addScalar("email", StandardBasicTypes.STRING)
					 .addScalar("tel", StandardBasicTypes.STRING)
					 .addScalar("user_icon", StandardBasicTypes.LONG)
					 .addScalar("user_icon_url", StandardBasicTypes.STRING)
					 .addScalar("register_date", StandardBasicTypes.DATE)
					 .addScalar("update_date", StandardBasicTypes.DATE)
					 .addScalar("team_id", StandardBasicTypes.LONG)
					 .addScalar("system_id", StandardBasicTypes.INTEGER)
					 .addScalar("system_type", StandardBasicTypes.INTEGER)
					 .addScalar("role_id", StandardBasicTypes.LONG)
					 .addScalar("department_id", StandardBasicTypes.LONG)
					 .addScalar("menu_item_list", StandardBasicTypes.STRING)
					 .setResultTransformer(Transformers.aliasToBean(UserCopy.class)); 
			 dataWrapper.setData(query.list());
	        }catch(Exception e){
	            e.printStackTrace();
	            //dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
	        }
        return dataWrapper;
	}
	@Override
	public List<User> findUserLikeProjct(List<String> userList) {
		List<User> ret = null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(User.class);
        Disjunction dis = Restrictions.disjunction();
        if(userList!=null){
        	for(int i=0;i<userList.size();i++){
        		dis.add(Restrictions.eq("id", Long.valueOf(userList.get(i))));
        	}
        }
        criteria.add(dis);
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (ret == null || ret.size() < 0) {
			return null;
		}
		return ret;
	}
	@Override
	public List<UserSelect> getUserListByWorkName(String workName,Long projectId) {
		List<UserSelect> retDataWrapper = new ArrayList<UserSelect>();
		String sql="";
		if(workName.equals("经理") || workName.equals("预算员") || workName.equals("质量员") || workName.equals("安全员")){
			sql = "select id,real_name from user where work_name like '%"+workName+"%'";
		}
		if(workName.equals("班组长") || workName.equals("施工员")){
			sql = "select id,real_name from user where id in(select user_id from user_project where project_id="
					+projectId+") and work_name='"+workName+"'";
		}
		if(workName.equals("其他")){
			sql = "select id,real_name from user where work_name not like '%经理%' and work_name!='质量员' "
					+"and work_name !='预算员' and work_name!='安全员' and work_name!='班组长' and work_name!='施工员'";
		}
		Session session=getSession();
	    try{
		    Query query = session.createSQLQuery(sql)
				 .addScalar("id",StandardBasicTypes.LONG)
				 .addScalar("real_name", StandardBasicTypes.STRING)
				 .setResultTransformer(Transformers.aliasToBean(UserSelect.class)); 
		    retDataWrapper=query.list();
            
        }catch(Exception e){
            e.printStackTrace();
            //dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
        }
	    return retDataWrapper;
	}
	@Override
	public List<UserId> getAllUserIdList(String ids) {
		List<UserId> ret = new ArrayList<UserId>();
        String sql="select id from user where user_type=0 or user_type=1 or user_type=3";
        String sqlor="";
        if(ids!=null){
        	String[] idsb = ids.split(",");
        	for(String str:idsb){
            	sqlor=sqlor+ " or id="+str;
        	}
        	sql=sql+sqlor;
        }
        Session session=getSession();
	    try{
		    Query query = session.createSQLQuery(sql)
				 .addScalar("id",StandardBasicTypes.LONG)
				 .setResultTransformer(Transformers.aliasToBean(UserId.class)); 
		    ret=query.list();
        }catch(Exception e){
            e.printStackTrace();
            //dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
        }
	    return ret;
	}

}
