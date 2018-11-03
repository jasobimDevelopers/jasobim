package com.my.spring.DAOImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.ImageRecordDao;
import com.my.spring.model.ImageRecord;
import com.my.spring.model.ImageRecordBuildingInfo;
import com.my.spring.model.ImageRecordData;
import com.my.spring.model.UserTeamIndex;

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

	@Override
	public List<ImageRecord> getImageRecordListByProjectIdAndBuildingId(Long projectId, Long id) {
		List<ImageRecord> ret = new ArrayList<ImageRecord>();
		Session session = getSession();
		Criteria criteria = session.createCriteria(ImageRecord.class);
		if(projectId!=null){
			criteria.add(Restrictions.eq("projectId", projectId));
		}
		if(id!=null){
			criteria.add(Restrictions.eq("buildingId", id));
		}
		try {
	            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
		return ret;
	}

	@Override
	public List<ImageRecordData> getImageRecordBuildingInfoListByGroupBy(ImageRecord infos) {
		List<ImageRecordData> gets=new ArrayList<ImageRecordData>();
		String sql = "select project_part as projectPart,unit_part as unitPart from image_record where project_id="
		+infos.getProjectId()+" and building_id="+infos.getId()+" group by project_part,unit_part order by project_part_time desc";
		Session session=getSession();
		try{
			 Query query = session.createSQLQuery(sql)
					 .addScalar("projectPart", StandardBasicTypes.INTEGER)
					 .addScalar("unitPart", StandardBasicTypes.INTEGER)
					 .setResultTransformer(Transformers.aliasToBean(ImageRecordData.class));
			 	gets=query.list();
	        }catch(Exception e){
	            e.printStackTrace();
	    }
		return gets;
	}

}
