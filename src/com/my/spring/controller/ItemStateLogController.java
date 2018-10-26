package com.my.spring.controller;

import com.my.spring.model.ItemStateLog;
import com.my.spring.model.ItemStateLogPojo;
import com.my.spring.service.ItemStateLogService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value="api/itemStateLog")
public class ItemStateLogController {
    @Autowired
    ItemStateLogService ItemStateLogService;
    @RequestMapping(value="/addItemStateLog", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<ItemStateLog> addItemStateLog(
            @ModelAttribute ItemStateLog ItemStateLog,
            @RequestParam(value = "selfIdList",required = false) String selfIdList,
            @RequestParam(value = "token",required = true) String token){
        return ItemStateLogService.addItemStateLog(ItemStateLog,token,selfIdList);
    }
    @RequestMapping(value="/deleteItemStateLog")
    @ResponseBody
    public DataWrapper<Void> deleteItemStateLog(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return ItemStateLogService.deleteItemStateLog(id,token);
    }

    @RequestMapping(value="/getItemStateLogList",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<ItemStateLogPojo>> getItemStateLogList(
    		@RequestParam(value = "token",required = true) String token,
    		@RequestParam(value = "projectId",required = true) Long projectId,
    		@RequestParam(value = "idList",required = true) String idList){
        return ItemStateLogService.getItemStateLogList(token,projectId,idList);
    }
    @RequestMapping(value="/getAllItemStateLogList",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<ItemStateLogPojo>> getAllItemStateLogList(
    		@RequestParam(value = "token",required = true) String token,
    		@RequestParam(value = "projectId",required = true) Long projectId){
        return ItemStateLogService.getAllItemStateLogList(token,projectId);
    }
    @RequestMapping(value="/admin/getItemStateLogByProjectId",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<ItemStateLog> getItemStateLogByProjectId(
    		@RequestParam(value = "projectId",required = true) Long projectId,
    		@RequestParam(value = "token",required = true) String token){
        return ItemStateLogService.getItemStateLogByProjectId(projectId,token);
    }
}
