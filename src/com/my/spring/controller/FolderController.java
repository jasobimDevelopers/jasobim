package com.my.spring.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.my.spring.model.Folder;
import com.my.spring.model.FolderPojo;
import com.my.spring.service.FolderService;
import com.my.spring.utils.DataWrapper;

@Controller
@RequestMapping(value="api/folder")
public class FolderController {
	 @Autowired
	 FolderService folderService;
	 
	 
	 /*
	  * 新建文件夹
	  * 
	  * */
	 @RequestMapping(value="/addFolder",method=RequestMethod.POST)
     @ResponseBody
     public DataWrapper<Void> addFloder(
    		@ModelAttribute Folder floder,
    		@RequestParam(value="token",required=true) String token){
        return folderService.addFloder(token, floder);
     }
	 
	 /*
	  * 
	  * 上传文件
	  * */
	 @RequestMapping(value="/uploadFiles",method=RequestMethod.POST)
     @ResponseBody
     public DataWrapper<Void> addFiles(
    		@ModelAttribute Folder floder,
    		@RequestParam(value = "fileList", required = true) MultipartFile[] fileList,
            HttpServletRequest request,
    		@RequestParam(value="token",required=true) String token){
        return folderService.uploadFloder(token, floder,fileList,request);
     }
	 
	 /*
	  * 
	  * 上传文件夹
	  * */
	 @RequestMapping(value="/uploadFolders",method=RequestMethod.POST)
     @ResponseBody
     public DataWrapper<Void> uploadFloders(
    		@RequestParam(value = "file", required = true) MultipartFile file,
            HttpServletRequest request,
            @RequestParam(value="filePath",required=true) String filePath,
            @RequestParam(value="projectId",required=true) Long projectId,
            @RequestParam(value="pid",required=true) Long pid,
    		@RequestParam(value="token",required=true) String token){
        return folderService.uploadFloderFiles(token, filePath,file,request,pid,projectId);
     }
	 
	 
	 /*
	  * 获取文件的树状结构
	  * */
     @RequestMapping(value="/getFolderList",method=RequestMethod.GET)
     @ResponseBody
     public DataWrapper<Object> getFloderList(
    		@RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute Folder floder,
    		@RequestParam(value="token",required=true) String token,
    		@RequestParam(value="content",required=false) String content){
        return folderService.getFolderList(token, floder);
     }
	 /*
	  * 获取parrentlist
	  * */
     @RequestMapping(value="/getFolderIndexList",method=RequestMethod.GET)
     @ResponseBody
     public DataWrapper<List<FolderPojo>> getFolderIndexList(
    		@RequestParam(value="token",required=true) String token,
    		@RequestParam(value="projectId",required=false) Long projectId,
    		@RequestParam(value="pid",required=false) Long pid){
        return folderService.getFolderIndexList(token, projectId,pid);
     }
     
     /*
      * 文件或者文件夹重命名
      * 
      * **/
     @RequestMapping(value="/updateFolder",method=RequestMethod.POST)
     @ResponseBody
     public DataWrapper<Void> updateFloder(
    		 @RequestParam(value="id",required=true) Long id,
    		 @RequestParam(value="name",required=true) String name,
    		@RequestParam(value="token",required=true) String token){
        return folderService.updateFloder(token, name,id);
     }
     
     /*
      * 
      * 移动文件到
      * **/
     @RequestMapping(value="/takeFolderTo",method=RequestMethod.POST)
     @ResponseBody
     public DataWrapper<Void> tokenFolderTo(
    		 @RequestParam(value="id",required=true) Long id,
    		 @RequestParam(value="pid",required=true) Long pid,
    		 @RequestParam(value="token",required=true) String token
    		 ){
    	 return folderService.takeFolderTo(token,id,pid);
     }
     /*
      * 
      * 删除文件或者文件夹
      * **/
     @RequestMapping(value="/deleteFolder",method=RequestMethod.GET)
     @ResponseBody
     public DataWrapper<Void> deleteFloder(
    		 @RequestParam(value="ids",required=true) String ids,
    		 @RequestParam(value="token",required=true) String token){
        return folderService.deleteFloder(token, ids);
     }   
     
     /*
      * 
      * 搜索文件名接口
      * **/
     @RequestMapping(value="/findFileLists",method=RequestMethod.GET)
     @ResponseBody
     public DataWrapper<List<FolderPojo>> findFileLists(
    		 @RequestParam(value="name",required=true) String name,
    		 @RequestParam(value="projectId",required=true) Long projectId,
    		 @RequestParam(value="token",required=true) String token){
        return folderService.findFileLists(token, name,projectId);
     }   
     
     /*
      * 文件夹或者文件下载
      * 
      * */
     @RequestMapping(value ="/batchDownload", method = RequestMethod.GET)
     @ResponseBody
     public DataWrapper<Void> BatchDownload(
         @RequestParam(value = "ids", required = true) String ids,
         @RequestParam(value = "token", required = true) String token,
         @RequestParam(value = "projectId", required = true) Long projectId,
         @RequestParam(value = "pid", required = true) Long pid,
         HttpServletRequest request, HttpServletResponse response) throws Exception{
    	 return folderService.batchDownload(token,ids,pid,projectId,request,response);
     }
    
    
     
}
