 package com.my.spring.controller;

import com.my.spring.enums.CallStatusEnum;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.ProcessLog;
import com.my.spring.model.ProcessLogPojo;
import com.my.spring.service.ProcessLogService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@Controller
@RequestMapping(value="api/processLog")
public class ProcessLogController {
    @Autowired
    ProcessLogService ProcessLogService;
    @RequestMapping(value="/admin/addProcessLog", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addProcessLog(
            @ModelAttribute ProcessLog ProcessLog,
            @RequestParam(value = "token",required = true) String token){
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		dataWrapper=ProcessLogService.addProcessLog(ProcessLog,token);
		if(dataWrapper.getCallStatus()==CallStatusEnum.SUCCEED){
            	return dataWrapper;
    	}else{
    		dataWrapper.setErrorCode(ErrorCodeEnum.Error);
    	}
        return dataWrapper;
    }
    @RequestMapping(value="/deleteProcessLog",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteProcessLog(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return ProcessLogService.deleteProcessLog(id,token);
    }



    @RequestMapping(value="/admin/getProcessLogList", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<ProcessLogPojo>> getProcessLogList(
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute ProcessLog ProcessLog){
        return ProcessLogService.getProcessLogList(token,pageIndex,pageSize,ProcessLog);
    }

}
