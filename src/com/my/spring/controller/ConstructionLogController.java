 package com.my.spring.controller;

import com.my.spring.model.ConstructionLog;
import com.my.spring.model.ConstructionLogPojo;
import com.my.spring.service.ConstructionLogService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@Controller
@CrossOrigin
@RequestMapping(value="api/constructionLog")
public class ConstructionLogController {
    @Autowired
    ConstructionLogService constructionLogService;
    @RequestMapping(value="/web/addConstructionLog", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<ConstructionLog> addConstructionLog(
            @ModelAttribute ConstructionLog ps,
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value = "cityNode",required = true) String cityNode,
            @RequestParam(value = "constructDates",required = true) String constructDates){
        return constructionLogService.addConstructionLog(ps,token,constructDates,cityNode);
    }
    
    @RequestMapping(value="/deleteConstructionLog",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteConstructionLog(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return constructionLogService.deleteConstructionLog(id,token);
    }
    
    @RequestMapping(value="/web/getConstructionLogList", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<ConstructionLogPojo>> getConstructionLogList(
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@RequestParam(value="start",required=false) String start,
    		@RequestParam(value="end",required=false) String end,
    		@RequestParam(value="userRealName",required=false) String userRealName,
    		@ModelAttribute ConstructionLog ps){
        return constructionLogService.getConstructionLogList(token,pageIndex,pageSize,ps,start,end,userRealName);
    }
    @RequestMapping(value="/web/exportConstructionLog", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<String> exportConstructionLog(
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value="id",required=true) Long id,
    		@ModelAttribute ConstructionLog ps){
        return constructionLogService.exportConstructionLog(token,id);
    }
    
   
}
