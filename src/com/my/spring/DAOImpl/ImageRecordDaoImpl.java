package com.my.spring.DAOImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.ImageRecordDao;
import com.my.spring.model.ImageRecord;

/**
* @author 徐雨祥
* @version 创建时间：2018年10月31日 下午1:27:07
* 类说明
*/
public class ImageRecordDaoImpl extends BaseDao<ImageRecord> implements ImageRecordDao {

	@Override
	public boolean addImageRecord(ImageRecord imageRecord) {
		// TODO Auto-generated method stub
		return save(imageRecord);
	}

	@Override
	public boolean deleteImageRecordById(Long id) {
		// TODO Auto-generated method stub
		return delete(get(id));
	}

	@Override
	public boolean updateImageRecord(ImageRecord imageRecord) {
		// TODO Auto-generated method stub
		return update(imageRecord);
	}

	@Override
	public ImageRecord getImageRecordById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

	@Override
	public List<ImageRecord> getImageRecordList(ImageRecord imageRecord) {
		// TODO Auto-generated method stub
		List<ImageRecord> ret = new ArrayList<ImageRecord>();
		Session session = getSession();
		Criteria criteria = session.createCriteria(ImageRecord.class);
		criteria.addOrder(Order.desc("createDate"));
		if(imageRecord.getProjectId()!=null){
			criteria.add(Restrictions.eq("projectId", imageRecord.getProjectId()));
		}
		try {
	            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
		return ret;
	}

}
