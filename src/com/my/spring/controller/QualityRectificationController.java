package com.my.spring.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.my.spring.model.QualityRectification;
import com.my.spring.model.QualityRectificationPojo;
import com.my.spring.service.QualityRectificationService;
import com.my.spring.utils.DataWrapper;


@Controller
@RequestMapping(value="api/qualityRectification")
public class QualityRectificationController {
    @Autowired
    QualityRectificationService QualityManageService;  
   
    @RequestMapping(value="/admin/addQualityRectification", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<QualityRectification> addQualityManage(
            @ModelAttribute QualityRectification qualityManage,
            @RequestParam(value = "pics",required = false) MultipartFile[] pics,
            @RequestParam(value = "vois",required = false) MultipartFile[] vois,
            @RequestParam(value = "qualityCheckId",required = true) Long qualityCheckId,
            HttpServletRequest request,
            @RequestParam(value = "fDate",required = true) String fDate,
            @RequestParam(value = "sendUsers",required = true) String sendUsers,
            @RequestParam(value = "token",required = true) String token){
        return QualityManageService.addQualityRectification(token,fDate,sendUsers,pics,vois,request,qualityManage,qualityCheckId);
    }
    @RequestMapping(value="/admin/updateQualityRectification", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateQualityManage(
            @ModelAttribute QualityRectification qualityManage,
            @RequestParam(value = "pics",required = false) MultipartFile[] pics,
            @RequestParam(value = "vois",required = false) MultipartFile[] vois,
            HttpServletRequest request,
            @RequestParam(value = "fDate",required = false) String fDate,
            @RequestParam(value = "token",required = true) String token){
        return QualityManageService.updateQualityRectification(token,fDate,pics,vois,request,qualityManage);
    }
    @RequestMapping(value="/admin/deleteQualityRectification",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteQualityManage(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return QualityManageService.deleteQualityRectificationById(token,id);
    }
   

    @RequestMapping(value="/admin/getQualityRectificationList", method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<QualityRectificationPojo>> getQualityManageList(
    		@RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute QualityRectification QualityManage,
            @RequestParam(value = "token",required = false) String token){
        return QualityManageService.getQualityRectificationList(pageIndex,pageSize,QualityManage,token);
    }
    
    /*
     * 复检接口
     * state(0、不通过 1、通过 )
     * score(评分)
     * */
    @RequestMapping(value="/qualityCheckAgain", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> qualityCheckAgain(
    		@RequestParam(value = "qualityId",required = true) Long qualityId,
            @RequestParam(value = "score",required = true) Integer score,
            @RequestParam(value = "state",required = true) Integer state,
            @RequestParam(value = "token",required = true) String token){
        return QualityManageService.qualityRectificationCheckAgain(token,qualityId,score,state);
    }
    
    /*
     * 整改回复接口
     * **/
    @RequestMapping(value="/qualityCheck", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> qualityCheck(
    		@RequestParam(value = "qualityId",required = true) Long qualityId,
            @RequestParam(value = "schedule",required = true) Integer schedule,
            @RequestParam(value = "content",required = false) String content,
            HttpServletRequest request,
            @RequestParam(value = "pics",required = false) MultipartFile[] pics,
            @RequestParam(value = "vois",required = false) MultipartFile[] vois,
            @RequestParam(value = "token",required = true) String token){
        return QualityManageService.qualityRectificationCheck(token,qualityId,schedule,content,pics,vois,request);
    }
    

}
