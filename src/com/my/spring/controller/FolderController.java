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

import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Duct;
import com.my.spring.model.DuctPojo;
import com.my.spring.model.DuctPojos;
import com.my.spring.model.Folder;
import com.my.spring.model.FolderPojo;
import com.my.spring.service.DuctService;
import com.my.spring.service.FolderService;
import com.my.spring.utils.DataWrapper;

@Controller
@RequestMapping(value="api/floder")
public class FolderController {
	 @Autowired
	    FolderService floderService;
	   
	    @RequestMapping(value="/getFolderList",method=RequestMethod.GET)
	    @ResponseBody
	    public DataWrapper<Object> getFloderList(
	    		@RequestParam(value="pageIndex",required=false) Integer pageIndex,
	    		@RequestParam(value="pageSize",required=false) Integer pageSize,
	    		@ModelAttribute Folder floder,
	    		@RequestParam(value="token",required=true) String token,
	    		@RequestParam(value="content",required=false) String content){
	        return floderService.getFolderList(token, floder);
	    }
	    
	    
}
