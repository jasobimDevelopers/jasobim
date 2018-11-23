package com.my.spring.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.my.spring.model.ImageRecordBuildingInfo;
import com.my.spring.service.ImageRecordBuildingInfoService;
import com.my.spring.utils.DataWrapper;
/**
* @author 徐雨祥
* @version 创建时间：2018年11月2日 上午8:50:23
* 类说明
*/
@Controller
@RequestMapping(value="api/imageRecordBuildingInfo")
public class ImageRecordBuildingInfoController {
	@Autowired
	ImageRecordBuildingInfoService infoService;  
   
    @RequestMapping(value="/addImageRecordBuildingInfo", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<ImageRecordBuildingInfo> addImageRecordBuildingInfo(
            @ModelAttribute ImageRecordBuildingInfo info,
            @RequestParam(value = "token",required = true) String token){
        return infoService.addImageRecordBuildingInfo(token,info);
    }
    
    @RequestMapping(value="/updateImageRecordBuildingInfo", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateImageRecordBuilding(
            @ModelAttribute ImageRecordBuildingInfo info,
            @RequestParam(value = "token",required = true) String token){
        return infoService.updateImageRecordBuildingInfo(token,info);
    }
    
    @RequestMapping(value="/deleteImageRecordBuildingInfo",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteImageRecordBuildingInfo(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return infoService.deleteImageRecordBuildingInfo(token, id);
    }

    @RequestMapping(value="/getImageRecordBuildingInfoById",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<ImageRecordBuildingInfo> getImageRecordBuildingInfoById(
    		@RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return infoService.getImageRecordBuildingInfoById(token,id);
    }
    
    @RequestMapping(value="/getImageRecordBuildingInfoList",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<ImageRecordBuildingInfo>> getImageRecordBuildingInfoList(
    		@ModelAttribute ImageRecordBuildingInfo info,
            @RequestParam(value = "token",required = true) String token){
        return infoService.getImageRecordBuildingInfoList(token,info);
    }

}
