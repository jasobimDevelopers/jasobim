package com.my.spring.controller;

import com.my.spring.model.MeasuredProblem;
import com.my.spring.model.MeasuredProblemPojo;
import com.my.spring.service.MeasuredProblemService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value="api/measuredProblem")
public class MeasuredProblemController {
    @Autowired
    MeasuredProblemService buildingService;
    @RequestMapping(value="/addMeasuredProblem", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addMeasuredProblem(
            @ModelAttribute MeasuredProblem building,
            @RequestParam(value = "files", required = true) MultipartFile[] files,
            @RequestParam(value = "vois", required = true) MultipartFile[] vois,
            HttpServletRequest request,
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value = "fDate",required = true) String fDate){
        return buildingService.addMeasuredProblem(building,token,files,vois,request,fDate);
    }
    @RequestMapping(value="/updateMeasuredProblem", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateMeasuredProblem(
            @ModelAttribute MeasuredProblem building,
            @RequestParam(value = "files", required = true) MultipartFile[] files,
            @RequestParam(value = "vois", required = true) MultipartFile[] vois,
            HttpServletRequest request,
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value = "fDate",required = true) String fDate){
        return buildingService.updateMeasuredProblem(building,token,files,vois,request,fDate);
    }
    @RequestMapping(value="/updateMeasuredProblemList", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateMeasuredProblemList(
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value = "editString",required = true) String editString){
        return buildingService.updateMeasuredProblemList(token,editString);
    }
    @RequestMapping(value="/deleteMeasuredProblem")
    @ResponseBody
    public DataWrapper<Void> deleteMeasuredProblem(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return buildingService.deleteMeasuredProblem(id,token);
    }


    @RequestMapping(value="/getMeasuredProblemByProjectId",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<MeasuredProblemPojo>> getMeasuredProblemByProjectId(
    		@RequestParam(value = "projectId",required = true) Long projectId,
    		@RequestParam(value = "id",required = false) Long id,
    		@RequestParam(value = "status",required = false) Integer status,
    		@RequestParam(value = "bfmIds",required = false) String bfmIds,
    		@RequestParam(value = "checkTypeIds",required = false) String checkTypeIds,
    		@RequestParam(value = "token",required = true) String token){
        return buildingService.getMeasuredProblemByProjectId(id,projectId,token,status,bfmIds,checkTypeIds);
    }
    @RequestMapping(value="/getMeasuredProblemByPointId",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<MeasuredProblemPojo> getMeasuredProblemByPointId(
    		@RequestParam(value = "pointId",required = true) Long pointId,
    		@RequestParam(value = "token",required = true) String token){
        return buildingService.getMeasuredProblemByPointId(pointId,token);
    }
    
    /*
     * 复检接口
     * state(0、不通过 1、通过 )
     * score(评分)
     * */
    @RequestMapping(value="/measuredProblemCheckAgain", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> measuredProblemCheckAgain(
    		@RequestParam(value = "measuredId",required = true) Long measuredId,
            @RequestParam(value = "score",required = true) Integer score,
            @RequestParam(value = "state",required = true) Integer state,
            @RequestParam(value = "token",required = true) String token){
        return buildingService.qualityRectificationCheckAgain(token,measuredId,score,state);
    }
    
    /*
     * 整改回复接口
     * **/
    @RequestMapping(value="/measuredProblemCheck", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> measuredProblemCheck(
    		@RequestParam(value = "measuredId",required = true) Long measuredId,
            @RequestParam(value = "schedule",required = true) Integer schedule,
            @RequestParam(value = "content",required = false) String content,
            HttpServletRequest request,
            @RequestParam(value = "pics",required = false) MultipartFile[] pics,
            @RequestParam(value = "vois",required = false) MultipartFile[] vois,
            @RequestParam(value = "token",required = true) String token){
        return buildingService.qualityRectificationCheck(token,measuredId,schedule,content,pics,vois,request);
    }
}
