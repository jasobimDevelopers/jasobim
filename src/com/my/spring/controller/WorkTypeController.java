package com.my.spring.controller;
import com.my.spring.model.WorkType;
import com.my.spring.service.WorkTypeService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping(value="api/workType")
public class WorkTypeController {
    @Autowired
    WorkTypeService WorkTypeService;
    @RequestMapping(value="/admin/addWorkType", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addWorkType(
            @ModelAttribute WorkType WorkType,
            @RequestParam(value = "token",required = true) String token){
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		dataWrapper=WorkTypeService.addWorkType(WorkType,token);
        return dataWrapper;
    }
    @RequestMapping(value="/deleteWorkType",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteWorkType(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return WorkTypeService.deleteWorkType(id,token);
    }

    @RequestMapping(value="/admin/updateWorkType",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateWorkType(
            @ModelAttribute WorkType WorkType,
            @RequestParam(value = "token",required = false) String token){
        System.out.println(WorkType);
        return WorkTypeService.updateWorkType(WorkType,token);
    }


    @RequestMapping(value="/admin/getWorkTypeList", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<WorkType>> getWorkTypeList(
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value = "type",required = true) Integer type,
            @RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute WorkType WorkType){
        return WorkTypeService.getWorkTypeList(token,pageIndex,pageSize,WorkType,type);
    }
   
}
