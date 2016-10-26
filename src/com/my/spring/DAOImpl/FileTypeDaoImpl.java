package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.FileTypeDao;
import com.my.spring.model.File;
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
public class FileTypeDaoImpl extends BaseDao<File> implements FileTypeDao {

    @Override
    public boolean addFileType(File FileType) {
        return save(FileType);
    }

    @Override
    public boolean deleteFileType(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updateFileType(File FileType) {
        return update(FileType);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<File>> getFileTypeList() {
        DataWrapper<List<File>> retDataWrapper = new DataWrapper<List<File>>();
        List<File> ret = new ArrayList<File>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(File.class);
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
