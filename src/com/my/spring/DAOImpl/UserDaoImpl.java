package com.my.spring.DAOImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.User;
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
         
        if(user.getTeamInformation()!=null && !user.getTeamInformation().equals("")){
        	criteria.add(Restrictions.eq("teamInformation", user.getTeamInformation()));
        }
        if(user.getProjectList()!=null){
        	criteria.add(Restrictions.like("projectList", "%" + user.getProjectList() + "%"));
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
        	User us=new User();
        	us=userList.getData().get(0);
        	us.setPassword(MD5Util.getMD5String(MD5Util.getMD5String("123456") + "嘉实安装"));
        	update(us);
		}else{
			users.setErrorCode(ErrorCodeEnum.Error);
		}
        users.setData(userList.getData().get(0));
		return users;
	}

	@Override
	public boolean deleteUser(Long userid) {
		// TODO Auto-generated method stub
		
		return delete(get(userid));
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<User>> findUserLikeRealName(String username) {
		List<User> ret = null;
		DataWrapper<List<User>> users=new DataWrapper<List<User>>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.like("realName",username,MatchMode.ANYWHERE));
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
	public DataWrapper<List<User>> getUserTeam(Integer pageSize, Integer pageIndex, Long projectId) {
		DataWrapper<List<User>> dataWrapper = new DataWrapper<List<User>>();
        List<User> ret = null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(User.class);
        
        if(projectId!=null){
        	criteria.add(Restrictions.like("projectList", "%" + projectId + "%"));
        }
        criteria.add(Restrictions.eq("userType",3));
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

}
