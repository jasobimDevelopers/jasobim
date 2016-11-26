package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.FileDao;
import com.my.spring.DAO.QuestionDao;
import com.my.spring.DAO.QuestionFileDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.model.Files;
import com.my.spring.model.Question;
import com.my.spring.model.QuestionFile;
import com.my.spring.model.QuestionPojo;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
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
    public DataWrapper<List<QuestionPojo>> getQuestionList(Long projectId ,Integer pageIndex, Integer pageSize, Question question) {
    	DataWrapper<List<QuestionPojo>> retDataWrapperPojo = new DataWrapper<List<QuestionPojo>>();
    	DataWrapper<List<QuestionFile>> retDataWrapperPojos = new DataWrapper<List<QuestionFile>>();
        List<Question> ret = new ArrayList<Question>();
        List<QuestionPojo> retpojo = new ArrayList<QuestionPojo>();
        
        Session session = getSession();
        Criteria criteria = session.createCriteria(Question.class);
//        criteria.addOrder(Order.desc("publishDate"));
        criteria.add(Restrictions.eq("projectId", question.getProjectId()));
        if(question.getPriority()!=null){
        	criteria.add(Restrictions.eq("priority", question.getPriority()));
        }
        if(question.getQuestionType()!=null){
        	criteria.add(Restrictions.eq("questionType", question.getQuestionType()));
        }
        if(question.getState()!=null){
        	criteria.add(Restrictions.eq("state", question.getState()));
        }
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
        for(int i=0;i<ret.size();i++)
        {
        	String[] fileLists=new String[10];
        	String[] fileNameLists=new String[10];
        	int flag=0;
        	QuestionPojo questionpojo=new QuestionPojo();
        	retDataWrapperPojos=questionFileDao.getQuestionFileByQuestionId(ret.get(i).getId());
        	if(retDataWrapperPojos.getData()!=null)
        	{
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
        	questionpojo.setProjectId(projectId);
        	questionpojo.setQuestionDate(ret.get(i).getQuestionDate());
        	questionpojo.setQuestionType(ret.get(i).getQuestionType());
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
	public Question getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}
}
