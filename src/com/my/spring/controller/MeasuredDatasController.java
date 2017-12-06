package com.my.spring.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.spring.model.MeasuredDatas;
import com.my.spring.service.MeasuredDatasService;
import com.my.spring.utils.DataWrapper;

@Controller
@RequestMapping(value="api/measuredDatas")
public class MeasuredDatasController {
    @Autowired
    MeasuredDatasService measuredDatasService;  
    
  
    
    @RequestMapping(value="/addMeasuredDatas", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addMeasuredDatas(
            @ModelAttribute MeasuredDatas measuredDatas,
            @RequestParam(value = "token",required = false) String token,
            @RequestParam(value = "sceneFlag",required = false) String sceneFlag){
        return measuredDatasService.addMeasuredDatas(measuredDatas,token,sceneFlag);
    }
    @RequestMapping(value="/deleteMeasuredDatas")
    @ResponseBody
    public DataWrapper<Void> deleteMeasuredData(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return measuredDatasService.deleteMeasuredDatas(id,token);
    }

    @RequestMapping(value="/getMeasuredDatasList", method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<MeasuredDatas>> getMeasuredDatasList(
    		@RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute MeasuredDatas measuredDatas,
            @RequestParam(value = "token",required = false) String token){
        return measuredDatasService.getMeasuredDatasList(pageIndex,pageSize,measuredDatas,token);
    }
 

}
