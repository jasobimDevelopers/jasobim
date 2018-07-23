package com.my.spring.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.spring.model.AttenceLog;
import com.my.spring.model.AttenceLogPojo;
import com.my.spring.service.AttenceLogService;
import com.my.spring.utils.DataWrapper;

@Controller
@RequestMapping(value="api/attenceLog")
public class AttenceLogController {
	@Autowired
    AttenceLogService amService;
    @RequestMapping(value="/addAttenceLog", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<String> addAttenceLog(
            @ModelAttribute AttenceLog am,
            @RequestParam(value = "lat",required = true) Double lat,/////打卡所在经度
            @RequestParam(value = "lng",required = true) Double lng,/////打卡所在纬度
            @RequestParam(value = "token",required = true) String token){
        return amService.addAttenceLog(am, token,lat,lng);
    }
    @RequestMapping(value="/deleteAttenceLog",method=RequestMethod.GET)
    @ResponseBody
    
    public DataWrapper<Void> deleteAttenceLog(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return amService.deleteAttenceLog(id,token);
    }

    @RequestMapping(value="/getAttenceLogList", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<AttenceLogPojo>> getAttenceLogList(
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@RequestParam(value = "year",required = false) Integer year,
    		@RequestParam(value = "month",required = false) Integer month,
    		@ModelAttribute AttenceLog ps){
        return amService.getAttenceLogList(token, ps, pageSize, pageIndex,year,month);
    }
    @RequestMapping(value="/getAttenceLogById", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<AttenceLog> getAttenceLogById(
            @RequestParam(value = "token",required = true) String token,
    		@ModelAttribute AttenceLog ps){
        return amService.getAttenceLogById(token, ps);
    }
   
}
