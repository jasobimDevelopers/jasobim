package com.my.spring.controller;
import com.my.spring.model.PaperOfMeasured;
import com.my.spring.model.PaperOfMeasuredPojo;
import com.my.spring.service.PaperOfMeasuredService;
import com.my.spring.utils.DataWrapper;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value="api/paperOfMeasured")
public class PaperOfMeasuredController {
    @Autowired
    PaperOfMeasuredService paperService;
    @RequestMapping(value="/addPaperOfMeasured", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addPaperOfMeasured(
            @ModelAttribute PaperOfMeasured building,
            HttpServletRequest request,
            @RequestParam(value = "file", required = true) MultipartFile file,
            @RequestParam(value = "token",required = true) String token){
        return paperService.addPaperOfMeasured(building,token,file,request);
    }
    @RequestMapping(value="/deletePaperOfMeasured",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> deleteBuilding(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return paperService.deletePaperOfMeasured(id,token);
    }

    @RequestMapping(value="/getPaperOfMeasuredByProjectId",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<PaperOfMeasuredPojo> getPaperOfMeasuredByProjectId(
    		@RequestParam(value = "projectId",required = true) Long projectId,
    		@RequestParam(value = "token",required = true) String token){
        return paperService.getPaperOfMeasuredByProjectId(projectId,token);
    }
}
