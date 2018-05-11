package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.FileDao;
import com.my.spring.DAO.QualityQuestionDao;
import com.my.spring.DAO.QuestionFileDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.model.Files;
import com.my.spring.model.QualityQuestion;
import com.my.spring.model.QualityQuestionPojo;
import com.my.spring.model.Question;
import com.my.spring.model.QuestionCopy;
import com.my.spring.model.QuestionFile;
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
public class QualityQuestionDaoImpl extends BaseDao<QualityQuestion> implements QualityQuestionDao {
    @Autowired
	private UserDao userDao;
    @Autowired
	private QuestionFileDao questionFileDao;
    @Autowired
	private FileDao filesDao;
	
    
    @Override
    public boolean addQualityQuestion(QualityQuestion question) {
        return save(question);
    }

	@Override
    public boolean deleteQualityQuestion(Long id,Long projectId) {
    	String sql = "delete from quality_question where id="+id+" and project_id="+projectId;
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
    public boolean deleteQualityQuestion(Long id) {
    	String sql = "delete from quality_question where id="+id;
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
    public boolean deleteQualityQuestionByProjectId(Long projectId) {
    	String sql = "delete from quality_question where project_id="+projectId;
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
    public boolean updateQualityQuestion(QualityQuestion question) {
        return update(question);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<QualityQuestion>> getQualityQuestionList(String content,Long projectId ,Integer pageIndex, Integer pageSize, QualityQuestion question,Long[] userIdList,String projectList) {
    	DataWrapper<List<QualityQuestion>> retDataWrapperPojo = new DataWrapper<List<QualityQuestion>>();
        List<QualityQuestion> ret = new ArrayList<QualityQuestion>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(QualityQuestion.class);
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
	public QualityQuestion getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

	@Override
	public Long getQualityQuestionListOfSort() {
		Session session=getSession();
		int flag=0;
		Query query=session.createSQLQuery("select count(*) from quality_question where priority=" + flag);
		return  Long.parseLong(query.list().get(0).toString());
	}

	@Override
	public Long getQualityQuestionListOfImportant() {
		Session session=getSession();
		int flag=1;
		Query query=session.createSQLQuery("select count(*) from quality_question where priority=" + flag);
		return  Long.parseLong(query.list().get(0).toString());
	}

	@Override
	public Long getQualityQuestionListOfUrgent() {
		Session session=getSession();
		int flag=2;
		Query query=session.createSQLQuery("select count(*) from quality_question where priority=" + flag);
		return  Long.parseLong(query.list().get(0).toString());
	}

	@Override
	public Long getQualityQuestionList() {
		Session session=getSession();
		Query query=session.createSQLQuery("select count(*) from quality_question");
		return  Long.parseLong(query.list().get(0).toString());
	}

	@Override
	public DataWrapper<List<QualityQuestion>> getQualityQuestionListByLike(String content) {
		@SuppressWarnings("unused")
		String sql="SELECT * FROM quality_question WHERE CONCAT(IFNULL(name,''),IFNULL(intro,''),IFNULL(trades,''),IFNULL(question_date,'')) LIKE '%"+content+"%'" ; 
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<QualityQuestionPojo>> getQualityQuestionList(Integer pageIndex, Integer pageSize, QualityQuestion question) {
		DataWrapper<List<QualityQuestionPojo>> retDataWrapperPojo = new DataWrapper<List<QualityQuestionPojo>>();
    	DataWrapper<List<QuestionFile>> retDataWrapperPojos = new DataWrapper<List<QuestionFile>>();
        List<Question> ret = new ArrayList<Question>();
        List<QualityQuestionPojo> retpojo = new ArrayList<QualityQuestionPojo>();
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
        	QualityQuestionPojo questionpojo=new QualityQuestionPojo();
        	if(ret.get(i).getUserList()!=null){
        		String[] nameList = new String[ret.get(i).getUserList().split(",").length];
        		for(int k=0;k<nameList.length;k++){
        			nameList[k]=userDao.getById(Long.valueOf(nameList[k])).getRealName();
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
	public List<QuestionCopy> getQuestionListByAdmin(Long userId,Integer pageIndex, Integer pageSize) {
		if(pageIndex==null){
			pageIndex=1;
		}
		if(pageSize==null){
			pageSize=10;
		}
		//select a.* from question a where a.project_id in (select c.project_id from user_project c where c.user_id=33)
		List<QuestionCopy> retDataWrapper = new ArrayList<QuestionCopy>();
		String sql = "select a.* from quality_question a,notice b where b.notice_type=0 "
		+"and b.read_state=0 and b.about_id=a.id and a.user_id!="+userId;
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
	public List<QuestionCopy> getQuestionListByLeader(Long id, Integer pageIndex, Integer pageSize) {
		if(pageIndex==null){
			pageIndex=1;
		}
		if(pageSize==null){
			pageSize=10;
		}
		//select a.* from question a where a.project_id in (select c.project_id from user_project c where c.user_id=33)
		List<QuestionCopy> retDataWrapper = new ArrayList<QuestionCopy>();
		String sql = "select *,COUNT(1) as total from quality_question where id in(select e.about_id "
		+"from notice e where e.notice_type=0 and e.user_id="+id+" and e.read_state=0 "
		+"and (e.about_id in (select a.id from question a where a.project_id "
		+"in (select c.project_id from user_project c where c.user_id="+id+"))))";
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
				 .addScalar("total", StandardBasicTypes.INTEGER)
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
		String sql = "select b.*,COUNT(1) as total from notice a,quality_question b where a.user_id="+id
		+" and a.read_state=0 and a.notice_type=0 and a.about_id=b.id";
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
					 .addScalar("total",StandardBasicTypes.INTEGER)
				 .setResultTransformer(Transformers.aliasToBean(QuestionCopy.class)); 
		    retDataWrapper=query.list();
            
        }catch(Exception e){
            e.printStackTrace();
            //dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
        }
		return retDataWrapper;
	}
	public DataWrapper<List<QualityQuestion>> getQualityQuestionLists(Integer searchType,String content, Long projectId, Integer pageIndex, Integer pageSize, QualityQuestion question, Long[] userIdList) {
	DataWrapper<List<QualityQuestion>> retDataWrapperPojo = new DataWrapper<List<QualityQuestion>>();
    List<QualityQuestion> ret = new ArrayList<QualityQuestion>();
    Session session = getSession();
    Criteria criteria = session.createCriteria(QualityQuestion.class);
    ///////
    criteria.addOrder(Order.desc("questionDate"));
    /////////
    if(question.getProjectId()!=null){
        criteria.add(Restrictions.eq("projectId", question.getProjectId()));
    } 
    if(question.getId()!=null){
        criteria.add(Restrictions.eq("id", question.getId()));
    } 
    /////通过创建人搜索
    if(searchType!=null){
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
}
