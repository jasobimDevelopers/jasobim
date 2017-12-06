package com.my.spring.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Item;
import com.my.spring.model.MeasuredData;
import com.my.spring.model.MeasuredDataPojo;
import com.my.spring.model.MinItem;
import com.my.spring.model.MinItemPojo;
import com.my.spring.service.ItemService;
import com.my.spring.service.MeasuredDataService;
import com.my.spring.utils.DataWrapper;
@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping(value="api/measuredData")
public class MeasuredDataController {
    @Autowired
    MeasuredDataService measuredDataService;  
    
  
    
    @RequestMapping(value="/addMeasuredData", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addMeasuredData(
            @ModelAttribute MeasuredData measuredData,
            @RequestParam(value = "token",required = false) String token,
            @RequestParam(value = "webToken",required = false) String webToken){
        return measuredDataService.addMeasuredData(measuredData,token,webToken);
    }
    @RequestMapping(value="/deleteMeasuredData")
    @ResponseBody
    public DataWrapper<Void> deleteMeasuredData(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return measuredDataService.deleteMeasuredData(id,token);
    }

    @RequestMapping(value="/getMeasuredDataList", method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<MeasuredDataPojo>> getMeasuredDataList(
    		@RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute MeasuredData measuredData,
            @RequestParam(value = "token",required = false) String token){
        return measuredDataService.getMeasuredDataList(pageIndex,pageSize,measuredData,token);
    }
    
    @RequestMapping(value="/getMeasuredDataById",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<MeasuredDataPojo> getMeasuredDataById(
    		@RequestParam(value = "measuredDataId",required = true) Long measuredDataId,
            @RequestParam(value = "token",required = false) String token){
        return measuredDataService.getMeasuredDataById(measuredDataId,token);
    }
   
  

}
