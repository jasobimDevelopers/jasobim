package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.UserTeamDao;
import com.my.spring.model.UserTeam;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
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




}
