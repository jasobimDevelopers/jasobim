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

import com.my.spring.model.ConstructionTaskNewPojo;
import com.my.spring.model.ProcessLogPojo;

/**
* @author 徐雨祥
* @version 创建时间：2018年9月6日 上午8:10:51
* 类说明
*/
public class ConstructionTaskExportUtil {
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
        	 FileOutputStream fout = new FileOutputStream("C://Users/Han/Desktop/taskLog.xls");
             getValue(logList,taskList,"歌林小镇",fout);
             fout.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	public static void getValue(List<ProcessLogPojo> logList,List<ConstructionTaskNewPojo> taskList,String projectName,FileOutputStream fout){
		 if(taskList==null){
         	taskList = new ArrayList<ConstructionTaskNewPojo>();
         }
		 if(logList==null){
			 logList = new ArrayList<ProcessLogPojo>();
		 }
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
	            HSSFCellStyle erStyle = createCellStyle(workbook,(short)14,true,true);
	            erStyle.setWrapText(true);
	            HSSFCellStyle headErStyle = createHeadCellStyle(workbook,(short)14,true,true);
	            HSSFCellStyle noBorder=createCellStyleNoBorder(workbook,(short)11,true,true);
	            //标题样式
	            HSSFCellStyle colStyle = createCellStyle(workbook,(short)9,false,true);
	            colStyle.setWrapText(true);
	            HSSFCellStyle colStyle1 = createCellStyle(workbook,(short)9,true,true);
	            colStyle1.setWrapText(true);
	            HSSFCellStyle colStyle2 = createCellStyleLeft(workbook,(short)9,true,true);
	            colStyle2.setWrapText(true);
	            HSSFCellStyle colStyle3 = createCellStyleRight(workbook,(short)9,true,true);
	            colStyle3.setWrapText(true);
	            HSSFCellStyle colStyle4 = createCellStyleLeftNoBorder(workbook,(short)9,true,true);
	            colStyle3.setWrapText(true);
	            //2.创建工作表
	            HSSFSheet sheet = workbook.createSheet("施工单表");
	            
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
	            for(int i=0;i<taskList.size();i++){
	            	sheet.addMergedRegion(new CellRangeAddress(7+i,7+i,1,2));
	            }
	            sheet.addMergedRegion(new CellRangeAddress(7+taskList.size(),7+taskList.size(),0,7));
	            sheet.addMergedRegion(new CellRangeAddress(9+taskList.size(),9+taskList.size(),0,8));
	            sheet.addMergedRegion(new CellRangeAddress(10+taskList.size(),10+taskList.size(),3,4));
	            sheet.addMergedRegion(new CellRangeAddress(10+taskList.size(),10+taskList.size(),5,6));
	            sheet.addMergedRegion(new CellRangeAddress(10+taskList.size(),10+taskList.size(),7,8));
	            sheet.addMergedRegion(new CellRangeAddress(11+taskList.size()+logList.size(),11+taskList.size()+logList.size(),0,8));
	            
	            for(int i=1;i<=logList.size();i++){
	            	sheet.addMergedRegion(new CellRangeAddress(10+taskList.size()+i,10+taskList.size()+i,3,4));
	 	            sheet.addMergedRegion(new CellRangeAddress(10+taskList.size()+i,10+taskList.size()+i,5,6));
	 	            sheet.addMergedRegion(new CellRangeAddress(10+taskList.size()+i,10+taskList.size()+i,7,8));
	            }
	            //设置默认列宽
	            //sheet.setDefaultColumnWidth(10);
	           // sheet.autoSizeColumn(i); 
	            sheet.setColumnWidth(0, 256*4);
	            sheet.setColumnWidth(1, 256*11);
	            sheet.setColumnWidth(5, 256*7);
	            sheet.setColumnWidth(5, 256*8);
	            sheet.setColumnWidth(5, 256*9);
	            sheet.setColumnWidth(5, 256*15);
	            sheet.setColumnWidth(5, 256*11);
	            sheet.setColumnWidth(5, 256*9);
	            sheet.setColumnWidth(5, 256*9);
	           
	            //3.创建行
	            //////////////////////////////////////////////////////////////
	            //3.1创建头标题行;并且设置头标题
	            HSSFRow row = sheet.createRow(0);
	            row.setHeightInPoints(36);
	            HSSFCell cell = row.createCell(0);
	    
	            //加载单元格样式
	            cell.setCellStyle(headErStyle);
	            
	            cell.setCellValue("上海嘉实（集团）有限公司");
	            ///////第二行
	            HSSFRow row1 = sheet.createRow(1);
	            row1.setHeightInPoints(27);
	            HSSFCell cell1 = row1.createCell(0);
	            //加载单元格样式
	            cell1.setCellStyle(noBorder);
	            
	            cell1.setCellValue(projectName+"项目部零星派工任务单");
	           
	            ///////////第三行
	            String code=taskList.get(0).getConstructionTaskDate();//任务单编号

	            String[] strNow = code.split("-");
				code=strNow[0]+strNow[1]+strNow[2]+String.valueOf(taskList.get(0).getId());
	            HSSFRow row2 = sheet.createRow(2);
	            row2.setHeightInPoints(27);
	            HSSFCell cell21 = row2.createCell(0);
	            cell21.setCellStyle(colStyle4);
                cell21.setCellValue("任务单编号："+code);
                HSSFCell cell22 = row2.createCell(4);
                cell22.setCellStyle(colStyle3);
                cell22.setCellValue("签发人："+taskList.get(0).getCreateUser());
	            /////////////////第四行
	            //3.2创建列标题;并且设置列标题
	            HSSFRow row3 = sheet.createRow(3);
            	for(int j=0;j<9;j++){
            		HSSFCell cells = row3.createCell(j);
            		cells.setCellStyle(colStyle1);
            	}
	            row2.setHeightInPoints(27);
	            HSSFCell cell31 = row3.createCell(0);
	            cell31.setCellStyle(colStyle1);
                cell31.setCellValue("任务单名称");
                HSSFCell cell32 = row3.createCell(3);
                cell32.setCellStyle(colStyle);
                cell32.setCellValue(taskList.get(0).getName());
                ////////////////第五行
                HSSFRow row4 = sheet.createRow(4);
                for(int j=0;j<9;j++){
            		HSSFCell cells = row4.createCell(j);
            		cells.setCellStyle(colStyle1);
            	}
	            row2.setHeightInPoints(27);
	            HSSFCell cell41 = row4.createCell(0);
	            cell41.setCellStyle(colStyle1);
                cell41.setCellValue("项目标段");
                HSSFCell cell42 = row4.createCell(3);
                cell42.setCellStyle(colStyle);
                cell42.setCellValue(taskList.get(0).getTenders());
                ////////////第六行
                HSSFRow row5 = sheet.createRow(5);
                for(int j=0;j<9;j++){
            		HSSFCell cells = row5.createCell(j);
            		cells.setCellStyle(colStyle1);
            	}
	            row2.setHeightInPoints(27);
	            HSSFCell cell51 = row5.createCell(0);
	            cell51.setCellStyle(colStyle1);
                cell51.setCellValue("任务单日期");
                HSSFCell cell52 = row5.createCell(3);
                cell52.setCellStyle(colStyle);
                cell52.setCellValue(taskList.get(0).getConstructionTaskDate());
                //////////表头一
                String[] titleList1 = {"序号","任务内容","施工部位","施工类型","人员","白班/夜班","工日","人工出勤工资"};
                HSSFRow row6 = sheet.createRow(6);
                for(int j=0;j<9;j++){
            		HSSFCell cells = row6.createCell(j);
            		cells.setCellStyle(colStyle1);
            	}
                row2.setHeightInPoints(27);
	            HSSFCell cell61 = row6.createCell(0);
	            cell61.setCellStyle(colStyle1);
                cell61.setCellValue(titleList1[0]);
                HSSFCell cell62 = row6.createCell(1);
	            cell62.setCellStyle(colStyle1);
                cell62.setCellValue(titleList1[1]);
                for(int i=2;i<titleList1.length;i++){
                	 HSSFCell cell63 = row6.createCell(i+1);
                	 cell63.setCellStyle(colStyle1);
                	 cell63.setCellValue(titleList1[i]);
                }
                ////////施工内容条目循环
               
                for(int i=0;i<taskList.size();i++){
                	 HSSFRow row7 = sheet.createRow(7+i);
                	 for(int j=0;j<9;j++){
                 		HSSFCell cells = row7.createCell(j);
                 		cells.setCellStyle(colStyle1);
                 	 }
                     row7.setHeightInPoints(27);
                	for(int j=0;j<=titleList1.length;j++){
                		if(j==0){
                			HSSFCell cell71 = row7.createCell(j);
            	            cell71.setCellStyle(colStyle);
            	            cell71.setCellValue(i+1);
                		}
                		if(j==1){
                			HSSFCell cell72 = row7.createCell(j);
    			        	cell72.setCellStyle(colStyle);
    			        	cell72.setCellValue(taskList.get(i).getConstructContent());
                		}
                		if(j==3){
                			HSSFCell cell73 = row7.createCell(j);
    			        	cell73.setCellStyle(colStyle);
    			        	cell73.setCellValue(taskList.get(i).getConstructPart());
                		}
                		if(j==4){
                			HSSFCell cell74 = row7.createCell(j);
    			        	cell74.setCellStyle(colStyle);
    			        	cell74.setCellValue(taskList.get(i).getConstructType());
                		}
                		if(j==5){
                			HSSFCell cell75 = row7.createCell(j);
    			        	cell75.setCellStyle(colStyle);
    			        	cell75.setCellValue(taskList.get(i).getTeamUserIds());
                		}
                		if(j==6){
                			HSSFCell cell76 = row7.createCell(j);
    			        	cell76.setCellStyle(colStyle);
    			        	cell76.setCellValue(taskList.get(i).getDayWorkHours()+"/"+taskList.get(i).getNightWorkHours());
                		}
                		if(j==7){
                			HSSFCell cell77 = row7.createCell(j);
    			        	cell77.setCellStyle(colStyle);
    			        	cell77.setCellValue(taskList.get(i).getDayNums());
                		}
                		if(j==8){
                			HSSFCell cell78 = row7.createCell(j);
    			        	cell78.setCellStyle(colStyle);
    			        	cell78.setCellValue(taskList.get(i).getSalary());
                		}
                	}     
                }
                Double salaryNums=0.0;
                for(int j=0;j<taskList.size();j++){
                	salaryNums=salaryNums+taskList.get(j).getSalary();
                }
                ////////////////内容添加
                HSSFRow row8 = sheet.createRow(7+taskList.size());
                for(int j=0;j<9;j++){
             		HSSFCell cells = row8.createCell(j);
             		cells.setCellStyle(colStyle1);
             	}
                row8.setHeightInPoints(27);
	            HSSFCell cell81 = row8.createCell(0);
	            cell81.setCellStyle(colStyle);
                cell81.setCellValue("合计");
                HSSFCell cell82 = row8.createCell(8);
	            cell82.setCellStyle(colStyle);
                cell82.setCellValue(salaryNums);
                HSSFRow row10 = sheet.createRow(9+taskList.size());
                for(int j=0;j<9;j++){
             		HSSFCell cells = row10.createCell(j);
             		cells.setCellStyle(colStyle1);
             	}
                row10.setHeightInPoints(20);
	            HSSFCell cell101 = row10.createCell(0);
	            cell101.setCellStyle(colStyle2);
	            cell101.setCellValue("审批流程：");
	            String[] approveTitle ={"序号","审核时间","审核人","审核岗位","是否同意","审核意见"};
	            String[] itemStateStr={"同意","不同意"};
	            HSSFRow row11 = sheet.createRow(10+taskList.size());
	            for(int j=0;j<9;j++){
             		HSSFCell cells = row11.createCell(j);
             		cells.setCellStyle(colStyle1);
             	}
                row11.setHeightInPoints(27);
                ///////////////////////////////
           	    HSSFCell cell111 = row11.createCell(0);
           	    cell111.setCellStyle(colStyle1);
           	    cell111.setCellValue(approveTitle[0]);
           	    ////////////////////////////////
           	    HSSFCell cell112 = row11.createCell(1);
        	    cell112.setCellStyle(colStyle1);
        	    cell112.setCellValue(approveTitle[1]);
        	    /////////////////////////////////
        	    HSSFCell cell113 = row11.createCell(2);
           	    cell113.setCellStyle(colStyle1);
           	    cell113.setCellValue(approveTitle[2]);
           	    ///////////////////////////
           	    HSSFCell cell114 = row11.createCell(3);
        	    cell114.setCellStyle(colStyle1);
        	    cell114.setCellValue(approveTitle[3]);
        	    ///////////////////////////
        	    HSSFCell cell116 = row11.createCell(5);
           	    cell116.setCellStyle(colStyle1);
           	    cell116.setCellValue(approveTitle[4]);
           	    //////////////////////////
           	    HSSFCell cell118 = row11.createCell(7);
        	    cell118.setCellStyle(colStyle1);
        	    cell118.setCellValue(approveTitle[5]);
        	    //////审批条目内容
        	    for(int q=0;q<logList.size();q++){
        	    	 HSSFRow row12 = sheet.createRow(14+q);
        	    	 for(int j=0;j<9;j++){
                  		HSSFCell cells = row12.createCell(j);
                  		cells.setCellStyle(colStyle1);
                  	}
        	    	 row12.setHeightInPoints(27);
                	for(int j=0;j<=approveTitle.length;j++){
                		if(j==0){
                			HSSFCell cell121 = row12.createCell(j);
            	            cell121.setCellStyle(colStyle);
            	            cell121.setCellValue(q+1);
                		}
                		if(j==1){
                			HSSFCell cell122 = row12.createCell(j);
    			        	cell122.setCellStyle(colStyle);
    			        	cell122.setCellValue(logList.get(q).getApproveDate());
                		}
                		if(j==2){
                			HSSFCell cell123 = row12.createCell(j);
    			        	cell123.setCellStyle(colStyle);
    			        	cell123.setCellValue(logList.get(q).getApproveUser());
                		}
                		if(j==3){
                			HSSFCell cell124 = row12.createCell(j);
    			        	cell124.setCellStyle(colStyle);
    			        	cell124.setCellValue(logList.get(q).getItemName());
                		}
                		if(j==5){
                			HSSFCell cell125 = row12.createCell(j);
    			        	cell125.setCellStyle(colStyle);
    			        	if(!logList.isEmpty()){
    			        		if(logList.get(q).getItemState()!=null){
    			        			cell125.setCellValue(itemStateStr[logList.get(q).getItemState()]);
    			        		}
    			        	}
                		}
                		if(j==6){
                			HSSFCell cell126 = row12.createCell(j+1);
    			        	cell126.setCellStyle(colStyle);
    			        	cell126.setCellValue(logList.get(q).getNote());
                		}
            
                	} 
        	    }
        	    HSSFRow row13 = sheet.createRow(11+taskList.size()+logList.size());
        	    for(int j=0;j<9;j++){
              		HSSFCell cells = row13.createCell(j);
              		cells.setCellStyle(colStyle1);
              	}
                row13.setHeightInPoints(27);
                HSSFCell cell131 = row13.createCell(0);
                cell131.setCellStyle(colStyle2);
                cell131.setCellValue("填单时间："+taskList.get(0).getCreateDate());
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
