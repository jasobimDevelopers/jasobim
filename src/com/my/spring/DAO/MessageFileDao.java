package com.my.spring.DAO;

import com.my.spring.model.MessageFile;
import com.my.spring.utils.DataWrapper;

import java.util.List;

public interface MessageFileDao {
	boolean addMessageFile(MessageFile messageFile);
    boolean deleteMessageFile(Long id);
    DataWrapper<List<MessageFile>> getMessageFileList();
    DataWrapper<List<MessageFile>> getMessageFileListByUserId(Long userId);
    DataWrapper<List<MessageFile>> getMessageFileListByMessageId(Long messageId);
    boolean deleteMessageFileByMessageId(Long id);
    boolean deleteMessageFileByMessageIds(Long id);
}
