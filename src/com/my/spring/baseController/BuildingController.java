package com.my.spring.baseController;

import com.my.spring.model.BuildingEntity;
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
            @ModelAttribute BuildingEntity building,
            @RequestParam(value = "token",required = false) String token){
        return buildingService.addBuilding(building);
    }
    @RequestMapping(value="deleteBuilding")
    @ResponseBody
    public DataWrapper<Void> deleteBuilding(
            @RequestParam(value = "id",required = false) Long id,
            @RequestParam(value = "token",required = false) String token){
        return buildingService.deleteBuilding(id);
    }

    @RequestMapping(value="updateBuilding",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateBuilding(
            @ModelAttribute BuildingEntity building,
            @RequestParam(value = "token",required = false) String token){
       // System.out.println(building);
        return buildingService.updateBuilding(building);
    }


    @RequestMapping(value="getBuildingList")
    @ResponseBody
    public DataWrapper<List<BuildingEntity>> getBuildingList(
            @RequestParam(value = "token",required = false) String token){
        return buildingService.getBuildingList();
    }
}
