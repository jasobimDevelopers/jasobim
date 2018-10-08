 package com.my.spring.controller;

import com.my.spring.model.DepartmentPojo;
import com.my.spring.model.ProcessData;
import com.my.spring.model.ProcessDataPojo;
import com.my.spring.model.ProcessItem;
import com.my.spring.model.ProcessItemPojo;
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
    public DataWrapper<ProcessData> addProcessData(
            @ModelAttribute ProcessData ProcessData,
            @RequestParam(value = "token",required = true) String token){
    	DataWrapper<ProcessData> dataWrapper = new DataWrapper<ProcessData>();
		dataWrapper=ProcessDataService.addProcessData(ProcessData,token);
		return dataWrapper;
    }
    @RequestMapping(value="/admin/addProcessItem", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addProcessItem(
            @ModelAttribute ProcessItem processItem,
            @RequestParam(value = "token",required = true) String token){
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		dataWrapper=ProcessDataService.addProcessItem(processItem,token);
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
            @RequestParam(value = "type",required = false) Integer type,
            @RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute ProcessData ProcessData){
        return ProcessDataService.getProcessDataList(token,pageIndex,pageSize,ProcessData,type);
    }

    @RequestMapping(value="/admin/getProcessItemListById", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<ProcessItemPojo>> getProcessItemListById(
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value="id",required=true) Long id){
        return ProcessDataService.getProcessItemListById(token,id);
    }
    
}
