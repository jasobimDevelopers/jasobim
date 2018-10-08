package com.my.spring.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.spring.model.OutputValue;
import com.my.spring.model.OutputValuePojo;
import com.my.spring.service.OutputValueService;
import com.my.spring.utils.DataWrapper;

@Controller
@RequestMapping(value="api/outputValue")
public class OutputValueController {
    @Autowired
    OutputValueService OutputValueService;  
   
    @RequestMapping(value="/admin/addOutputValue", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<OutputValue> addOutputValue(
            @ModelAttribute OutputValue OutputValue,
            @RequestParam(value = "token",required = true) String token){
        return OutputValueService.addOutputValue(token,OutputValue);
    }
    
    @RequestMapping(value="/admin/deleteOutputValue",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteOutputValue(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return OutputValueService.deleteOutputValueById(token,id);
    }
    
    @RequestMapping(value="/admin/updateOutputValue",method=RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateOutputValue(
    		@ModelAttribute OutputValue OutputValue,
            @RequestParam(value = "token",required = true) String token){
        return OutputValueService.updateOutputValue(token,OutputValue);
    }
   

    @RequestMapping(value="/admin/getOutputValueList", method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<OutputValuePojo>> getOutputValueList(
    		@RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@RequestParam(value = "dateStart",required = false) String dateStart,
            @RequestParam(value = "dateFinished",required = false) String dateFinished,
    		@ModelAttribute OutputValue OutputValue,
            @RequestParam(value = "token",required = false) String token){
        return OutputValueService.getOutputValueList(pageIndex,pageSize,OutputValue,token,dateStart,dateFinished);
    }
    @RequestMapping(value="/admin/getOutputValueById",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<OutputValue> getOutputValueById(
    		@RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = false) String token){
        return OutputValueService.getOutputValueById(token,id);
    }
    @RequestMapping(value="/exportOutputValue",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<String> exportOutputValue(
    		@RequestParam(value = "projectId",required = true) Long projectId,
            @RequestParam(value = "token",required = true) String token){
        return OutputValueService.exportOutputValue(token,projectId);
    }

}
