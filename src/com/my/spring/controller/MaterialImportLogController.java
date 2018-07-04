package com.my.spring.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.spring.model.MaterialImportLog;
import com.my.spring.model.MaterialImportLogPojo;
import com.my.spring.service.MaterialImportLogService;
import com.my.spring.utils.DataWrapper;

@Controller
@RequestMapping(value="api/MaterialImportLog")
public class MaterialImportLogController {
	@Autowired
    MaterialImportLogService amService;
    @RequestMapping(value="/addMaterialImportLog", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addMaterialImportLog(
            @ModelAttribute MaterialImportLog am,
            @RequestParam(value = "token",required = true) String token){
        return amService.addMaterialImportLog(am, token);
    }
    @RequestMapping(value="/deleteMaterialImportLog",method=RequestMethod.GET)
    @ResponseBody
    
    public DataWrapper<Void> deleteMaterialImportLog(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return amService.deleteMaterialImportLog(id,token);
    }

    @RequestMapping(value="/getMaterialImportLogList", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<MaterialImportLogPojo>> getMaterialImportLogList(
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute MaterialImportLog ps){
        return amService.getMaterialImportLogList(token, ps, pageSize, pageIndex);
    }
   
   
}
