package com.my.spring.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.my.spring.model.ImageRecord;
import com.my.spring.model.ImageRecordData;
import com.my.spring.model.ImageRecordPojo;
import com.my.spring.service.ImageRecordService;
import com.my.spring.utils.DataWrapper;

@Controller
@RequestMapping(value="api/imageRecord")
public class ImageRecordController {
    @Autowired
    ImageRecordService imageRecordService;  
   
    @RequestMapping(value="/admin/addImageRecord", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addImageRecord(
            @ModelAttribute ImageRecord imageRecord,
            @RequestParam(value = "token",required = true) String token){
        return imageRecordService.addImageRecord(token,imageRecord);
    }
    @RequestMapping(value="/admin/updateImageRecord", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<ImageRecord> updateImageRecord(
            @ModelAttribute ImageRecord ImageRecord,
            @RequestParam(value = "token",required = true) String token){
        return imageRecordService.updateImageRecord(token,ImageRecord);
    }
    @RequestMapping(value="/admin/deleteImageRecord",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteImageRecord(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return imageRecordService.deleteImageRecord(token, id);
    }
   

    @RequestMapping(value="/admin/getImageRecordList", method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<ImageRecordPojo>> getImageRecordList(
    		@ModelAttribute ImageRecord imageRecord,
            @RequestParam(value = "token",required = true) String token){
        return imageRecordService.getImageRecordList(token, imageRecord);
    }
    @RequestMapping(value="/getImageRecordListByBuildingId", method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<ImageRecordData>> getImageRecordListByBuildingId(
    		@ModelAttribute ImageRecord imageRecord,
            @RequestParam(value = "token",required = true) String token){
        return imageRecordService.getImageRecordListByBuildingId(token, imageRecord);
    }

}
