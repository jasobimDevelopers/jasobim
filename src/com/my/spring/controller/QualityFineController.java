package com.my.spring.controller;

import com.my.spring.model.QualityFine;
import com.my.spring.model.QualityFinePojo;
import com.my.spring.service.QualityFineService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@Controller
@RequestMapping(value="api/qualityFine")
public class QualityFineController {
    @Autowired
    QualityFineService fineService;
    @RequestMapping(value="/addQualityFine", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addQualityFine(
            @ModelAttribute QualityFine fine,
            @RequestParam(value = "token",required = true) String token){
        return fineService.addQualityFine(fine,token);
    }
    @RequestMapping(value="/deleteQualityFine",method=RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> deleteDuct(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return fineService.deleteQualityFine(id,token);
    }
    @RequestMapping(value="/getQualityFineList",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<QualityFinePojo>> getQualityFineList(
    		@RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute QualityFine duct,
    		@RequestParam(value="token",required=true) String token){
        return fineService.getQualityFineList(pageIndex,pageSize,duct,token);
    }
    
    
    @RequestMapping(value="/admin/updateQualityFine",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateQualityFine(
            @ModelAttribute QualityFine duct,
            @RequestParam(value = "token",required = true) String token){
        return fineService.updateQualityFine(duct,token);
    }
   
}
