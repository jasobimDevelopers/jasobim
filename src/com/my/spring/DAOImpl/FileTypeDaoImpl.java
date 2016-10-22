package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.FileTypeDao;
import com.my.spring.model.FileType;
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
public class FileTypeDaoImpl extends BaseDao<FileType> implements FileTypeDao {

    @Override
    public boolean addFileType(FileType FileType) {
        return save(FileType);
    }

    @Override
    public boolean deleteFileType(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updateFileType(FileType FileType) {
        return update(FileType);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<FileType>> getFileTypeList() {
        DataWrapper<List<FileType>> retDataWrapper = new DataWrapper<List<FileType>>();
        List<FileType> ret = new ArrayList<FileType>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(FileType.class);
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
