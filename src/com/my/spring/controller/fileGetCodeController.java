package com.my.spring.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.my.spring.service.CodeService;
import com.my.spring.utils.DataWrapper;

@Controller
@RequestMapping(value="api/code")
public class fileGetCodeController {
    @Autowired
    CodeService codeService;  
    /*
     *模型的原始构件信息上传
     */
    @RequestMapping(value="/admin/uploadItem", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<List<String>> uploadItem(
            @RequestParam(value = "fileList", required = false) MultipartFile[] fileList,
            @RequestParam(value = "token",required = true) String token,
            HttpServletRequest request){
    	String filePath = "/files/code/";
        return codeService.batchImport(filePath, fileList,token,request);
    }
    
   

}
