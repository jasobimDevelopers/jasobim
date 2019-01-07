package com.my.spring.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.my.spring.model.CheckLists;
import com.my.spring.service.CheckListsService;
import com.my.spring.utils.DataWrapper;

@Controller
@RequestMapping(value="api/checkLists")
public class CheckListsController {
    @Autowired
    CheckListsService checkListsService;  
   
    @RequestMapping(value="/admin/addCheckLists", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<CheckLists> addCheckLists(
            @ModelAttribute CheckLists CheckLists,
            @RequestParam(value = "token",required = true) String token){
        return checkListsService.addCheckLists(token,CheckLists);
    }
    @RequestMapping(value="/admin/updateCheckLists", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateCheckLists(
            @ModelAttribute CheckLists CheckLists,
            @RequestParam(value = "token",required = true) String token){
        return checkListsService.updateCheckLists(token,CheckLists);
    }
    @RequestMapping(value="/admin/deleteCheckLists",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteCheckLists(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return checkListsService.deleteCheckListsById(token,id);
    }
   

    @RequestMapping(value="/admin/getCheckListsList", method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<CheckLists>> getCheckListsList(
    		@RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute CheckLists CheckLists,
            @RequestParam(value = "token",required = false) String token){
        return checkListsService.getCheckListsList(pageIndex,pageSize,CheckLists,token);
    }

}
