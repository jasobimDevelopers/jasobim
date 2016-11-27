package com.my.spring.controller;

import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Quantity;
import com.my.spring.service.QuantityService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016/6/22.
 */
@Controller
@RequestMapping(value="api/quantity")
public class QuantityController {
    @Autowired
    QuantityService quantityService;
    @RequestMapping(value="/admin/uploadItem", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> uploadItem(
            @RequestParam(value = "fileList", required = false) MultipartFile file,
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value = "projectId",required = true) Long projectId,
            HttpServletRequest request){
    	String filePath = "/fileupload/quantitys";
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
    	if(quantityService.batchImport(filePath, file,token,request,projectId)){
            	dataWrapper.setErrorCode(ErrorCodeEnum.No_Error);
        }else{
            	dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }
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
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute Quantity quantity){
        return quantityService.getQuantityList(projectId,token,pageIndex,pageSize,quantity);
    }
    
    @RequestMapping(value="/getQuantityDetailsByAdmin")
    @ResponseBody
    public DataWrapper<Quantity> getQuantityDetailsByAdmin(
    		@RequestParam(value = "id",required = true) Long quantityId,
            @RequestParam(value = "token",required = true) String token){
        return quantityService.getQuantityDetailsByAdmin(quantityId, token);
    }
    
    @RequestMapping(value="/exportQuantity")
    @ResponseBody
    public DataWrapper<String> exportQuantity(
    		@RequestParam(value = "projectId",required = true) Long projectId,
            @RequestParam(value = "token",required = true) String token,
            HttpServletRequest request) {
        return quantityService.exportQuantity(projectId, token, request);
    }
    
    
}
