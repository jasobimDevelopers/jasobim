package com.my.spring.serviceImpl;

import com.my.spring.DAO.MessageDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Message;
import com.my.spring.service.MessageService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Service("messageService")
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageDao MessageDao;
    @Override
    public DataWrapper<Void> addMessage(Message message) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        if(!MessageDao.addMessage(message)) {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<Void> deleteMessage(Long id) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        if(!MessageDao.deleteMessage(id)) {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<Void> updateMessage(Message message) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        if(!MessageDao.updateMessage(message)) {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<List<Message>> getMessageList() {
        return MessageDao.getMessageList();
    }
}
