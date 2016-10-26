package com.my.spring.serviceImpl;

import com.my.spring.DAO.MessageFileDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.MessageFile;
import com.my.spring.service.MessageFileService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Service("messageFileService")
public class MessageFileServiceImpl implements MessageFileService {
    @Autowired
    MessageFileDao messageFileDao;
    @Override
    public DataWrapper<Void> addMessageFile(MessageFile MessageFile,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        if(!messageFileDao.addMessageFile(MessageFile)) {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<Void> deleteMessageFile(Long id,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        if(!messageFileDao.deleteMessageFile(id)) {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }


    @Override
    public DataWrapper<List<MessageFile>> getMessageFileList(String token ) {
        return messageFileDao.getMessageFileList();
    }

	@Override
	public DataWrapper<List<MessageFile>> getMessageFileListByUserId(Long userId,String token) {
		// TODO Auto-generated method stub
		return messageFileDao.getMessageFileListByUserId(userId);
	}

}
