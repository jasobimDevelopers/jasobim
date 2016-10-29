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
        return "login";
    }
    
    @RequestMapping(value="/test")
    public String testPage(){
        return "test_prf";
    }
}
