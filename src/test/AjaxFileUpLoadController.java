package test;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/ajaxFileUpLoadController")
public class AjaxFileUpLoadController {
	
	@RequestMapping("toTest")
	public String toTest(){
		return "testUpload";
	}
	
	/**
	 * ajax文件上传的公用类，该方法可用于检查文件大小是否符合要求，
	 * @param file input元素的name
	 * @param request
	 * @return
	 */
	@RequestMapping("upload")
	@ResponseBody
	public String uploadFile(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request){
		 String path = request.getSession().getServletContext().getRealPath("upload");
		 String fileName=file.getOriginalFilename();
		 File targetFile=new File(path+"/"+fileName);
		 String info="";
		 try {
			file.transferTo(targetFile);
			long fileLength = targetFile.length();
			System.out.println("文件大小："+fileLength);
			float result=fileLength/1024;			//转换成KB
			info="{\"length\":\""+result+"\",\"state\":\"y\"}";
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return info;
	}
}
