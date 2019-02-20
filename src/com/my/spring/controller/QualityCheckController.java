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
import com.my.spring.model.QualityCheck;
import com.my.spring.model.QualityCheckPartPojo;
import com.my.spring.model.QualityCheckPojo;
import com.my.spring.service.QualityCheckService;
import com.my.spring.utils.DataWrapper;


@Controller
@RequestMapping(value="api/qualityCheck")
public class QualityCheckController {
    @Autowired
    QualityCheckService QualityManageService;  
   
    @RequestMapping(value="/admin/addQualityCheck", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Long> addQualityManage(
            @ModelAttribute QualityCheck qualityManage,
            @RequestParam(value = "pics",required = false) MultipartFile[] pics,
            @RequestParam(value = "vois",required = false) MultipartFile[] vois,
            HttpServletRequest request,
            @RequestParam(value = "token",required = true) String token){
        return QualityManageService.addQualityCheck(token,pics,vois,request,qualityManage);
    }
    @RequestMapping(value="/admin/updateQualityCheck", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateQualityManage(
            @ModelAttribute QualityCheck qualityManage,
            @RequestParam(value = "pics",required = false) MultipartFile[] pics,
            @RequestParam(value = "vois",required = false) MultipartFile[] vois,
            HttpServletRequest request,
            @RequestParam(value = "token",required = true) String token){
        return QualityManageService.updateQualityCheck(token,pics,vois,request,qualityManage);
    }
    @RequestMapping(value="/admin/deleteQualityCheck",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteQualityManage(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return QualityManageService.deleteQualityCheckById(token,id);
    }
   

    @RequestMapping(value="/admin/getQualityCheckList", method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<QualityCheckPojo>> getQualityManageList(
    		@RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute QualityCheck QualityManage,
    		@RequestParam(value="start",required=false) String start,
    		@RequestParam(value="end",required=false) String end,
    		@RequestParam(value="find",required=false) String find,
            @RequestParam(value = "token",required = false) String token){
        return QualityManageService.getQualityCheckList(pageIndex,pageSize,QualityManage,token,start,end,find);
    }
    
    /*
     * 奖惩事由列表获取
     * */
    @RequestMapping(value="/admin/getQualityCheckPartList", method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<QualityCheckPartPojo>> getQualityCheckPartList(
    		@RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
            @RequestParam(value = "token",required = false) String token,
            @RequestParam(value = "projectId",required = true) Long projectId){
        return QualityManageService.getQualityCheckPartList(pageIndex,pageSize,token,projectId);
    }

}
