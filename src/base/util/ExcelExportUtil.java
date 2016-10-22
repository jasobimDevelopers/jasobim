package base.util;



import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang.StringUtils;




/**
 * 通用的Excel导出工具类
 * @author zj
 *
 */
@SuppressWarnings("rawtypes")
public class ExcelExportUtil {
	/**
	 * 产生一个以当前服务器时间的文件名称
	 * @param fileName 文件主名称
	 * @return 文件主名称+时间组成的文件名
	 */
	public static String getNewFileName(String fileName)
	{
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
	    StringBuilder sb = new StringBuilder();
	    sb.append(fileName);
	    sb.append(sf.format(System.currentTimeMillis()));
		sb.append(".xls");
	    return sb.toString();
	}
	/**
	 * 获得文件的路径
	 * @return 路径
	 */
	public static String getFilePath(String root)
	{
	   StringBuilder sb = new StringBuilder();
	   sb.append(root);
	   sb.append(File.separator + "download" + File.separator);
	   return sb.toString();
	}
	/**
	 * 下载Excel文档
	 * @param fileName excel文件名
	 * @param response
	 */
	public static void downLoadExcel(String fileName,HttpServletRequest request, HttpServletResponse response)
	{
		OutputStream out;
		String root=request.getSession().getServletContext().getRealPath("/");
		try {
			//设置输出的格式
     		response.reset();
     		response.setHeader("Content-Type","application/vnd.ms-excel; charset=UTF-8");
     		response.setHeader("Content-Disposition","attachment; fileName=\""+new String(fileName.getBytes("GBK"),"ISO8859-1")+"\"");
			out = response.getOutputStream();
			String filePath = getFilePath(root)+fileName;
			File file = new File(filePath);
			FileInputStream fin = new FileInputStream(file); 
	  		byte[] b = new byte[2048]; 
	  		while (fin.read(b) > 0) {
	  			out.write(b); 
	  		} 
	  		out.flush(); 
	  		out.close(); 
	  		fin.close(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 生成Excel
	 * @param fileName 生成的Excel名
	 * @param excelTitle 列表数据标题
	 * @param tableHeaderList 表头
	 * @param dataList 数据集合
	 * @param displayList 需要显示的列
	 * @param clasVo 数据对象(支持自定义类模型对象以及HashMap)
	 * @return 新的文件名
	 */
	public static String createExcel(HttpServletRequest request, String fileName,String excelTitle,List<String> tableHeaderList,List dataList,List<String> displayList,Class clasVo){
		String root=request.getSession().getServletContext().getRealPath("/");
		WritableWorkbook workbook = null;
		String newFileName = getNewFileName(fileName);
		String filePath = getFilePath(root)+newFileName;
		File fileDir = new File(getFilePath(root));
		if(!fileDir.exists())
		{
			//创建目录
			fileDir.mkdir();
		}
		File file = new File(filePath);
		try {
			if(!file.exists())
			{
				//创建新文件
				file.createNewFile();
			}
			workbook = Workbook.createWorkbook(file);// 创建可写入的Excel工作薄对象
			WritableSheet sheet = workbook.createSheet(fileName, 0);//创建单张工作薄对象
            //填充数据
			putContent(sheet,excelTitle,tableHeaderList,dataList,displayList,clasVo);
			//写数据
			workbook.write();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//关闭连接
			if(workbook != null)
			{
				try {
					workbook.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	   return newFileName;
	}

	/**
	 * 生成Excel
	 * @param packageName 指定生成文件的中间路径
	 * @param fileName 生成的Excel名
	 * @param excelTitle 列表数据标题
	 * @param tableHeaderList 表头
	 * @param dataList 数据集合
	 * @param displayList 需要显示的列
	 * @param clasVo 数据对象(支持自定义类模型对象以及HashMap)
	 * @return 新的文件名
	 */
	public static String createExcel(HttpServletRequest request,String packageName, String fileName,String excelTitle,List<String> tableHeaderList,List dataList,List<String> displayList,Class clasVo){
		String root=request.getSession().getServletContext().getRealPath("/");
		WritableWorkbook workbook = null;
		String newFileName = getNewFileName(fileName);
		String filePath = root+packageName+newFileName;
		File fileDir = new File(root+packageName);
		if(!fileDir.exists())
		{
			//创建目录
			fileDir.mkdir();
		}
		File file = new File(filePath);
		try {
			if(!file.exists())
			{
				//创建新文件
				file.createNewFile();
			}
			workbook = Workbook.createWorkbook(file);// 创建可写入的Excel工作薄对象
			WritableSheet sheet = workbook.createSheet(fileName, 0);//创建单张工作薄对象
            //填充数据
			putContent(sheet,excelTitle,tableHeaderList,dataList,displayList,clasVo);
			//写数据
			workbook.write();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//关闭连接
			if(workbook != null)
			{
				try {
					workbook.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	   return newFileName;
	}
	
	/**
	 * 生成多Sheet Excel
	 * @param fileName 生成的Excel名
	 * @param excelTitle 列表数据标题
	 * @param sheet1Name 标题1
	 * @param sheet2Name 标题2
	 * @param tableHeaderList 表头
	 * @param dataList 数据集合
	 * @param displayList 需要显示的列
	 * @param clasVo 数据对象(支持自定义类模型对象以及HashMap)
	 * @param tableHeaderList 表头
	 * @param dataList 数据集合
	 * @param displayList 需要显示的列
	 * @param clasVo 数据对象(支持自定义类模型对象以及HashMap)
	 * @return 新的文件名
	 */
	public static String createExcelMutableSheet(HttpServletRequest request, String fileName,String excelTitle,String sheet1Name,String sheet2Name,
			List<String> tableHeader1List,List data1List,List<String> display1List,Class clasVo1,
			List<String> tableHeader2List,List data2List,List<String> display2List,Class clasVo2){
		String root=request.getSession().getServletContext().getRealPath("/");
		WritableWorkbook workbook = null;
		String newFileName = getNewFileName(fileName);
		String filePath = getFilePath(root)+newFileName;
		File fileDir = new File(getFilePath(root));
		if(!fileDir.exists())
		{
			//创建目录
			fileDir.mkdir();
		}
		File file = new File(filePath);
		try {
			if(!file.exists())
			{
				//创建新文件
				file.createNewFile();
			}
			workbook = Workbook.createWorkbook(file);// 创建可写入的Excel工作薄对象
			WritableSheet sheet1 = workbook.createSheet(sheet1Name, 0);//创建单张工作薄对象
            //填充数据
			putContent(sheet1,excelTitle,tableHeader1List,data1List,display1List,clasVo1);
			
			WritableSheet sheet2 = workbook.createSheet(sheet2Name, 1);//创建单张工作薄对象
            //填充数据
			putContent(sheet2,excelTitle,tableHeader2List,data2List,display2List,clasVo2);
			//写数据
			workbook.write();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//关闭连接
			if(workbook != null)
			{
				try {
					workbook.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	   return newFileName;
	}
	/**
	 * 填充数据
	 * @param sheet 
	 * @param excelTitle excel名
	 * @param tableHeaderList 表头
	 * @param dataList 数据集
	 * @param displayList 对应的字段
	 * @param clasVo 类
	 */
	public static void putContent(WritableSheet sheet,
			String excelTitle,
			List<String> tableHeaderList,
			List dataList,List<String> displayList, Class clasVo){	
		// 定义标题格式
		//WritableCellFormat wcf_title = CellFormatSetUtil.getTitleFirstFormat();
		// 定义表头格式
		WritableCellFormat wcf_tablehead = CellFormatSetUtil.getTableHeadFirstFormat();
		// 定义正文格式
		WritableCellFormat wcf_center = CellFormatSetUtil.getTextFirstFormat();
		try {
			int rowId = 0;
			if(tableHeaderList != null)
			{
			
				//填充表头---第1行
				for(int i=0;i<tableHeaderList.size();i++)
				{
					 sheet.setColumnView(i,18); 
					 sheet.addCell(new Label(i, rowId, tableHeaderList.get(i),wcf_tablehead));
				}
				 rowId++;
			}
		
	        for (Object data : dataList) {
	            int columnId = 0;// 写入第几列 第一列为自动计算的行号 数据从第二列开始写
	            String fieldName;
	            if(clasVo.equals(HashMap.class)){			//对HashMap的特殊处理
	            	for (int i = 0; i < displayList.size(); i++) {
	            		fieldName = displayList.get(i);
	            		for(Method method : HashMap.class.getMethods()){
	            			if(StringUtils.containsIgnoreCase(String.valueOf(method), ".get(")){
	                			Object value = method.invoke(data, fieldName);;
	    	                    String cellValue = (value != null) ? value.toString(): "";
	    	                    try {
	    	                    	if (value instanceof Date) {//对时间格式做格式化输出
	    	                    		Date date = (Date) value;
	    	                    		cellValue = DateProcessUtil.getStrDate(date,DateProcessUtil.YYYYMMDDHHMMSS);
	    	                    	}
	    	                    	 if (isNumber(cellValue)){
	    	                    		if(isInteger(cellValue)){
	 	    	                    		sheet.addCell(new jxl.write.Number(columnId, rowId, Integer.parseInt(cellValue), wcf_center));
	    	                    		}
	    	                    		else{
	 	    	                    		sheet.addCell(new jxl.write.Number(columnId, rowId, Double.parseDouble(cellValue), wcf_center));
	    	                    		}
	    	                    	 }
	    	                    	 else{
	 	    	                        sheet.addCell(new Label(columnId, rowId, cellValue, wcf_center));
	    	                    	 }
	    	                    } catch (Exception e) {
	    	                        e.printStackTrace();
	    	                    } 
	    	                    columnId++;
	            			}
	            		}
	            	}
	            }
	            else{										//自定义类对象的处理
	            	for (int i = 0; i < displayList.size(); i++) {
	            		fieldName = displayList.get(i);
	            		for(Method method : clasVo.getMethods()){
		            		if(StringUtils.containsIgnoreCase(String.valueOf(method), ".get"+fieldName+"()")){
		            			Object value = method.invoke((Object)data);
			                    String cellValue = (value != null) ? value.toString(): "";
			                    try {
			                    	 if (value instanceof Date) {//对时间格式做格式化输出
			                    		     Date date = (Date) value;
			                    		     cellValue = DateProcessUtil.getStrDate(date,DateProcessUtil.YYYYMMDDHHMMSS);
			                    		}
	    	                    	 if (isNumber(cellValue)){
		    	                    		if(isInteger(cellValue)){
		 	    	                    		sheet.addCell(new jxl.write.Number(columnId, rowId, Integer.parseInt(cellValue), wcf_center));
		    	                    		}
		    	                    		else{
		 	    	                    		sheet.addCell(new jxl.write.Number(columnId, rowId, Double.parseDouble(cellValue), wcf_center));
		    	                    		}
		    	                    	 }
		    	                    	 else{
		 	    	                        sheet.addCell(new Label(columnId, rowId, cellValue, wcf_center));
		    	                    	 }
			                    } catch (Exception e) {
			                        e.printStackTrace();
			                    } 
			                    columnId++;
		            		}
	            			
	            		}
	            	}
	            }
	            rowId++;
	        }
	        //sheet.getSettings().setProtected(false);
	        
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
	}
	
	private static boolean isInteger(String value){
		try{
			Integer.parseInt(value);
			return true;
		}
		catch(Exception e){
			return false;
		}
	}
	
	private static boolean isNumber(String value){
		try{
			Double.parseDouble(value);
			return true;
		}
		catch(Exception e){
			return false;
		}
	}
	
}
