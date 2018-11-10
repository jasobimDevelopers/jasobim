package com.my.spring.DAOImpl;

import com.google.zxing.WriterException;
import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.ItemDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Duct;
import com.my.spring.model.Item;
import com.my.spring.model.ItemCount;
import com.my.spring.model.ItemStateLog;
import com.my.spring.model.QuantityPojo;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.QRUtil;
import com.my.spring.utils.ReadTxt;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
        if(item.getProjectId()!=null){
        	if(item.getProjectId()!=(long)267 && item.getProjectId()!=(long)268){
           	 if(item.getModelFlag()!=null){
                	criteria.add(Restrictions.eq("modelFlag", item.getModelFlag()));
                }
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
	@Override
	public Item getItemBySelfId(Long projectId) {
		Item dataWrapper=new Item();
		List<Item> ret = new ArrayList<Item>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Item.class);
       
        if(projectId!=null){
        	criteria.add(Restrictions.eq("projectId",projectId));
        }
        QRUtil qr = new QRUtil();
        File file=new File("C:/Users/Han/Desktop/水电井.txt");  
        BufferedReader reader=null;  
        String temp=null;
       /**/
        ItemDao itemDao= new ItemDaoImpl();
        ReadTxt readTxt = new ReadTxt(itemDao);
        List<Long> itemIdLists = new ArrayList<Long>();
        try{  
        	/*QrCodeFile= new File("C://Users/Han/Desktop/水电井二维码/电缆桥架/"+"430685"+".png");
    		qurl=new String("http://jasobim.com.cn/page/itemInfo.html?id=430685");
    		QRCodeUtil2.drawLogoQRCode(logoFile, QrCodeFile, qurl, null);*/
                reader=new BufferedReader(new FileReader(file));  
                while((temp=reader.readLine())!=null){  
                    itemIdLists.add(Long.valueOf(temp));
                }  
                criteria.add(Restrictions.in("selfId", itemIdLists));
        }  
        catch(Exception e){  
            e.printStackTrace();  
        }  
        finally{  
            if(reader!=null){  
                try{  
                    reader.close();  
                }  
                catch(Exception e){  
                    e.printStackTrace();  
                }  
            }  
        }  
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (ret != null && ret.size() > 0) {
        	for(Item s: ret){
        		try {
					qr.runCode(s);
				} catch (WriterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
		}else{
			dataWrapper=null;
		}
       
		return dataWrapper;
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
		int flag=1;
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

	@Override
	public List<ItemCount> getNumsGroupBy(Long projectId) {
		String sql = "select name,profession_type as professionType,service_type as serviceType,family_and_type as familyAndType,"
				+"system_type as systemType,size,sum(length) as length,sum(area) as area,count(id) as num,"
				+"self_id as selfId from item where project_id="
				+projectId+" GROUP BY service_type,system_type,family_and_type,size,profession_type";
		List<ItemCount> dataWrapper=new ArrayList<ItemCount>();
		Session session=getSession();
		 try{
			 Query query = session.createSQLQuery(sql)
					 .addScalar("name",StandardBasicTypes.STRING)
					 .addScalar("serviceType",StandardBasicTypes.STRING)
					 .addScalar("familyAndType",StandardBasicTypes.STRING)
					 .addScalar("systemType",StandardBasicTypes.STRING)
					 .addScalar("size",StandardBasicTypes.STRING)
					 .addScalar("length", StandardBasicTypes.DOUBLE)
					 .addScalar("area",StandardBasicTypes.DOUBLE)
					 .addScalar("num",StandardBasicTypes.INTEGER)
					 .addScalar("selfId", StandardBasicTypes.LONG)
					 .addScalar("professionType", StandardBasicTypes.INTEGER)
					 .setResultTransformer(Transformers.aliasToBean(ItemCount.class)); 
			 dataWrapper=query.list();
	            
	        }catch(Exception e){
	            e.printStackTrace();
	            //dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
	        }
		return dataWrapper;
	}
	
	@Override
	public List<ItemCount> getNumsGroupByState(Long projectId) {
		String sql = "select group_concat(self_id) as idList,name,profession_type as professionType,service_type as serviceType,family_and_type as familyAndType,"
				+"system_type as systemType,size,sum(length) as length,sum(area) as area,count(id) as num,"
				+"self_id as selfId from item where project_id="
				+projectId+" and state=1 GROUP BY service_type,system_type,family_and_type,size,profession_type";
		List<ItemCount> dataWrapper=new ArrayList<ItemCount>();
		Session session=getSession();
		 try{
			 Query query = session.createSQLQuery(sql)
					 .addScalar("name",StandardBasicTypes.STRING)
					 .addScalar("idList",StandardBasicTypes.STRING)
					 .addScalar("serviceType",StandardBasicTypes.STRING)
					 .addScalar("familyAndType",StandardBasicTypes.STRING)
					 .addScalar("systemType",StandardBasicTypes.STRING)
					 .addScalar("size",StandardBasicTypes.STRING)
					 .addScalar("length", StandardBasicTypes.DOUBLE)
					 .addScalar("area",StandardBasicTypes.DOUBLE)
					 .addScalar("num",StandardBasicTypes.INTEGER)
					 .addScalar("selfId", StandardBasicTypes.LONG)
					 .addScalar("professionType", StandardBasicTypes.INTEGER)
					 .setResultTransformer(Transformers.aliasToBean(ItemCount.class)); 
			 dataWrapper=query.list();
	            
	        }catch(Exception e){
	            e.printStackTrace();
	            //dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
	        }
		return dataWrapper;
	}

	@Override
	public List<Item> getItemBySelfId(ItemStateLog itemStateLog,String idList) {
		List<Item> ret = new ArrayList<Item>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Item.class);
        if(itemStateLog.getProjectId()!=null){
        	criteria.add(Restrictions.eq("projectId",itemStateLog.getProjectId()));
        }
        if(idList!=null){
        	List<Long> ids = new ArrayList<Long>();
        	for(int i=0;i<idList.split(",").length;i++){
        		ids.add(Long.valueOf(idList.split(",")[i]));
        	}
        	criteria.add(Restrictions.in("selfId",ids));
        }
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
	}

	@Override
	public Item getItemBySelfId(Long selfId, Long projectId) {
		List<Item> ret = new ArrayList<Item>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Item.class);
        if(projectId!=null){
        	criteria.add(Restrictions.eq("projectId",projectId));
        }
        if(selfId!=null){
        	criteria.add(Restrictions.eq("selfId",selfId));
        }
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(!ret.isEmpty()){
        	return ret.get(0);
        }
        return null;
	}
	@Override
    public DataWrapper<List<Item>> getItemLists(Long projectId,Integer pageSize, Integer pageIndex,Item item) {
        DataWrapper<List<Item>> retDataWrapper = new DataWrapper<List<Item>>();
        List<Item> ret = new ArrayList<Item>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Item.class);      
        ///////////////////////////////
        criteria.add(Restrictions.eq("projectId", projectId));
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

	@Override
	public List<ItemCount> getNumsGroupByProfesstionType(Long projectId) {
		
		String sql = "select count(id) as num,profession_type as professionType from item where project_id="
		+projectId+" GROUP BY profession_type";
		List<ItemCount> dataWrapper=new ArrayList<ItemCount>();
		Session session=getSession();
		 try{
			 Query query = session.createSQLQuery(sql)
					 .addScalar("num",StandardBasicTypes.INTEGER)
					 .addScalar("professionType", StandardBasicTypes.INTEGER)
					 .setResultTransformer(Transformers.aliasToBean(ItemCount.class)); 
			 dataWrapper=query.list();
	        }catch(Exception e){
	            e.printStackTrace();
	            //dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
	        }
		return dataWrapper;		
	}

	@Override
	public List<ItemCount> getNumsGroupByProfesstionTypeAndState(Long projectId) {
		String sql = "select count(id) as num,profession_type as professionType from item where project_id="
				+projectId+" and state=1 GROUP BY profession_type";
				List<ItemCount> dataWrapper=new ArrayList<ItemCount>();
				Session session=getSession();
				 try{
					 Query query = session.createSQLQuery(sql)
							 .addScalar("num",StandardBasicTypes.INTEGER)
							 .addScalar("professionType", StandardBasicTypes.INTEGER)
							 .setResultTransformer(Transformers.aliasToBean(ItemCount.class)); 
					 dataWrapper=query.list();
			        }catch(Exception e){
			            e.printStackTrace();
			            //dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
			        }
				return dataWrapper;		
	}
	@Override
	public List<Object> getAllNumsGroupByStates(Long projectId) {
		String sql = "(select count(*) from item where project_id="
				+projectId+" and state=1) UNION all (select count(*) from item where project_id="+projectId+")";
		List<Object> dataWrapper=new ArrayList<Object>();
		Session session=getSession();
		 try{
			 Query query = session.createSQLQuery(sql);
			 dataWrapper=query.list();
	        }catch(Exception e){
	            e.printStackTrace();
	            //dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
	        }
		return dataWrapper;
	}

	@Override
	public boolean updateItemList(List<Item> item) {
		// TODO Auto-generated method stub
		return updateList(item);
	}

	@Override
	public boolean updateItemByProjectIdAndSelfIds(Long projectId, String selfIdList) {
		String sql = "update item set state=0 where project_id="+projectId+" and self_id=";
		String[] selfIdLists = selfIdList.split(",");
		for(int i=0;i<selfIdLists.length;i++){
			if(i==(selfIdLists.length-1)){
				sql=sql+selfIdLists[i];
			}else{
				sql=sql+selfIdLists[i]+" or self_id=";
			}
			
		}
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

	@Override
	public List<Item> getItemListsByIdList(Long projectId, String idList) {
	        List<Item> ret = new ArrayList<Item>();
	        Session session = getSession();
	        Criteria criteria = session.createCriteria(Item.class);
	        
	        ///////////////////////////////
	        criteria.add(Restrictions.eq("projectId", projectId));
	        if(idList!=null){
	        	String[] ids = idList.split(",");
	        	List<Long> realids = new ArrayList<Long>();
	        	for(int i=0;i<ids.length;i++){
	        		realids.add(Long.valueOf(ids[i]));
	        	}
	        	criteria.add(Restrictions.in("selfId", realids));
	        }
	       
	        try {
	            ret = criteria.list();
	        }catch (Exception e){
	            e.printStackTrace();
	        }
	        return ret;
	}


}
