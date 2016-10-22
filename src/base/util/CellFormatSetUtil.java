package base.util;

import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.Pattern;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WriteException;

/**
 * Excel导出生成cell工具类
 * @author zj
 *
 */
public class CellFormatSetUtil {
	
	/**
	 * 标题格式设置
	 * @return  格式
	 */
	public static WritableCellFormat getTitleFirstFormat(){

		WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 17,
				WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
				jxl.format.Colour.WHITE);
		WritableCellFormat wcf_title = new WritableCellFormat(titleFont);
		try {
			wcf_title.setBackground(Colour.TEAL2, Pattern.SOLID);
			wcf_title.setBorder(Border.ALL, BorderLineStyle.DOUBLE,
					Colour.OCEAN_BLUE);
			wcf_title.setVerticalAlignment(VerticalAlignment.CENTRE); // 垂直对齐
			wcf_title.setAlignment(Alignment.CENTRE);
			wcf_title.setLocked(false);
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return wcf_title;
	}
	/**
	 * 表头格式设置1
	 * @return 表头格式
	 */
	public static WritableCellFormat getTableHeadFirstFormat(){

		WritableFont NormalFont = new WritableFont(WritableFont.createFont("黑体"), 11,WritableFont.NO_BOLD);
		WritableCellFormat wcf = new WritableCellFormat(NormalFont);
		try {
			wcf.setBorder(Border.ALL, BorderLineStyle.DASHED,Colour.GREY_40_PERCENT);
			wcf.setVerticalAlignment(VerticalAlignment.CENTRE); // 垂直对齐
			wcf.setAlignment(Alignment.CENTRE);
			wcf.setBackground(Colour.GRAY_25);
			wcf.setLocked(false);
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return wcf;
	}
	   /**
	    * 正文格式设置1	
	    * @return 正文格式
	    */
		public static WritableCellFormat getTextFirstFormat()
		{
			WritableFont NormalFont = new WritableFont(WritableFont.createFont("宋体"), 10,WritableFont.NO_BOLD);
			WritableCellFormat wcf = new WritableCellFormat(NormalFont);
			try {
				wcf.setBorder(Border.ALL, BorderLineStyle.DASHED, Colour.GREY_40_PERCENT);
				//wcf.setVerticalAlignment(VerticalAlignment.CENTRE); // 垂直对齐
				//wcf.setAlignment(Alignment.CENTRE);
				wcf.setLocked(false);
			} catch (WriteException e) {
				e.printStackTrace();
			}
			return wcf;
		}
		
}
