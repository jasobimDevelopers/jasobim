package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.QuestionDao;
import com.my.spring.model.Question;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Repository
public class QuestionDaoImpl extends BaseDao<Question> implements QuestionDao {

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
    public boolean updateQuestion(Question question) {
        return update(question);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<Question>> getQuestionList(Long projectId ,Integer pageIndex, Integer pageSize, Question question) {
    	DataWrapper<List<Question>> retDataWrapper = new DataWrapper<List<Question>>();
        List<Question> ret = new ArrayList<Question>();
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
        retDataWrapper.setData(ret);
        retDataWrapper.setTotalNumber(totalItemNum);
        retDataWrapper.setCurrentPage(pageIndex);
        retDataWrapper.setTotalPage(totalPageNum);
        retDataWrapper.setNumberPerPage(pageSize);
        return retDataWrapper;
    }

	@Override
	public Question getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}
}
