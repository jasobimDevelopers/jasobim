package com.my.spring.utils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import com.my.spring.model.OutputValuePojo;
import com.my.spring.model.ProjectOutputValue;
public class ExportExcelOfOutputValue {
	public static void main(String[] arg){
		try {
			File outDir =new File("C://Users/Han/Desktop/test");
			outDir.mkdirs();
			FileOutputStream fout = new FileOutputStream("C://Users/Han/Desktop/test/outputValue.xls");
			//getValue(null, fout,"歌林小镇",null);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
	}
	public void getValue(List<OutputValuePojo> list,FileOutputStream fout,String projectName,ProjectOutputValue pv){
		  try{
	            //1.创建工作簿
	            HSSFWorkbook workbook = new HSSFWorkbook();
	            CellRangeAddress callRangeAddress1 = new CellRangeAddress(0,0,0,14);//表头
	            CellRangeAddress callRangeAddress21 = new CellRangeAddress(1,2,0,0);//序号
	            CellRangeAddress callRangeAddress22 = new CellRangeAddress(1,2,1,1);//时间
	            CellRangeAddress callRangeAddress23 = new CellRangeAddress(1,2,2,2);//合同价
	            CellRangeAddress callRangeAddress24 = new CellRangeAddress(1,2,3,3);//甲供材
	            CellRangeAddress callRangeAddress25 = new CellRangeAddress(1,2,4,4);//合同产值
	            CellRangeAddress callRangeAddress26 = new CellRangeAddress(1,2,5,5);//签证变更（万元）
	            CellRangeAddress callRangeAddress27 = new CellRangeAddress(1,2,6,6);//工期对比
	            CellRangeAddress callRangeAddress28 = new CellRangeAddress(1,1,7,8);//本月产值
	            CellRangeAddress callRangeAddress29 = new CellRangeAddress(1,1,9,12);//当年产值
	            CellRangeAddress callRangeAddress210 = new CellRangeAddress(1,1,13,14);//累计产值
	            //公司名称
	            HSSFCellStyle erStyle = createCellStyle(workbook,(short)14,true,true);
	            erStyle.setWrapText(true);
	            //项目名称
	            HSSFCellStyle erStyle1 = createCellStyleBorder(workbook,(short)11,true,true);
	            erStyle1.setWrapText(true);
	            //内容样式
	            HSSFCellStyle cellStyle = createCellStyleBorder(workbook,(short)10,false,true);
	            //内容样式
	            // HSSFCellStyle erStyleBorder = createCellStyleBorder(workbook);
	            //2.创建工作表
	            HSSFSheet sheet = workbook.createSheet("项目员工月工资单");
	            //2.1加载合并单元格对象
	            sheet.addMergedRegion(callRangeAddress1);
	            sheet.addMergedRegion(callRangeAddress21);
	            sheet.addMergedRegion(callRangeAddress22);
	            sheet.addMergedRegion(callRangeAddress23);
	            sheet.addMergedRegion(callRangeAddress24);
	            sheet.addMergedRegion(callRangeAddress25);
	            sheet.addMergedRegion(callRangeAddress26);
	            sheet.addMergedRegion(callRangeAddress27);
	            sheet.addMergedRegion(callRangeAddress28);
	            sheet.addMergedRegion(callRangeAddress29);
	            sheet.addMergedRegion(callRangeAddress210);
	            //设置默认列宽
	            sheet.setDefaultColumnWidth(10);
	            //3.创建行
	            //////////////////////////////////////////////////////////////////
	            String[] title1={"序号","时间","合同价","甲供材","合同产值","签证变更（万元）","工期对比"};
	            String[] title2={"本月经营收入","本月甲供材","上月累计经营收入（当年）","本月累计经营收入（当年）","上月累计甲供材（当年）","本月累计甲供材（当年）","经营收入","甲供材"};
				//////////////////////////////////////////////////////////////
	           
	            
				//3.1创建头标题行;并且设置头标题
				HSSFRow row1 = sheet.createRow(0);
				row1.setHeightInPoints(25);
				HSSFCell cell = row1.createCell(0);
				//加载单元格样式
				cell.setCellStyle(erStyle);
				cell.setCellValue(projectName+"经营目标完成情况报表   （安装公司）");
	            ///创建公司名称行
	            HSSFRow row2 = sheet.createRow(1);
	            row2.setHeightInPoints(15);
	            for(int i=0;i<7;i++){
	            	 HSSFCell cell1 = row2.createCell(i);
	 	             //加载单元格样式
	 	             cell1.setCellStyle(erStyle1);
	 	             cell1.setCellValue(title1[i]);
	            }
	            
	            HSSFCell cell7 = row2.createCell(7);
	            //加载单元格样式
                cell7.setCellStyle(erStyle1);
                cell7.setCellValue("本月产值");
               
                ///////
                HSSFCell cell8 = row2.createCell(8);
	            //加载单元格样式
                cell8.setCellStyle(erStyle1);
                HSSFCell cell10 = row2.createCell(10);
	            //加载单元格样式
                cell10.setCellStyle(erStyle1);
                HSSFCell cell11 = row2.createCell(11);
	            //加载单元格样式
                cell11.setCellStyle(erStyle1);
                HSSFCell cell12 = row2.createCell(12);
	            //加载单元格样式
                cell12.setCellStyle(erStyle1);
                HSSFCell cell9 = row2.createCell(9);
                cell9.setCellStyle(erStyle1);
                cell9.setCellValue("当年产值");
                HSSFCell cell13 = row2.createCell(13);
	            //加载单元格样式
                cell13.setCellStyle(erStyle1);
                cell13.setCellValue("累计产值");
                HSSFCell cell14 = row2.createCell(14);
	            //加载单元格样式
                cell14.setCellStyle(erStyle1);
	            //3.2创建列标题;并且设置列标题
                
	            HSSFRow row3 = sheet.createRow(2);
	            row3.setHeightInPoints(45);
	            for(int i=0;i<7;i++){
	            	HSSFCell cell32 = row3.createCell(i);
	                //加载单元格样式
	                cell32.setCellStyle(erStyle1);
	            }
	            for(int i=0;i<title2.length;i++)
	            {
	                HSSFCell cell33 = row3.createCell(i+7);
	                //加载单元格样式
	                cell33.setCellStyle(erStyle1);
	                cell33.setCellValue(title2[i]);
	            }
	            //4.操作单元格;将用户列表写入excel
	            if(list != null)
	            {
	                for(int j=0;j<list.size();j++)
	                {
	                	 //创建数据行,前面有两行,头标题行和列标题行
	                    HSSFRow row4 = sheet.createRow(j+3);
	                    row4.setHeightInPoints(20);
	                    HSSFCell cell0 = row4.createCell(0);
	                    cell0.setCellStyle(cellStyle);
	                    //创建数据行,前面有两行,头标题行和列标题行
	                    cell0.setCellValue(j+1);
	                    
	                    HSSFCell cell1s = row4.createCell(1);
	                    cell1s.setCellStyle(cellStyle);
	                    cell1s.setCellValue(list.get(j).getYear()+"年"+list.get(j).getMonth()+"月");
	                    
	                    HSSFCell cell2s = row4.createCell(2);
	                    cell2s.setCellStyle(cellStyle);
	                    cell2s.setCellValue(pv.getContractPrice());
	                    
	                    HSSFCell cell3s = row4.createCell(3);
	                    cell3s.setCellStyle(cellStyle);
	                    cell3s.setCellValue(pv.getArmour());
	                    
	                    HSSFCell cell4s = row4.createCell(4);
	                    cell4s.setCellStyle(cellStyle);
	                    cell4s.setCellValue(pv.getContractOutputValue());
	                    
	                    HSSFCell cell5s = row4.createCell(5);
	                    cell5s.setCellStyle(cellStyle);
	                    cell5s.setCellValue(pv.getVisaChange());
	                    
	                    HSSFCell cell6s = row4.createCell(6);
	                    cell6s.setCellStyle(cellStyle);
	                    cell6s.setCellValue(pv.getPeriodComparison());
	                    
	                    HSSFCell cell7s = row4.createCell(7);
	                    cell7s.setCellStyle(cellStyle);
	                    cell7s.setCellValue(list.get(j).getOperatingIncomeNum());
	                    
	                    HSSFCell cell8s = row4.createCell(8);
	                    cell8s.setCellStyle(cellStyle);
	                    cell8s.setCellValue(list.get(j).getArmourNum());
	                    
	                    HSSFCell cell9s = row4.createCell(9);
	                    cell9s.setCellStyle(cellStyle);
	                    cell9s.setCellValue(list.get(j).getLastMonthOperatingIncomeNum());
	                    
	                    HSSFCell cell10s = row4.createCell(10);
	                    cell10s.setCellStyle(cellStyle);
	                    cell10s.setCellValue(list.get(j).getLastMonthOperatingIncomeNum()+list.get(j).getOperatingIncomeNum());
	                    
	                    HSSFCell cell11s = row4.createCell(11);
	                    cell11s.setCellStyle(cellStyle);
	                    cell11s.setCellValue(list.get(j).getLastMonthArmourNum());
	                    
	                    HSSFCell cell12s = row4.createCell(12);
	                    cell12s.setCellStyle(cellStyle);
	                    cell12s.setCellValue(list.get(j).getLastMonthArmourNum()+list.get(j).getArmourNum());
	                    
	                    HSSFCell cell13s = row4.createCell(13);
	                    cell13s.setCellStyle(cellStyle);
	                    cell13s.setCellValue(list.get(j).getAllOperatingIncomeNum());
	                    
	                    HSSFCell cell14s = row4.createCell(14);
	                    cell14s.setCellStyle(cellStyle);
	                    cell14s.setCellValue(list.get(j).getAllOpeArmourNum());
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
   
    /**
     * 
     * @param workbook
     * @param fontsize
     * @return 单元格样式
     */
    private static HSSFCellStyle createCellStyleBorder(HSSFWorkbook workbook, short fontsize,boolean flag,boolean flag1) {
        // TODO Auto-generated method stub
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
    
   
}