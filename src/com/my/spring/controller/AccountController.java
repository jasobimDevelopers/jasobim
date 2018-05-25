package com.my.spring.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.spring.utils.DataWrapper;

@Controller
public class AccountController {
	@RequestMapping(value="api/ssocallback", method = RequestMethod.POST)
    @ResponseBody
    //@Cacheable(value="user-key", condition="#userId <50")
    public DataWrapper<Void> ssocallback(
    		HttpServletRequest request){
        String id_token = request.getParameter("id_token");
		return null;
    }
   
}
