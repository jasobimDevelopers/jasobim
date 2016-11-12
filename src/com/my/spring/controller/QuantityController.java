package com.my.spring.controller;

import com.my.spring.model.Quantity;
import com.my.spring.service.QuantityService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Controller
@RequestMapping(value="api/quantity")
public class QuantityController {
    @Autowired
    QuantityService quantityService;
    @RequestMapping(value="/addQuantity", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addQuantity(
            @ModelAttribute Quantity quantity,
            @RequestParam(value = "token",required = true) String token){
        	///////////////////工程量计算
    	    /*按照楼号计算*/
    	
    	return quantityService.addQuantity(quantity,token);
    }
    @RequestMapping(value="/deleteQuantity")
    @ResponseBody
    public DataWrapper<Void> deleteQuantity(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return quantityService.deleteQuantity(id,token);
    }

    @RequestMapping(value="/updateQuantity",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateQuantity(
            @ModelAttribute Quantity quantity,
            @RequestParam(value = "token",required = true) String token){
        System.out.println(quantity);
        return quantityService.updateQuantity(quantity,token);
    }


    @RequestMapping(value="/getQuantityList",method =RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<Quantity>> getQuantityList(
    		@RequestParam(value = "projectId",required = true) Long projectId,
            @RequestParam(value = "token",required = true) String token){
        return quantityService.getQuantityList(projectId,token);
    }
    
    @RequestMapping(value="/getQuantityDetailsByAdmin")
    @ResponseBody
    public DataWrapper<Quantity> getQuantityDetailsByAdmin(
    		@RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return quantityService.getQuantityDetailsByAdmin(id, token);
    }
    
    
}
