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

import com.my.spring.service.FileUploadService;
import com.my.spring.utils.DataWrapper;

@Controller
@RequestMapping(value="api/fileUpload")
public class FileUploadController {
    @Autowired
    FileUploadService fileUploadService;  
    /*
     *文件上传并生成二维码
     *返回二维码图片的地址
     */
    @RequestMapping(value="/admin/uploadFiles", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<List<String>> uploadItem(
            @RequestParam(value = "fileList", required = false) MultipartFile[] fileList,
            @RequestParam(value = "token",required = true) String token,
            HttpServletRequest request){
    	String filePath = "files/uploadFiles";
        return fileUploadService.batchImport(filePath, fileList,token,request);
    }
}
