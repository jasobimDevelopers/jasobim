package com.my.spring.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.my.spring.model.Nature;
import com.my.spring.service.NatureService;
import com.my.spring.utils.DataWrapper;

@Controller
@RequestMapping(value="api/nature")
public class NatureController {
    @Autowired
    NatureService NatureService;  
   
    @RequestMapping(value="/admin/addNature", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Nature> addNature(
            @ModelAttribute Nature Nature,
            @RequestParam(value = "token",required = true) String token){
        return NatureService.addNature(token,Nature);
    }
    @RequestMapping(value="/admin/updateNature", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateNature(
            @ModelAttribute Nature Nature,
            @RequestParam(value = "token",required = true) String token){
        return NatureService.updateNature(token,Nature);
    }
    @RequestMapping(value="/admin/deleteNature",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteNature(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return NatureService.deleteNatureById(token,id);
    }

    @RequestMapping(value="/admin/getNatureList", method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<Nature>> getNatureList(
    		@RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute Nature Nature,
            @RequestParam(value = "token",required = false) String token){
        return NatureService.getNatureList(pageIndex,pageSize,Nature,token);
    }

}
