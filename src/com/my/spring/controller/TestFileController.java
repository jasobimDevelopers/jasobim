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

import com.my.spring.DAO.QuantityDao;
import com.my.spring.jpush.PushMessage;
import com.my.spring.model.Files;
import com.my.spring.model.Quantity;
import com.my.spring.service.FileService;
import com.my.spring.utils.DataWrapper;
@Controller
@RequestMapping(value="api/test")
public class TestFileController {
	@Autowired
	FileService fileService;
	
	@Autowired
	QuantityDao quantityDao;
	
	//fileService提供服务，不提供接口，本文件下的接口皆为测试，需删除
	//上传文件服务，需要输入文件的目标路径和文件内容，其中文件的目标路径不包含文件名，示例如下filePath
	//filePath具体的内容，可以参考templateSpringMVC中fileServiceImpl里面filePath的初始化
	//本服务返回的类型为字符串，内容为文件保存后的文件名
	//存src的时候最好这样存    src=upload/user1/file.jpg  存它在本项目中的相对路径
	//那么在使用的时候，可以通过该路径访问  http://localhost:8080/JasoBIM/upload/user1/file.jpg
	@RequestMapping(value="/testUploadFile", method = RequestMethod.POST)
    @ResponseBody
    public Files testUploadFile(
    		@RequestParam(value = "type", required = true) Integer type,
    		HttpServletRequest request,
    		@RequestParam(value = "file", required = false) MultipartFile file) {
		String filePath = "/fileupload/testfile";
        return fileService.uploadFile(filePath, file,type,request);
    }
	
	//删除文件的时候只要把它包含文件名的路径作为参数，即可删除，结果为true or false
	@RequestMapping(value="/testDeleteFile", method = RequestMethod.DELETE)
    @ResponseBody
    public boolean testDeleteFile(
    		@RequestParam(value = "fileName", required = true) String filePathAndName,
    		HttpServletRequest request) {
		//根据id去查Files,取出Files里面的url,传参
		filePathAndName= "/fileupload/testfile/7c8eabdd4c1787f40be437d1367431ce.jpg";
        return fileService.deleteFileByPath(filePathAndName,request);
    }
	
	@RequestMapping(value="/testExportFile", method = RequestMethod.GET)
    @ResponseBody
	public boolean testExportTable(
  
    		HttpServletRequest request) {
		//根据id去查Files,取出Files里面的url,传参
//		String filePath = request.getSession().getServletContext().getRealPath("/") + "/test.xls";
		String filePath = "E:/test.xls";
        return true;
    }
	
	@RequestMapping(value="/testGroupBy", method = RequestMethod.GET)
    @ResponseBody
	public DataWrapper<List<Quantity>> testGroupBy(
			@RequestParam(value = "pageSize", required = true) int pageSize,
			@RequestParam(value = "pageIndex", required = true) int pageIndex
			) {

        return quantityDao.testGroupBy(pageSize, pageIndex);
    }
	
	@RequestMapping(value="/testLog", method = RequestMethod.GET)
    @ResponseBody
	public DataWrapper<Void> testLog(
			) {
		PushMessage.testSch();
        return null;
    }

}
