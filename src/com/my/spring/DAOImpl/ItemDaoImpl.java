package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.ItemDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Item;
import com.my.spring.model.QuantityPojo;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemDaoImpl extends BaseDao<Item> implements ItemDao {

    @Override
    public boolean addItem(Item Item) {
        return save(Item);
    }

    @Override
    public boolean deleteItem(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updateItem(Item Item) {
        return update(Item);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<Item>> getItemList(Long projectId,Integer pageSize, Integer pageIndex,Item item) {
        DataWrapper<List<Item>> retDataWrapper = new DataWrapper<List<Item>>();
        List<Item> ret = new ArrayList<Item>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Item.class);
        
        ///////////////////////////////
        criteria.add(Restrictions.eq("projectId", projectId));
        if(item.getProfessionType()!=null){
        	if(item.getProfessionType()!=-1){
        		criteria.add(Restrictions.eq("professionType", item.getProfessionType()));
        	}
        }
        if(item.getName()!=null){
        	criteria.add(Restrictions.like("name", "%"+item.getName()+"%"));
        }
        if(item.getProjectId()!=(long)267 && item.getProjectId()!=(long)268){
        	 if(item.getModelFlag()!=null){
             	criteria.add(Restrictions.eq("modelFlag", item.getModelFlag()));
             }
        }
        if(item.getSelfId()!=null){
        	criteria.add(Restrictions.eq("selfId", item.getSelfId()));
        }
        if(item.getBuildingNum()!=null){
        	if(item.getBuildingNum()!=-1){
        		criteria.add(Restrictions.eq("buildingNum", item.getBuildingNum()));
        	}
        }
        if(item.getHouseholdNum()!=null){
        	if(item.getHouseholdNum()!=-1){
        		criteria.add(Restrictions.eq("householdNum", item.getHouseholdNum()));
        	}
        }
        if(item.getFloorNum()!=null){
        	if(item.getFloorNum()!=-1){
        		criteria.add(Restrictions.eq("floorNum", item.getFloorNum()));
        	}
        }
        	
        /////////////////////////////////////
   
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

    //////////根据项目id删除构件和相应的工程量
	@SuppressWarnings("unused")
	@Override
	public DataWrapper<Void> deleteItemByProjectId(Long projectId, String token) {
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		String sql="delete a.*,b.* from item as a,quantity as b where a.project_id=b.project_id and a.project_id="+projectId;
		Session session=getSession();
		 try{
	            Query query=session.createSQLQuery(sql);
	            session.getTransaction().commit();
	            session.flush();
	        }catch(Exception e){
	            e.printStackTrace();
	            session.getTransaction().rollback();
	            dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
	        }
		 
		return dataWrapper;
	}

	@Override
	public Item getItemById(Long id) {
		@SuppressWarnings("unused")
		DataWrapper<Item> dataWrapper = new DataWrapper<Item>();
		return get(id);
	}
	

	

	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<QuantityPojo>> getSameItem(Long projectId) {
		String sql = "select count(*) as num,SUM(length) as lengthnum,SUM(area) as areanum,"
				 +"project_id,building_num,unit_num,floor_num,household_num,system_type,"
				 +"service_type,family_and_type,size,material,name,type_name,profession_type from item where project_id="
				 +projectId
				 + " GROUP BY project_id,building_num,unit_num,floor_num,household_num,"
				 + "system_type,service_type,family_and_type,size,material,name,type_name";
		DataWrapper<List<QuantityPojo>> dataWrapper=new DataWrapper<List<QuantityPojo>>();
		Session session=getSession();
		 try{
			 Query query = session.createSQLQuery(sql)
					 .addScalar("num",StandardBasicTypes.LONG)
					 .addScalar("lengthnum", StandardBasicTypes.DOUBLE)
					 .addScalar("areanum",StandardBasicTypes.DOUBLE)
					 .addScalar("project_id",StandardBasicTypes.LONG)
					 .addScalar("building_num", StandardBasicTypes.INTEGER)
					 .addScalar("unit_num", StandardBasicTypes.INTEGER)
					 .addScalar("floor_num", StandardBasicTypes.INTEGER)
					 .addScalar("household_num", StandardBasicTypes.INTEGER)
					 .addScalar("system_type", StandardBasicTypes.STRING)
					 .addScalar("service_type", StandardBasicTypes.STRING)
					 .addScalar("family_and_type", StandardBasicTypes.STRING)
					 .addScalar("size", StandardBasicTypes.STRING)
					 .addScalar("material", StandardBasicTypes.STRING)
					 .addScalar("type_name", StandardBasicTypes.STRING)
					 .addScalar("name", StandardBasicTypes.STRING)
					 .addScalar("profession_type", StandardBasicTypes.INTEGER)
					 .setResultTransformer(Transformers.aliasToBean(QuantityPojo.class)); 
			 dataWrapper.setData(query.list());
	            
	        }catch(Exception e){
	            e.printStackTrace();
	            //dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
	        }
		 
		return dataWrapper;
	}

	@Override
	public Long getItemByBase(Long projectId,Long buildingId) {
		Session session=getSession();
		int flag=4;
		Query query=session.createSQLQuery("select count(distinct(floor_num)) from item where project_id=" + projectId + " and building_num=" + buildingId + " and floor_num<"+flag);
		return  Long.parseLong(query.list().get(0).toString());
	}
	
	@Override
	public Long getItemByBuidlingNum(Long projectId,Long buildingId) {
		Session session=getSession();
		Query query=session.createSQLQuery("select count(distinct(floor_num)) from item where project_id=" + projectId+ " and building_num=" + buildingId);
		return  Long.parseLong(query.list().get(0).toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getHouseHoldType(Long projectId, Long buildingId, Long floorId) {
		// TODO Auto-generated method stub
		Session session=getSession();
		Query query=session.createSQLQuery("select distinct(household_num) from item where project_id=" + projectId + " and building_num=" + buildingId + " and floor_num="+floorId);
		return  query.list();
	}

	@Override
	public boolean addItemList(List<Item> itemList) {
		// TODO Auto-generated method stub
		return saveList(itemList);
	}


	@Override
	public boolean deleteItemByPorjectId(Long projectId) {
		String sql = "delete from item where project_id="+projectId;
		Session session=getSession();
		 try{
			 Query query = session.createSQLQuery(sql);
			 if(query.executeUpdate()==1){
				 return true;
			 }
			 
	        }catch(Exception e){
	            e.printStackTrace();
	        }
		 
		return false;
	}

}
