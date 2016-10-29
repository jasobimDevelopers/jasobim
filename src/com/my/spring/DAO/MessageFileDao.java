package com.my.spring.DAO;

import com.my.spring.model.MessageFile;
import com.my.spring.utils.DataWrapper;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface MessageFileDao {
	boolean addMessageFile(MessageFile messageFile);
    boolean deleteMessageFile(Long id);
    DataWrapper<List<MessageFile>> getMessageFileList();
    DataWrapper<List<MessageFile>> getMessageFileListByUserId(Long userId);
	boolean deleteMessageFileByMessageId(Long id);
}
