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
import com.my.spring.model.ConstructionLogPojo;
import com.my.spring.model.ProductionRecords;

/**
* @author 徐雨祥
* @version 创建时间：2018年9月6日 上午8:10:51
* 类说明
*/
public class ConstructionLogExportUtil {
	public static void main(String[] arg){
        try {
        	ConstructionLogPojo log = new ConstructionLogPojo();
   		 	log.setConstructionDate("2018-09-01");
   		 	log.setEmergencyState(0);
   		 	log.setMaterialDiscloseContent("材料进场测试");
   		 	log.setMaterialDiscloseState(1);
   		 	log.setQualityDiscloseContent("质量交底记录测试");
   		 	log.setQualityDiscloseState(1);
   		 	log.setSafetyDiscloseContent("安全交底记录测试");
   		 	log.setSafetyDiscloseState(1);
   		 	log.setTechnologyDiscloseContent("技术交底记录测试");
   		 	log.setTechnologyDiscloseState(1);
   		 	log.setDayTemperature("25-29°");
   		 	log.setDayWeather("大雨");
   		 	log.setDayWindForce("3级");
        	List<ProductionRecords> productList = new  ArrayList<ProductionRecords>();
        	for(int i=0;i<3;i++){
        		 ProductionRecords record = new ProductionRecords();
        		 record.setConstructContent("施工内容测试"+i);
        		 record.setConstructPartName("施工部位测试"+i);
        		 record.setUserNameList("张三、李四"+i);
        		 productList.add(record);
        	}
        	log.setProductionRecordsList(new  ArrayList<ProductionRecords>());
        	 FileOutputStream fout = new FileOutputStream("C://Users/Han/Desktop/constructionLog.xls");
        	//FileOutputStream fout1 = new FileOutputStream("D://jasobim/tomcat_8080/webapps/ROOT/files/constructionLog");
             getValue(log,"歌林小镇","黄卫国",fout);
             fout.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	public static void getValue(ConstructionLogPojo log,String projectName,String leader,FileOutputStream fout){
		 
		try{
	            //1.创建工作簿
	            HSSFWorkbook workbook = new HSSFWorkbook();
	            //1.1创建合并单元格对象
	            CellRangeAddress callRangeAddress1 = new CellRangeAddress(0,0,0,5);//起始行,结束行,起始列,结束列
	            CellRangeAddress callRangeAddress2 = new CellRangeAddress(1,1,3,5);//起始行,结束行,起始列,结束列
	            //标题
	            CellRangeAddress callRangeAddress3 = new CellRangeAddress(3,3,1,5);//起始行,结束行,起始列,结束列
	            CellRangeAddress callRangeAddress4 = new CellRangeAddress(4,4,0,5);//起始行,结束行,起始列,结束列
	            ////生产情况记录表
	            CellRangeAddress callRangeAddress5 = new CellRangeAddress(5,5,2,5);//起始行,结束行,起始列,结束列
	            //公司名称
	            HSSFCellStyle erStyle = createCellStyle(workbook,(short)18,true,true);
	            erStyle.setWrapText(true);
	            HSSFCellStyle erStyle1 = createCellStyle(workbook,(short)12,false,true);
	            erStyle1.setWrapText(true);
	            HSSFCellStyle noBorder=createCellStyleNoBorder(workbook,(short)18,true,true);
	            HSSFCellStyle leftStyle=createCellStyleLeft(workbook,(short)14,false,true);
	            //2.创建工作表
	            HSSFSheet sheet = workbook.createSheet("施工日志表");
	            
	            //2.1加载合并单元格对象
	            sheet.addMergedRegion(callRangeAddress1);
	            sheet.addMergedRegion(callRangeAddress2);
	            sheet.addMergedRegion(callRangeAddress3);
	            sheet.addMergedRegion(callRangeAddress4);
	            sheet.addMergedRegion(callRangeAddress5);
	            List<ProductionRecords> productList = log.getProductionRecordsList();
	            int size=0;
	            if(!productList.isEmpty()){
	            	 for(int i=0;i<productList.size();i++){
	 	            	sheet.addMergedRegion(new CellRangeAddress(6+i,6+i,2,5));
	 	            }
	            	 size= productList.size();
	            }
            	sheet.addMergedRegion(new CellRangeAddress(6+size,6+size,0,5));
            	///
            	sheet.addMergedRegion(new CellRangeAddress(7+size,7+size,0,1));
            	sheet.addMergedRegion(new CellRangeAddress(7+size,7+size,2,5));
            	///
            	sheet.addMergedRegion(new CellRangeAddress(8+size,8+size,0,1));
            	sheet.addMergedRegion(new CellRangeAddress(8+size,8+size,2,5));
            	//
            	sheet.addMergedRegion(new CellRangeAddress(9+size,9+size,0,1));
            	sheet.addMergedRegion(new CellRangeAddress(9+size,9+size,2,5));
            	//
            	sheet.addMergedRegion(new CellRangeAddress(10+size,10+size,0,1));
            	sheet.addMergedRegion(new CellRangeAddress(10+size,10+size,2,5));
            	///
            	sheet.addMergedRegion(new CellRangeAddress(11+size,11+size,1,2));
            	sheet.addMergedRegion(new CellRangeAddress(11+size,11+size,4,5));
	            
	            sheet.setDefaultColumnWidth(18);
	           // sheet.setColumnWidth(0, 256*6);
	            //sheet.setColumnWidth(1, 256*22);
	            //sheet.setColumnWidth(5, 256*16);
	            //3.创建行
	            //////////////////////////////////////////////////////////////
	            //3.1创建头标题行;并且设置头标题
	            HSSFRow row = sheet.createRow(0);
	            row.setHeightInPoints(30);
	            HSSFCell cell = row.createCell(0);
	    
	            //加载单元格样式
	            cell.setCellStyle(noBorder);
	            
	            cell.setCellValue(projectName+"施工日志");
	            ///////第二行
	            HSSFRow row1 = sheet.createRow(1);
	            for(int j=0;j<6;j++){
            		HSSFCell cells = row1.createCell(j);
            		cells.setCellStyle(erStyle1);
            	}
	            row1.setHeightInPoints(25);
	            //////////
	            HSSFCell cell1 = row1.createCell(0);
	            cell1.setCellStyle(erStyle1);
	            cell1.setCellValue("日期");
	            HSSFCell cell2 = row1.createCell(1);
	            cell2.setCellStyle(erStyle1);
	            cell2.setCellValue(log.getConstructionDate());
	            HSSFCell cell3 = row1.createCell(2);
	            cell3.setCellStyle(erStyle1);
	            cell3.setCellValue("施工部位");
	            HSSFCell cell4 = row1.createCell(3);
	            cell4.setCellStyle(erStyle1);
	            if(!productList.isEmpty()){
	            	cell4.setCellValue(productList.get(0).getConstructPartName());
	            }
	            /////第三行
	            HSSFRow row2 = sheet.createRow(2);
                row2.setHeightInPoints(25);
	            HSSFCell cell31 = row2.createCell(0);
	            cell31.setCellStyle(erStyle1);
	            cell31.setCellValue("天气状况");
	            HSSFCell cell32 = row2.createCell(1);
	            cell32.setCellStyle(erStyle1);
	            cell32.setCellValue(log.getDayWeather());
	            HSSFCell cell33 = row2.createCell(2);
	            cell33.setCellStyle(erStyle1);
	            cell33.setCellValue("风力");
	            HSSFCell cell34 = row2.createCell(3);
	            cell34.setCellStyle(erStyle1);
	            cell34.setCellValue(log.getDayWindForce());
	            HSSFCell cell35 = row2.createCell(4);
	            cell35.setCellStyle(erStyle1);
	            cell35.setCellValue("温度");
	            HSSFCell cell36 = row2.createCell(5);
	            cell36.setCellStyle(erStyle1);
	            cell36.setCellValue(log.getDayTemperature());
	            //////第四行
	            HSSFRow row3 = sheet.createRow(3);
	            for(int j=0;j<6;j++){
            		HSSFCell cells = row3.createCell(j);
            		cells.setCellStyle(erStyle1);
            	}
                row3.setHeightInPoints(25);
	            HSSFCell cell41 = row3.createCell(0);
	            cell41.setCellStyle(erStyle1);
	            cell41.setCellValue("突发事件");
	            HSSFCell cell42 = row3.createCell(1);
	            cell42.setCellStyle(erStyle1);
	            if(log.getEmergencyState()==0){
	            	 cell42.setCellValue("√无/有");
	            }
	            if(log.getEmergencyState()==1){
	            	cell42.setCellValue("无/有√");
	            }
	            ///////第五行
	            HSSFRow row4 = sheet.createRow(4);
	            for(int j=0;j<6;j++){
            		HSSFCell cells = row4.createCell(j);
            		cells.setCellStyle(erStyle1);
            	}
                row4.setHeightInPoints(25);
	            HSSFCell cell51 = row4.createCell(0);
	            cell51.setCellStyle(leftStyle);
	            cell51.setCellValue("生产情况记录表：");
	            ////////第六行
	            HSSFRow row5 = sheet.createRow(5);
	            for(int j=0;j<6;j++){
            		HSSFCell cells = row5.createCell(j);
            		cells.setCellStyle(erStyle1);
            	}
                row5.setHeightInPoints(25);
	            HSSFCell cell61 = row5.createCell(0);
	            cell61.setCellStyle(erStyle1);
	            cell61.setCellValue("作业人员");
	            HSSFCell cell62 = row5.createCell(1);
	            cell62.setCellStyle(erStyle1);
	            cell62.setCellValue("施工部位");
	            HSSFCell cell63 = row5.createCell(2);
	            cell63.setCellStyle(erStyle1);
	            cell63.setCellValue("主要施工内容");
	            //////第七行循环遍历内容
	            if(!productList.isEmpty()){
	            	for(int i=0;i<productList.size();i++){
	            		HSSFRow row6 = sheet.createRow(6+i);
	            		 for(int j=0;j<6;j++){
	                 		HSSFCell cells = row6.createCell(j);
	                 		cells.setCellStyle(erStyle1);
	                 	}
	                    row6.setHeightInPoints(20);
	    	            HSSFCell cell71 = row6.createCell(0);
	    	            cell71.setCellStyle(erStyle1);
	    	            cell71.setCellValue(productList.get(i).getUserNameList());
	    	            HSSFCell cell72 = row6.createCell(1);
	    	            cell72.setCellStyle(erStyle1);
	    	            cell72.setCellValue(productList.get(i).getConstructPartName());
	    	            HSSFCell cell73 = row6.createCell(2);
	    	            cell73.setCellStyle(erStyle1);
	    	            cell73.setCellValue(productList.get(i).getConstructContent());
	            	}
	            }
	            /////第七+size行
                HSSFRow row7 = sheet.createRow(6+productList.size());
                for(int j=0;j<6;j++){
            		HSSFCell cells = row7.createCell(j);
            		cells.setCellStyle(erStyle1);
            	}
                row7.setHeightInPoints(20);
	            HSSFCell cell71 = row7.createCell(0);
	            cell71.setCellStyle(leftStyle);
	            cell71.setCellValue("技术质量安全工作记录（材料进出场记录）：");
	            ///第八行
	            HSSFRow row8 = sheet.createRow(7+productList.size());
	            for(int j=0;j<6;j++){
            		HSSFCell cells = row8.createCell(j);
            		cells.setCellStyle(erStyle1);
            	}
                row8.setHeightInPoints(20);
	            HSSFCell cell81 = row8.createCell(0);
	            cell81.setCellStyle(erStyle1);
	            if(log.getTechnologyDiscloseState()==0){
	            	cell81.setCellValue("技术工作记录：          "+"√无/有");
	            }
	            if(log.getTechnologyDiscloseState()==1){
	            	cell81.setCellValue("技术工作记录：          "+"无/有√");
	            }
	            HSSFCell cell82 = row8.createCell(2);
	            cell82.setCellStyle(erStyle1);
	            cell82.setCellValue(log.getTechnologyDiscloseContent());
	            
	            //第九行
	            HSSFRow row9 = sheet.createRow(8+productList.size());
	            for(int j=0;j<6;j++){
            		HSSFCell cells = row9.createCell(j);
            		cells.setCellStyle(erStyle1);
            	}
                row9.setHeightInPoints(20);
	            HSSFCell cell91 = row9.createCell(0);
	            cell91.setCellStyle(erStyle1);
	            if(log.getQualityDiscloseState()==0){
	            	cell91.setCellValue("质量工作记录：          "+"√无/有");
	            }
	            if(log.getQualityDiscloseState()==1){
	            	cell91.setCellValue("质量工作记录：          "+"无/有√");
	            }
	            HSSFCell cell92 = row9.createCell(2);
	            cell92.setCellStyle(erStyle1);
	            cell92.setCellValue(log.getQualityDiscloseContent());
	            
	            /////第十行
	            HSSFRow row10 = sheet.createRow(9+productList.size());
	            for(int j=0;j<6;j++){
            		HSSFCell cells = row10.createCell(j);
            		cells.setCellStyle(erStyle1);
            	}
                row10.setHeightInPoints(20);
	            HSSFCell cell101 = row10.createCell(0);
	            cell101.setCellStyle(erStyle1);
	            if(log.getSafetyDiscloseState()==0){
	            	cell101.setCellValue("安全工作记录：          "+"√无/有");
	            }
	            if(log.getSafetyDiscloseState()==1){
	            	cell101.setCellValue("安全工作记录：          "+"无/有√");
	            }
	            HSSFCell cell102 = row10.createCell(2);
	            cell102.setCellStyle(erStyle1);
	            cell102.setCellValue(log.getSafetyDiscloseContent());
	            
	            
	            /////第十一行
	            HSSFRow row11 = sheet.createRow(10+productList.size());
	            for(int j=0;j<6;j++){
            		HSSFCell cells = row11.createCell(j);
            		cells.setCellStyle(erStyle1);
            	}
                row11.setHeightInPoints(20);
	            HSSFCell cell111 = row11.createCell(0);
	            cell111.setCellStyle(erStyle1);
	            if(log.getMaterialDiscloseState()==0){
	            	cell111.setCellValue("材料进出场记录：          "+"√无/有");
	            }
	            if(log.getMaterialDiscloseState()==1){
	            	cell111.setCellValue("材料进出场记录：          "+"无/有√");
	            }
	            HSSFCell cell112 = row11.createCell(2);
	            cell112.setCellStyle(erStyle1);
	            cell112.setCellValue(log.getMaterialDiscloseContent());
	           
	            //////第十二行
	            HSSFRow row12 = sheet.createRow(11+productList.size());
	            for(int j=0;j<6;j++){
            		HSSFCell cells = row12.createCell(j);
            		cells.setCellStyle(erStyle1);
            	}
                row12.setHeightInPoints(20);
	            HSSFCell cell121 = row12.createCell(0);
	            cell121.setCellStyle(erStyle1);
	            cell121.setCellValue("项目负责人");
	            HSSFCell cell122 = row12.createCell(1);
	            cell122.setCellStyle(erStyle1);
	            cell122.setCellValue(leader);
	            HSSFCell cell123 = row12.createCell(3);
	            cell123.setCellStyle(erStyle1);
	            cell123.setCellValue("记录人");
	            HSSFCell cell124 = row12.createCell(4);
	            cell124.setCellStyle(erStyle1);
	            cell124.setCellValue(log.getCreateUserName());
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
