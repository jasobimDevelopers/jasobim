package com.my.spring.DAOImpl;
import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.ItemDataDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.ItemData;
import com.my.spring.model.ItemDataGet;
import com.my.spring.model.ItemDataIndex;
import com.my.spring.model.MenuListCopy;
import com.my.spring.model.UserTeamIndex;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemDataDaoImpl extends BaseDao<ItemData> implements ItemDataDao {

    @Override
    public boolean addItemData(ItemData ItemData) {
        return save(ItemData);
    }


    @Override
    public boolean updateItemData(ItemData ItemData) {
        return update(ItemData);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<ItemData>> getItemDataList( Integer pageIndex, Integer pageSize, ItemData ItemData) {
        DataWrapper<List<ItemData>> retDataWrapper = new DataWrapper<List<ItemData>>();
        List<ItemData> ret = new ArrayList<ItemData>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(ItemData.class);
        criteria.addOrder(Order.desc("createDate"));
        if(ItemData.getCreateUser()!=null){
        	criteria.add(Restrictions.eq("createUser", ItemData.getCreateUser()));
        }
        /////////////////////////////////////
   
        if (pageSize == null) {
			pageSize = 10;
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
	public ItemData getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}


	@Override
	public ItemDataGet getFirstItemByProcessDataId(Long processDataId) {
		List<ItemDataGet> dataWrapper = new ArrayList<ItemDataGet>();
		String sql="select a.id,a.name,a.approve_user as approveUser,a.work_name as workName from item_data a,process_item b where a.id=b.item_id and b.process_id="
		+processDataId+" and b.which=1";
		Session session=getSession();
		 try{
			 Query query = session.createSQLQuery(sql)
					 .addScalar("id",StandardBasicTypes.LONG)
					 .addScalar("approveUser", StandardBasicTypes.LONG)
					 .addScalar("workName",StandardBasicTypes.INTEGER)
					 .addScalar("name",StandardBasicTypes.STRING)
					 .setResultTransformer(Transformers.aliasToBean(ItemDataGet.class)); 
			 dataWrapper= query.list();
	        }catch(Exception e){
	            e.printStackTrace();
	            //dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
	        }
		 if(!dataWrapper.isEmpty()){
			 return dataWrapper.get(0);
		 }
		return null;
	}


	@Override
	public boolean deleteItemDataByUserId(Long id) {
		String sql = "delete from item_data where user_id="+id;
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
	public boolean addItemDataList(List<ItemData> newList2) {
		// TODO Auto-generated method stub
		return saveList(newList2);
	}


	@Override
	public List<ItemDataIndex> getItemDataListByUserId(Long id,Integer pageSize,Integer pageIndex) {
		List<ItemDataIndex> gets=new ArrayList<ItemDataIndex>();
		if(pageSize==null){
			pageSize=10;
		}
		if(pageIndex==null){
			pageIndex=0;
		}
		String sql = "select b.name,b.id,b.approve_user as approveUser,b.work_name as workName,a.indexs,b.create_date as createDate,b.create_user as createUser from user_index a,user_team b where a.about_type=3 and a.about_id=b.id and a.user_id="
		+id+" ORDER BY a.indexs asc limit"+ pageIndex+","+pageSize;
		Session session=getSession();
		try{
			 Query query = session.createSQLQuery(sql)
					 .addScalar("id", StandardBasicTypes.LONG)
					 .addScalar("name", StandardBasicTypes.STRING)
					 .addScalar("indexs", StandardBasicTypes.LONG)
					 .addScalar("workName", StandardBasicTypes.INTEGER)
					 .addScalar("approveUser", StandardBasicTypes.LONG)
					 .addScalar("createDate",StandardBasicTypes.TIMESTAMP)
					 .addScalar("createUser", StandardBasicTypes.LONG)
					 .setResultTransformer(Transformers.aliasToBean(ItemDataIndex.class));
			 	gets=query.list();
	        }catch(Exception e){
	            e.printStackTrace();
	    }
		return gets;
	}
	@Override
	public Integer getItemDataSizeByUserId(Long id) {
		Integer total=0;
		String sql = "select COUNT(*) as total from user_index where user_id="+id+" and about_type=1";
		Session session=getSession();
		try{
			 Query query = session.createSQLQuery(sql);
			 total=Integer.valueOf(query.getQueryString());
	        }catch(Exception e){
	            e.printStackTrace();
	    }
		return total;
	}

}
