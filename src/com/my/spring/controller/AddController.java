package com.my.spring.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.support.logging.LogFactory;
import com.my.spring.service.CustomerService;



@Controller
@RequestMapping("api/customer")
public class AddController {
    
    private static com.alibaba.druid.support.logging.Log log = LogFactory.getLog(AddController.class);
     @Autowired
     CustomerService customerService;
     
     @RequestMapping(value = "addController", method = RequestMethod.POST)
     @ResponseBody
     public String batchimport(@RequestParam(value="filename") MultipartFile file,
             HttpServletRequest request,HttpServletResponse response) throws IOException{
         log.info("AddController ..batchimport() start");
         //判断文件是否为空
         if(file==null) return null;
         //获取文件名
         String name=file.getOriginalFilename();
         //进一步判断文件是否为空（即判断其大小是否为0或其名称是否为null）
         long size=file.getSize();
         if(name==null || ("").equals(name) && size==0) return null;
         
         //批量导入。参数：文件名，文件。
         boolean b = customerService.batchImport(name,file);
         if(b){
              String Msg ="批量导入EXCEL成功！";
              request.getSession().setAttribute("msg",Msg);    
         }else{
              String Msg ="批量导入EXCEL失败！";
              request.getSession().setAttribute("msg",Msg);
         } 
        return "Customer/addCustomer3";
     }
         
}