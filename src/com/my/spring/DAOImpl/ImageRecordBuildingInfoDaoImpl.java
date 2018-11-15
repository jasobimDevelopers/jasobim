package com.my.spring.DAOImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.ImageRecordBuildingInfoDao;
import com.my.spring.model.ImageRecord;
import com.my.spring.model.ImageRecordBuildingInfo;

/**
* @author 徐雨祥
* @version 创建时间：2018年11月2日 上午8:31:37
* 类说明
*/
@Repository
public class ImageRecordBuildingInfoDaoImpl extends BaseDao<ImageRecordBuildingInfo> implements ImageRecordBuildingInfoDao{

	@Override
	public boolean addImageRecordBuildingInfo(ImageRecordBuildingInfo info) {
		// TODO Auto-generated method stub
		return save(info);
	}

	@Override
	public boolean updateImageRecordBuildingInfo(ImageRecordBuildingInfo info) {
		// TODO Auto-generated method stub
		return update(info);
	}

	@Override
	public boolean deleteImageRecordBuildingInfo(Long id) {
		// TODO Auto-generated method stub
		return delete(get(id));
	}

	@Override
	public ImageRecordBuildingInfo getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

	@Override
	public List<ImageRecordBuildingInfo> getImageRecordBuildingInfoList(ImageRecordBuildingInfo info) {
		List<ImageRecordBuildingInfo> ret = new ArrayList<ImageRecordBuildingInfo>();
		Session session = getSession();
		Criteria criteria = session.createCriteria(ImageRecordBuildingInfo.class);
		criteria.addOrder(Order.asc("indexs"));
		if(info.getProjectId()!=null){
			criteria.add(Restrictions.eq("projectId", info.getProjectId()));
		}
		try {
	            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
		return ret;
	}


}
