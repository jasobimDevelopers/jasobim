package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.UserProjectDao;
import com.my.spring.model.UserProject;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserProjectDaoImpl extends BaseDao<UserProject> implements UserProjectDao {

    @Override
    public boolean addUserProject(UserProject paper) {
        return save(paper);
    }
    @Override
    public boolean addUserProjectList(List<UserProject> paper) {
        return saveList(paper);
    }

    @Override
    public boolean deleteUserProject(Long id) {
        return delete(get(id));
    }
    @Override
	public UserProject getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

	
}
