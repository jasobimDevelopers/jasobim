package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.ElementDao;
import com.my.spring.model.Element;
import com.my.spring.model.UserEntity;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Repository
public class ElementDaoImpl extends BaseDao<Element> implements ElementDao {

    @Override
    public boolean addElement(Element Element) {
        return save(Element);
    }

    @Override
    public boolean deleteElement(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updateElement(Element Element) {
        return update(Element);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<Element>> getElementList() {
        DataWrapper<List<Element>> retDataWrapper = new DataWrapper<List<Element>>();
        List<Element> ret = new ArrayList<Element>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Element.class);
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
	public List<Element> getElementByLocation(String location) {
		//String sql = "select user.* from t_user user,t_token token where token.token = " + token + " and token.user_id = user.id";
		char str1 = 0;
		for(int i=location.length()-1;i>=0;i--){
			if(location.charAt(i)>='A' && location.charAt(i)<='Z'){
				str1=location.charAt(i);
				break;
			}
		}
		int temp=str1+1;
		char str2=(char) temp;
        String location2=location+str2+"%";
		String sql = "select * from element where location LIKE "+location2;
		Session session = getSession();
        Query query = session.createSQLQuery(sql)
                .addEntity(Element.class);

        List<Element> elementList = query.list();
        if(elementList != null && elementList.size() > 0) {
            return elementList;
        }else {
            return null;
        }
	}
}
