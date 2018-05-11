package com.my.spring.controller;

import com.my.spring.model.SafeFine;
import com.my.spring.model.SafeFinePojo;
import com.my.spring.service.SafeFineService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping(value="api/safeFine")
public class SafeFineController {
    @Autowired
    SafeFineService fineService;
    @RequestMapping(value="/addSafeFine", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addSafeFine(
            @ModelAttribute SafeFine Duct,
            @RequestParam(value = "token",required = true) String token){
        return fineService.addSafeFine(Duct,token);
    }
    @RequestMapping(value="/deleteSafeFine",method=RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> deleteSafeFine(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return fineService.deleteSafeFine(id,token);
    }
    @RequestMapping(value="/getSafeFineList",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<SafeFinePojo>> getSafeFineList(
    		@RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute SafeFine duct,
    		@RequestParam(value="token",required=true) String token){
        return fineService.getSafeFineList(pageIndex,pageSize,duct,token);
    }
    
  
}
