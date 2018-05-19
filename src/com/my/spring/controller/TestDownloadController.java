package com.my.spring.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.spring.model.Folder;
import com.my.spring.service.FolderService;
import com.my.spring.utils.DataWrapper;

@Controller
@RequestMapping(value="api/test")
public class TestDownloadController {
	@Autowired
	 FolderService floderService;
	 
	 
	 /*
	  * 新建文件夹
	  * 
	  * */
	 @RequestMapping(value="/download",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> DownloadZip(
    		 HttpServletRequest request, HttpServletResponse response) throws Exception{
       return floderService.testDownloadZip(request,response);
    }
}
