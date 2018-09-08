package com.my.spring.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.my.spring.model.ValueOutput;
import com.my.spring.parameters.Parameters;

public class WriteDataToExcel{
	private static String[] months = {"一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"};
	public static void main(String[] arg){
		List<ValueOutput> valueList = new ArrayList<ValueOutput>();
		for(int i=0;i<10;i++){
			ValueOutput v = new ValueOutput();
			v.setFinished(20);
			v.setMonth(4);
			v.setYear(2017);
			v.setDate(new Date());
			valueList.add(v);
		}
		WriteData(valueList,2500);
	}
	public static String WriteData(List<ValueOutput> valueList,Integer projectPrice){
    	HSSFWorkbook workbook = new HSSFWorkbook();
        //第二部，在workbook中创建一个sheet对应excel中的sheet
        HSSFSheet sheet = workbook.createSheet("产值表一");
        HSSFCellStyle styleBorderThin= workbook.createCellStyle();
        styleBorderThin.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        styleBorderThin.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        styleBorderThin.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        styleBorderThin.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        /////////////
        ///sheet.createFreezePane(1, 3);// 冻结    
        // 设置列宽    
        sheet.setColumnWidth(0, 3000);    
        sheet.setColumnWidth(1, 3000);    
        sheet.setColumnWidth(2, 3000);    
        sheet.setColumnWidth(3, 3000);    
        sheet.setColumnWidth(4, 3000);    
        sheet.setColumnWidth(5, 6500);    
       
        //sheet.set
       
      ////////////////////
        //第三部，在sheet表中添加表头第0行，老版本的poi对sheet的行列有限制
        HSSFRow row = sheet.createRow(0);
        //第四步，创建单元格，设置表头
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("月份");
        cell = row.createCell(1);
        cell.setCellValue("当月产值");
        cell = row.createCell(2);
        cell.setCellValue("累计产值");
        cell = row.createCell(3);
        cell.setCellValue("总产值");
        cell = row.createCell(4);
        cell.setCellValue("累计占比");
        cell = row.createCell(5);
        cell.setCellValue("上传时间");

        //第五步，写入实体数据，实际应用中这些数据从数据库得到,对象封装数据，集合包对象。对象的属性值对应表的每行的值
        Double num=0.0;//累计产值
        for (int i = 0; i < valueList.size(); i++) {
            HSSFRow row1 = sheet.createRow(i + 1);
            ValueOutput user = valueList.get(i);
            //创建单元格设值
            row1.createCell(0).setCellValue(months[user.getMonth()-1]);
            row1.createCell(1).setCellValue(user.getFinished()+"万元");
            if(i==0){
            	num=user.getFinished();
            }else{
            	num+=valueList.get(i).getFinished();
            }
            row1.createCell(2).setCellValue(num+"万元");
            row1.createCell(3).setCellValue(projectPrice+"万元");
            double x=23.5455; 
            NumberFormat ddf1=NumberFormat.getNumberInstance() ; 

            ddf1.setMaximumFractionDigits(3); 
            String s= ddf1.format((num*100)/projectPrice) ;
            row1.createCell(4).setCellValue(s+"%");
            row1.createCell(5).setCellValue(Parameters.getSdf().format(user.getDate()));
        }

        //将文件保存到指定的位置
        try {
            FileOutputStream fos = new FileOutputStream("D://jasobim/tomcat_8080/webapps/valueOutputFiles/valueOutput.xls");
            workbook.write(fos);
            System.out.println("写入成功");
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    	return "/valueOutputFiles/valueOutput.xls";
    }
    
}
