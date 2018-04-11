package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.FolderDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Folder;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
@Repository
public class FolderDaoImpl extends BaseDao<Folder> implements FolderDao {

    @Override
    public boolean addFloder(Folder File) {
        return save(File);
    }
    @Override
    public boolean deleteFloder(Long id) {
        return delete(get(id));
    }


    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<Folder>> getFolderList(Folder folder) {
        DataWrapper<List<Folder>> retDataWrapper = new DataWrapper<List<Folder>>();
        List<Folder> ret = new ArrayList<Folder>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Folder.class);
        criteria.add(Restrictions.eq("fileType", 0));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        retDataWrapper.setData(ret);
        return retDataWrapper;
    }

	@Override
	public Folder getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<Folder> getByName(String name) {
		DataWrapper<Folder> dataWrapper = new DataWrapper<Folder>();
		String sql="select * from floder where name="+name;
		Session session=getSession();
		 try{
	            Query query=session.createSQLQuery(sql).addEntity(Folder.class);
	            List<Folder> test=query.list();
	            dataWrapper.setData(test.get(0));
	        }catch(Exception e){
	            e.printStackTrace();
	            dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
	        }
		 
		return dataWrapper;
	}
}
