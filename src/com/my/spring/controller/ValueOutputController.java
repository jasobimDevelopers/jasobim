package com.my.spring.controller;

import com.my.spring.model.ValueOutput;
import com.my.spring.model.ValueOutputPojo;
import com.my.spring.service.ValueOutputService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

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
    @RequestMapping(value="/deleteValueOutput",method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteValueOutput(
            @RequestParam(value = "idList",required = true) String idList,
            @RequestParam(value = "token",required = true) String token){
        return ValueOutputService.deleteValueOutput(idList,token);
    }


    @RequestMapping(value="/getValueOutputList",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<ValueOutputPojo>> getValueOutputList(
    		@RequestParam(value = "token",required = true) String token){
        return ValueOutputService.getValueOutputList(token);
    }
    
    
    @RequestMapping(value="/admin/getValueOutputByProjectName",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<ValueOutputPojo>> getValueOutputByProjectId(
    		@RequestParam(value = "projectName",required = true) String projectName,
    		@RequestParam(value = "projectId",required = false) Long projectId,
    		@RequestParam(value = "token",required = true) String token){
        return ValueOutputService.getValueOutputByProjectId(projectName,projectId,token);
    }
    
    @RequestMapping(value="/updateValueOutput",method=RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateValueOutput(
    		@ModelAttribute ValueOutput ValueOutput,
    		@RequestParam(value = "token",required = true) String token) {
    	return ValueOutputService.updateValueOutput(ValueOutput, token);
    	
    }
    //管理员分页获取产值列表
  	@RequestMapping(value="/admin/getValueOutputLists", method = RequestMethod.GET)
      @ResponseBody
      public DataWrapper<List<ValueOutputPojo>> getValueOutputListByAdmin(
      		@RequestParam(value="pageIndex",required=false) Integer pageIndex,
      		@RequestParam(value="pageSize",required=false) Integer pageSize,
      		@RequestParam(value="dates",required=false) String dates,
      		@ModelAttribute ValueOutput valueOutput,
      		@RequestParam(value="token",required=true) String token) {
  		  if(dates!=null){
  			try  
  			{  
  			    SimpleDateFormat sdf = new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
  			  valueOutput.setDate(sdf.parse(dates));  
  			}  
  			catch (ParseException e)  
  			{  
  			    System.out.println(e.getMessage());  
  			}  

  		  }
          return ValueOutputService.getValueOutputLists(pageIndex,pageSize,valueOutput,token,dates);
      }
}
