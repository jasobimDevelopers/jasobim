package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.QuestionFileDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.MessageFile;
import com.my.spring.model.QuestionFile;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
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
	public DataWrapper<List<QuestionFile>> deleteQuestionFileByQuestionId(Long id) {
		DataWrapper<List<QuestionFile>> questionFiles=new DataWrapper<List<QuestionFile>>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(MessageFile.class);
        criteria.add(Restrictions.eq("messageId",id));
        try {
        	questionFiles.setData( criteria.list());
        }catch (Exception e){
            e.printStackTrace();
        }
        if (questionFiles.getData() != null && questionFiles.getData().size() > 0) {
        	questionFiles.setErrorCode(ErrorCodeEnum.Error);
			return questionFiles;
		}else{
			questionFiles.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
		}
		return questionFiles;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<QuestionFile>> getQuestionFileByQuestionId(Long questionId) {
		// TODO Auto-generated method stub
		DataWrapper<List<QuestionFile>> retDataWrapper = new DataWrapper<List<QuestionFile>>();
        
        //ret=find("select * from User where userId=?"+userId);
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
}
