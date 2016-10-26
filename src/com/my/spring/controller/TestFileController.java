package com.my.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.my.spring.service.FileService;
@Controller
@RequestMapping(value="api/test")
public class TestFileController {
	@Autowired
	FileService fileService;
	
	//fileService提供服务，不提供接口，本文件下的接口皆为测试，需删除
	//上传文件服务，需要输入文件的目标路径和文件内容，其中文件的目标路径不包含文件名，示例如下filePath
	//filePath具体的内容，可以参考templateSpringMVC中fileServiceImpl里面filePath的初始化
	//本服务返回的类型为字符串，内容为文件保存后的文件名
	//存src的时候最好这样存    src=upload/user1/file.jpg  存它在本项目中的相对路径
	//那么在使用的时候，可以通过该路径访问  http://localhost:8080/JasoBIM/upload/user1/file.jpg
	@RequestMapping(value="/testUploadFile", method = RequestMethod.POST)
    @ResponseBody
    public String testUploadFile(
    		@RequestParam(value = "file", required = true) MultipartFile file) {
		String filePath = "/Users/dapan/Desktop/testFileService/photo";
        return fileService.uploadFile(filePath, file);
    }
	
	//删除文件的时候只要把它包含文件名的路径作为参数，即可删除，结果为true or false
	@RequestMapping(value="/testDeleteFile", method = RequestMethod.DELETE)
    @ResponseBody
    public boolean testDeleteFile(
    		@RequestParam(value = "fileName", required = true) String fileName) {
		String filePath = "/Users/dapan/Desktop/testFileService/photo";
        return fileService.deleteFile(filePath + "/" + fileName);
    }

}
