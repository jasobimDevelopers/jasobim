package base.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件夹处理类
 * @author zj
 *
 */
public class FolderPath {
	public static String rootFolder = "";
	/**
	 * 二维码文件夹/upload/Qrcode
	 */
	public static String Qrcode = "/upload/Qrcode/";
	/**
	 * 获取上传后的路径
	 * 
	 * @param folderName
	 * @param fileName
	 * @return
	 */
	public static String getFileUploadPath(String folderName, String fileName) {
		return rootFolder + folderName + fileName;
	}

	/**
	 * 获取物理路径
	 * 
	 * @param request
	 * @param path
	 *            相对路径
	 * @return
	 */
	public static String getPhysicalPath(HttpServletRequest request, String path) {
	 String folder=	 request.getSession().getServletContext().getRealPath(path);
	 
	 File f=new File(folder);
			if(!f.exists())
			{
				f.mkdirs();
			}
		return folder;
	}
	/**
	 * 上传文件
	 * @param HttpServletRequest request 
	 * @param MultipartFile file 
	 * @param folder 文件夹路径
	 * @param fileExtName 文件扩展名 .png .jpg
	 * @return
	 * @throws IOException
	 */
	public static String uploadFile(HttpServletRequest request,
			MultipartFile file, String folder, String fileExtName)
			throws IOException {
		
		
		String fn = new Date().getTime()+ file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		
		if(file != null && !file.isEmpty())
		{
			
			String path = request.getSession().getServletContext().getRealPath(folder);
			
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path,fn));
		} 
		
		return getFileUploadPath(folder, fn);
	}
	
	/**
	 * 上传文件
	 * @param HttpServletRequest request 
	 * @param MultipartFile file 
	 * @param folder 文件夹路径
	 * @param fileExtName 文件扩展名 .png .jpg
	 * @return 上传图片的绝对路径
	 * @throws IOException
	 */
	public static String uploadFileGetPhysicalPath(HttpServletRequest request,
			MultipartFile file, String folder, String fileExtName)
			throws IOException {
		
		String physicalPath="";
		String fn = new Date().getTime()+ file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		
		if(file != null && !file.isEmpty())
		{
			
			String path = request.getSession().getServletContext().getRealPath(folder);
			File f=new File(path,fn);
			FileUtils.copyInputStreamToFile(file.getInputStream(),f);
			physicalPath=f.getAbsolutePath().toString();
		} 
		
		return physicalPath;
	}
	/**
	 * 上传文件
	 * @param HttpServletRequest request 
	 * @param MultipartFile file 
	 * @param folder 文件夹路径
	 * @return
	 * @throws IOException
	 */
	public static String uploadFile(HttpServletRequest request,
			MultipartFile file, String folder)
			throws IOException {
		
		
		String fn = new Date().getTime()+ file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		
		if(file != null && !file.isEmpty())
		{
			
			String path = request.getSession().getServletContext().getRealPath(folder);
			
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path,fn));
		} 
		
		return getFileUploadPath(folder, fn);
	}
	/**
	 * 创建日期文件夹
	 * @param request
	 * @return
	 */
	public static String CreateFilePath(HttpServletRequest request,String folderPath)
	{
    	String physicalFolder= FolderPath.getPhysicalPath(request,folderPath);

		String dateFolder=new SimpleDateFormat("yyyy_MM_dd").format(new Date());
		File file = new File(physicalFolder,dateFolder);
		if(!file.exists()&&!file.isDirectory())
		{
			file.mkdirs();
		}
		return file.getAbsolutePath();
	}
	
	/**
	 * 创建UUID文件夹
	 * @param request
	 * @return
	 */
	public static String CreateFilePathUUID(HttpServletRequest request,String folderPath)
	{
    	String physicalFolder= FolderPath.getPhysicalPath(request,folderPath); 
		String uuid=UUID.randomUUID()+"";
		File file = new File(physicalFolder,uuid);
		if(!file.exists()&&!file.isDirectory())
		{
			file.mkdirs();
		}
		return file.getAbsolutePath();
	}
	
	/**
	 * 创建文件夹下的子文件夹
	 * @param folderPath
	 * @param subFolder
	 * @return
	 */
	public static String CreateFilePathUUID(String folderPath,String subFolder)
	{
		File file = new File(folderPath,subFolder);
		if(!file.exists()&&!file.isDirectory())
		{
			file.mkdirs();
		}
		return file.getAbsolutePath();
	}
}
