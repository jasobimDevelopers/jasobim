package com.my.spring.controller;

import com.my.spring.model.PaperPointInfo;
import com.my.spring.service.PaperPointInfoService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value="api/paperPointInfo")
public class PaperPointInfoController {
    @Autowired
    PaperPointInfoService infoService;
    @RequestMapping(value="/addPaperPointInfo", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addPaperPointInfo(
            @ModelAttribute PaperPointInfo building,
            HttpServletRequest request,
            @RequestParam(value = "", required = true)MultipartFile paper,
            @RequestParam(value = "token",required = true) String token){
        return infoService.addPaperPointInfo(building,paper,request,token);
    }
    @RequestMapping(value="/deletePaperPointInfo")
    @ResponseBody
    public DataWrapper<Void> deletePaperPointInfo(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return infoService.deletePaperPointInfo(id,token);
    }


    @RequestMapping(value="/getPaperPointInfoList")
    @ResponseBody
    public DataWrapper<List<PaperPointInfo>> getPaperPointInfoList(){
        return infoService.getPaperPointInfoList();
    }
    @RequestMapping(value="/getPaperPointInfoByProjectId",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<PaperPointInfo> getPaperPointInfoByProjectId(
    		@RequestParam(value = "projectId",required = true) Long projectId,
    		@RequestParam(value = "token",required = true) String token){
        return infoService.getPaperPointInfoByProjectId(projectId,token);
    }
}
