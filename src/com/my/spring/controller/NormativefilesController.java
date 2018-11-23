 package com.my.spring.controller;

import com.my.spring.enums.CallStatusEnum;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Normativefiles;
import com.my.spring.model.NormativefilesPojo;
import com.my.spring.model.NormativefilesPojos;
import com.my.spring.service.NormativefilesService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping(value="api/normativefiles")
public class NormativefilesController {
    @Autowired
    NormativefilesService NormativefilesService;
    @RequestMapping(value="/admin/addNormativefiles", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addNormativefiles(
            @ModelAttribute Normativefiles ps,
            @RequestParam(value = "fileList", required = true) MultipartFile[] fileList,
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value = "fileType",required = true) Integer fileType,
            HttpServletRequest request){
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		dataWrapper=NormativefilesService.addNormativefiles(ps,token,fileList,fileType,request);
		if(dataWrapper.getCallStatus()==CallStatusEnum.SUCCEED){
            	return dataWrapper;
    	}else{
    		dataWrapper.setErrorCode(ErrorCodeEnum.Error);
    	}
        return dataWrapper;
    }
    @RequestMapping(value="/deleteNormativefiles",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteNormativefiles(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "fileId",required = true) Long fileId,
            @RequestParam(value = "token",required = true) String token){
        return NormativefilesService.deleteNormativefiles(id,token,fileId);
    }

    @RequestMapping(value="/admin/getNormativefilesList", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<NormativefilesPojo>> getNormativefilesList(
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute Normativefiles ps){
        return NormativefilesService.getNormativefilesList(token,pageIndex,pageSize,ps);
    }
    @RequestMapping(value="/getNormativefilesLists", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<NormativefilesPojos>> getNormativefilesLists(
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value = "projectId",required = false) Long projectId,
            @RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute Normativefiles ps){
        return NormativefilesService.getNormativefilesLists(token,pageIndex,pageSize,ps,projectId);
    }
    ////通过用户id查找留言
    @RequestMapping(value="/getNormativefilesListByUserId",method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<Normativefiles>> getNewsListByUserId(
    		@RequestParam(value = "userId",required = true) Long userId,
            @RequestParam(value = "token",required = true) String token){
        return NormativefilesService.getNormativefilesListByUserId(userId,token);
    }
   
}
