package com.my.spring.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.my.spring.model.ConstructionLogPojo;
import com.my.spring.model.ConstructionTaskNewPojo;
import com.my.spring.model.ProcessLogPojo;

/**
* @author 徐雨祥
* @version 创建时间：2018年9月6日 上午8:10:51
* 类说明
*/
public class ConstructionLogExportUtil {
	public static void main(String[] arg){
        try {
        	 List<ProcessLogPojo> logList = new ArrayList<ProcessLogPojo>();
        	 List<ConstructionTaskNewPojo> taskList = new ArrayList<ConstructionTaskNewPojo>();
        	 for(int i=0;i<3;i++){
        		 ProcessLogPojo processLogPojo = new ProcessLogPojo();
        		 processLogPojo.setApproveDate("2018-08-01 15:33:25");
        		 processLogPojo.setApproveUser("张三");
        		 processLogPojo.setItemName("班组长");
        		 processLogPojo.setItemState(0);
        		 processLogPojo.setNote("审批意见");
        		 logList.add(processLogPojo);
        		 ConstructionTaskNewPojo constructionTaskNewPojo = new ConstructionTaskNewPojo();
        		 constructionTaskNewPojo.setConstructionTaskDate("2018-08-30");
        		 constructionTaskNewPojo.setId((long)54);
        		 constructionTaskNewPojo.setCreateDate("2018-08-25 15:30:30");
        		 constructionTaskNewPojo.setCreateUser("xyx");
        		 constructionTaskNewPojo.setConstructContent("小食堂主橱，办公室保洁");
        		 constructionTaskNewPojo.setConstructPart("8井楼车库");
        		 constructionTaskNewPojo.setName("施工任务单测试");
        		 constructionTaskNewPojo.setConstructType("电焊工");
        		 constructionTaskNewPojo.setTenders("标段一");
        		 constructionTaskNewPojo.setTeamUserIds("张其华,朱小根");
        		 constructionTaskNewPojo.setDayWorkHours(10);
        		 constructionTaskNewPojo.setNightWorkHours(3);
        		 constructionTaskNewPojo.setDayNums(1.5);
        		 constructionTaskNewPojo.setSalary(320.5);
        		 taskList.add(constructionTaskNewPojo);
        	 }
        	 //FileOutputStream fout = new FileOutputStream("C://Users/Han/Desktop/taskLog.xls");
        	 FileOutputStream fout1 = new FileOutputStream("D://jasobim/tomcat_8080/webapps/ROOT/files/constructionLog");
             //getValue(logList,taskList,"歌林小镇",fout);
             fout1.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	public static void getValue(ConstructionLogPojo log,String projectName,FileOutputStream fout){
		 
		try{
	            //1.创建工作簿
	            HSSFWorkbook workbook = new HSSFWorkbook();
	            //1.1创建合并单元格对象
	            CellRangeAddress callRangeAddress1 = new CellRangeAddress(0,0,0,8);//起始行,结束行,起始列,结束列
	            CellRangeAddress callRangeAddress2 = new CellRangeAddress(1,1,0,8);//起始行,结束行,起始列,结束列
	            //标题
	            CellRangeAddress callRangeAddress31 = new CellRangeAddress(2,2,0,3);//起始行,结束行,起始列,结束列
	            CellRangeAddress callRangeAddress32 = new CellRangeAddress(2,2,4,8);//起始行,结束行,起始列,结束列
	            CellRangeAddress callRangeAddress41 = new CellRangeAddress(3,3,0,2);//起始行,结束行,起始列,结束列
	            CellRangeAddress callRangeAddress42 = new CellRangeAddress(3,3,3,8);//起始行,结束行,起始列,结束列
	            CellRangeAddress callRangeAddress51 = new CellRangeAddress(4,4,0,2);//起始行,结束行,起始列,结束列
	            CellRangeAddress callRangeAddress52 = new CellRangeAddress(4,4,3,8);//起始行,结束行,起始列,结束列
	            CellRangeAddress callRangeAddress61 = new CellRangeAddress(5,5,0,2);//起始行,结束行,起始列,结束列
	            CellRangeAddress callRangeAddress62 = new CellRangeAddress(5,5,3,8);//起始行,结束行,起始列,结束列
	            CellRangeAddress callRangeAddress72 = new CellRangeAddress(6,6,1,2);//起始行,结束行,起始列,结束列
	            
	            //公司名称
	            HSSFCellStyle erStyle = createCellStyle(workbook,(short)18,true,true);
	            erStyle.setWrapText(true);
	            HSSFCellStyle headErStyle = createHeadCellStyle(workbook,(short)18,true,true);
	            HSSFCellStyle noBorder=createCellStyleNoBorder(workbook,(short)11,true,true);
	            //2.创建工作表
	            HSSFSheet sheet = workbook.createSheet("施工日志表");
	            
	            //2.1加载合并单元格对象
	            sheet.addMergedRegion(callRangeAddress1);
	            sheet.addMergedRegion(callRangeAddress2);
	            sheet.addMergedRegion(callRangeAddress31);
	            sheet.addMergedRegion(callRangeAddress32);
	            sheet.addMergedRegion(callRangeAddress41);
	            sheet.addMergedRegion(callRangeAddress42);
	            sheet.addMergedRegion(callRangeAddress51);
	            sheet.addMergedRegion(callRangeAddress52);
	            sheet.addMergedRegion(callRangeAddress61);
	            sheet.addMergedRegion(callRangeAddress62);
	            sheet.addMergedRegion(callRangeAddress72);
	           /* for(int i=0;i<taskList.size();i++){
	            	sheet.addMergedRegion(new CellRangeAddress(7+i,7+i,1,2));
	            }
	            sheet.addMergedRegion(new CellRangeAddress(7+taskList.size(),7+taskList.size(),0,7));
	            sheet.addMergedRegion(new CellRangeAddress(9+taskList.size(),9+taskList.size(),0,8));
	            sheet.addMergedRegion(new CellRangeAddress(10+taskList.size(),10+taskList.size(),3,4));
	            sheet.addMergedRegion(new CellRangeAddress(10+taskList.size(),10+taskList.size(),5,6));
	            sheet.addMergedRegion(new CellRangeAddress(10+taskList.size(),10+taskList.size(),7,8));
	            sheet.addMergedRegion(new CellRangeAddress(11+taskList.size()+logList.size(),11+taskList.size()+logList.size(),0,8));
	            */
	           
	            //设置默认列宽
	            //sheet.setDefaultColumnWidth(10);
	           // sheet.autoSizeColumn(i); 
	            sheet.setColumnWidth(0, 256*6);
	            sheet.setColumnWidth(1, 256*22);
	            sheet.setColumnWidth(5, 256*16);
	            //3.创建行
	            //////////////////////////////////////////////////////////////
	            //3.1创建头标题行;并且设置头标题
	            HSSFRow row = sheet.createRow(0);
	            row.setHeightInPoints(35);
	            HSSFCell cell = row.createCell(0);
	    
	            //加载单元格样式
	            cell.setCellStyle(headErStyle);
	            
	            cell.setCellValue("上海嘉实（集团）有限公司");
	            ///////第二行
	            HSSFRow row1 = sheet.createRow(1);
	            row1.setHeightInPoints(25);
	            HSSFCell cell1 = row1.createCell(0);
	            //加载单元格样式
	            cell1.setCellStyle(noBorder);
	            
	            cell1.setCellValue(projectName+"项目部零星派工任务单");
	           
	           
       
                HSSFRow row10 = sheet.createRow(9);
                row10.setHeightInPoints(20);
	            HSSFCell cell101 = row10.createCell(0);
	            cell101.setCellValue("审批流程：");
	            String[] approveTitle ={"序号","审核时间","审核人","审核岗位","是否同意","审核意见"};
	            String[] itemStateStr={"同意","不同意"};
	           
	            //5.输出
	            workbook.write(fout);
//	            workbook.close();
	            //out.close();
	        }catch(Exception e)
	        {
	            e.printStackTrace();
	        }
	}
	
	/**
	   * 
	   * @param workbook
	   * @param fontsize
	   * @return 单元格样式
	   */
	  private static HSSFCellStyle createHeadCellStyle(HSSFWorkbook workbook, short fontsize,boolean flag,boolean flag1) {
	      // TODO Auto-generated method stub
	      HSSFCellStyle style = workbook.createCellStyle();
	      //是否水平居中
	      if(flag1){
	      	style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
	      }
	      style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
	      //创建字体
	      HSSFFont font = workbook.createFont();
	      //是否加粗字体
	      if(flag){
	          font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	      }
	      font.setFontHeightInPoints(fontsize);
	      //加载字体
	      style.setFont(font);
	      return style;
	  }
  /**
   * 
   * @param workbook
   * @param fontsize
   * @return 单元格样式
   */
  private static HSSFCellStyle createCellStyle(HSSFWorkbook workbook, short fontsize,boolean flag,boolean flag1) {
      // TODO Auto-generated method stub
      HSSFCellStyle style = workbook.createCellStyle();
      //是否水平居中
      if(flag1){
      	style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
      }
      style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
      style.setBorderRight(HSSFCellStyle.BORDER_THIN);
      style.setBorderTop(HSSFCellStyle.BORDER_THIN);
      style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
      style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
      //创建字体
      HSSFFont font = workbook.createFont();
      //是否加粗字体
      if(flag){
          font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
      }
      font.setFontHeightInPoints(fontsize);
      //加载字体
      style.setFont(font);
      return style;
  }
  private static HSSFCellStyle createCellStyleNoBorder(HSSFWorkbook workbook, short fontsize,boolean flag,boolean flag1) {
      // TODO Auto-generated method stub
      HSSFCellStyle style = workbook.createCellStyle();
      //是否水平居中
      if(flag1){
      	style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
      }
      style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
      //创建字体
      HSSFFont font = workbook.createFont();
      //是否加粗字体
      if(flag){
          font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
      }
      font.setFontHeightInPoints(fontsize);
      //加载字体
      style.setFont(font);
      return style;
  }
  /**
   * 
   * @param workbook
   * @param fontsize
   * @return 单元格样式
   */
  private static HSSFCellStyle createCellStyleLeft(HSSFWorkbook workbook, short fontsize,boolean flag,boolean flag1) {
      // TODO Auto-generated method stub
      HSSFCellStyle style = workbook.createCellStyle();
      //是否水平居中
      if(flag1){
      	style.setAlignment(HSSFCellStyle.ALIGN_LEFT);//居左
      }
      style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
      style.setBorderRight(HSSFCellStyle.BORDER_THIN);
      style.setBorderTop(HSSFCellStyle.BORDER_THIN);
      style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
      style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
      //创建字体
      HSSFFont font = workbook.createFont();
      //是否加粗字体
      if(flag){
          font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
      }
      font.setFontHeightInPoints(fontsize);
      //加载字体
      style.setFont(font);
      return style;
  }
  private static HSSFCellStyle createCellStyleLeftNoBorder(HSSFWorkbook workbook, short fontsize,boolean flag,boolean flag1) {
      // TODO Auto-generated method stub
      HSSFCellStyle style = workbook.createCellStyle();
      //是否水平居中
      if(flag1){
      	style.setAlignment(HSSFCellStyle.ALIGN_LEFT);//居左
      }
      style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
      //创建字体
      HSSFFont font = workbook.createFont();
      //是否加粗字体
      if(flag){
          font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
      }
      font.setFontHeightInPoints(fontsize);
      //加载字体
      style.setFont(font);
      return style;
  }
  private static HSSFCellStyle createCellStyleRight(HSSFWorkbook workbook, short fontsize,boolean flag,boolean flag1) {
      // TODO Auto-generated method stub
      HSSFCellStyle style = workbook.createCellStyle();
      //是否水平居中
      if(flag1){
      	style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);//居左
      }
      style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
      //创建字体
      HSSFFont font = workbook.createFont();
      //是否加粗字体
      if(flag){
          font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
      }
      font.setFontHeightInPoints(fontsize);
      //加载字体
      style.setFont(font);
      return style;
  }
}
