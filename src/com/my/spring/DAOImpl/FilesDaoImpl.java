package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.FilesDao;
import com.my.spring.model.Files;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Repository
public class FilesDaoImpl extends BaseDao<Files> implements FilesDao {

    @Override
    public boolean addFiles(Files Files) {
        return save(Files);
    }

    @Override
    public boolean deleteFiles(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updateFiles(Files Files) {
        return update(Files);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<Files>> getFilesList() {
        DataWrapper<List<Files>> retDataWrapper = new DataWrapper<List<Files>>();
        List<Files> ret = new ArrayList<Files>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Files.class);
//        criteria.addOrder(Order.desc("publishDate"));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        retDataWrapper.setData(ret);
        return retDataWrapper;
    }
}
