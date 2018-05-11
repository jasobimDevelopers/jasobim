package com.my.spring.DAO;

import com.my.spring.model.Message;
import com.my.spring.model.MessageCopy;
import com.my.spring.utils.DataWrapper;

import java.util.List;

public interface MessageDao {
	boolean addMessage(Message message);
    boolean deleteMessage(Long id);
    boolean updateMessage(Message message);
    Message getById(Long id);
    DataWrapper<List<Message>> getMessageList(Integer pageIndex, Integer pageSize, Message message);
    DataWrapper<List<Message>> getMessageListByUserId(Long userId);
	DataWrapper<List<Message>> getMessageListByQuestionId(Long questionId);
	boolean deleteMessageByQuestionId(Long projectId);
	Message getMessageListById(Long id);
	DataWrapper<List<Message>> getMessageListByQualityId(Long id);
	boolean deleteMessageByQualityId(Long qualityId);
	List<MessageCopy> getMessageListNotRead(Long id, Integer pageSize, Integer pageIndex);
}
