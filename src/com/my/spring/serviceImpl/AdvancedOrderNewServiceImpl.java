package com.my.spring.serviceImpl;

import com.my.spring.DAO.AdvancedOrderCollectDao;
import com.my.spring.DAO.AdvancedOrderDao;
import com.my.spring.DAO.AdvancedOrderNewDao;
import com.my.spring.DAO.NoticeDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.DAO.UserLogDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.AdvancedOrderNew;
import com.my.spring.model.AdvancedOrderNewPojo;
import com.my.spring.model.User;
import com.my.spring.parameters.Parameters;
import com.my.spring.service.AdvancedOrderNewService;
import com.my.spring.service.FileService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.NumberToCN;
import com.my.spring.utils.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

@Service("advancedOrderNewService")
public class AdvancedOrderNewServiceImpl implements AdvancedOrderNewService {
    @Autowired
    AdvancedOrderNewDao advancedOrderNewDao;
    @Autowired
    UserDao userDao;
    @Autowired
    UserLogDao userLogDao;
    @Autowired
    NoticeDao noticeDao;
    @Autowired
    FileService fileService;
    @Autowired
    AdvancedOrderCollectDao advancedOrderCollectDao;
    @Autowired
    FileService filesService;
    private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
    @SuppressWarnings("unused")
	private NumberToCN numberChange;
    @Override
	public DataWrapper<Void> addAdvancedOrderNew(AdvancedOrderNew advancedOrder, String token,
			MultipartFile[] contentFiles, HttpServletRequest request) {
    	DataWrapper<Void> result = new DataWrapper<Void>();
    	User user = SessionManager.getSession(token);
    	if(user!=null){
    		if(advancedOrder!=null){
    			advancedOrder.setCreateDate(new Date());
    			advancedOrder.setSubmitUserId(user.getId());
    			advancedOrderNewDao.addAdvancedOrderNew(advancedOrder);
    		}
    	}else{
    		result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
    	}
		return result;
	}
	@Override
	public DataWrapper<List<AdvancedOrderNewPojo>> getAdvancedOrderNewList(String token, Integer pageIndex,
			Integer pageSize, AdvancedOrderNew advancedOrder, String content) {
		DataWrapper<List<AdvancedOrderNewPojo>> result = new DataWrapper<List<AdvancedOrderNewPojo>>();
    	User user = SessionManager.getSession(token);
    	if(user!=null){
    		
    	}else{
    		result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
    	}
		return result;
	}
	@Override
	public DataWrapper<List<AdvancedOrderNew>> getAdvancedOrderNewListByUserId(Long userId, String token) {
		DataWrapper<List<AdvancedOrderNew>> result = new DataWrapper<List<AdvancedOrderNew>>();
    	User user = SessionManager.getSession(token);
    	if(user!=null){
    		
    	}else{
    		result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
    	}
		return result;
	}
	
	@Override
	public DataWrapper<Void> deleteAdvancedOrderNew(Long id, String token) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
    	if(user!=null){
    		advancedOrderNewDao.deleteAdvancedOrderNew(id);
    	}else{
    		result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
    	}
		return result;
	}
	@Override
	public DataWrapper<Void> updateAdvancedOrderNew(AdvancedOrderNew advancedOrder, String token,
			HttpServletRequest request, MultipartFile photoOfFinished) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
    	if(user!=null){
    		
    	}else{
    		result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
    	}
		return result;
	}
	@Override
	public DataWrapper<AdvancedOrderNewPojo> getAdvancedOrderNewById(Long id, String token, String weixin) {
		DataWrapper<AdvancedOrderNewPojo> result = new DataWrapper<AdvancedOrderNewPojo>();
		User user = SessionManager.getSession(token);
    	if(user!=null){
    		
    	}else{
    		result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
    	}
		return result;
	}
	@Override
	public DataWrapper<String> exportAdvancedOrderNew(Long id, String token) {
		DataWrapper<String> result = new DataWrapper<String>();
		User user = SessionManager.getSession(token);
    	if(user!=null){
    		
    	}else{
    		result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
    	}
		return result;
	}
    
	
}
