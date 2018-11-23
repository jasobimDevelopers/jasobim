package com.my.spring.controller;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.my.spring.model.User;
import com.my.spring.model.UserLog;
import com.my.spring.parameters.ProjectUrlUtil;
import com.my.spring.service.UserLogService;
import com.my.spring.utils.SessionManager;

public class FirstController implements HandlerInterceptor {
	@Autowired
	UserLogService userLogService;
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        return true;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

        System.out.println("postHandle run!");
    }
    @SuppressWarnings("static-access")
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    	String token = httpServletRequest.getParameter("token");
    	String projectId = httpServletRequest.getParameter("projectId");
    	String id =httpServletRequest.getParameter("id") ;
    	User userInMemory = SessionManager.getSession(token);
    	if(userInMemory!=null){
    		String url=httpServletRequest.getServletPath();
        	ProjectUrlUtil putil=new ProjectUrlUtil();
        	if(ProjectUrlUtil.getUrlList().get(url)!=null){
        		UserLog userlog = new UserLog();
        		userlog.setActionDate(new Date());
        		userlog.setProjectPart(putil.getUrlList().get(url));
        		userlog.setSystemType(userInMemory.getSystemId());
        		userlog.setUserType(userInMemory.getUserType());
        		userlog.setUserId(userInMemory.getId());
        		if(projectId!=null){
        			userlog.setProjectId(Long.valueOf(projectId));
        		}
        		if(id!=null){
        			userlog.setFileId(Long.valueOf(id));
        		}
        		//0.浏览  1.增加  3.修改
        		if(url.contains("add") || url.contains("import") || url.contains("upload")){
        			userlog.setActionType(1);
        		}else if(url.contains("update")){
        			userlog.setActionType(3);
        		}else if(url.contains("get")){
        			userlog.setActionType(0);
        		}
        		userLogService.addUserLog(userlog, token);
        	}
    	}
        System.out.println("afterCompletion run!");
    }
}