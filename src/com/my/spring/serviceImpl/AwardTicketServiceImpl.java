package com.my.spring.serviceImpl;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.my.spring.DAO.AwardTicketDao;
import com.my.spring.DAO.FileDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.DAO.UserIndexDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.AwardTicket;
import com.my.spring.model.AwardTicketPojo;
import com.my.spring.model.Files;
import com.my.spring.model.User;
import com.my.spring.parameters.Parameters;
import com.my.spring.service.AwardTicketService;
import com.my.spring.service.FileService;
import com.my.spring.service.UserIndexService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

@Service("AwardTicketService")
public class AwardTicketServiceImpl implements AwardTicketService  {
	@Autowired
	AwardTicketDao AwardTicketDao;
	@Autowired
	UserIndexService userIndexService;
	@Autowired
	UserIndexDao userIndexDao;
	@Autowired
	UserDao userDao;
	@Autowired
    FileDao fileDao;
	@Autowired
	FileService fileService;
	private String filePath = "/files/awardTicket";
	@Override
	public DataWrapper<Void> deleteAwardTicketById(String token,Long id) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(!AwardTicketDao.deleteAwardTicket(id)){
				result.setErrorCode(ErrorCodeEnum.Error);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<AwardTicket> addAwardTicket(String token,String dDate,AwardTicket role,MultipartFile[] pics, MultipartFile[] vois, HttpServletRequest request) {
		DataWrapper<AwardTicket> result = new DataWrapper<AwardTicket>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(role!=null){
				role.setCreateUser(user.getId());
				role.setCreateDate(new Date());
				if(dDate!=null){
					try {
						role.setAwardDate(Parameters.getSdfs().parse(dDate));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				}
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
					role.setPictures(picsStr);
				}
				if(vois!=null){
					String voisStr="";
					for(int i=0;i<vois.length;i++){
						Files file=fileService.uploadFile(filePath+"/voices", pics[i], 3, request);
						if(i==(vois.length-1)){
							voisStr=voisStr+file.getId();
						}else{
							voisStr=voisStr+file.getId()+",";
						}
					}
					role.setVoices(voisStr);
				}
				if(!AwardTicketDao.addAwardTicket(role)){
					result.setErrorCode(ErrorCodeEnum.Error);
				}
			}
			
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<Void> deleteAwardTicketByIdList(String token,String[] id) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(!AwardTicketDao.deleteAwardTicketList(id)){
				result.setErrorCode(ErrorCodeEnum.Error);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}


	@Override
	public DataWrapper<List<AwardTicketPojo>> getAwardTicketList(Integer pageIndex, Integer pageSize, AwardTicket AwardTicket,
			String token) {
		DataWrapper<List<AwardTicketPojo>> result = new DataWrapper<List<AwardTicketPojo>>();
		DataWrapper<List<AwardTicket>> dataWrapper = new DataWrapper<List<AwardTicket>>();
		List<AwardTicketPojo> dpp = new ArrayList<AwardTicketPojo>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			dataWrapper=AwardTicketDao.getAwardTicketList(pageIndex, pageSize, AwardTicket);
			if(dataWrapper.getData()!=null){
				for(int i=0;i<dataWrapper.getData().size();i++){
					AwardTicketPojo pojo=new AwardTicketPojo();
					pojo.setRemark(dataWrapper.getData().get(i).getRemark());
					pojo.setId(dataWrapper.getData().get(i).getId());
					pojo.setAwardNum(dataWrapper.getData().get(i).getAwardNum());
					pojo.setAboutId(dataWrapper.getData().get(i).getAboutId());
					pojo.setAwardType(dataWrapper.getData().get(i).getAwardType());
					User users =userDao.getById(dataWrapper.getData().get(i).getCreateUser());
					if(users!=null){
						pojo.setCreateUserName(user.getRealName());
					}
					List<Files> files1=fileDao.getByIds(dataWrapper.getData().get(i).getPictures());
					if(!files1.isEmpty()){
						List<String> pics = new ArrayList<String>();
						for(Files f:files1){
							pics.add(f.getUrl());
						}
						pojo.setPictures(pics);
					}
					List<Files> files2=fileDao.getByIds(dataWrapper.getData().get(i).getVoices());
					if(!files2.isEmpty()){
						List<String> vs = new ArrayList<String>();
						for(Files f:files2){
							vs.add(f.getUrl());
						}
						pojo.setVoices(vs);
					}
					List<String> personLiables = new ArrayList<String>();
					if(dataWrapper.getData().get(i).getPersonLiable()!=null){
						String[] ps = dataWrapper.getData().get(i).getPersonLiable().split(",");
						for(int s=0;s<ps.length;s++){
							User pe = userDao.getById(Long.valueOf(ps[s]));
							if(pe!=null){
								personLiables.add(pe.getRealName());
							}
						}
						pojo.setPersonLiable(personLiables);
					}
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
					SimpleDateFormat sdfa=new SimpleDateFormat("yyyy-MM-dd");
    	    		String str=sdf.format(dataWrapper.getData().get(i).getCreateDate()); 
    	    		String stra=sdfa.format(dataWrapper.getData().get(i).getAwardDate()); 
    	    		pojo.setAwardDate(stra);
    	    		pojo.setCreateDate(str);
    	    		dpp.add(pojo);
				}
				result.setData(dpp);
				result.setCallStatus(dataWrapper.getCallStatus());
				result.setCurrentPage(dataWrapper.getCurrentPage());
				result.setNumberPerPage(dataWrapper.getNumberPerPage());
				result.setTotalNumber(dataWrapper.getTotalNumber());
				result.setTotalPage(dataWrapper.getTotalPage());
				result.setErrorCode(dataWrapper.getErrorCode());
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}
}
