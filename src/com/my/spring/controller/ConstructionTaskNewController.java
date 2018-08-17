 package com.my.spring.controller;

import com.my.spring.model.ConstructionTaskNew;
import com.my.spring.model.ConstructionTaskNewPojo;
import com.my.spring.model.ItemDataPojo;
import com.my.spring.model.ProcessLog;
import com.my.spring.model.ProcessLogPojo;
import com.my.spring.service.ConstructionTaskNewService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import javax.servlet.http.HttpServletRequest;



@Controller
@RequestMapping(value="api/constructionTaskNew")
public class ConstructionTaskNewController {
    @Autowired
    ConstructionTaskNewService ConstructionTaskNewService;
    /**
     *新增接口
     * */
    @RequestMapping(value="/admin/addConstructionTaskNew", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addConstructionTaskNew(
            @ModelAttribute ConstructionTaskNew ConstructionTaskNew,
            @RequestParam(value = "imags",required = false) MultipartFile[] imags,
            HttpServletRequest request,
            @RequestParam(value = "token",required = true) String token){
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		dataWrapper=ConstructionTaskNewService.addConstructionTaskNew(ConstructionTaskNew,token,imags,request);
        return dataWrapper;
    }
    /**
     * 删除接口
     * */
    @RequestMapping(value="/deleteConstructionTaskNew",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteConstructionTaskNew(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return ConstructionTaskNewService.deleteConstructionTaskNew(id,token);
    }
    /**
     * 更新接口
     * */
    @RequestMapping(value="/admin/updateConstructionTaskNew",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateConstructionTaskNew(
            @ModelAttribute ConstructionTaskNew ConstructionTaskNew,
            @RequestParam(value = "token",required = false) String token){
        System.out.println(ConstructionTaskNew);
        return ConstructionTaskNewService.updateConstructionTaskNew(ConstructionTaskNew,token);
    }

    /**
     * 列表获取接口
     * */
    @RequestMapping(value="/admin/getConstructionTaskNewList", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<ConstructionTaskNewPojo>> getConstructionTaskNewList(
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute ConstructionTaskNew ConstructionTaskNew){
        return ConstructionTaskNewService.getConstructionTaskNewList(token,pageIndex,pageSize,ConstructionTaskNew);
    }
    /**
     * 审批接口
     * 
     * */
    @RequestMapping(value="/self/approveConstructionTaskNew", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<ProcessLogPojo> approveConstructionTaskNew(
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "note",required = true) String note,
            @RequestParam(value = "idea",required = true) Integer idea,
            @RequestParam(value = "processDataId",required = true) Long processDataId,
            @RequestParam(value = "currentNodeId",required = true) Integer currentNodeId){//currentNodeId+1
        return ConstructionTaskNewService.approveConstructionTaskNew(token,id,note,idea,processDataId,currentNodeId);
    }
    
    /**
     * 详情获取接口
     * */
    @RequestMapping(value="/getConstructionTaskNewDetail", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<ConstructionTaskNewPojo>> getConstructionTaskNewDetail(
            @RequestParam(value = "token",required = true) String token,
    		@ModelAttribute ConstructionTaskNew ConstructionTaskNew){
        return ConstructionTaskNewService.getConstructionTaskNewDetail(token,ConstructionTaskNew);
    }
    
    /**
     * 详情流程节点审批记录获取接口
     * */
    @RequestMapping(value="/getProcessLogByConstructionId", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<ProcessLogPojo>> getProcessLogByConstructionId(
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "processDataId",required = true) Long processDataId){
        return ConstructionTaskNewService.getProcessLogByConstructionId(token,id,processDataId);
    }
    /**
     * 详情流程所有节点审批记录获取接口
     * */
    @RequestMapping(value="/getAllProcessLogByConstructionId", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<ProcessLogPojo>> getAllProcessLogByConstructionId(
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "processDataId",required = true) Long processDataId){
        return ConstructionTaskNewService.getAllProcessLogByConstructionId(token,id,processDataId);
    }
   
}
