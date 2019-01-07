package com.my.spring.DAO;

import com.my.spring.model.Reply;
import com.my.spring.utils.DataWrapper;
import java.util.List;

public interface ReplyDao {

	boolean addReply(Reply Reply);

	Reply getById(Long id);

	DataWrapper<List<Reply>> getReplyList(Integer pageSize, Integer pageIndex, Reply Reply);
}
