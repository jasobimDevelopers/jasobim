package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.QuestionFileDao;
import com.my.spring.model.QuestionFile;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class QuestionFileDaoImpl extends BaseDao<QuestionFile> implements QuestionFileDao {

    @Override
    public boolean addQuestionFile(QuestionFile questionFile) {
        return save(questionFile);
    }

    @Override
    public boolean deleteQuestionFile(Long id) {
        return delete(get(id));
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<QuestionFile>> getQuestionFileList() {
        DataWrapper<List<QuestionFile>> retDataWrapper = new DataWrapper<List<QuestionFile>>();
        List<QuestionFile> ret = new ArrayList<QuestionFile>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(QuestionFile.class);
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
	public DataWrapper<List<QuestionFile>> getQuestionFileListByUserId(Long userId) {
		 DataWrapper<List<QuestionFile>> retDataWrapper = new DataWrapper<List<QuestionFile>>();
	        
	        //ret=find("select * from User where userId=?"+userId);
		List<QuestionFile> ret = null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(QuestionFile.class);
        criteria.add(Restrictions.eq("userId",userId));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (ret != null && ret.size() > 0) {
			retDataWrapper.setData(ret);
		}
		return retDataWrapper;
	}

	@Override
	public boolean deleteQuestionFileByQuestionId(Long id) {
		String sql = "delete from question_file where question_id="+id;
		Session session=getSession();
		boolean temp=true;
		 try{
			 Query query = session.createSQLQuery(sql);
			 if(query.executeUpdate()!=1){
			 }
	        }catch(Exception e){
	            e.printStackTrace();
	        }
		return temp;
	}
	@Override
	public boolean deleteQuestionFileByQualityId(Long id) {
		String sql = "delete from question_file where quality_id="+id;
		Session session=getSession();
		boolean temp=true;
		 try{
			 Query query = session.createSQLQuery(sql);
			 if(query.executeUpdate()!=1){
			 }
	        }catch(Exception e){
	            e.printStackTrace();
	        }
		return temp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<QuestionFile>> getQuestionFileByQuestionId(Long questionId) {
		DataWrapper<List<QuestionFile>> retDataWrapper = new DataWrapper<List<QuestionFile>>();
		List<QuestionFile> ret = null;
		Session session = getSession();
		Criteria criteria = session.createCriteria(QuestionFile.class);
		criteria.add(Restrictions.eq("questionId",questionId));
		try {
			ret = criteria.list();
		}catch (Exception e){
			e.printStackTrace();
		}
		if (ret != null && ret.size() > 0) {
			retDataWrapper.setData(ret);
		}
		return retDataWrapper;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<QuestionFile>> getQuestionFileByQualityId(Long qualityId) {
		DataWrapper<List<QuestionFile>> retDataWrapper = new DataWrapper<List<QuestionFile>>();
		List<QuestionFile> ret = null;
		Session session = getSession();
		Criteria criteria = session.createCriteria(QuestionFile.class);
		criteria.add(Restrictions.eq("qualityId",qualityId));
		try {
			ret = criteria.list();
		}catch (Exception e){
			e.printStackTrace();
		}
		if (ret != null && ret.size() > 0) {
			retDataWrapper.setData(ret);
		}
		return retDataWrapper;
	}
}
