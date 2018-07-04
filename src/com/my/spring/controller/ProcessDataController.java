 package com.my.spring.controller;

import com.my.spring.enums.CallStatusEnum;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.ProcessData;
import com.my.spring.model.ProcessDataPojo;
import com.my.spring.service.ProcessDataService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@Controller
@RequestMapping(value="api/processData")
public class ProcessDataController {
    @Autowired
    ProcessDataService ProcessDataService;
    @RequestMapping(value="/admin/addProcessData", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addProcessData(
            @ModelAttribute ProcessData ProcessData,
            @RequestParam(value = "token",required = true) String token){
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		dataWrapper=ProcessDataService.addProcessData(ProcessData,token);
		if(dataWrapper.getCallStatus()==CallStatusEnum.SUCCEED){
            	return dataWrapper;
    	}else{
    		dataWrapper.setErrorCode(ErrorCodeEnum.Error);
    	}
        return dataWrapper;
    }
    @RequestMapping(value="/deleteProcessData",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteProcessData(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return ProcessDataService.deleteProcessData(id,token);
    }

    @RequestMapping(value="/admin/updateProcessData",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateProcessData(
            @ModelAttribute ProcessData ProcessData,
            @RequestParam(value = "token",required = false) String token){
        System.out.println(ProcessData);
        return ProcessDataService.updateProcessData(ProcessData,token);
    }


    @RequestMapping(value="/admin/getProcessDataList", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<ProcessDataPojo>> getProcessDataList(
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value = "type",required = true) Integer type,
            @RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute ProcessData ProcessData){
        return ProcessDataService.getProcessDataList(token,pageIndex,pageSize,ProcessData,type);
    }
   
}
