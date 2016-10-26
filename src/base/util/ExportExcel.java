package base.util;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * Excel生成工具类
 * @author zj
 *
 */
public class ExportExcel {

	public static<T> HSSFWorkbook CreateExcel(String sheetName,
			Hashtable<String, String> columnsName, List<Hashtable<String, String>> data) { 
		//创建工作簿
		HSSFWorkbook wb = new HSSFWorkbook();
		if (!columnsName.isEmpty()) {

			createHssfSheet(sheetName, columnsName, data, wb);
		
		}
		return wb;
	}

	public static void createHssfSheet(String sheetName,
			Hashtable<String, String> columnsName,
			List<Hashtable<String, String>> data, HSSFWorkbook wb) {
		// 创建了一个工作簿 并设置sheet名称
		HSSFSheet sheet = wb.createSheet(sheetName);
		// 创建标题列
		HSSFRow titleRow = sheet.createRow(0);
		// 样式
		HSSFCellStyle style = wb.createCellStyle();
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.LIME.index);
 
		
		
		Map<String, String> map = columnsName;
		// 设置其实列
		int cellCount = 0;
		// 创建标题栏
		for (Map.Entry<String, String> item : map.entrySet()) {
			sheet.setColumnWidth(cellCount, 20 * 256);
			HSSFCell t0 = titleRow.createCell(cellCount);
			t0.setCellStyle(style);
			t0.setCellValue(item.getKey());
			//下一个单元格的位置
			cellCount++;
		}
		int rowCount=1;
		for (Hashtable<String, String> table : data) {
			HSSFRow row = sheet.createRow(rowCount);
			cellCount = 0;
			for (Map.Entry<String, String> item : map.entrySet()) { 
				//往excel单元格中填充数据
				row.createCell(cellCount).setCellValue(table.get(item.getValue()));	 
				//下一个单元格的位置
				cellCount++;
			}
			//下一行的位置
			rowCount++; 
		}
	}
}
