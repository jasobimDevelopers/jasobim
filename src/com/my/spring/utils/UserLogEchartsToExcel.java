package com.my.spring.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.my.spring.model.UserLogCount;
import com.my.spring.model.UserLogMonth;
import com.my.spring.model.UserLogPart;
import com.my.spring.model.UserLogPojos;
import com.my.spring.parameters.ProjectDatas;

public class UserLogEchartsToExcel {
	public static void main(String[] arg){
		List<UserLogCount> logList = new ArrayList<UserLogCount>();
		UserLogCount user1 = new UserLogCount();
		user1.setReal_name("王峰");
		user1.setName("无锡王珂啊啊阿达阿达万科");
		user1.setNum(121);
		logList.add(user1);
		UserLogCount user2 = new UserLogCount();
		user2.setReal_name("李磊");
		user2.setName("万科");
		user2.setNum(122);
		logList.add(user2);
       
        try {
        	 FileOutputStream fout = new FileOutputStream("C://Users/Han/Desktop/userPersonalLog.xls");
             getValue(null,null,fout,"小明",2018,"2018-01-01","2018-02-03");
             fout.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	public static void getValue(List<UserLogPart> partLogList,List<UserLogMonth> monthLogList,FileOutputStream fout,String name,Integer year,String start,String end){
		  try{
	            //1.创建工作簿
	            HSSFWorkbook workbook = new HSSFWorkbook();
	            //1.1创建合并单元格对象
	            CellRangeAddress callRangeAddress1 = new CellRangeAddress(0,0,0,5);//起始行,结束行,起始列,结束列
	            //1.1创建合并单元格对象
	            CellRangeAddress callRangeAddress21 = new CellRangeAddress(1,1,0,2);//起始行,结束行,起始列,结束列
	          //1.1创建合并单元格对象
	            CellRangeAddress callRangeAddress22 = new CellRangeAddress(1,1,4,5);//起始行,结束行,起始列,结束列
	            //1.1创建合并单元格对象
	            CellRangeAddress callRangeAddress31 = new CellRangeAddress(2,2,0,2);//起始行,结束行,起始列,结束列
	          //1.1创建合并单元格对象
	            CellRangeAddress callRangeAddress32 = new CellRangeAddress(2,2,4,5);//起始行,结束行,起始列,结束列
	            //标题
	            CellRangeAddress callRangeAddress41 = new CellRangeAddress(3,3,0,0);//起始行,结束行,起始列,结束列
	            CellRangeAddress callRangeAddress42 = new CellRangeAddress(3,3,1,1);//起始行,结束行,起始列,结束列
	            CellRangeAddress callRangeAddress43 = new CellRangeAddress(3,3,2,2);//起始行,结束行,起始列,结束列
	            CellRangeAddress callRangeAddress45 = new CellRangeAddress(3,3,4,4);//起始行,结束行,起始列,结束列
	            CellRangeAddress callRangeAddress46 = new CellRangeAddress(3,3,5,5);//起始行,结束行,起始列,结束列
	            //公司名称
	            HSSFCellStyle erStyle = createCellStyle(workbook,(short)18,true,true);
	            erStyle.setWrapText(true);
	            //项目名称
	            HSSFCellStyle erStyle1 = createCellStyle(workbook,(short)14,true,true);
	            erStyle.setWrapText(true);
	            //表单名称
	            HSSFCellStyle erStyle2 = createCellStyle(workbook,(short)10,true,true);
	            erStyle.setWrapText(true);
	            //标题样式
	            HSSFCellStyle colStyle = createCellStyle(workbook,(short)12,true,true);
	            //内容样式
	            HSSFCellStyle cellStyle = createCellStyle(workbook,(short)10,false,true);
	            //2.创建工作表
	            HSSFSheet sheet = workbook.createSheet("用户记录分析表");
	            //2.1加载合并单元格对象
	            sheet.addMergedRegion(callRangeAddress1);
	            sheet.addMergedRegion(callRangeAddress21);
	            sheet.addMergedRegion(callRangeAddress22);
	            sheet.addMergedRegion(callRangeAddress31);
	            sheet.addMergedRegion(callRangeAddress32);
	            sheet.addMergedRegion(callRangeAddress41);
	            sheet.addMergedRegion(callRangeAddress42);
	            sheet.addMergedRegion(callRangeAddress43);
	            sheet.addMergedRegion(callRangeAddress45);
	            sheet.addMergedRegion(callRangeAddress46);
	            //设置默认列宽
	            sheet.setDefaultColumnWidth(18);
	            //3.创建行
	            //////////////////////////////////////////////////////////////
	            //3.1创建头标题行;并且设置头标题
	            HSSFRow row = sheet.createRow(0);
	            row.setHeightInPoints(35);
	            HSSFCell cell = row.createCell(0);
	    
	            //加载单元格样式
	            cell.setCellStyle(erStyle);
	            
	            cell.setCellValue(name);
	            //////////////////////////////////////////////////////////////////
	            ///创建公司名称行
	            HSSFRow row1 = sheet.createRow(1);
	            row1.setHeightInPoints(25);
	            /////
	            HSSFCell cell1 = row1.createCell(0);
	            //加载单元格样式
	            cell1.setCellStyle(erStyle1);
	            cell1.setCellValue("记录次数");
	            ////////
	            HSSFCell cell12 = row1.createCell(4);
	            //加载单元格样式
	            cell12.setCellStyle(erStyle1);
	            cell12.setCellValue("各功能区域占比");
	            ///////////////////////////////////////////////////////////////
	            HSSFRow row2 = sheet.createRow(2);
	            row2.setHeightInPoints(25);
	            HSSFCell cell2 = row2.createCell(0);
	            cell2.setCellStyle(erStyle2);
	            cell2.setCellValue("时间：  "+year+"年 按月/季度");
	            HSSFCell cell21 = row2.createCell(4);
	            cell21.setCellStyle(erStyle2);
	            cell21.setCellValue("时间：  "+start+" 至  "+end);
	            
	            ///////////////////////////////////////////////////////////////
	            //3.2创建列标题;并且设置列标题
	            HSSFRow row3 = sheet.createRow(3);
	            String[] titles = {"月份","使用次数","真实数据"};//""为占位字符串
	            String[] titles2 = {"功能区域","占比"};
	            row3.setHeightInPoints(20);
	            for(int i=0;i<titles.length;i++)
	            {
	                HSSFCell cell3 = row3.createCell(i);
	                //加载单元格样式
	                cell3.setCellStyle(colStyle);
	                cell3.setCellValue(titles[i]);
	            }
	            for(int i=0;i<titles2.length;i++)
	            {
	                HSSFCell cell32 = row3.createCell(i+4);
	                //加载单元格样式
	                cell32.setCellStyle(colStyle);
	                cell32.setCellValue(titles2[i]);
	            }
	            String[] months = {"一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"};
	            String[] parts = {"模型","图纸","首页","交底","进度管理","安全管理","消息","统计管理","个人中心","规范查阅","模型构建信息","质量管理","新闻资讯","实测实量","云盘管理","物资管理","劳动力监测","考勤管理","工程量变更","进程管理","施工任务单","预付单"};
	            //4.操作单元格;将用户列表写入excel
	            if(monthLogList!=null){
	            	 if(!monthLogList.isEmpty())
	 	            {
	 	                for(int j=0;j<partLogList.size();j++)
	 	                {
	 	                	 //创建数据行,前面有两行,头标题行和列标题行
	 	                    HSSFRow row4 = sheet.createRow(j+4);
	 	                    row4.setHeightInPoints(20);
	 	                    if(j<12){
	 	                    	 HSSFCell cell0 = row4.createCell(0);
	 	 	                    cell0.setCellStyle(cellStyle);
	 	 	                    //创建数据行,前面有两行,头标题行和列标题行
	 	 	                    cell0.setCellValue(months[j]);
	 	 	                    
	 	 	                    HSSFCell cell1s = row4.createCell(1);
	 	 	                    cell1s.setCellStyle(cellStyle);
	 	 	                    cell1s.setCellValue(monthLogList.get(j).getNum());
	 	 	                    
	 	 	                    HSSFCell cell2s = row4.createCell(2);
	 	 	                    cell2s.setCellStyle(cellStyle);
	 	 	                    cell2s.setCellValue(monthLogList.get(j).getRealNum());
	 	 	                    
	 	 	                    /////////////////////////
	 	 	                    
	 	 	                    HSSFCell cell4s = row4.createCell(4);
	 	 	                    cell4s.setCellStyle(cellStyle);
	 	 	                    cell4s.setCellValue(parts[partLogList.get(j).getProjectPart()]);
	 	 	                    HSSFCell cell5s = row4.createCell(5);
	 	 	                    cell5s.setCellStyle(cellStyle);
	 	 	                    cell5s.setCellValue(partLogList.get(j).getNums());
	 	 	                    
	 	                    }else{
	 	 	                    HSSFCell cell4s = row4.createCell(4);
	 	 	                    cell4s.setCellStyle(cellStyle);
	 	 	                    cell4s.setCellValue(parts[partLogList.get(j).getProjectPart()]);
	 	 	                    HSSFCell cell5s = row4.createCell(5);
	 	 	                    cell5s.setCellStyle(cellStyle);
	 	 	                    cell5s.setCellValue(partLogList.get(j).getNums());
	 	                    }
	 	                   
	 	                }
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
