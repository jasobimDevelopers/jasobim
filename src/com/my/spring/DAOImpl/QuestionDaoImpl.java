package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.QuestionDao;
import com.my.spring.model.Question;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Session;
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
    public boolean deleteQuestion(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updateQuestion(Question question) {
        return update(question);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<Question>> getQuestionList() {
        DataWrapper<List<Question>> retDataWrapper = new DataWrapper<List<Question>>();
        List<Question> ret = new ArrayList<Question>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Question.class);
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
	public Question getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}
}
