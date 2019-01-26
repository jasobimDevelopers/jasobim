package com.my.spring.serviceImpl;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.my.spring.DAO.ReplyDao;
import com.my.spring.DAO.FileDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Reply;
import com.my.spring.model.ReplyPojo;
import com.my.spring.model.Files;
import com.my.spring.model.User;
import com.my.spring.service.ReplyService;
import com.my.spring.service.FileService;
import com.my.spring.service.UserLogService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;


@Service("replyService")
public class ReplyServiceImpl implements ReplyService {
	@Autowired
	ReplyDao ReplyDao;
	@Autowired
	UserDao userDao;
	@Autowired
	UserLogService userLogService;
	@Autowired
    FileDao fileDao;
	@Autowired
	FileService fileService;
	private String filePath = "/files/Reply";
	@Override
	public DataWrapper<List<ReplyPojo>> getReplyList(Integer pageIndex, Integer pageSize, Reply Reply, String token) {
		// TODO Auto-generated method stub
		DataWrapper<List<Reply>> dataWrapper = new DataWrapper<List<Reply>>();
		List<ReplyPojo> Replypojo = new ArrayList<ReplyPojo>();
		
		DataWrapper<List<ReplyPojo>> dataWrapperpojo = new DataWrapper<List<ReplyPojo>>();
		User adminInMemory = SessionManager.getSession(token);
		if (adminInMemory != null) {
			dataWrapper=ReplyDao.getReplyList(pageSize, pageIndex,Reply);
			if(dataWrapper.getData()!=null){
				for(int i=0;i<dataWrapper.getData().size();i++){
					ReplyPojo Replypojos=new ReplyPojo();
					Replypojos.setReplyContent(dataWrapper.getData().get(i).getReplyContent());
					Replypojos.setId(dataWrapper.getData().get(i).getId());
					User user =userDao.getById(dataWrapper.getData().get(i).getReplyUser());
					Replypojos.setSchedule(dataWrapper.getData().get(i).getSchedule());
					if(user!=null){
						Replypojos.setReplyUserName(user.getRealName());
						if(user.getUserIconUrl()!=null){
							Replypojos.setReplyUserIcon(user.getUserIconUrl());
						}else if(user.getUserIcon()!=null){
							Files usericon = fileService.getById(user.getUserIcon());
							if(usericon!=null){
								Replypojos.setReplyUserIcon(usericon.getUrl());
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
							Replypojos.setPictures(pics);
						}
					}
					if(dataWrapper.getData().get(i).getVoices()!=null && !dataWrapper.getData().get(i).getVoices().equals("")){
						List<Files> files2=fileDao.getByIds(dataWrapper.getData().get(i).getVoices());
						if(!files2.isEmpty()){
							List<String> vs = new ArrayList<String>();
							for(Files f:files2){
								vs.add(f.getUrl());
							}
							Replypojos.setVoices(vs);
						}
					}
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
    	    		String str=sdf.format(dataWrapper.getData().get(i).getCreateDate()); 
    	    		Replypojos.setCreateDate(str);
    	    		Replypojo.add(Replypojos);
				}
				dataWrapperpojo.setData(Replypojo);
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
		return dataWrapperpojo;
	}


	

	@Override
	public DataWrapper<Void> addReply(String token,MultipartFile[] pics,MultipartFile[] vois,HttpServletRequest request,Reply Reply) {
		DataWrapper<Void> dataWrapper = new DataWrapper<>();
		Reply reply=new Reply();
		User userInMemory=SessionManager.getSession(token);
		if(userInMemory!=null){
			reply.setReplyContent(Reply.getReplyContent());
			reply.setCreateDate(new Date());
			reply.setReplyUser(userInMemory.getId());
			reply.setAboutId(Reply.getAboutId());
			reply.setReplyType(Reply.getReplyType());
			reply.setSchedule(Reply.getSchedule());
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
				reply.setPictures(picsStr);
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
				reply.setVoices(voisStr);
			}
			if(!ReplyDao.addReply(reply)) {
				dataWrapper.setErrorCode(ErrorCodeEnum.Error);
			}
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		
		return dataWrapper;
	}

}
