package com.my.spring.utils;

import java.io.FileOutputStream;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import com.my.spring.model.UserLogPojos;

public class UserLogToExcel {
	public void getValue(List<UserLogPojos> logList,FileOutputStream fout,String projectName,Integer month){
		  try{
	            //1.创建工作簿
	            HSSFWorkbook workbook = new HSSFWorkbook();
	            //1.1创建合并单元格对象
	            CellRangeAddress callRangeAddress1 = new CellRangeAddress(0,0,0,5);//起始行,结束行,起始列,结束列
	            //1.1创建合并单元格对象
	            CellRangeAddress callRangeAddress2 = new CellRangeAddress(1,1,0,5);//起始行,结束行,起始列,结束列
	          //1.1创建合并单元格对象
	            CellRangeAddress callRangeAddress3 = new CellRangeAddress(2,2,0,5);//起始行,结束行,起始列,结束列
	            //标题
	            CellRangeAddress callRangeAddress31 = new CellRangeAddress(3,3,0,0);//起始行,结束行,起始列,结束列
	            CellRangeAddress callRangeAddress32 = new CellRangeAddress(3,3,1,1);//起始行,结束行,起始列,结束列
	            CellRangeAddress callRangeAddress33 = new CellRangeAddress(3,3,2,2);//起始行,结束行,起始列,结束列
	            CellRangeAddress callRangeAddress34 = new CellRangeAddress(3,3,3,3);//起始行,结束行,起始列,结束列
	            CellRangeAddress callRangeAddress35 = new CellRangeAddress(3,3,4,4);//起始行,结束行,起始列,结束列
	            CellRangeAddress callRangeAddress36 = new CellRangeAddress(3,3,5,5);//起始行,结束行,起始列,结束列
	            //公司名称
	            HSSFCellStyle erStyle = createCellStyle(workbook,(short)18,true,true);
	            erStyle.setWrapText(true);
	            //项目名称
	            HSSFCellStyle erStyle1 = createCellStyle(workbook,(short)16,true,true);
	            erStyle.setWrapText(true);
	            //表单名称
	            HSSFCellStyle erStyle2 = createCellStyle(workbook,(short)15,true,true);
	            erStyle.setWrapText(true);
	            //标题样式
	            HSSFCellStyle colStyle = createCellStyle(workbook,(short)12,true,true);
	            //内容样式
	            HSSFCellStyle cellStyle = createCellStyle(workbook,(short)10,false,true);
	            //2.创建工作表
	            HSSFSheet sheet = workbook.createSheet("项目员工月工资单");
	            //2.1加载合并单元格对象
	            sheet.addMergedRegion(callRangeAddress1);
	            sheet.addMergedRegion(callRangeAddress2);
	            sheet.addMergedRegion(callRangeAddress3);
	            sheet.addMergedRegion(callRangeAddress31);
	            sheet.addMergedRegion(callRangeAddress32);
	            sheet.addMergedRegion(callRangeAddress33);
	            sheet.addMergedRegion(callRangeAddress34);
	            sheet.addMergedRegion(callRangeAddress35);
	            sheet.addMergedRegion(callRangeAddress36);
	            //设置默认列宽
	            sheet.setDefaultColumnWidth(15);
	            //3.创建行
	            //////////////////////////////////////////////////////////////
	            //3.1创建头标题行;并且设置头标题
	            HSSFRow row = sheet.createRow(0);
	            row.setHeightInPoints(35);
	            HSSFCell cell = row.createCell(0);
	    
	            //加载单元格样式
	            cell.setCellStyle(erStyle);
	            
	            cell.setCellValue("上海嘉实（集团）有限公司");
	            //////////////////////////////////////////////////////////////////
	            ///创建公司名称行
	            HSSFRow row1 = sheet.createRow(1);
	            row1.setHeightInPoints(25);
	            HSSFCell cell1 = row1.createCell(0);
	    
	            //加载单元格样式
	            cell1.setCellStyle(erStyle1);
	            
	            cell1.setCellValue(projectName+"项目部 ");
	            ///////////////////////////////////////////////////////////////
	            HSSFRow row2 = sheet.createRow(2);
	            row2.setHeightInPoints(25);
	            HSSFCell cell2 = row2.createCell(0);
	            cell2.setCellStyle(erStyle2);
	            cell2.setCellValue("产业工人工资统计表  "+month+" 月份");
	            ///////////////////////////////////////////////////////////////
	            //3.2创建列标题;并且设置列标题
	            HSSFRow row3 = sheet.createRow(3);
	            String[] titles = {"姓名","工种","工时","工日","日工资","月累计工资"};//""为占位字符串
	            row3.setHeightInPoints(20);
	            for(int i=0;i<titles.length;i++)
	            {
	                HSSFCell cell3 = row3.createCell(i);
	                //加载单元格样式
	                cell3.setCellStyle(colStyle);
	                cell3.setCellValue(titles[i]);
	            }
	          
	            
	            //4.操作单元格;将用户列表写入excel
	            if(logList != null)
	            {
	                for(int j=0;j<logList.size();j++)
	                {
	                	 //创建数据行,前面有两行,头标题行和列标题行
	                    HSSFRow row4 = sheet.createRow(j+4);
	                    row4.setHeightInPoints(20);
	                    HSSFCell cell0 = row4.createCell(0);
	                    cell0.setCellStyle(cellStyle);
	                    //创建数据行,前面有两行,头标题行和列标题行
	                    cell0.setCellValue(logList.get(j).getUserName());
	                    
	                    HSSFCell cell1s = row4.createCell(1);
	                    cell1s.setCellStyle(cellStyle);
	                   // cell1s.setCellValue(logList.get(j).getWorkName());
	                    
	                    HSSFCell cell2s = row4.createCell(2);
	                    cell2s.setCellStyle(cellStyle);
	                   // cell2s.setCellValue(logList.get(j).getHourNum());
	                    
	                    HSSFCell cell3s = row4.createCell(3);
	                    cell3s.setCellStyle(cellStyle);
	                   // cell3s.setCellValue(logList.get(j).getDayNum());
	                    
	                    HSSFCell cell4s = row4.createCell(4);
	                    cell4s.setCellStyle(cellStyle);
	                   // cell4s.setCellValue(userList.get(j).getDaySalary());
	                    
	                    HSSFCell cell5s = row4.createCell(5);
	                    cell5s.setCellStyle(cellStyle);
	                    //cell5s.setCellValue(logList.get(j).getSalaryNum());
	                }
	            }
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
	private static HSSFCellStyle createCellStyle(HSSFWorkbook workbook, short fontsize,boolean flag,boolean flag1) {
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
}
