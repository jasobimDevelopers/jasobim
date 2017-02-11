package com.my.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2016/6/22.
 */
@Controller
public class NavigationController {
    @RequestMapping(value="/home")
    public String mainPage(){
        return "home";
    }
    @RequestMapping(value="/userList")
    public String userPage(){
        return "home";
    }
    @RequestMapping(value="/projectList")
    public String projectPage(){
        return "home";
    }
    @RequestMapping(value="/login") 
    public String loginPage(){
        return "login";
    }
    
    
    @RequestMapping(value="/projectQuestionList")
    public String questionPage(){
        return "home";
    }
    
    
    @RequestMapping(value="/user_home")
    public String userMainPage(){
        return "user_home";
    }
    @RequestMapping(value="/user_projectList")
    public String userProjectPage(){
        return "user_home";
    }
    
    @RequestMapping(value="/user_projectQuestionList")
    public String userQuestionPage(){
        return "user_home";
    }
    
    @RequestMapping(value="/codeInformationList")
    public String codeInformationListPage(){
    	return "user_home";
    }
    
    @RequestMapping(value="/aboutList")
    public String userAboutusPage(){
        return "user_home";
    }
    
    @RequestMapping(value="/suggesttionList")
    public String userSuggesttionPage(){
    	return "user_home";
    }
}
