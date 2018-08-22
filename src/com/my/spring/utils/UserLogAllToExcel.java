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

public class UserLogAllToExcel {
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
        	 FileOutputStream fout = new FileOutputStream("C://Users/Han/Desktop/userLog.xls");
             getValue(logList,fout,"2018-01-01","2018-02-03");
             fout.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	public static void getValue(List<UserLogCount> logList,FileOutputStream fout,String start,String end){
		  try{
	            //1.创建工作簿
	            HSSFWorkbook workbook = new HSSFWorkbook();
	            //1.1创建合并单元格对象
	            CellRangeAddress callRangeAddress1 = new CellRangeAddress(0,0,0,2);//起始行,结束行,起始列,结束列
	            CellRangeAddress callRangeAddress2 = new CellRangeAddress(1,1,0,2);//起始行,结束行,起始列,结束列
	            //标题
	            CellRangeAddress callRangeAddress31 = new CellRangeAddress(2,2,0,0);//起始行,结束行,起始列,结束列
	            CellRangeAddress callRangeAddress32 = new CellRangeAddress(2,2,1,1);//起始行,结束行,起始列,结束列
	            CellRangeAddress callRangeAddress33 = new CellRangeAddress(2,2,2,2);//起始行,结束行,起始列,结束列
	            //公司名称
	            HSSFCellStyle erStyle = createCellStyle(workbook,(short)18,true,true);
	            erStyle.setWrapText(true);
	            erStyle.setWrapText(true);
	            //标题样式
	            HSSFCellStyle colStyle = createCellStyle(workbook,(short)12,true,true);
	            //内容样式
	            HSSFCellStyle cellStyle = createCellStyle(workbook,(short)10,false,true);
	            //2.创建工作表
	            HSSFSheet sheet = workbook.createSheet("用户操作记录表");
	            
	            //2.1加载合并单元格对象
	            sheet.addMergedRegion(callRangeAddress1);
	            sheet.addMergedRegion(callRangeAddress2);
	            sheet.addMergedRegion(callRangeAddress31);
	            sheet.addMergedRegion(callRangeAddress32);
	            sheet.addMergedRegion(callRangeAddress33);
	            //设置默认列宽
	            sheet.setDefaultColumnWidth(20);

	            //3.创建行
	            //////////////////////////////////////////////////////////////
	            //3.1创建头标题行;并且设置头标题
	            HSSFRow row = sheet.createRow(0);
	            row.setHeightInPoints(35);
	            HSSFCell cell = row.createCell(0);
	    
	            //加载单元格样式
	            cell.setCellStyle(erStyle);
	            
	            cell.setCellValue("用户操作记录表");
	            ///////
	            HSSFRow row1 = sheet.createRow(1);
	            row1.setHeightInPoints(20);
	            HSSFCell cell1 = row1.createCell(0);
	    
	            //加载单元格样式
	            cell1.setCellStyle(colStyle);
	            String time="";
	            if(start!=null){
	            	time=time+start+"  ——  ";
	            }
	            if(end!=null){
	            	if(!time.equals("")){
	            		time=time+end;
	            	}else{
	            		time=time+"  ——  "+end;
	            	}
	            }
	            cell1.setCellValue(time);
	            //3.2创建列标题;并且设置列标题
	            HSSFRow row2 = sheet.createRow(2);
	            String[] titles = {"姓名","项目名称","操作次数"};//""为占位字符串
	            row1.setHeightInPoints(20);
	            for(int i=0;i<titles.length;i++)
	            {
	                HSSFCell cell2 = row2.createCell(i);
	                //加载单元格样式
	                cell2.setCellStyle(colStyle);
	                cell2.setCellValue(titles[i]);
	            }
	          
	            
	            //4.操作单元格;将用户列表写入excel
	            if(logList != null)
	            {
	                for(int j=0;j<logList.size();j++)
	                {
	                	 //创建数据行,前面有两行,头标题行和列标题行
	                    HSSFRow row3 = sheet.createRow(j+3);
	                    row3.setHeightInPoints(20);
	                    HSSFCell cell0 = row3.createCell(0);
	                    cell0.setCellStyle(cellStyle);
	                    //创建数据行,前面有两行,头标题行和列标题行
	                    cell0.setCellValue(logList.get(j).getReal_name());
	                    
	                    HSSFCell cell1s = row3.createCell(1);
	                    cell1s.setCellStyle(cellStyle);
	                    cell1s.setCellValue(logList.get(j).getName());
	                    
	                    HSSFCell cell2s = row3.createCell(2);
	                    cell2s.setCellStyle(cellStyle);
	                    cell2s.setCellValue(logList.get(j).getNum());
	                }
	            }
	            //sheet.autoSizeColumn((short)1); //调整第二列宽度
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
