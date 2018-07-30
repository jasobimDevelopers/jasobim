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
public class ExportExcel {
	public void getValue(List<WorkSheetDetail> userList,FileOutputStream fout){
		  try{
	            //1.创建工作簿
	            HSSFWorkbook workbook = new HSSFWorkbook();
	            //1.1创建合并单元格对象
	            CellRangeAddress callRangeAddress = new CellRangeAddress(0,0,0,7);//起始行,结束行,起始列,结束列
	            CellRangeAddress callRangeAddress1 = new CellRangeAddress(1,1,0,7);//起始行,结束行,起始列,结束列
	            //班组与时间start
	            CellRangeAddress callRangeAddress20 = new CellRangeAddress(2,2,0,2);//起始行,结束行,起始列,结束列
	            CellRangeAddress callRangeAddress21 = new CellRangeAddress(2,2,3,4);//起始行,结束行,起始列,结束列
	            CellRangeAddress callRangeAddress22 = new CellRangeAddress(2,2,5,7);//起始行,结束行,起始列,结束列
	            //班组与时间end
	            
	            //标题
	            CellRangeAddress callRangeAddress31 = new CellRangeAddress(3,4,0,0);//起始行,结束行,起始列,结束列
	            CellRangeAddress callRangeAddress32 = new CellRangeAddress(3,4,1,1);//起始行,结束行,起始列,结束列
	            CellRangeAddress callRangeAddress33 = new CellRangeAddress(3,4,2,2);//起始行,结束行,起始列,结束列
	            CellRangeAddress callRangeAddress34 = new CellRangeAddress(3,3,3,4);//起始行,结束行,起始列,结束列
	            CellRangeAddress callRangeAddress35 = new CellRangeAddress(3,4,5,5);//起始行,结束行,起始列,结束列
	            CellRangeAddress callRangeAddress36 = new CellRangeAddress(3,4,6,6);//起始行,结束行,起始列,结束列
	            CellRangeAddress callRangeAddress37 = new CellRangeAddress(3,4,7,7);//起始行,结束行,起始列,结束列
	            
	            //金额
	            CellRangeAddress callRangeAddressnumber1 = new CellRangeAddress(userList.size()+5,userList.size()+5,0,2);//起始行,结束行,起始列,结束列
	            CellRangeAddress callRangeAddressnumber2 = new CellRangeAddress(userList.size()+5,userList.size()+5,3,7);//起始行,结束行,起始列,结束列
 
	            //负责人
	            CellRangeAddress callRangeAddressPersion1 = new CellRangeAddress(userList.size()+6,userList.size()+6,0,2);//起始行,结束行,起始列,结束列
	            CellRangeAddress callRangeAddressPersion2 = new CellRangeAddress(userList.size()+6,userList.size()+6,3,4);//起始行,结束行,起始列,结束列
	            CellRangeAddress callRangeAddressPersion3 = new CellRangeAddress(userList.size()+6,userList.size()+6,5,7);//起始行,结束行,起始列,结束列
 
	            //说明
	            CellRangeAddress callRangeAddressinfo = new CellRangeAddress(userList.size()+7,userList.size()+7,0,7);//起始行,结束行,起始列,结束列
	            CellRangeAddress callRangeAddressinfo1 = new CellRangeAddress(userList.size()+8,userList.size()+8,0,7);//起始行,结束行,起始列,结束列
	            CellRangeAddress callRangeAddressinfo2 = new CellRangeAddress(userList.size()+9,userList.size()+9,0,7);//起始行,结束行,起始列,结束列
 
	            //部项目经理部
	            HSSFCellStyle headStyle = createCellStyle(workbook,(short)10,false,true);
	            //派工单
	            HSSFCellStyle erStyle = createCellStyle(workbook,(short)13,true,true);
	            //班组和时间
	            HSSFCellStyle sanStyle = createCellStyle(workbook,(short)10,false,false);
	            //标题样式
	            HSSFCellStyle colStyle = createCellStyle(workbook,(short)10,true,true);
	            //内容样式
	            HSSFCellStyle cellStyle = createCellStyle(workbook,(short)10,false,true);
	            //2.创建工作表
	            HSSFSheet sheet = workbook.createSheet("派单");
	            //2.1加载合并单元格对象
	            sheet.addMergedRegion(callRangeAddress);
	            sheet.addMergedRegion(callRangeAddress1);
	            sheet.addMergedRegion(callRangeAddress20);
	            sheet.addMergedRegion(callRangeAddress21);
	            sheet.addMergedRegion(callRangeAddress22);
	            sheet.addMergedRegion(callRangeAddress31);
	            sheet.addMergedRegion(callRangeAddress32);
	            sheet.addMergedRegion(callRangeAddress33);
	            sheet.addMergedRegion(callRangeAddress34);
	            sheet.addMergedRegion(callRangeAddress35);
	            sheet.addMergedRegion(callRangeAddress36);
	            sheet.addMergedRegion(callRangeAddress37);
	            sheet.addMergedRegion(callRangeAddressnumber1);
	            sheet.addMergedRegion(callRangeAddressnumber2);
	            sheet.addMergedRegion(callRangeAddressPersion1);
	            sheet.addMergedRegion(callRangeAddressPersion2);
	            sheet.addMergedRegion(callRangeAddressPersion3);
	            sheet.addMergedRegion(callRangeAddressinfo);
	            sheet.addMergedRegion(callRangeAddressinfo1);
	            sheet.addMergedRegion(callRangeAddressinfo2);
	            //设置默认列宽
	            sheet.setDefaultColumnWidth(15);
	            //3.创建行
	            //3.1创建头标题行;并且设置头标题
	            HSSFRow row = sheet.createRow(0);
	            HSSFCell cell = row.createCell(0);
	            //加载单元格样式
	            cell.setCellStyle(headStyle);
	            cell.setCellValue("xxxx项目部");
	            
	            HSSFRow rower = sheet.createRow(1);
	            HSSFCell celler = rower.createCell(0);
	            //加载单元格样式
	            celler.setCellStyle(erStyle);
	            celler.setCellValue("派 工 单");
	            
	            HSSFRow rowsan = sheet.createRow(2);
	            HSSFCell cellsan = rowsan.createCell(0);
	            HSSFCell cellsan1 = rowsan.createCell(3);
	            HSSFCell cellsan2 = rowsan.createCell(5);
	            //加载单元格样式
	            cellsan.setCellStyle(sanStyle);
	            cellsan.setCellValue("协作单位：x施工一堆");
	            cellsan1.setCellStyle(sanStyle);
	            cellsan1.setCellValue("");
	            cellsan2.setCellStyle(sanStyle);
	            cellsan2.setCellValue("时间：2017年 10月 20日");
	            
	            //3.2创建列标题;并且设置列标题
	            HSSFRow row2 = sheet.createRow(3);
	            String[] titles = {"序号","工作内容","用工总人数","工日数","","单价（元）","金额(元）","备注"};//""为占位字符串
	            for(int i=0;i<titles.length;i++)
	            {
	                HSSFCell cell2 = row2.createCell(i);
	                //加载单元格样式
	                cell2.setCellStyle(colStyle);
	                cell2.setCellValue(titles[i]);
	            }
	            
	            HSSFRow rowfour = sheet.createRow(4);
	            String[] titlefour = {"普工用工数","技工用工数"};
	            for(int i=0;i<titlefour.length;i++)
	            {
	                HSSFCell cell2 = rowfour.createCell(i+3);
	                //加载单元格样式
	                cell2.setCellStyle(colStyle);
	                cell2.setCellValue(titlefour[i]);
	            }
	            
	            
	            //4.操作单元格;将用户列表写入excel
	            if(userList != null)
	            {
	            	int i=1;
	                for(int j=0;j<userList.size();j++)
	                {
	                    //创建数据行,前面有两行,头标题行和列标题行
	                    HSSFRow row3 = sheet.createRow(j+5);
	                    HSSFCell cell0 = row3.createCell(0);
	                    cell0.setCellStyle(cellStyle);
	                    cell0.setCellValue(i++);
	                    
	                    HSSFCell cell1 = row3.createCell(1);
	                    cell1.setCellStyle(cellStyle);
	                    cell1.setCellValue(userList.get(j).getWorkCtx());
	                    
	                    HSSFCell cell2 = row3.createCell(2);
	                    cell2.setCellStyle(cellStyle);
	                    cell2.setCellValue(userList.get(j).getTotalHumanDays());
	                    
	                    HSSFCell cell3 = row3.createCell(3);
	                    cell3.setCellStyle(cellStyle);
	                    cell3.setCellValue(userList.get(j).getGwnNum());
	                    
	                    HSSFCell cell4 = row3.createCell(4);
	                    cell4.setCellStyle(cellStyle);
	                    cell4.setCellValue(userList.get(j).getTmnNum());
	                    
	                    HSSFCell cell5 = row3.createCell(5);
	                    cell5.setCellStyle(cellStyle);
	                    cell5.setCellValue(userList.get(j).getTotalHumanDays());
	                    
	                    HSSFCell cell6 = row3.createCell(6);
	                    cell6.setCellStyle(cellStyle);
	                    cell6.setCellValue(userList.get(j).getUnitAmount());
	                    
	                    HSSFCell cell7= row3.createCell(7);
	                    cell7.setCellStyle(cellStyle);
	                    cell7.setCellValue(userList.get(j).getUnitPrice());
	                }
	            }
	            
	            HSSFRow rownumber = sheet.createRow(userList.size()+5);
	            HSSFCell cellnumber = rownumber.createCell(0);
	            HSSFCell cellnumber1 = rownumber.createCell(3);
	            //加载单元格样式
	            cellnumber.setCellStyle(sanStyle);
	            cellnumber.setCellValue("金额合计(大写)");
	            cellnumber1.setCellStyle(sanStyle);
	            cellnumber1.setCellValue("￥ 78 元； 大写：柒拾捌元整");
 
	            HSSFRow rowpersion = sheet.createRow(userList.size()+6);
	            HSSFCell cellpersion = rowpersion.createCell(0);
	            HSSFCell cellpersion1 = rowpersion.createCell(3);
	            HSSFCell cellpersion2 = rowpersion.createCell(5);
 
	            //加载单元格样式
	            cellpersion.setCellStyle(sanStyle);
	            cellpersion.setCellValue("协作单位负责人：");
	            cellpersion1.setCellStyle(sanStyle);
	            cellpersion1.setCellValue("经办人：");
	            cellpersion2.setCellStyle(sanStyle);
	            cellpersion2.setCellValue("部门负责人：");
	            
	            HSSFRow rowinfo = sheet.createRow(userList.size()+7);
	            HSSFCell cellinfo = rowinfo.createCell(0);
	            cellinfo.setCellStyle(sanStyle);
	            cellinfo.setCellValue("说明：1、本标工单一式两联，第一联为派工人（工长）存根，第二联用作结算。");
	            
	            HSSFRow rowinfo1 = sheet.createRow(userList.size()+8);
	            HSSFCell cellinfo1 = rowinfo1.createCell(0);
	            cellinfo1.setCellStyle(sanStyle);
	            cellinfo1.setCellValue("2、本标工单必须在用工当日签认，否则不予认可；三日内交合同处汇总。");
	            
	            HSSFRow rowinfo2 = sheet.createRow(userList.size()+9);
	            HSSFCell cellinfo2 = rowinfo2.createCell(0);
	            cellinfo2.setCellStyle(sanStyle);
	            cellinfo2.setCellValue("3、工日数填写精确到半个工日。");
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