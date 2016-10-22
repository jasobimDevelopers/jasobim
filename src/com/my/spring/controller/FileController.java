package com.my.spring.controller;

import com.my.spring.model.Customer;
import com.my.spring.model.Element;
import com.my.spring.service.ElementService;
import com.my.spring.service.FileService;
import com.my.spring.serviceImpl.ReadExcel;
import com.my.spring.utils.DataWrapper;
import com.wordnik.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016/6/22.
 */
@Api(value= "File", description="文件上传接口",position=10)
@Controller
@RequestMapping(value="api/file")
public class FileController {
    @Autowired
    FileService fileService;
    @Autowired
    ElementService elementService;
    @RequestMapping(value="uploadFile",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> uploadFile(
            @RequestParam(value = "filename", required = false) MultipartFile file,
            HttpServletRequest request,
            @RequestParam(value = "token",required = false) String token){
         //判断文件是否为空
         if(file==null) return null;
         //获取文件名
         String name=file.getOriginalFilename();
         //进一步判断文件是否为空（即判断其大小是否为0或其名称是否为null）
         long size=file.getSize();
         if(name==null || ("").equals(name) && size==0) return null;
         
         //批量导入。参数：文件名，文件。
         boolean b = elementService.batchImport(name,file);
         if(b){
              String Msg ="批量导入EXCEL成功！";
              request.getSession().setAttribute("msg",Msg);    
         }else{
              String Msg ="批量导入EXCEL失败！";
              request.getSession().setAttribute("msg",Msg);
         } 
		
        return fileService.uploadFile(request,file);
    }
}
