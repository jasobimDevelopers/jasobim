 package com.my.spring.controller;

import com.my.spring.enums.CallStatusEnum;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Material;
import com.my.spring.model.MaterialPojo;
import com.my.spring.service.MaterialService;
import com.my.spring.utils.DataWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@Controller
@RequestMapping(value="api/material")
public class MaterialController {
    MaterialService materialService;
    @RequestMapping(value="/vs/addMaterial", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addMaterial(
            @ModelAttribute Material news,
            @RequestParam(value = "token",required = true) String token){
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		dataWrapper=materialService.addMaterial(news,token);
		if(dataWrapper.getCallStatus()==CallStatusEnum.SUCCEED){
            	return dataWrapper;
    	}else{
    		dataWrapper.setErrorCode(ErrorCodeEnum.Error);
    	}
        return dataWrapper;
    }
    @RequestMapping(value="/admin/deleteMaterial",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteNews(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return materialService.deleteMaterial(id,token);
    }

    @RequestMapping(value="updateMaterial",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateMaterial(
            @ModelAttribute Material News,
            @RequestParam(value = "token",required = false) String token){
        System.out.println(News);
        return materialService.updateMaterial(News,token);
    }


    @RequestMapping(value="/vs/getMaterialList", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<MaterialPojo>> getMaterialList(
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute Material News){
        return materialService.getMaterialList(token,pageIndex,pageSize,News);
    }
   
}
