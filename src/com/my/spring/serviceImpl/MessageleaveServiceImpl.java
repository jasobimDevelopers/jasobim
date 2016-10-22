package com.my.spring.serviceImpl;

import com.my.spring.DAO.MessageleaveDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Messageleave;
import com.my.spring.service.MessageleaveService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Service("messageleaveService")
public class MessageleaveServiceImpl implements MessageleaveService {
    @Autowired
    MessageleaveDao MessageleaveDao;
    @Override
    public DataWrapper<Void> addMessageleave(Messageleave messageleave) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        if(!MessageleaveDao.addMessageleave(messageleave)) {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<Void> deleteMessageleave(Long id) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        if(!MessageleaveDao.deleteMessageleave(id)) {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<Void> updateMessageleave(Messageleave messageleave) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        if(!MessageleaveDao.updateMessageleave(messageleave)) {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<List<Messageleave>> getMessageleaveList() {
        return MessageleaveDao.getMessageleaveList();
    }
}
