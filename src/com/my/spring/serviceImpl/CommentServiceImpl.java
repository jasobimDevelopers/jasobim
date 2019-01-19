package com.my.spring.serviceImpl;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.my.spring.DAO.CommentDao;
import com.my.spring.DAO.FileDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.Comment;
import com.my.spring.model.CommentPojo;
import com.my.spring.model.Files;
import com.my.spring.model.User;
import com.my.spring.service.CommentService;
import com.my.spring.service.FileService;
import com.my.spring.service.UserLogService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;


@Service("commentService")
public class CommentServiceImpl implements CommentService {
	@Autowired
	CommentDao CommentDao;
	@Autowired
	UserDao userDao;
	@Autowired
	UserLogService userLogService;
	@Autowired
    FileDao fileDao;
	@Autowired
	FileService fileService;
	private String filePath = "/files/comment";
	@Override
	public DataWrapper<List<CommentPojo>> getCommentList(Integer pageIndex, Integer pageSize, Comment Comment, String token) {
		// TODO Auto-generated method stub
		DataWrapper<List<Comment>> dataWrapper = new DataWrapper<List<Comment>>();
		List<CommentPojo> Commentpojo = new ArrayList<CommentPojo>();
		
		DataWrapper<List<CommentPojo>> dataWrapperpojo = new DataWrapper<List<CommentPojo>>();
		User adminInMemory = SessionManager.getSession(token);
		if (adminInMemory != null) {
			User adminInDB = userDao.getById(adminInMemory.getId());
			if (adminInDB.getUserType() == UserTypeEnum.Admin.getType()) {
				dataWrapper=CommentDao.getCommentList(pageSize, pageIndex,Comment);
				if(dataWrapper.getData()!=null){
					for(int i=0;i<dataWrapper.getData().size();i++){
						CommentPojo Commentpojos=new CommentPojo();
						Commentpojos.setCommentContent(dataWrapper.getData().get(i).getCommentContent());
						Commentpojos.setId(dataWrapper.getData().get(i).getId());
						User user =userDao.getById(dataWrapper.getData().get(i).getCommentUser());
						if(user!=null){
							Commentpojos.setCommentUserName(user.getRealName());
							if(user.getUserIconUrl()!=null){
								Commentpojos.setCommentUserIcon(user.getUserIconUrl());
							}else if(user.getUserIcon()!=null){
								Files usericon = fileService.getById(user.getUserIcon());
								if(usericon!=null){
									Commentpojos.setCommentUserIcon(usericon.getUrl());
								}
							}
						}
						if(dataWrapper.getData().get(i).getPictures()!=null && !dataWrapper.getData().get(i).getPictures().equals("")){
							List<Files> files1=fileDao.getByIds(dataWrapper.getData().get(i).getPictures());
							if(!files1.isEmpty()){
								List<String> pics = new ArrayList<String>();
								for(Files f:files1){
									pics.add(f.getUrl());
								}
								Commentpojos.setPictures(pics);
							}
						}
						if(dataWrapper.getData().get(i).getVoices()!=null && !dataWrapper.getData().get(i).getVoices().equals("")){
							List<Files> files2=fileDao.getByIds(dataWrapper.getData().get(i).getVoices());
							if(!files2.isEmpty()){
								List<String> vs = new ArrayList<String>();
								for(Files f:files2){
									vs.add(f.getUrl());
								}
								Commentpojos.setVoices(vs);
							}
						}
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    	    		String str=sdf.format(dataWrapper.getData().get(i).getCreateDate()); 
	    	    		Commentpojos.setCreateDate(str);
	    	    		Commentpojo.add(Commentpojos);
					}
					dataWrapperpojo.setData(Commentpojo);
					dataWrapperpojo.setCallStatus(dataWrapper.getCallStatus());
					dataWrapperpojo.setCurrentPage(dataWrapper.getCurrentPage());
					dataWrapperpojo.setNumberPerPage(dataWrapper.getNumberPerPage());
					dataWrapperpojo.setTotalNumber(dataWrapper.getTotalNumber());
					dataWrapperpojo.setTotalPage(dataWrapper.getTotalPage());
					dataWrapperpojo.setErrorCode(dataWrapper.getErrorCode());
				}
			} else {
				dataWrapperpojo.setErrorCode(ErrorCodeEnum.AUTH_Error);
			}
		} else {
			dataWrapperpojo.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dataWrapperpojo;
	}


	

	@Override
	public DataWrapper<Void> addComment(String token,MultipartFile[] pics,MultipartFile[] vois,HttpServletRequest request,Comment comment) {
		DataWrapper<Void> dataWrapper = new DataWrapper<>();
		Comment Comment=new Comment();
		User userInMemory=SessionManager.getSession(token);
		if(userInMemory!=null){
			Comment.setCommentContent(comment.getCommentContent());
			Comment.setCreateDate(new Date());
			Comment.setCommentUser(userInMemory.getId());
			Comment.setAboutId(comment.getAboutId());
			Comment.setReplyType(comment.getReplyType());
			if(pics!=null){
				String picsStr="";
				for(int i=0;i<pics.length;i++){
					Files file=fileService.uploadFile(filePath+"/pictures", pics[i], 3, request);
					if(i==(pics.length-1)){
						picsStr=picsStr+file.getId();
					}else{
						picsStr=picsStr+file.getId()+",";
					}
				}
				Comment.setPictures(picsStr);
			}
			if(vois!=null){
				String voisStr="";
				for(int i=0;i<vois.length;i++){
					Files file=fileService.uploadFile(filePath+"/voices", vois[i], 3, request);
					if(i==(vois.length-1)){
						voisStr=voisStr+file.getId();
					}else{
						voisStr=voisStr+file.getId()+",";
					}
				}
				Comment.setVoices(voisStr);
			}
			if(!CommentDao.addComment(Comment)) {
				dataWrapper.setErrorCode(ErrorCodeEnum.Error);
			}
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		
		return dataWrapper;
	}

}
