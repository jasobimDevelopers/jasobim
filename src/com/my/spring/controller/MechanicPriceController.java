package com.my.spring.controller;

import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.spring.model.AttenceLog;
import com.my.spring.model.AttenceLogPojo;
import com.my.spring.model.MechanicPrice;
import com.my.spring.model.MechanicPricePojo;
import com.my.spring.service.AttenceLogService;
import com.my.spring.service.MechanicPriceService;
import com.my.spring.utils.DataWrapper;

@Controller
@RequestMapping(value="api/mechanicPrice")
public class MechanicPriceController {
	@Autowired
	MechanicPriceService amService;
    @RequestMapping(value="/addMechanicPrice", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addMechanicPrice(
            @ModelAttribute MechanicPrice am,
            @RequestParam(value = "token",required = true) String token){
        return amService.addMechanicPrice(am, token);
    }
    
    @RequestMapping(value="/updateMechanicPrice", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateMechanicPrice(
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value = "info",required = true) String[] info,
            @RequestParam(value = "dates",required = true) String dates){
        return amService.updateMechanicPrice(info, token,dates);
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
            @RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute MechanicPrice ps){
        return amService.getMechanicPriceList(token, ps, pageSize, pageIndex);
    }
   
   
}
