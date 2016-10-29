package com.my.spring.service;

import com.my.spring.model.Message;
import com.my.spring.utils.DataWrapper;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface MessageService {
    //DataWrapper<Void> updateMessage(Message message,String token);
    DataWrapper<List<Message>> getMessageList(String token);
    DataWrapper<List<Message>> getMessageListByUserId(Long userId,String token);
	DataWrapper<Void> addMessage(Message message, String token, MultipartFile file, 
			HttpServletRequest request);
	DataWrapper<Void> deleteMessage(Long id, String token, HttpServletRequest request);
}
