package com.my.spring.service;

import com.my.spring.model.Message;
import com.my.spring.utils.DataWrapper;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface MessageService {
	DataWrapper<Void> addMessage(Message message);
    DataWrapper<Void> deleteMessage(Long id);
    DataWrapper<Void> updateMessage(Message message);
    DataWrapper<List<Message>> getMessageList();
}
