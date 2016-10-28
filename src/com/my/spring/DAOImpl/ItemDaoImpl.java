package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.ItemDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Item;
import com.my.spring.model.Quantity;
import com.my.spring.model.QuantityPojo;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/22.
 */
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
    public DataWrapper<List<Item>> getItemList() {
        DataWrapper<List<Item>> retDataWrapper = new DataWrapper<List<Item>>();
        List<Item> ret = new ArrayList<Item>();
        
        Session session = getSession();
        Criteria criteria = session.createCriteria(Item.class);
//        criteria.addOrder(Order.desc("publishDate"));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        retDataWrapper.setData(ret);
        return retDataWrapper;
    }

	@Override
	public List<Item> getItemByLocation(String location) {
		//String sql = "select user.* from t_user user,t_token token where token.token = " + token + " and token.user_id = user.id";
		char str1 = 0;
		for(int i=location.length()-1;i>=0;i--){
			if(location.charAt(i)>='A' && location.charAt(i)<='Z'){
				str1=location.charAt(i);
				break;
			}
		}
		int temp=str1+1;
		char str2=(char) temp;
        String location2=location+str2+"%";
		String sql = "select * from Item where location LIKE "+location2;
		Session session = getSession();
        Query query = session.createSQLQuery(sql)
                .addEntity(Item.class);
        List<Item> ItemList = query.list();
        if(ItemList != null && ItemList.size() > 0) {
            return ItemList;
        }else {
            return null;
        }
	}

	////输入project_id和type_name删除，并删除quantity里面project_id和name一致的
	@Override
	public DataWrapper<Void> deleteItemByTypeNameAndProjectId(Long projectId, String typeName, String token) {
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		String sql="delete a.*,b.* from item as a,quantity as b where a.project_id=b.project_id and a.type_name=b.name and a.project_id="+projectId+" and a.type_name="+typeName;
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
    //////////根据项目id删除构件和相应的工程量
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
		DataWrapper<Item> dataWrapper = new DataWrapper<Item>();
		return get(id);
	}

	//////工程量条件提取
	@Override
	public List<Item> getItemByOthers(Long projectId, Long typeName, Long buildingNum, Long floorNum,
			Long unitNum, Long householdNum) {
		String sql = null;
		DataWrapper<List<Item>> dataWrapper = new DataWrapper<List<Item>>();
		//////1.类型-楼号-单元号-楼层号-户号
		if(projectId!=null && buildingNum!=null && floorNum!=null 
		  && unitNum!=null && householdNum!=null && typeName!=null){
			sql="select * from item as a where a.project_id="+projectId
				+" and a.type_name="+typeName+" and a.building_num="
		        +buildingNum+" and a.floor_num="+floorNum+" and a.unit_num="
				+unitNum+" and a.house_hold="+householdNum;
		}
		/////2.项目-楼号-单元号-楼层号-户号
		if(projectId!=null && buildingNum!=null && floorNum!=null 
				  && unitNum!=null && householdNum!=null && typeName==null){
			sql="select * from item as a where a.project_id="+projectId
					+" and a.building_num="+buildingNum+" and a.floor_num="
					+floorNum+" and a.unit_num="+unitNum
					+" and a.house_hold="+householdNum;
		}
		//////3.项目-楼号-单元号-层号
		if(projectId!=null && buildingNum!=null && floorNum!=null 
				  && unitNum!=null && householdNum==null && typeName==null){
			sql="select * from item as a where a.project_id="+projectId
					+" and a.type_name="+typeName+" and a.building_num="
			        +buildingNum+" and a.floor_num="+floorNum+" and a.unit_num="
					+unitNum;
		}
		/////4.项目-楼号-单元号
		if(projectId!=null && buildingNum!=null && floorNum==null 
				  && unitNum!=null && householdNum==null && typeName==null){
			sql="select * from item as a where a.project_id="+projectId
					+" and a.type_name="+typeName+" and a.building_num="
			        +buildingNum+" and a.unit_num="
					+unitNum;
		}
		/////5.项目-楼号
		if(projectId!=null && buildingNum!=null && floorNum==null 
				  && unitNum==null && householdNum==null && typeName==null){
			sql="select * from item as a where a.project_id="+projectId
					+" and a.type_name="+typeName+" and a.building_num="
			        +buildingNum;
		}
		/////6.项目-类型
		if(projectId!=null && buildingNum==null && floorNum==null 
				  && unitNum==null && householdNum==null && typeName!=null){
			sql="select * from item as a where a.project_id="+projectId
					+" and a.type_name="+typeName;
		}
		/////7.类型-项目-楼号-单元号-楼层号
		if(projectId!=null && buildingNum!=null && floorNum!=null 
				  && unitNum!=null && householdNum==null && typeName!=null){
			sql="select * from item as a where a.project_id="+projectId
					+" and a.type_name="+typeName+" and a.building_num="
					+buildingNum+" and a.unit_num="+unitNum+" and a.floor_num="+floorNum;
		}
		/////8.类型-项目-楼号-单元
		if(projectId!=null && buildingNum!=null && floorNum==null 
				  && unitNum!=null && householdNum==null && typeName!=null){
			sql="select * from item as a where a.project_id="+projectId
					+" and a.type_name="+typeName+" and a.building_num="
					+buildingNum+" and a.unit_num="+unitNum;
		}
		/////9.类型-项目-楼号
		if(projectId!=null && buildingNum!=null && floorNum==null 
				  && unitNum==null && householdNum==null && typeName!=null){
			sql="select * from item as a where a.project_id="+projectId
					+" and a.type_name="+typeName+" and a.building_num="
					+buildingNum;
		}
		
		Session session=getSession();
		 try{
	            Query query=session.createSQLQuery(sql);
	            dataWrapper.setData(query.list());
	            session.getTransaction().commit();
	            session.flush();
	        }catch(Exception e){
	            e.printStackTrace();
	            session.getTransaction().rollback();
	            dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
	        }
		 
		return dataWrapper.getData();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getSameItem() {
		String sql = "select count(*) as num,SUM(length) as lengthnum,SUM(area) as areanum,"
				 +"project_id,building_num,unit_num,floor_num,household_num,system_type,"
				 +"service_type,family_and_type,size,material,name,type_name from item "
				 + "GROUP BY project_id,building_num,unit_num,floor_num,household_num,"
				 + "system_type,service_type,family_and_type,size,material,name,type_name";
		List dataWrapper = new ArrayList<>();
		QuantityPojo pojo=new QuantityPojo();
		Session session=getSession();
		 try{
			 Query query = this.getSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(QuantityPojo.class)); 
			 dataWrapper.addAll(query.list());
	            
	        }catch(Exception e){
	            e.printStackTrace();
	            //dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
	        }
		 
		return dataWrapper;
	}
}
