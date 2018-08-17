package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.FileDao;
import com.my.spring.DAO.QuestionDao;
import com.my.spring.DAO.QuestionFileDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Files;
import com.my.spring.model.Projectvs;
import com.my.spring.model.Question;
import com.my.spring.model.QuestionCopy;
import com.my.spring.model.QuestionFile;
import com.my.spring.model.QuestionPojo;
import com.my.spring.model.User;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Repository
public class QuestionDaoImpl extends BaseDao<Question> implements QuestionDao {
    @Autowired
	private UserDao userDao;
    @Autowired
	private QuestionFileDao questionFileDao;
    @Autowired
	private FileDao filesDao;
	
    
    @Override
    public boolean addQuestion(Question question) {
        return save(question);
    }

	@Override
    public boolean deleteQuestion(Long id,Long projectId) {
    	String sql = "delete from question where id="+id+" and project_id="+projectId;
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
    public boolean deleteQuestionByUserId(Long id) {
    	String sql = "delete from question where user_id="+id;
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
    public boolean deleteQuestion(Long id) {
    	String sql = "delete from question where id="+id;
		Session session=getSession();
		boolean bool=false;
		 try{
				 Query query = session.createSQLQuery(sql);
				 int temp=query.executeUpdate();
				 if(temp==1){
					 bool =true;
				 }
	        }catch(Exception e){
	            e.printStackTrace();
	        }
		 
		return bool;
        
    }
	@Override
    public boolean deleteQuestionByProjectId(Long projectId) {
    	String sql = "delete from question where project_id="+projectId;
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
    public boolean updateQuestion(Question question) {
        return update(question);
    }

    @SuppressWarnings("unchecked")
	@Override
	/////searchType(0.1.2.)
    public DataWrapper<List<Question>> getQuestionLists(Integer searchType,String content,Long projectId ,Integer pageIndex, Integer pageSize, Question question,Long[] userIdList) {
    	DataWrapper<List<Question>> retDataWrapperPojo = new DataWrapper<List<Question>>();
        List<Question> ret = new ArrayList<Question>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Question.class);
        ///////
        criteria.addOrder(Order.desc("questionDate"));
        /////////
        if(question.getProjectId()!=null){
            criteria.add(Restrictions.eq("projectId", question.getProjectId()));
        } 
        if(question.getId()!=null){
            criteria.add(Restrictions.eq("id", question.getId()));
        } 
        if(searchType!=null){
        	 /////通过创建人搜索
            if(searchType==0){
            	if(content!=null && !content.equals("")){
                    criteria.add(Restrictions.eq("userId", Long.valueOf(content)));
                } 
            }
            /////通过问题名称
            if(searchType==1){
            	if(content!=null && !content.equals("")){
            		criteria.add(Restrictions.like("name", "%"+content+"%"));
            	}
            }
            /////通过问题详情
            if(searchType==2){
            	if(content!=null && !content.equals("")){
            		criteria.add(Restrictions.like("intro", "%"+content+"%"));
            	}
            }
        }
       
        if (pageSize == null){
			pageSize = 10;
		}
        if (pageIndex == null){
			pageIndex = 1;
		}
        
        // 取总页数
        criteria.setProjection(Projections.rowCount());
        int totalItemNum = ((Long)criteria.uniqueResult()).intValue();
        int totalPageNum = DaoUtil.getTotalPageNumber(totalItemNum, pageSize);
       
        criteria.setProjection(null);
       
        if (pageSize > 0 && pageIndex > 0) {
            criteria.setMaxResults(pageSize);// 最大显示记录数
            criteria.setFirstResult((pageIndex - 1) * pageSize);// 从第几条开始
            /////////////
        }
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        
        retDataWrapperPojo.setData(ret);
        retDataWrapperPojo.setTotalNumber(totalItemNum);
        retDataWrapperPojo.setCurrentPage(pageIndex);
        retDataWrapperPojo.setTotalPage(totalPageNum);
        retDataWrapperPojo.setNumberPerPage(pageSize);
        
        return retDataWrapperPojo;
    }
    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<Question>> getQuestionList(String content,Long projectId ,Integer pageIndex, Integer pageSize, Question question,Long[] userIdList,String projectList) {
    	DataWrapper<List<Question>> retDataWrapperPojo = new DataWrapper<List<Question>>();
        List<Question> ret = new ArrayList<Question>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Question.class);
        ///////
        criteria.addOrder(Order.desc("questionDate"));
        if(projectList!=null && !projectList.equals("null")){
        	String[] ss =projectList.split(",");
            Disjunction dis = Restrictions.disjunction();
            for (int i = 0; i < ss.length; i++) {
            	long test=Integer.valueOf(ss[i]);
                dis.add(Restrictions.eq("projectId", test));
            }
            criteria.add(dis);
        }
        /////////
        if(projectId!=null){
            criteria.add(Restrictions.eq("projectId", question.getProjectId()));
        } 
        if(question.getId()!=null){
            criteria.add(Restrictions.eq("id", question.getId()));
        } 
        if(content!=null && !content.equals("")){
        	String test = "";
        	for(int i=0;i<content.length();i++)
        	{
        		if(i==0)
        		{
        			test=test+'%'+content.charAt(i)+'%';
        		}else{
        			test=test+content.charAt(i)+'%';
        		}
        		
        	}
        	Disjunction dis = Restrictions.disjunction();        	
        	dis.add(Restrictions.like("name", test,MatchMode.ANYWHERE));
        	//dis.add(Restrictions.like("intro", content,MatchMode.ANYWHERE));
        	dis.add(Restrictions.like("trades", test,MatchMode.ANYWHERE));
        	
        	if(question.getPriority()!=null && question.getPriority()!=-1) 
        	{
        		criteria.add(Restrictions.eq("priority", question.getPriority()));
			} 
        	if(userIdList!=null)
        	{
        		for(int i=0;i<userIdList.length;i++){
        			dis.add(Restrictions.eq("userId", userIdList[i]));
        		}
        		
			}
			if(question.getState()!=null)
			{
				criteria.add(Restrictions.eq("state", question.getState()));
			}
			criteria.add(dis);
        }else{
    		 if(question.getPriority()!=null){
            	if(question.getPriority()==-1){
                	Disjunction dis = Restrictions.disjunction();        	
                	dis.add(Restrictions.eq("priority", 1));
                	dis.add(Restrictions.eq("priority", 2));
                	criteria.add(dis);
                }else{
                	criteria.add(Restrictions.eq("priority", question.getPriority()));
                }
             }
             if(question.getState()!=null){
             	criteria.add(Restrictions.eq("state", question.getState()));
             }
        }
        
        if (pageSize == null){
			pageSize = 10;
		}
        if (pageIndex == null){
			pageIndex = 1;
		}
        
        // 取总页数
        criteria.setProjection(Projections.rowCount());
        int totalItemNum = ((Long)criteria.uniqueResult()).intValue();
        int totalPageNum = DaoUtil.getTotalPageNumber(totalItemNum, pageSize);
       
        criteria.setProjection(null);
       
        if (pageSize > 0 && pageIndex > 0) {
            criteria.setMaxResults(pageSize);// 最大显示记录数
            criteria.setFirstResult((pageIndex - 1) * pageSize);// 从第几条开始
            /////////////
        }
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        
        retDataWrapperPojo.setData(ret);
        retDataWrapperPojo.setTotalNumber(totalItemNum);
        retDataWrapperPojo.setCurrentPage(pageIndex);
        retDataWrapperPojo.setTotalPage(totalPageNum);
        retDataWrapperPojo.setNumberPerPage(pageSize);
        
        return retDataWrapperPojo;
    }

	@Override
	public Question getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

	@Override
	public Long getQuestionListOfSort() {
		Session session=getSession();
		int flag=0;
		Query query=session.createSQLQuery("select count(*) from question where priority=" + flag);
		return  Long.parseLong(query.list().get(0).toString());
	}

	@Override
	public Long getQuestionListOfImportant() {
		Session session=getSession();
		int flag=1;
		Query query=session.createSQLQuery("select count(*) from question where priority=" + flag);
		return  Long.parseLong(query.list().get(0).toString());
	}

	@Override
	public Long getQuestionListOfUrgent() {
		Session session=getSession();
		int flag=2;
		Query query=session.createSQLQuery("select count(*) from question where priority=" + flag);
		return  Long.parseLong(query.list().get(0).toString());
	}

	@Override
	public Long getQuestionList() {
		Session session=getSession();
		Query query=session.createSQLQuery("select count(*) from question");
		return  Long.parseLong(query.list().get(0).toString());
	}

	@Override
	public DataWrapper<List<Question>> getQuestionListByLike(String content) {
		@SuppressWarnings("unused")
		String sql="SELECT * FROM question WHERE CONCAT(IFNULL(name,''),IFNULL(intro,''),IFNULL(trades,''),IFNULL(question_date,'')) LIKE '%"+content+"%'" ; 
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<QuestionPojo>> getQuestionList(Integer pageIndex, Integer pageSize, Question question) {
		DataWrapper<List<QuestionPojo>> retDataWrapperPojo = new DataWrapper<List<QuestionPojo>>();
    	DataWrapper<List<QuestionFile>> retDataWrapperPojos = new DataWrapper<List<QuestionFile>>();
        List<Question> ret = new ArrayList<Question>();
        List<QuestionPojo> retpojo = new ArrayList<QuestionPojo>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Question.class);
        criteria.add(Restrictions.eq("userId", question.getUserId()));
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
       
        criteria.setProjection(null);
       
        if (pageSize > 0 && pageIndex > 0) {
            criteria.setMaxResults(pageSize);// 最大显示记录数
            criteria.setFirstResult((pageIndex - 1) * pageSize);// 从第几条开始
            /////////////
            
        }
        //////////////
       
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        for(int i=0;i<ret.size();i++)
        {
        	QuestionPojo questionpojo=new QuestionPojo();
        	if(ret.get(i).getUserList()!=null){
        		List<String> nameList = new ArrayList<String>();
        		String[] nameLists = ret.get(i).getUserList().split(",");
        		for(int k=0;k<nameLists.length;k++){
        			User users = userDao.getById(Long.valueOf(nameLists[k]));
        			nameList.add(users.getRealName());;
        		}
        		questionpojo.setUserNameLists(nameList);
        	}else{
        		questionpojo.setUserNameLists(null);
        	}
        	retDataWrapperPojos=questionFileDao.getQuestionFileByQuestionId(ret.get(i).getId());
        	if(retDataWrapperPojos.getData()!=null)
        	{
        		String[] fileLists=new String[retDataWrapperPojos.getData().size()];
            	String[] fileNameLists=new String[retDataWrapperPojos.getData().size()];
            	int flag=0;
        		for(int j=0;j<retDataWrapperPojos.getData().size();j++)
        		{
        			Long fileId=retDataWrapperPojos.getData().get(j).getFileId();
        			Files file=filesDao.getById(fileId);
        			if(file!=null)
        			{
        				String fileItem=file.getUrl();
        				fileLists[flag]=fileItem;
        				String nameList=retDataWrapperPojos.getData().get(j).getOriginName();
            			if(nameList!=null){
            				fileNameLists[flag]=nameList;
            			}
        				flag++;
        			}
        			
        			
        		}
        		if(fileLists!=null)
        		{
        			questionpojo.setFileList(fileLists);
        		}				
        	}
        	String username=userDao.getById(ret.get(i).getUserId()).getRealName();
        	questionpojo.setUserId(username);
        	questionpojo.setCodeInformation(ret.get(i).getCodeInformation());
        	questionpojo.setPriority(ret.get(i).getPriority());
        	questionpojo.setId(ret.get(i).getId());
        	questionpojo.setIntro(ret.get(i).getIntro());
        	questionpojo.setName(ret.get(i).getName());
        	questionpojo.setProjectId(ret.get(i).getProjectId());
        	questionpojo.setPosition(ret.get(i).getPosition());
        	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        	questionpojo.setQuestionDate(sdf.format(ret.get(i).getQuestionDate()));
        	questionpojo.setState(ret.get(i).getState());
        	questionpojo.setTrades(ret.get(i).getTrades());        	        	
        	retpojo.add(i, questionpojo);
        }
        retDataWrapperPojo.setData(retpojo);
        retDataWrapperPojo.setTotalNumber(totalItemNum);
        retDataWrapperPojo.setCurrentPage(pageIndex);
        retDataWrapperPojo.setTotalPage(totalPageNum);
        retDataWrapperPojo.setNumberPerPage(pageSize);
        
        return retDataWrapperPojo;
	}

	@Override
	public List<QuestionCopy> getQuestionListByLeader(Long id,Integer pageIndex,Integer pageSize) {
		if(pageIndex==null){
			pageIndex=1;
		}
		if(pageSize==null){
			pageSize=10;
		}
		//select a.* from question a where a.project_id in (select c.project_id from user_project c where c.user_id=33)
		List<QuestionCopy> retDataWrapper = new ArrayList<QuestionCopy>();
		String sql = "select * from question where id in(select e.about_id "
		+"from notice e where e.notice_type=1 and e.user_id="+id+" and e.read_state=0 "
		+"and (e.about_id in (select a.id from question a where a.project_id "
		+"in (select c.project_id from user_project c where c.user_id="+id+"))) order by e.create_date DESC)";
		if(pageIndex!=-1){
			sql = sql +" limit "+(pageSize*pageIndex-pageSize)+","+pageSize;
		}
		Session session=getSession();
	    try{
		    Query query = session.createSQLQuery(sql)
				 .addScalar("id",StandardBasicTypes.LONG)
				 .addScalar("user_id", StandardBasicTypes.LONG)
				 .addScalar("name",StandardBasicTypes.STRING)
				 .addScalar("trades",StandardBasicTypes.STRING)
				 .addScalar("intro", StandardBasicTypes.STRING)
				 .addScalar("question_date", StandardBasicTypes.TIMESTAMP)
				 .addScalar("priority", StandardBasicTypes.INTEGER)
				 .addScalar("state", StandardBasicTypes.INTEGER)
				 .addScalar("code_information", StandardBasicTypes.STRING)
				 .addScalar("project_id",StandardBasicTypes.LONG)
				 .addScalar("position", StandardBasicTypes.STRING)
				 .addScalar("user_list", StandardBasicTypes.STRING)
				 .addScalar("voice_id_list", StandardBasicTypes.STRING)
				 .addScalar("model_flag", StandardBasicTypes.STRING)
				 .setResultTransformer(Transformers.aliasToBean(QuestionCopy.class)); 
		    retDataWrapper=query.list();
            
        }catch(Exception e){
            e.printStackTrace();
            //dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
        }
		return retDataWrapper;
	}

	@Override
	public List<QuestionCopy> getQuestionListByAdmin(Long userId,Integer pageIndex, Integer pageSize) {
		if(pageIndex==null){
			pageIndex=1;
		}
		if(pageSize==null){
			pageSize=10;
		}
		//select a.* from question a where a.project_id in (select c.project_id from user_project c where c.user_id=33)
		List<QuestionCopy> retDataWrapper = new ArrayList<QuestionCopy>();
		String sql = "select a.* from question a,notice b where b.notice_type=1 "
		+"and b.read_state=0 and b.about_id=a.id and b.user_id="+userId;
		if(pageIndex!=-1){
			sql = sql +" limit "+(pageSize*pageIndex-pageSize)+","+pageSize;
		}
		Session session=getSession();
	    try{
		    Query query = session.createSQLQuery(sql)
		    		.addScalar("id",StandardBasicTypes.LONG)
					 .addScalar("user_id", StandardBasicTypes.LONG)
					 .addScalar("name",StandardBasicTypes.STRING)
					 .addScalar("trades",StandardBasicTypes.STRING)
					 .addScalar("intro", StandardBasicTypes.STRING)
					 .addScalar("question_date", StandardBasicTypes.DATE)
					 .addScalar("priority", StandardBasicTypes.INTEGER)
					 .addScalar("state", StandardBasicTypes.INTEGER)
					 .addScalar("code_information", StandardBasicTypes.STRING)
					 .addScalar("project_id",StandardBasicTypes.LONG)
					 .addScalar("position", StandardBasicTypes.STRING)
					 .addScalar("user_list", StandardBasicTypes.STRING)
					 .addScalar("voice_id_list", StandardBasicTypes.STRING)
					 .addScalar("model_flag", StandardBasicTypes.STRING)
				 .setResultTransformer(Transformers.aliasToBean(QuestionCopy.class)); 
		    retDataWrapper=query.list();
            
        }catch(Exception e){
            e.printStackTrace();
            //dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
        }
		return retDataWrapper;
	}

	@Override
	public List<QuestionCopy> getQuestionListByNorUser(Long id, Integer pageIndex, Integer pageSize) {
		if(pageIndex==null){
			pageIndex=1;
		}
		if(pageSize==null){
			pageSize=10;
		}
		//select a.* from question a where a.project_id in (select c.project_id from user_project c where c.user_id=33)
		List<QuestionCopy> retDataWrapper = new ArrayList<QuestionCopy>();
		String sql = "select b.* from notice a,question b where a.user_id="+id
		+" and a.read_state=0 and a.notice_type=1 and a.about_id=b.id ORDER BY a.create_date DESC";
		if(pageIndex!=-1){
			sql = sql +" limit "+(pageSize*pageIndex-pageSize)+","+pageSize;
		}
		Session session=getSession();
	    try{
		    Query query = session.createSQLQuery(sql)
		    		.addScalar("id",StandardBasicTypes.LONG)
					 .addScalar("user_id", StandardBasicTypes.LONG)
					 .addScalar("name",StandardBasicTypes.STRING)
					 .addScalar("trades",StandardBasicTypes.STRING)
					 .addScalar("intro", StandardBasicTypes.STRING)
					 .addScalar("question_date", StandardBasicTypes.TIMESTAMP)
					 .addScalar("priority", StandardBasicTypes.INTEGER)
					 .addScalar("state", StandardBasicTypes.INTEGER)
					 .addScalar("code_information", StandardBasicTypes.STRING)
					 .addScalar("project_id",StandardBasicTypes.LONG)
					 .addScalar("position", StandardBasicTypes.STRING)
					 .addScalar("user_list", StandardBasicTypes.STRING)
					 .addScalar("voice_id_list", StandardBasicTypes.STRING)
					 .addScalar("model_flag", StandardBasicTypes.STRING)
				 .setResultTransformer(Transformers.aliasToBean(QuestionCopy.class)); 
		    retDataWrapper=query.list();
            
        }catch(Exception e){
            e.printStackTrace();
            //dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
        }
		return retDataWrapper;
		
	}
}
