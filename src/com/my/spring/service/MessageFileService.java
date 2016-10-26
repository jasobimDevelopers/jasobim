package com.my.spring.service;

import com.my.spring.model.MessageFile;
import com.my.spring.utils.DataWrapper;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface MessageFileService {
	DataWrapper<Void> addMessageFile(MessageFile MessageFile,String token);
    DataWrapper<List<MessageFile>> getMessageFileList(String token);
    DataWrapper<List<MessageFile>> getMessageFileListByUserId(Long userId,String token);
	DataWrapper<Void> deleteMessageFile(Long id,String token);
}
