package com.my.spring.DAO;

import com.my.spring.model.Message;
import com.my.spring.utils.DataWrapper;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface MessageDao {
	boolean addMessage(Message message);
    boolean deleteMessage(Long id);
    boolean updateMessage(Message message);
    DataWrapper<List<Message>> getMessageList();
}
