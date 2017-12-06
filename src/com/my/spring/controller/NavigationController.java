package com.my.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
    @RequestMapping(value="/itemGetList")
    public String itemGetPage(){
    	return "home";
    }
    @RequestMapping(value="/feedbackList")
    public String feedbackPage(){
    	return "home";
    }
    @RequestMapping(value="/valueOutputList")
    public String valueOutputPage(){
    	return "home";
    }
    @RequestMapping(value="/userLogList")
    public String userLogPage(){
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
    
    @RequestMapping(value="/videoCodeList")
    public String videoCodeListPage(){
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
    @RequestMapping(value="/productionList")
    public String productionPage(){
        return "user_home";
    }
    @RequestMapping(value="/user_constructionTask")
    public String userConstructionPage(){
        return "user_home";
    }
    @RequestMapping(value="/measured_data")
    public String userMeasuredDataPage(){
        return "user_home";
    }
    @RequestMapping(value="/normativefilesList")
    public String userNormativeDataPage(){
        return "user_home";
    }
    @RequestMapping(value="/user_advancedOrder")
    public String userAdvancedPage(){
        return "user_home";
    }
    
    ///////////////
    @RequestMapping(value="/new_user_home")
    public String newUserMainPage(){
        return "new_user_home";
    }
   /* @RequestMapping(value="/new_user_projectList")
    public String newUserProjectPage(){
        return "new_user_home";
    }
    
    @RequestMapping(value="/new_user_projectQuestionList")
    public String newUserQuestionPage(){
        return "new_user_home";
    }
    
    @RequestMapping(value="/codeInformationList")
    public String newCodeInformationListPage(){
    	return "new_user_home";
    }
    
    @RequestMapping(value="/aboutList")
    public String newUserAboutusPage(){
        return "new_user_home";
    }
    
    @RequestMapping(value="/suggesttionList")
    public String newUserSuggesttionPage(){
    	return "new_user_home";
    }
    @RequestMapping(value="/productionList")
    public String newProductionPage(){
        return "new_user_home";
    }*/
    
}
