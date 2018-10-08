package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.MenuListDao;
import com.my.spring.model.MenuList;
import com.my.spring.model.MenuListCopy;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
@Repository
public class MenuListDaoImpl extends BaseDao<MenuList> implements MenuListDao {

    @Override
    public boolean addMenuList(MenuList File) {
        return save(File);
    }
    @Override
    public boolean deleteMenuList(Long id) {
        return delete(get(id));
    }


    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<MenuList>> getMenuLists() {
        DataWrapper<List<MenuList>> retDataWrapper = new DataWrapper<List<MenuList>>();
        List<MenuList> ret = new ArrayList<MenuList>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(MenuList.class);
        criteria.add(Restrictions.isNotNull("pid"));
//        criteria.addOrder(Order.desc("publishDate"));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        retDataWrapper.setData(ret);
        return retDataWrapper;
    }
    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<MenuListCopy>> getMenuListByIdList(String[] id) {
        Session session = getSession();
        String str="";
        for(int i=0;i<id.length;i++){
        	if(str.equals("")){
        		str="id="+id[i];
        	}else{
        		str=str+" or "+"id="+id[i];
        	}
        }
        String sql="select * from menu_list where ("+str+")";
        DataWrapper<List<MenuListCopy>> dataWrapper=new DataWrapper<List<MenuListCopy>>();
		 try{
			 Query query = session.createSQLQuery(sql)
					 .addScalar("id",StandardBasicTypes.LONG)
					 .addScalar("create_user", StandardBasicTypes.LONG)
					 .addScalar("menu_path",StandardBasicTypes.STRING)
					 .addScalar("menu_name",StandardBasicTypes.STRING)
					 .addScalar("pid", StandardBasicTypes.STRING)
					 .addScalar("create_date", StandardBasicTypes.DATE)
					 .addScalar("remark", StandardBasicTypes.STRING)
					 .setResultTransformer(Transformers.aliasToBean(MenuListCopy.class)); 
			 dataWrapper.setData(query.list());
	            
	        }catch(Exception e){
	            e.printStackTrace();
	            //dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
	        }
		 
        return dataWrapper;
    }
    @SuppressWarnings("unchecked")
   	@Override
       public DataWrapper<List<MenuList>> getMenuListByIdLists(String[] id) {
    	 DataWrapper<List<MenuList>> retDataWrapper = new  DataWrapper<List<MenuList>>();
    	 List<MenuList>  ret = new ArrayList<MenuList>();
    	 Session session = getSession();
           Criteria criteria = session.createCriteria(MenuList.class);
           criteria.add(Restrictions.in("id", id));
//           criteria.addOrder(Order.desc("publishDate"));
           try {
           	ret = criteria.list();
           }catch (Exception e){
               e.printStackTrace();
           }
           retDataWrapper.setData(ret);
           return retDataWrapper;
       }

	@Override
	public MenuList getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

	
	@Override
	public boolean deleteMenuListList(String[] ids) {
		// TODO Auto-generated method stub
		return deleteList(ids);
	}
	@Override
	public boolean updateMenuList(MenuList ml) {
		// TODO Auto-generated method stub
		return update(ml);
	}
	@Override
	public List<MenuListCopy> getMenuParrentByChild(List<MenuListCopy> ml) {
		Session session = getSession();
        String str="";
        for(int i=0;i<ml.size();i++){
        	if(str.equals("")){
        		str="id="+ml.get(i).getPid();
        	}else{
        		str=str+" or "+"id="+ml.get(i).getPid();
        	}
        }
        String sql="select * from menu_list where ("+str+")";
        List<MenuListCopy> dataWrapper=new ArrayList<MenuListCopy>();
		 try{
			 Query query = session.createSQLQuery(sql)
					 .addScalar("id",StandardBasicTypes.LONG)
					 .addScalar("create_user", StandardBasicTypes.LONG)
					 .addScalar("menu_path",StandardBasicTypes.STRING)
					 .addScalar("menu_name",StandardBasicTypes.STRING)
					 .addScalar("pid", StandardBasicTypes.STRING)
					 .addScalar("create_date", StandardBasicTypes.DATE)
					 .addScalar("remark", StandardBasicTypes.STRING)
					 .setResultTransformer(Transformers.aliasToBean(MenuListCopy.class)); 
			 dataWrapper=query.list();
	            
	        }catch(Exception e){
	            e.printStackTrace();
	            //dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
	        }
		 
        return dataWrapper;
	}
	@Override
	public List<MenuList> getAllMenuList() {
		List<MenuList> ret = new ArrayList<MenuList>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(MenuList.class);
//        criteria.addOrder(Order.desc("publishDate"));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
		return ret;
	}
	
}
