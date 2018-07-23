package com.my.spring.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.spring.model.MechanicData;
import com.my.spring.model.MechanicPrice;
import com.my.spring.model.MechanicPricePojo;
import com.my.spring.model.MechanicPricePojos;
import com.my.spring.service.MechanicPriceService;
import com.my.spring.utils.DataWrapper;

@Controller
@RequestMapping(value="api/mechanicPrice")
public class MechanicPriceController {
	@Autowired
	MechanicPriceService amService;
    @RequestMapping(value="/addMechanicPriceList", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addMechanicPrice(
    		@RequestParam(value = "am", required = false) String am,
            @RequestParam(value = "token",required = false) String token){
        return amService.addMechanicPriceList(am, token);
    }
    
    
    @RequestMapping(value="/addMechanicPrice", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addMechanicPrice(
    		@ModelAttribute  MechanicPrice am,
            @RequestParam(value = "token",required = true) String token){
        return amService.addMechanicPrice(am, token);
    }
    
    @RequestMapping(value="/updateMechanicPrice", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateMechanicPrice(
            @RequestParam(value = "token",required = true) String token,
            @ModelAttribute  MechanicPrice am,
            @RequestParam(value = "date",required = false) String date){
        return amService.updateMechanicPrice(am,token,date);
    }
    @RequestMapping(value="/deleteAttenceLog",method=RequestMethod.GET)
    @ResponseBody
    
    public DataWrapper<Void> deleteMechanicPrice(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return amService.deleteMechanicPrice(id,token);
    }

    @RequestMapping(value="/getMechanicPriceList", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<MechanicPricePojo>> getMechanicPriceList(
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value = "date",required = false) String date,
            @RequestParam(value = "projectId",required = true) Long projectId,
            @RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute MechanicPrice ps){
        return amService.getMechanicPriceList(token, ps, pageSize, pageIndex,projectId,date);
    }
    
    
    /**
     * 用工统计列表数据获取接口
     * 默认获取当月的用工统计信息
     * 可以通过传时间查询某月信息
     * 
     * */
    @RequestMapping(value="/getMechanicPriceNum", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<MechanicPricePojos>> getMechanicPriceNum(
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value = "projectId",required = true) Long projectId,
            @RequestParam(value = "date",required = false) String date,
    		@ModelAttribute MechanicPrice ps){
        return amService.getMechanicPriceNum(token,projectId,date);
    }
    /**
     * 用工统计导出表
     * 
     * */
    @RequestMapping(value="/exportMechanicNum", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<String> exportMechanicNum(
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value = "projectId",required = true) Long projectId,
            @RequestParam(value = "date",required = false) String date,
    		@ModelAttribute MechanicPrice ps){
        return amService.exportMechanicNum(token,projectId,date);
    }
    /**
     * app劳动力监测接口
     * 
     * */
    @RequestMapping(value="/app/getMechanicDatas", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<MechanicData>> getMechanicDatas(
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value = "projectId",required = true) Long projectId,
            @RequestParam(value = "date",required = false) String date,
    		@ModelAttribute MechanicPrice ps){
        return amService.getMechanicDatas(token,projectId,date);
    }
}
