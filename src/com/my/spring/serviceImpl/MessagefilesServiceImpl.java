package com.my.spring.serviceImpl;

import com.my.spring.DAO.MessagefilesDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Messagefiles;
import com.my.spring.service.MessagefilesService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Service("messagefilesService")
public class MessagefilesServiceImpl implements MessagefilesService {
    @Autowired
    MessagefilesDao MessagefilesDao;
    @Override
    public DataWrapper<Void> addMessagefiles(Messagefiles messagefiles) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        if(!MessagefilesDao.addMessagefiles(messagefiles)) {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<Void> deleteMessagefiles(Long id) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        if(!MessagefilesDao.deleteMessagefiles(id)) {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<Void> updateMessagefiles(Messagefiles messagefiles) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        if(!MessagefilesDao.updateMessagefiles(messagefiles)) {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<List<Messagefiles>> getMessagefilesList() {
        return MessagefilesDao.getMessagefilesList();
    }
}
