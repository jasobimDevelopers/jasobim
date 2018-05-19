package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.FolderDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Folder;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
@Repository
public class FolderDaoImpl extends BaseDao<Folder> implements FolderDao {

    @Override
    public boolean addFloder(Folder File) {
        return save(File);
    }
    
    @Override
    public boolean updateFloder(Folder File) {
        return update(File);
    }
    @Override
    public boolean addFloderList(List<Folder> File) {
        return saveList(File);
    }
    @Override
    public boolean deleteFloder(Long id) {
        return delete(get(id));
    }


    @SuppressWarnings("unchecked")
	@Override
    public List<Folder> getFolderList(Folder folder) {
        List<Folder> ret = new ArrayList<Folder>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Folder.class);
        //criteria.add(Restrictions.eq("fileType", 0));
        
        if(folder.getName()!=null){
        	criteria.add(Restrictions.eq("name", folder.getName()));
        }
        if(folder.getProjectId()!=null){
        	criteria.add(Restrictions.eq("projectId", folder.getProjectId()));
        }
        if(folder.getParrentId()!=null){
        	criteria.add(Restrictions.eq("parrentId", folder.getParrentId()));
        }
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }
    @SuppressWarnings("unchecked")
	@Override
    public List<Folder> getFolderLists(List<Folder> folder) {
        DataWrapper<List<Folder>> retDataWrapper = new DataWrapper<List<Folder>>();
        List<Folder> ret = new ArrayList<Folder>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Folder.class);
        criteria.add(Restrictions.eq("fileType", 0));
        if(!folder.isEmpty()){
        	for(Folder folders:folder){
        		if(folders.getId()!=null){
                	criteria.add(Restrictions.ne("id", folders.getId()));
                }
        	}
        }
        
       
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }
    @SuppressWarnings("unchecked")
	@Override
    public List<Folder> getFolderListLike(Long projectId, String name) {
        List<Folder> ret = new ArrayList<Folder>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Folder.class);
        criteria.add(Restrictions.eq("fileType", 1));
        criteria.add(Restrictions.eq("projectId",projectId));
        if(name!=null){
        	String test = "";
        	for(int i=0;i<name.length();i++)
        	{
        		if(i==0)
        		{
        			test=test+'%'+name.charAt(i)+'%';
        		}else{
        			test=test+name.charAt(i)+'%';
        		}
        		
        	}
        	 criteria.add(Restrictions.like("name", test));
        }
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }
    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<Folder>> getFolderLists(Folder folder) {
        DataWrapper<List<Folder>> retDataWrapper = new DataWrapper<List<Folder>>();
        List<Folder> ret = new ArrayList<Folder>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Folder.class);
        criteria.add(Restrictions.eq("fileType", 1));
        if(folder.getProjectId()!=null){
        	criteria.add(Restrictions.eq("projectId", folder.getProjectId()));
        }
        if(folder.getName()!=null){
        	 criteria.add(Restrictions.eq("name", folder.getName()));
        }
        if(folder.getParrentId()!=null){
        	criteria.add(Restrictions.eq("parrentId", folder.getParrentId()));
        }
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        retDataWrapper.setData(ret);
        return retDataWrapper;
    }

	@Override
	public Folder getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<Folder> getByName(String name) {
		DataWrapper<Folder> dataWrapper = new DataWrapper<Folder>();
		String sql="select * from floder where name="+name;
		Session session=getSession();
		 try{
	            Query query=session.createSQLQuery(sql).addEntity(Folder.class);
	            List<Folder> test=query.list();
	            dataWrapper.setData(test.get(0));
	        }catch(Exception e){
	            e.printStackTrace();
	            dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
	        }
		 
		return dataWrapper;
	}
	
	@SuppressWarnings("unchecked")
	@Override
    public List<Folder> getAllFolder() {
        List<Folder> ret = new ArrayList<Folder>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Folder.class);
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }
	@SuppressWarnings("unchecked")
	@Override
    public List<Folder> getAllFolderss() {
        List<Folder> ret = new ArrayList<Folder>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Folder.class);
        criteria.add(Restrictions.eq("fileType", 0));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }

	@Override
	public boolean deleteFloderList(String[] ids) {
		// TODO Auto-generated method stub
		boolean bool=false;
		for(String s:ids){
			bool=delete(get(Long.valueOf(s)));
		}
		return bool;
	}

	@Override
	public List<Folder> getFolderIndexList(Long projectId, Long pid) {
        List<Folder> ret = new ArrayList<Folder>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Folder.class);
        //criteria.add(Restrictions.eq("fileType", 0));
        if(projectId!=null){
        	criteria.add(Restrictions.eq("projectId", projectId));
        }
        if(pid!=null){
        	 criteria.add(Restrictions.eq("parrentId", pid));
        }
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
	}
	@Override
	public List<Folder> getByIds(String ids) {
        List<Folder> ret = new ArrayList<Folder>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Folder.class);
        //criteria.add(Restrictions.eq("fileType", 0));
        Disjunction dis = Restrictions.disjunction();
        if(ids!=null){
        	String[] list = ids.split(",");
        	for(String id:list){
        		dis.add(Restrictions.eq("id", Long.valueOf(id)));
        	}
        	criteria.add(dis);
        }
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
	}
}
