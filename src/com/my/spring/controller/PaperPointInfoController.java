package com.my.spring.controller;

import com.my.spring.model.PaperPointInfo;
import com.my.spring.model.PaperPointInfoPojo;
import com.my.spring.model.PaperPointNumsLog;
import com.my.spring.model.PointConditionsCountNums;
import com.my.spring.service.PaperPointInfoService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping(value="api/paperPointInfo")
public class PaperPointInfoController {
    @Autowired
    PaperPointInfoService infoService;
    
    /*测量点位新增接口*/
    @RequestMapping(value="/addPaperPointInfo", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addPaperPointInfo(
    		 @RequestParam(value = "token",required = true) String token,
    		 @RequestParam(value = "jsonString",required = true) String jsonString){
        return infoService.addPaperPointInfo(token,jsonString);
    }
    @RequestMapping(value="/deletePaperPointInfo",method=RequestMethod.GET)
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
    /*点位统计接口*/
    /*
     * 统计该项目的所有点位
     * 统计该项目的已测点位数
     * 统计该项目的爆点数
     * */
    @RequestMapping(value="/getPaperPointInfoNums",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<PaperPointNumsLog>> getPaperPointInfoNums(
    		@ModelAttribute PaperPointNumsLog countLog,
    		@RequestParam(value = "token",required = true) String token)
    {
    	return infoService.getPaperPointInfoNums(token,countLog);
    }
    /*按楼栋号统计*/
    /*
     *按照楼栋号统计该楼栋的所有点位 
     * 按照楼栋号统计该楼栋的所有已测点位 
     * 按照楼栋号统计该楼栋的所有问题点位
     **/
    @RequestMapping(value="/getPaperPointInfoNumsByBuilding",method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<PointConditionsCountNums>> getPaperPointInfoNumsByBuilding(
    		@ModelAttribute PaperPointNumsLog countLog,
    		@RequestParam(value = "token",required = true) String token)
    {
    	return infoService.getPaperPointInfoNumsByConditions(token,countLog);
    }
    
    /*通过楼栋号按照具体区域统计*/
    /*
     *按照房间号统计所有点位 
     * 按照房间号统计所有已测点位 
     * 按照房间号统计所有问题点位
     **/
    @RequestMapping(value="/getPaperPointInfoNumsBySite",method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<PointConditionsCountNums>> getPaperPointInfoNumsBySite(
    		@ModelAttribute PaperPointNumsLog countLog,
    		@RequestParam(value = "bfmId",required = true) Long bfmId,
    		@RequestParam(value = "token",required = true) String token)
    {
    	return infoService.getPaperPointInfoNumsBySite(token,countLog,bfmId);
    }
    /*通过房间号获取所有测点*/
    @RequestMapping(value="/getPaperPointInfoList",method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<PaperPointInfoPojo> getPaperPointInfoList(
    		@ModelAttribute PaperPointNumsLog countLog,
    		@RequestParam(value = "siteId",required = true) Long siteId,
    		@RequestParam(value = "token",required = true) String token)
    {
    	return infoService.getPaperPointInfoList(token,countLog,siteId);
    }
}
