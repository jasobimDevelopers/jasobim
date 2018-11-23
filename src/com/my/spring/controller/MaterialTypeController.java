 package com.my.spring.controller;

import com.my.spring.model.MaterialType;
import com.my.spring.model.MaterialTypePojo;
import com.my.spring.service.MaterialTypeService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping(value="api/materialType")
public class MaterialTypeController {
	@Autowired
    MaterialTypeService materialTypeService;
    @RequestMapping(value="/vs/addMaterialType", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addMaterialType(
            @ModelAttribute MaterialType news,
            @RequestParam(value = "token",required = true) String token){
        return materialTypeService.addMaterialType(news,token);
    }
    @RequestMapping(value="/admin/deleteMaterialType",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteMaterialType(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return materialTypeService.deleteMaterialType(id,token);
    }

    @RequestMapping(value="/self/updateMaterialType",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateMaterialType(
            @ModelAttribute MaterialType News,
            @RequestParam(value = "token",required = false) String token){
        return materialTypeService.updateMaterialType(News,token);
    }


    @RequestMapping(value="/vs/getMaterialTypeList", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<MaterialTypePojo>> getMaterialTypeList(
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute MaterialType News){
        return materialTypeService.getMaterialTypeList(token,pageIndex,pageSize,News);
    }
   
}
