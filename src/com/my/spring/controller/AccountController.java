package com.my.spring.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.Test;

@Controller
public class AccountController {
	@RequestMapping(value="api/ssocallback", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> ssocallback(
    		HttpServletRequest request) throws IOException{
        String id_token = request.getParameter("id_token");
        String access_token = request.getParameter("access_token");
        Test test = new Test();
        test.test("http://192.168.2.53:8020/connect/userinfo", access_token);
		return null;
    }
   
}
