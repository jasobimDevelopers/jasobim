package com.my.spring.controller;

import com.my.spring.model.ValueOutput;
import com.my.spring.model.ValueOutputPojo;
import com.my.spring.service.ValueOutputService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Created by Administrator on 2016/6/22.
 */
@Controller
@RequestMapping(value="api/ValueOutput")
public class ValueOutputController {
    @Autowired
    ValueOutputService ValueOutputService;
    
    @RequestMapping(value="/addValueOutput", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addValueOutput(
            @ModelAttribute ValueOutput ValueOutput,
            @RequestParam(value = "token",required = true) String token){
        return ValueOutputService.addValueOutput(ValueOutput,token);
    }
    @RequestMapping(value="/deleteValueOutput")
    @ResponseBody
    public DataWrapper<Void> deleteValueOutput(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return ValueOutputService.deleteValueOutput(id,token);
    }


    @RequestMapping(value="/getValueOutputList",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<ValueOutputPojo>> getValueOutputList(
    		@RequestParam(value = "token",required = true) String token){
        return ValueOutputService.getValueOutputList(token);
    }
    @RequestMapping(value="/admin/getValueOutputByProjectId",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<ValueOutput> getValueOutputByProjectId(
    		@RequestParam(value = "projectId",required = true) Long projectId,
    		@RequestParam(value = "token",required = true) String token){
        return ValueOutputService.getValueOutputByProjectId(projectId,token);
    }
    @RequestMapping(value="/updateValueOutput",method=RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateValueOutput(
    		@ModelAttribute ValueOutput ValueOutput,
    		@RequestParam(value = "token",required = true) String token) {
    	return ValueOutputService.updateValueOutput(ValueOutput, token);
    	
    }
}
