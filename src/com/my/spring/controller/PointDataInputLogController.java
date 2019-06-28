package com.my.spring.controller;

import com.my.spring.model.PointDataInputLog;
import com.my.spring.service.PointDataInputLogService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping(value="api/pointDataInputLog")
public class PointDataInputLogController {
    @Autowired
    PointDataInputLogService buildingService;
    @RequestMapping(value="/addPointDataInputLog", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addBuilding(
    		@RequestParam(value = "jsonString",required = true) String jsonString,
            @RequestParam(value = "token",required = true) String token){
        return buildingService.addPointDataInputLog(jsonString,token);
    }
    @RequestMapping(value="/deletePointDataInputLog")
    @ResponseBody
    public DataWrapper<Void> deletePointDataInputLog(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return buildingService.deletePointDataInputLog(id,token);
    }

    @RequestMapping(value="/getPointDataInputLogByPointId",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<PointDataInputLog>> getPointDataInputLogByPointId(
    		@RequestParam(value = "pointId",required = true) Long pointId,
    		@RequestParam(value = "token",required = true) String token){
        return buildingService.getPointDataInputLogByPointId(pointId, token);
    }
}
