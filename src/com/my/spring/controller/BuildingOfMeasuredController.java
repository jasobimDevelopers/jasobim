package com.my.spring.controller;

import com.my.spring.model.BuildingOfMeasured;
import com.my.spring.service.BuildingOfMeasuredService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value="api/buildingOfMeasured")
public class BuildingOfMeasuredController {
    @Autowired
    BuildingOfMeasuredService buildingOfMeasuredService;
    @RequestMapping(value="/addBuildingOfMeasured", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addBuildingOfMeasured(
            @ModelAttribute BuildingOfMeasured buildingOfMeasured,
            @RequestParam(value = "token",required = true) String token){
        return buildingOfMeasuredService.addBuildingOfMeasured(buildingOfMeasured,token);
    }
    @RequestMapping(value="/deleteBuildingOfMeasured")
    @ResponseBody
    public DataWrapper<Void> deleteBuildingOfMeasured(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return buildingOfMeasuredService.deleteBuildingOfMeasured(id,token);
    }
    @RequestMapping(value="/getBuildingOfMeasuredByProjectId",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<BuildingOfMeasured> getBuildingByProjectId(
    		@RequestParam(value = "projectId",required = true) Long projectId,
    		@RequestParam(value = "token",required = true) String token){
        return buildingOfMeasuredService.getBuildingOfMeasuredByProjectId(projectId,token);
    }
}
