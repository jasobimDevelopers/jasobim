package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.ValueOutputDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.ValueOutput;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ValueOutputDaoImpl extends BaseDao<ValueOutput> implements ValueOutputDao {

    @Override
    public boolean addValueOutput(ValueOutput ValueOutput) {
        return save(ValueOutput);
    }

    @Override
    public boolean deleteValueOutput(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updateValueOutput(ValueOutput ValueOutput) {
        return update(ValueOutput);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<ValueOutput>> getValueOutputList(String projectList) {
        DataWrapper<List<ValueOutput>> retDataWrapper = new DataWrapper<List<ValueOutput>>();
        List<ValueOutput> ret = new ArrayList<ValueOutput>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(ValueOutput.class);
//        criteria.addOrder(Order.desc("publishDate"));
        
        if(projectList!=null && !projectList.equals("null")){
        	String[] ss =projectList.split(",");
            Disjunction dis = Restrictions.disjunction();
            for (int i = 0; i < ss.length; i++) {
            	long test=Integer.valueOf(ss[i]);
                dis.add(Restrictions.eq("projectId", test));
            }
            criteria .add(dis);
        }
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
	public DataWrapper<ValueOutput> getValueOutputByProjectId(Long projectId) {
		DataWrapper<ValueOutput> dataWrapper=new DataWrapper<ValueOutput>();
		List<ValueOutput> ret = new ArrayList<ValueOutput>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(ValueOutput.class);
        criteria.add(Restrictions.eq("projectId",projectId));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (ret != null && ret.size() > 0) {
        	dataWrapper.setData(ret.get(0));
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
		}
		return dataWrapper;
	}

	@Override
	public boolean addValueOutputList(List<ValueOutput> ValueOutputList) {
		// TODO Auto-generated method stub
		return saveList(ValueOutputList);
	}


}
