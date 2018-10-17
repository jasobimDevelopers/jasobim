package com.my.spring.service;

import com.my.spring.model.Message;
import com.my.spring.model.MessageCopyPojo;
import com.my.spring.model.MessagePojo;
import com.my.spring.utils.DataWrapper;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

public interface MessageService {
    //DataWrapper<Void> updateMessage(Message message,String token);
    DataWrapper<List<MessagePojo>> getMessageList(String token, Integer pageIndex, Integer pageSize, Message message);
    DataWrapper<List<Message>> getMessageListByUserId(Long userId,String token);
	DataWrapper<Void> addMessage(Message message, String token, MultipartFile[] file, 
			HttpServletRequest request);
	DataWrapper<Void> deleteMessage(Long id, String token, HttpServletRequest request);
	DataWrapper<List<MessagePojo>> getMessageListByQuestionId(Long questionId, String token, String weixin);
	DataWrapper<Void> updateMessage(Message message, String token);
	DataWrapper<Message> getMessageListById(Long id, String token);
	DataWrapper<List<MessageCopyPojo>> getMessageListOfNotRead(String token,Integer pageSize,Integer pageIndex);
	DataWrapper<List<MessagePojo>> getMessageListByQualityId(Long qualityId, String token, String weixin);
}
