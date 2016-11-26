package com.my.spring.service;

import com.my.spring.model.MessageFile;
import com.my.spring.model.MessageFilePojo;
import com.my.spring.utils.DataWrapper;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface MessageFileService {
    DataWrapper<List<MessageFile>> getMessageFileList(String token);
    DataWrapper<List<MessageFile>> getMessageFileListByUserId(Long userId,String token);
	DataWrapper<MessageFilePojo> addMessageFile(MessageFile messageFile, String token, MultipartFile file,
			HttpServletRequest request);
	DataWrapper<Void> deleteMessageFile(Long id, Long fileid, String token, 
			HttpServletRequest request);
	
}
