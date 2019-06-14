package com.my.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.spring.model.CheckListTypes;
import com.my.spring.service.CheckListTypesService;
import com.my.spring.utils.DataWrapper;

@Controller
@RequestMapping(value="api/checkListTypes")
public class CheckListTypesController {
	@Autowired
    CheckListTypesService checkListTypesService;  
   
    @RequestMapping(value="/admin/addCheckListTypes", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<CheckListTypes> addCheckListTypes(
            @ModelAttribute CheckListTypes CheckLists,
            @RequestParam(value = "token",required = true) String token){
        return checkListTypesService.addCheckListTypes(token,CheckLists);
    }
    @RequestMapping(value="/admin/updateCheckListTypes", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateCheckListTypes(
            @ModelAttribute CheckListTypes CheckLists,
            @RequestParam(value = "token",required = true) String token){
        return checkListTypesService.updateCheckListTypes(token,CheckLists);
    }
    
    @RequestMapping(value="/admin/deleteCheckListTypes",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteCheckListTypes(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return checkListTypesService.deleteCheckListTypesById(token,id);
    }
   

    @RequestMapping(value="/admin/getCheckListTypesList", method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<CheckListTypes>> getCheckListTypesList(
    		@RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute CheckListTypes CheckLists,
            @RequestParam(value = "token",required = false) String token){
        return checkListTypesService.getCheckListTypesList(pageIndex,pageSize,CheckLists,token);
    }
}
