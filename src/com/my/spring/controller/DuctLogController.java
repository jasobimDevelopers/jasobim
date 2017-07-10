package com.my.spring.controller;

import com.my.spring.model.DuctLog;
import com.my.spring.model.DuctLogPojo;
import com.my.spring.service.DuctLogService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value="api/ductLog")
public class DuctLogController {
    @Autowired
    DuctLogService DuctLogService;
    @RequestMapping(value="/addDuctLog", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addDuctLog(
            @ModelAttribute DuctLog DuctLog,
            @RequestParam(value = "token",required = true) String token){
        return DuctLogService.addDuctLog(DuctLog,token);
    }
    @RequestMapping(value="/deleteDuctLog",method=RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> deleteDuctLog(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return DuctLogService.deleteDuctLog(id,token);
    }
    @RequestMapping(value="/admin/getDuctLogByDuctId",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<DuctLogPojo>> getDuctLogByDuctId(
    		@ModelAttribute DuctLog DuctLog,
    		@RequestParam(value = "ductId",required = true) Long ductId,
    		@RequestParam(value = "token",required = true) String token){
        return DuctLogService.getDuctLogByDuctId(ductId,token,DuctLog);
    }
    
}
