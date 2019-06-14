package com.my.spring.controller;

import com.my.spring.model.MeasuredSite;
import com.my.spring.service.MeasuredSiteService;
import com.my.spring.utils.DataWrapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
@Controller
@RequestMapping(value="api/measuredSite")
public class MeasuredSiteController {
    @Autowired
    MeasuredSiteService buildingService;
    @RequestMapping(value="/addMeasuredSite", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addMeasuredSite(
            @ModelAttribute MeasuredSite building,
            @RequestParam(value = "siteDetail",required = true) String siteDetail,
            @RequestParam(value = "token",required = true) String token){
        return buildingService.addMeasuredSite(building,token,siteDetail);
    }
    @RequestMapping(value="/deleteMeasuredSite",method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteMeasuredSite(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return buildingService.deleteMeasuredSite(id,token);
    }

    @RequestMapping(value="/updateMeasuredSite",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> postMeasuredSite(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "siteNewName",required = false) String siteNewName,
            @RequestParam(value = "paperOfMeasuredId",required = false) Long paperOfMeasuredId,
            @RequestParam(value = "checkUser",required = false) Long checkUser,
            @RequestParam(value = "token",required = true) String token){
        return buildingService.updateMeasuredSite(id,token,siteNewName,paperOfMeasuredId,checkUser);
    }
    
    @RequestMapping(value="/getMeasuredSiteListByBuildingId",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<MeasuredSite>> getMeasuredSiteListByBuildingId(
    		@RequestParam(value = "buildingId",required = true) Long buildingId,
    		@RequestParam(value = "token",required = true) String token){
        return buildingService.getMeasuredSiteListByBuildingId(buildingId,token);
    }
}
