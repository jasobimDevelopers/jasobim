package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.MessageFileDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.MessageFile;
import com.my.spring.model.User;
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
public class MessageFileDaoImpl extends BaseDao<MessageFile> implements MessageFileDao {

    @Override
    public boolean addMessageFile(MessageFile MessageFile) {
        return save(MessageFile);
    }

    @Override
    public boolean deleteMessageFile(Long id) {
        return delete(get(id));
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<MessageFile>> getMessageFileList() {
        DataWrapper<List<MessageFile>> retDataWrapper = new DataWrapper<List<MessageFile>>();
        List<MessageFile> ret = new ArrayList<MessageFile>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(MessageFile.class);
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
	public DataWrapper<List<MessageFile>> getMessageFileListByUserId(Long userId) {
		 DataWrapper<List<MessageFile>> retDataWrapper = new DataWrapper<List<MessageFile>>();
	        
	        //ret=find("select * from User where userId=?"+userId);
		List<MessageFile> ret = null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(MessageFile.class);
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
	public DataWrapper<List<MessageFile>> deleteMessageFileByMessageId(Long id) {
		DataWrapper<List<MessageFile>> messageFiles=new DataWrapper<List<MessageFile>>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(MessageFile.class);
        criteria.add(Restrictions.eq("messageId",id));
        try {
        	messageFiles.setData( criteria.list());
        }catch (Exception e){
            e.printStackTrace();
        }
        if (messageFiles.getData() != null && messageFiles.getData().size() > 0) {
        	messageFiles.setErrorCode(ErrorCodeEnum.Error);
			return messageFiles;
		}else{
			messageFiles.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
		}
		return messageFiles;
	}
}
