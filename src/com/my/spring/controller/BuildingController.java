package com.my.spring.controller;

import com.my.spring.model.Building;
import com.my.spring.service.BuildingService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Controller
@RequestMapping(value="api/building")
public class BuildingController {
    @Autowired
    BuildingService buildingService;
    @RequestMapping(value="addBuilding", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addBuilding(
            @ModelAttribute Building building,
            @RequestParam(value = "token",required = true) String token){
        return buildingService.addBuilding(building,token);
    }
    @RequestMapping(value="deleteBuilding")
    @ResponseBody
    public DataWrapper<Void> deleteBuilding(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return buildingService.deleteBuilding(id,token);
    }

    @RequestMapping(value="updateBuilding",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateBuilding(
            @ModelAttribute Building building,
            @RequestParam(value = "token",required = true) String token){
       // System.out.println(building);
    	////////building 的id不能为空
        return buildingService.updateBuilding(building,token);
    }


    @RequestMapping(value="getBuildingList")
    @ResponseBody
    public DataWrapper<List<Building>> getBuildingList(){
        return buildingService.getBuildingList();
    }
    @RequestMapping(value="/admin/getBuildingByProjectId",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Building> getBuildingByProjectId(
    		@RequestParam(value = "projectId",required = true) Long projectId){
        return buildingService.getBuildingByProjectId(projectId);
    }
}
