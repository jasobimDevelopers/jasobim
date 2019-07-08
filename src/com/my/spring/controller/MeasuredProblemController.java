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
            @RequestParam(value = "pics", required = true) MultipartFile[] pics,
            @RequestParam(value = "vois", required = true) MultipartFile[] vois,
            HttpServletRequest request,
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value = "fDate",required = true) String fDate){
        return buildingService.addMeasuredProblem(building,token,pics,vois,request,fDate);
    }
    @RequestMapping(value="/updateMeasuredProblem", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateMeasuredProblem(
            @ModelAttribute MeasuredProblem building,
            @RequestParam(value = "pics", required = true) MultipartFile[] pics,
            @RequestParam(value = "vois", required = true) MultipartFile[] vois,
            HttpServletRequest request,
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value = "fDate",required = true) String fDate){
        return buildingService.updateMeasuredProblem(building,token,pics,vois,request,fDate);
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
    		@RequestParam(value = "siteId",required = false) Long siteId,
    		@RequestParam(value = "status",required = false) Integer status,
    		@RequestParam(value = "bfmIds",required = false) String bfmIds,
    		@RequestParam(value = "checkTypeIds",required = false) String checkTypeIds,
    		@RequestParam(value = "token",required = true) String token){
        return buildingService.getMeasuredProblemByProjectId(id,projectId,token,status,bfmIds,checkTypeIds,siteId);
    }
    @RequestMapping(value="/getMeasuredProblemByPointId",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<MeasuredProblemPojo> getMeasuredProblemByPointId(
    		@RequestParam(value = "pointId",required = true) Long pointId,
    		@RequestParam(value = "token",required = true) String token){
        return buildingService.getMeasuredProblemByPointId(pointId,token);
    }
    
    /*
     * 验收接口
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
    /**
     *批量验收接口 
     **/
    @RequestMapping(value="/measuredProblemListCheckAgain", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> measuredProblemListCheckAgain(
    		@RequestParam(value = "checkString",required = true) String checkString,
            @RequestParam(value = "token",required = true) String token){
        return buildingService.measuredProblemListCheckAgain(token,checkString);
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
    /**
     *催办接口 
     */
    @RequestMapping(value="/measuredProblemSend", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> measuredProblemSend(
    		@RequestParam(value = "aboutIds",required = true) String aboutIds,
    		@RequestParam(value = "projectId",required = true) Long projectId,
            @RequestParam(value = "token",required = true) String token){
        return buildingService.measuredProblemSend(aboutIds,token,projectId);
    }
    /*
     * 详情添加相关人员接口
     * */
    @RequestMapping(value="/measuredProblemAddUser", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<List<String>> measuredProblemAddUser(
    		@RequestParam(value = "aboutIds",required = true) String aboutIds,
    		@RequestParam(value = "measuredProblemId",required = true) Long measuredProblemId,
    		@RequestParam(value = "projectId",required = true) Long projectId,
            @RequestParam(value = "token",required = true) String token){
        return buildingService.measuredProblemAddUser(aboutIds,token,measuredProblemId,projectId);
    }
}
