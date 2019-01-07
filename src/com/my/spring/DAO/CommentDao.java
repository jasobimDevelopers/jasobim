package com.my.spring.DAO;

import com.my.spring.model.Comment;
import com.my.spring.utils.DataWrapper;
import java.util.List;

public interface CommentDao {

	boolean addComment(Comment Comment);

	boolean deleteComment(String[] ids);

	Comment getById(Long id);

	DataWrapper<List<Comment>> getCommentList(Integer pageSize, Integer pageIndex, Comment Comment);
}
