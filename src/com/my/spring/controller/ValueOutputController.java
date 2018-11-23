package com.my.spring.controller;

import com.my.spring.model.ValueOutput;
import com.my.spring.model.ValueOutputPojo;
import com.my.spring.service.ValueOutputService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping(value="api/valueOutput")
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
    /**
     * app获取当前项目产值统计接口
     * */
    @RequestMapping(value="/getValueOutputListnew",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<ValueOutputPojo> getValueOutputListnew(
    		@RequestParam(value = "token",required = true) String token,
    		@RequestParam(value = "projectId",required = true) Long projectId){
        return ValueOutputService.getValueOutputListnew(token,projectId);
    }
    /**
     * 
     * app获取当前项目每个月的产值详情接口
     * */
    @RequestMapping(value="/admin/getValueOutputByProjectId",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<ValueOutputPojo>> getValueOutputByProjectId(
    		@RequestParam(value = "projectName",required = false) String projectName,
    		@RequestParam(value = "projectId",required = false) Long projectId,
    		@RequestParam(value = "token",required = true) String token){
        return ValueOutputService.getValueOutputByProjectId(projectName,projectId,token);
    }
    @RequestMapping(value="/admin/getValueOutputByProjectName",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<ValueOutputPojo>> getValueOutputByProjectName(
    		@RequestParam(value = "projectName",required = false) String projectName,
    		@RequestParam(value = "projectId",required = false) Long projectId,
    		@RequestParam(value = "token",required = true) String token){
        return ValueOutputService.getValueOutputByProjectName(projectName,projectId,token);
    }
    ///////
    @RequestMapping(value="/updateValueOutput",method=RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateValueOutput(
    		@ModelAttribute ValueOutput ValueOutput,
    		@RequestParam(value = "token",required = true) String token) {
    	return ValueOutputService.updateValueOutput(ValueOutput, token);
    	
    }
    //////////////
    //管理员分页获取产值列表
  	@RequestMapping(value="/admin/getValueOutputLists", method = RequestMethod.GET)
      @ResponseBody
      public DataWrapper<List<ValueOutputPojo>> getValueOutputListByAdmin(
      		@RequestParam(value="pageIndex",required=false) Integer pageIndex,
      		@RequestParam(value="pageSize",required=false) Integer pageSize,
      		@RequestParam(value="dates",required=false) String dates,
      		@ModelAttribute ValueOutput valueOutput,
      		@RequestParam(value="token",required=true) String token) {
          return ValueOutputService.getValueOutputLists(pageIndex,pageSize,valueOutput,token,dates);
      }
  	@RequestMapping(value="/getValueOutputByDate", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<ValueOutputPojo> getValueOutputByDate(
    		@RequestParam(value="month",required=false) Integer month,
    		@RequestParam(value="year",required=false) Integer year,
    		@RequestParam(value="projectId",required=false) Long projectId,
    		@RequestParam(value="token",required=true) String token) {
        return ValueOutputService.getValueOutputByDate(month,year,projectId,token);
    }
  	@RequestMapping(value="/exportValueOutput", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<String> getValueOutputByDate(
    		@RequestParam(value="projectId",required=false) Long projectId,
    		@RequestParam(value="token",required=true) String token) {
        return ValueOutputService.exportValueOutput(projectId,token);
    }
  	
}
